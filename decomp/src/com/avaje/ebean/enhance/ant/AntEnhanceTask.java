/*     */ package com.avaje.ebean.enhance.ant;
/*     */ 
/*     */ import com.avaje.ebean.enhance.agent.Transformer;
/*     */ import java.io.File;
/*     */ import org.apache.tools.ant.Task;
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
/*     */ public class AntEnhanceTask
/*     */   extends Task
/*     */ {
/*     */   String classpath;
/*     */   String classSource;
/*     */   String classDestination;
/*     */   String transformArgs;
/*     */   String packages;
/*     */   
/*     */   public void execute() {
/*  59 */     File f = new File("");
/*  60 */     System.out.println("Current Directory: " + f.getAbsolutePath());
/*     */     
/*  62 */     StringBuilder extraClassPath = new StringBuilder();
/*  63 */     extraClassPath.append(this.classSource);
/*  64 */     if (this.classpath != null) {
/*     */       
/*  66 */       if (!extraClassPath.toString().endsWith(";"))
/*     */       {
/*  68 */         extraClassPath.append(";");
/*     */       }
/*  70 */       extraClassPath.append(this.classpath);
/*     */     } 
/*  72 */     Transformer t = new Transformer(extraClassPath.toString(), this.transformArgs);
/*     */     
/*  74 */     ClassLoader cl = AntEnhanceTask.class.getClassLoader();
/*  75 */     OfflineFileTransform ft = new OfflineFileTransform(t, cl, this.classSource, this.classDestination);
/*     */     
/*  77 */     ft.process(this.packages);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   public String getClasspath() { return this.classpath; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   public void setClasspath(String classpath) { this.classpath = classpath; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   public void setClassSource(String source) { this.classSource = source; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   public void setClassDestination(String destination) { this.classDestination = destination; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   public void setTransformArgs(String transformArgs) { this.transformArgs = transformArgs; }
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
/* 126 */   public void setPackages(String packages) { this.packages = packages; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\ant\AntEnhanceTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */