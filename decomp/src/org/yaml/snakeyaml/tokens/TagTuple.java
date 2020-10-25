/*    */ package org.yaml.snakeyaml.tokens;
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
/*    */ public final class TagTuple
/*    */ {
/*    */   private final String handle;
/*    */   private final String suffix;
/*    */   
/*    */   public TagTuple(String handle, String suffix) {
/* 24 */     if (suffix == null) {
/* 25 */       throw new NullPointerException("Suffix must be provided.");
/*    */     }
/* 27 */     this.handle = handle;
/* 28 */     this.suffix = suffix;
/*    */   }
/*    */ 
/*    */   
/* 32 */   public String getHandle() { return this.handle; }
/*    */ 
/*    */ 
/*    */   
/* 36 */   public String getSuffix() { return this.suffix; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\tokens\TagTuple.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */