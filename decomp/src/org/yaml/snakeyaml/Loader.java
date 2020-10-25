/*     */ package org.yaml.snakeyaml;
/*     */ 
/*     */ import java.io.Reader;
/*     */ import java.util.Iterator;
/*     */ import org.yaml.snakeyaml.composer.Composer;
/*     */ import org.yaml.snakeyaml.constructor.BaseConstructor;
/*     */ import org.yaml.snakeyaml.constructor.Constructor;
/*     */ import org.yaml.snakeyaml.error.YAMLException;
/*     */ import org.yaml.snakeyaml.events.Event;
/*     */ import org.yaml.snakeyaml.introspector.BeanAccess;
/*     */ import org.yaml.snakeyaml.nodes.Node;
/*     */ import org.yaml.snakeyaml.parser.Parser;
/*     */ import org.yaml.snakeyaml.parser.ParserImpl;
/*     */ import org.yaml.snakeyaml.reader.StreamReader;
/*     */ import org.yaml.snakeyaml.resolver.Resolver;
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
/*     */ public class Loader
/*     */ {
/*     */   protected final BaseConstructor constructor;
/*     */   protected Resolver resolver;
/*     */   private boolean attached;
/*     */   
/*     */   public Loader(BaseConstructor constructor) {
/*  40 */     this.attached = false;
/*     */ 
/*     */ 
/*     */     
/*  44 */     this.constructor = constructor;
/*     */   }
/*     */ 
/*     */   
/*  48 */   public Loader() { this(new Constructor()); }
/*     */ 
/*     */ 
/*     */   
/*  52 */   public void setBeanAccess(BeanAccess beanAccess) { this.constructor.getPropertyUtils().setBeanAccess(beanAccess); }
/*     */ 
/*     */   
/*     */   public Object load(Reader io) {
/*  56 */     Composer composer = new Composer(new ParserImpl(new StreamReader(io)), this.resolver);
/*  57 */     this.constructor.setComposer(composer);
/*  58 */     return this.constructor.getSingleData();
/*     */   }
/*     */   
/*     */   public Iterable<Object> loadAll(Reader yaml) {
/*  62 */     Composer composer = new Composer(new ParserImpl(new StreamReader(yaml)), this.resolver);
/*  63 */     this.constructor.setComposer(composer);
/*  64 */     Iterator<Object> result = new Iterator<Object>()
/*     */       {
/*  66 */         public boolean hasNext() { return Loader.this.constructor.checkData(); }
/*     */ 
/*     */ 
/*     */         
/*  70 */         public Object next() { return Loader.this.constructor.getData(); }
/*     */ 
/*     */         
/*     */         public void remove() {
/*  74 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*  77 */     return new YamlIterable(result);
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
/*     */   public Node compose(Reader yaml) {
/*  89 */     Composer composer = new Composer(new ParserImpl(new StreamReader(yaml)), this.resolver);
/*  90 */     this.constructor.setComposer(composer);
/*  91 */     return composer.getSingleNode();
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
/* 103 */     final Composer composer = new Composer(new ParserImpl(new StreamReader(yaml)), this.resolver);
/* 104 */     this.constructor.setComposer(composer);
/* 105 */     Iterator<Node> result = new Iterator<Node>()
/*     */       {
/* 107 */         public boolean hasNext() { return composer.checkNode(); }
/*     */ 
/*     */ 
/*     */         
/* 111 */         public Node next() { return composer.getNode(); }
/*     */ 
/*     */         
/*     */         public void remove() {
/* 115 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/* 118 */     return new NodeIterable(result);
/*     */   }
/*     */   
/*     */   private class NodeIterable
/*     */     extends Object implements Iterable<Node> {
/*     */     private Iterator<Node> iterator;
/*     */     
/* 125 */     public NodeIterable(Iterator<Node> iterator) { this.iterator = iterator; }
/*     */ 
/*     */ 
/*     */     
/* 129 */     public Iterator<Node> iterator() { return this.iterator; }
/*     */   }
/*     */   
/*     */   private class YamlIterable
/*     */     extends Object
/*     */     implements Iterable<Object>
/*     */   {
/*     */     private Iterator<Object> iterator;
/*     */     
/* 138 */     public YamlIterable(Iterator<Object> iterator) { this.iterator = iterator; }
/*     */ 
/*     */ 
/*     */     
/* 142 */     public Iterator<Object> iterator() { return this.iterator; }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 148 */   public void setResolver(Resolver resolver) { this.resolver = resolver; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setAttached() {
/* 155 */     if (!this.attached) {
/* 156 */       this.attached = true;
/*     */     } else {
/* 158 */       throw new YAMLException("Loader cannot be shared.");
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
/*     */   public Iterable<Event> parse(Reader yaml) {
/* 170 */     final ParserImpl parser = new ParserImpl(new StreamReader(yaml));
/* 171 */     Iterator<Event> result = new Iterator<Event>()
/*     */       {
/* 173 */         public boolean hasNext() { return (parser.peekEvent() != null); }
/*     */ 
/*     */ 
/*     */         
/* 177 */         public Event next() { return parser.getEvent(); }
/*     */ 
/*     */         
/*     */         public void remove() {
/* 181 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/* 184 */     return new EventIterable(result);
/*     */   }
/*     */   
/*     */   private class EventIterable
/*     */     extends Object implements Iterable<Event> {
/*     */     private Iterator<Event> iterator;
/*     */     
/* 191 */     public EventIterable(Iterator<Event> iterator) { this.iterator = iterator; }
/*     */ 
/*     */ 
/*     */     
/* 195 */     public Iterator<Event> iterator() { return this.iterator; }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\Loader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */