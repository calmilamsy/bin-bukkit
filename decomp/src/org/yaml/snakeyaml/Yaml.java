/*     */ package org.yaml.snakeyaml;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.io.StringWriter;
/*     */ import java.io.Writer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.regex.Pattern;
/*     */ import org.yaml.snakeyaml.composer.Composer;
/*     */ import org.yaml.snakeyaml.constructor.BaseConstructor;
/*     */ import org.yaml.snakeyaml.constructor.Constructor;
/*     */ import org.yaml.snakeyaml.emitter.Emitter;
/*     */ import org.yaml.snakeyaml.error.YAMLException;
/*     */ import org.yaml.snakeyaml.events.Event;
/*     */ import org.yaml.snakeyaml.introspector.BeanAccess;
/*     */ import org.yaml.snakeyaml.nodes.Node;
/*     */ import org.yaml.snakeyaml.nodes.Tag;
/*     */ import org.yaml.snakeyaml.parser.Parser;
/*     */ import org.yaml.snakeyaml.parser.ParserImpl;
/*     */ import org.yaml.snakeyaml.reader.StreamReader;
/*     */ import org.yaml.snakeyaml.reader.UnicodeReader;
/*     */ import org.yaml.snakeyaml.representer.Representer;
/*     */ import org.yaml.snakeyaml.resolver.Resolver;
/*     */ import org.yaml.snakeyaml.serializer.Serializer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Yaml
/*     */ {
/*     */   protected final Resolver resolver;
/*     */   private String name;
/*     */   protected BaseConstructor constructor;
/*     */   protected Representer representer;
/*     */   protected DumperOptions options;
/*     */   
/*  61 */   public Yaml() { this(new Constructor(), new Representer(), new DumperOptions(), new Resolver()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   public Yaml(DumperOptions options) { this(new Constructor(), new Representer(), options); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   public Yaml(Representer representer) { this(new Constructor(), representer); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   public Yaml(BaseConstructor constructor) { this(constructor, new Representer()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   public Yaml(BaseConstructor constructor, Representer representer) { this(constructor, representer, new DumperOptions()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 119 */   public Yaml(Representer representer, DumperOptions options) { this(new Constructor(), representer, options, new Resolver()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 134 */   public Yaml(BaseConstructor constructor, Representer representer, DumperOptions options) { this(constructor, representer, options, new Resolver()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Yaml(BaseConstructor constructor, Representer representer, DumperOptions options, Resolver resolver) {
/* 152 */     if (!constructor.isExplicitPropertyUtils()) {
/* 153 */       constructor.setPropertyUtils(representer.getPropertyUtils());
/* 154 */     } else if (!representer.isExplicitPropertyUtils()) {
/* 155 */       representer.setPropertyUtils(constructor.getPropertyUtils());
/*     */     } 
/* 157 */     this.constructor = constructor;
/* 158 */     representer.setDefaultFlowStyle(options.getDefaultFlowStyle());
/* 159 */     representer.setDefaultScalarStyle(options.getDefaultScalarStyle());
/* 160 */     representer.getPropertyUtils().setAllowReadOnlyProperties(options.isAllowReadOnlyProperties());
/*     */     
/* 162 */     this.representer = representer;
/* 163 */     this.options = options;
/* 164 */     this.resolver = resolver;
/* 165 */     this.name = "Yaml:" + System.identityHashCode(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String dump(Object data) {
/* 176 */     List<Object> list = new ArrayList<Object>(true);
/* 177 */     list.add(data);
/* 178 */     return dumpAll(list.iterator());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String dumpAll(Iterator<? extends Object> data) {
/* 189 */     StringWriter buffer = new StringWriter();
/* 190 */     dumpAll(data, buffer);
/* 191 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dump(Object data, Writer output) {
/* 203 */     List<Object> list = new ArrayList<Object>(true);
/* 204 */     list.add(data);
/* 205 */     dumpAll(list.iterator(), output);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dumpAll(Iterator<? extends Object> data, Writer output) {
/* 217 */     Serializer s = new Serializer(new Emitter(output, this.options), this.resolver, this.options);
/*     */     try {
/* 219 */       s.open();
/* 220 */       while (data.hasNext()) {
/* 221 */         this.representer.represent(s, data.next());
/*     */       }
/* 223 */       s.close();
/* 224 */     } catch (IOException e) {
/* 225 */       throw new YAMLException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 238 */   public Object load(String yaml) { return load(new StringReader(yaml)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 250 */   public Object load(InputStream io) { return load(new UnicodeReader(io)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object load(Reader io) {
/* 262 */     Composer composer = new Composer(new ParserImpl(new StreamReader(io)), this.resolver);
/* 263 */     this.constructor.setComposer(composer);
/* 264 */     return this.constructor.getSingleData();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterable<Object> loadAll(Reader yaml) {
/* 277 */     Composer composer = new Composer(new ParserImpl(new StreamReader(yaml)), this.resolver);
/* 278 */     this.constructor.setComposer(composer);
/* 279 */     Iterator<Object> result = new Iterator<Object>()
/*     */       {
/* 281 */         public boolean hasNext() { return Yaml.this.constructor.checkData(); }
/*     */ 
/*     */ 
/*     */         
/* 285 */         public Object next() { return Yaml.this.constructor.getData(); }
/*     */ 
/*     */         
/*     */         public void remove() {
/* 289 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/* 292 */     return new YamlIterable(result);
/*     */   }
/*     */   
/*     */   private class YamlIterable
/*     */     extends Object implements Iterable<Object> {
/*     */     private Iterator<Object> iterator;
/*     */     
/* 299 */     public YamlIterable(Iterator<Object> iterator) { this.iterator = iterator; }
/*     */ 
/*     */ 
/*     */     
/* 303 */     public Iterator<Object> iterator() { return this.iterator; }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 318 */   public Iterable<Object> loadAll(String yaml) { return loadAll(new StringReader(yaml)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 331 */   public Iterable<Object> loadAll(InputStream yaml) { return loadAll(new UnicodeReader(yaml)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node compose(Reader yaml) {
/* 343 */     Composer composer = new Composer(new ParserImpl(new StreamReader(yaml)), this.resolver);
/* 344 */     this.constructor.setComposer(composer);
/* 345 */     return composer.getSingleNode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterable<Node> composeAll(Reader yaml) {
/* 357 */     final Composer composer = new Composer(new ParserImpl(new StreamReader(yaml)), this.resolver);
/* 358 */     this.constructor.setComposer(composer);
/* 359 */     Iterator<Node> result = new Iterator<Node>()
/*     */       {
/* 361 */         public boolean hasNext() { return composer.checkNode(); }
/*     */ 
/*     */ 
/*     */         
/* 365 */         public Node next() { return composer.getNode(); }
/*     */ 
/*     */         
/*     */         public void remove() {
/* 369 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/* 372 */     return new NodeIterable(result);
/*     */   }
/*     */   
/*     */   private class NodeIterable
/*     */     extends Object implements Iterable<Node> {
/*     */     private Iterator<Node> iterator;
/*     */     
/* 379 */     public NodeIterable(Iterator<Node> iterator) { this.iterator = iterator; }
/*     */ 
/*     */ 
/*     */     
/* 383 */     public Iterator<Node> iterator() { return this.iterator; }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 402 */   public void addImplicitResolver(String tag, Pattern regexp, String first) { addImplicitResolver(new Tag(tag), regexp, first); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 418 */   public void addImplicitResolver(Tag tag, Pattern regexp, String first) { this.resolver.addImplicitResolver(tag, regexp, first); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 423 */   public String toString() { return this.name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 434 */   public String getName() { return this.name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 444 */   public void setName(String name) { this.name = name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterable<Event> parse(Reader yaml) {
/* 455 */     final ParserImpl parser = new ParserImpl(new StreamReader(yaml));
/* 456 */     Iterator<Event> result = new Iterator<Event>()
/*     */       {
/* 458 */         public boolean hasNext() { return (parser.peekEvent() != null); }
/*     */ 
/*     */ 
/*     */         
/* 462 */         public Event next() { return parser.getEvent(); }
/*     */ 
/*     */         
/*     */         public void remove() {
/* 466 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/* 469 */     return new EventIterable(result);
/*     */   }
/*     */   
/*     */   private class EventIterable
/*     */     extends Object implements Iterable<Event> {
/*     */     private Iterator<Event> iterator;
/*     */     
/* 476 */     public EventIterable(Iterator<Event> iterator) { this.iterator = iterator; }
/*     */ 
/*     */ 
/*     */     
/* 480 */     public Iterator<Event> iterator() { return this.iterator; }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBeanAccess(BeanAccess beanAccess) {
/* 485 */     this.constructor.getPropertyUtils().setBeanAccess(beanAccess);
/* 486 */     this.representer.getPropertyUtils().setBeanAccess(beanAccess);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 494 */   public Yaml(Loader loader) { this(loader, new Dumper(new DumperOptions())); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 501 */   public Yaml(Loader loader, Dumper dumper) { this(loader, dumper, new Resolver()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 508 */   public Yaml(Loader loader, Dumper dumper, Resolver resolver) { this(loader.constructor, dumper.representer, dumper.options, resolver); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 520 */   public Yaml(Dumper dumper) { this(new Constructor(), dumper.representer, dumper.options); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\Yaml.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */