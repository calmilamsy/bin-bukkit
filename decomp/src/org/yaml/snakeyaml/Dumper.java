/*    */ package org.yaml.snakeyaml;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ import java.util.Iterator;
/*    */ import org.yaml.snakeyaml.emitter.Emitter;
/*    */ import org.yaml.snakeyaml.error.YAMLException;
/*    */ import org.yaml.snakeyaml.representer.Representer;
/*    */ import org.yaml.snakeyaml.resolver.Resolver;
/*    */ import org.yaml.snakeyaml.serializer.Serializer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Dumper
/*    */ {
/*    */   protected final Representer representer;
/*    */   protected final DumperOptions options;
/*    */   private boolean attached;
/*    */   
/*    */   public Dumper(Representer representer, DumperOptions options) {
/* 34 */     this.attached = false;
/*    */ 
/*    */     
/* 37 */     this.representer = representer;
/* 38 */     representer.setDefaultFlowStyle(options.getDefaultFlowStyle());
/* 39 */     representer.setDefaultScalarStyle(options.getDefaultScalarStyle());
/* 40 */     representer.getPropertyUtils().setAllowReadOnlyProperties(options.isAllowReadOnlyProperties());
/*    */     
/* 42 */     this.options = options;
/*    */   }
/*    */ 
/*    */   
/* 46 */   public Dumper(DumperOptions options) { this(new Representer(), options); }
/*    */ 
/*    */ 
/*    */   
/* 50 */   public Dumper(Representer representer) { this(representer, new DumperOptions()); }
/*    */ 
/*    */ 
/*    */   
/* 54 */   public Dumper() { this(new Representer(), new DumperOptions()); }
/*    */ 
/*    */   
/*    */   public void dump(Iterator<? extends Object> iter, Writer output, Resolver resolver) {
/* 58 */     Serializer s = new Serializer(new Emitter(output, this.options), resolver, this.options);
/*    */     try {
/* 60 */       s.open();
/* 61 */       while (iter.hasNext()) {
/* 62 */         this.representer.represent(s, iter.next());
/*    */       }
/* 64 */       s.close();
/* 65 */     } catch (IOException e) {
/* 66 */       throw new YAMLException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void setAttached() {
/* 74 */     if (!this.attached) {
/* 75 */       this.attached = true;
/*    */     } else {
/* 77 */       throw new YAMLException("Dumper cannot be shared.");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\Dumper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */