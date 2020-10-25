/*    */ package org.yaml.snakeyaml.error;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MarkedYAMLException
/*    */   extends YAMLException
/*    */ {
/*    */   private static final long serialVersionUID = -9119388488683035101L;
/*    */   private String context;
/*    */   private Mark contextMark;
/*    */   private String problem;
/*    */   private Mark problemMark;
/*    */   private String note;
/*    */   
/* 33 */   protected MarkedYAMLException(String context, Mark contextMark, String problem, Mark problemMark, String note) { this(context, contextMark, problem, problemMark, note, null); }
/*    */ 
/*    */ 
/*    */   
/*    */   protected MarkedYAMLException(String context, Mark contextMark, String problem, Mark problemMark, String note, Throwable cause) {
/* 38 */     super(context + "; " + problem, cause);
/* 39 */     this.context = context;
/* 40 */     this.contextMark = contextMark;
/* 41 */     this.problem = problem;
/* 42 */     this.problemMark = problemMark;
/* 43 */     this.note = note;
/*    */   }
/*    */ 
/*    */   
/* 47 */   protected MarkedYAMLException(String context, Mark contextMark, String problem, Mark problemMark) { this(context, contextMark, problem, problemMark, null, null); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 52 */   protected MarkedYAMLException(String context, Mark contextMark, String problem, Mark problemMark, Throwable cause) { this(context, contextMark, problem, problemMark, null, cause); }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 57 */     StringBuilder lines = new StringBuilder();
/* 58 */     if (this.context != null) {
/* 59 */       lines.append(this.context);
/* 60 */       lines.append("\n");
/*    */     } 
/* 62 */     if (this.contextMark != null && (this.problem == null || this.problemMark == null || this.contextMark.getName() != this.problemMark.getName() || this.contextMark.getLine() != this.problemMark.getLine() || this.contextMark.getColumn() != this.problemMark.getColumn())) {
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 67 */       lines.append(this.contextMark.toString());
/* 68 */       lines.append("\n");
/*    */     } 
/* 70 */     if (this.problem != null) {
/* 71 */       lines.append(this.problem);
/* 72 */       lines.append("\n");
/*    */     } 
/* 74 */     if (this.problemMark != null) {
/* 75 */       lines.append(this.problemMark.toString());
/* 76 */       lines.append("\n");
/*    */     } 
/* 78 */     if (this.note != null) {
/* 79 */       lines.append(this.note);
/* 80 */       lines.append("\n");
/*    */     } 
/* 82 */     return lines.toString();
/*    */   }
/*    */ 
/*    */   
/* 86 */   public String getContext() { return this.context; }
/*    */ 
/*    */ 
/*    */   
/* 90 */   public Mark getContextMark() { return this.contextMark; }
/*    */ 
/*    */ 
/*    */   
/* 94 */   public String getProblem() { return this.problem; }
/*    */ 
/*    */ 
/*    */   
/* 98 */   public Mark getProblemMark() { return this.problemMark; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\error\MarkedYAMLException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */