/*     */ package org.yaml.snakeyaml;
/*     */ 
/*     */ import java.io.StringWriter;
/*     */ import java.io.Writer;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.yaml.snakeyaml.introspector.BeanAccess;
/*     */ import org.yaml.snakeyaml.nodes.Tag;
/*     */ import org.yaml.snakeyaml.representer.Representer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JavaBeanDumper
/*     */ {
/*     */   private boolean useGlobalTag;
/*     */   private DumperOptions.FlowStyle flowStyle;
/*     */   private DumperOptions options;
/*     */   private Representer representer;
/*     */   private Set<Class<? extends Object>> classTags;
/*     */   private final BeanAccess beanAccess;
/*     */   
/*     */   public JavaBeanDumper(boolean useGlobalTag, BeanAccess beanAccess) {
/*  47 */     this.useGlobalTag = useGlobalTag;
/*  48 */     this.beanAccess = beanAccess;
/*  49 */     this.flowStyle = DumperOptions.FlowStyle.BLOCK;
/*  50 */     this.classTags = new HashSet();
/*     */   }
/*     */ 
/*     */   
/*  54 */   public JavaBeanDumper(boolean useGlobalTag) { this(useGlobalTag, BeanAccess.DEFAULT); }
/*     */ 
/*     */ 
/*     */   
/*  58 */   public JavaBeanDumper(BeanAccess beanAccess) { this(false, beanAccess); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   public JavaBeanDumper() { this(BeanAccess.DEFAULT); }
/*     */ 
/*     */   
/*     */   public JavaBeanDumper(Representer representer, DumperOptions options) {
/*  69 */     if (representer == null) {
/*  70 */       throw new NullPointerException("Representer must be provided.");
/*     */     }
/*  72 */     if (options == null) {
/*  73 */       throw new NullPointerException("DumperOptions must be provided.");
/*     */     }
/*  75 */     this.options = options;
/*  76 */     this.representer = representer;
/*  77 */     this.beanAccess = null;
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
/*     */     Representer repr;
/*     */     DumperOptions doptions;
/*  91 */     if (this.options == null) {
/*  92 */       doptions = new DumperOptions();
/*  93 */       if (!this.useGlobalTag) {
/*  94 */         doptions.setExplicitRoot(Tag.MAP);
/*     */       }
/*  96 */       doptions.setDefaultFlowStyle(this.flowStyle);
/*     */     } else {
/*  98 */       doptions = this.options;
/*     */     } 
/*     */     
/* 101 */     if (this.representer == null) {
/* 102 */       repr = new Representer();
/* 103 */       repr.getPropertyUtils().setBeanAccess(this.beanAccess);
/* 104 */       for (Class<? extends Object> clazz : this.classTags) {
/* 105 */         repr.addClassTag(clazz, Tag.MAP);
/*     */       }
/*     */     } else {
/* 108 */       repr = this.representer;
/*     */     } 
/* 110 */     Yaml dumper = new Yaml(repr, doptions);
/* 111 */     dumper.dump(data, output);
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
/* 122 */     StringWriter buffer = new StringWriter();
/* 123 */     dump(data, buffer);
/* 124 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */   
/* 128 */   public boolean isUseGlobalTag() { return this.useGlobalTag; }
/*     */ 
/*     */ 
/*     */   
/* 132 */   public void setUseGlobalTag(boolean useGlobalTag) { this.useGlobalTag = useGlobalTag; }
/*     */ 
/*     */ 
/*     */   
/* 136 */   public DumperOptions.FlowStyle getFlowStyle() { return this.flowStyle; }
/*     */ 
/*     */ 
/*     */   
/* 140 */   public void setFlowStyle(DumperOptions.FlowStyle flowStyle) { this.flowStyle = flowStyle; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 150 */   public void setMapTagForBean(Class<? extends Object> clazz) { this.classTags.add(clazz); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\JavaBeanDumper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */