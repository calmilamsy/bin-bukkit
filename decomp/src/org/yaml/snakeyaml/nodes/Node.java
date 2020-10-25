/*     */ package org.yaml.snakeyaml.nodes;
/*     */ 
/*     */ import org.yaml.snakeyaml.error.Mark;
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
/*     */ public abstract class Node
/*     */ {
/*     */   private Tag tag;
/*     */   private Mark startMark;
/*     */   protected Mark endMark;
/*     */   private Class<? extends Object> type;
/*     */   private boolean twoStepsConstruction;
/*     */   protected boolean resolved;
/*     */   protected Boolean useClassConstructor;
/*     */   
/*     */   public Node(Tag tag, Mark startMark, Mark endMark) {
/*  47 */     setTag(tag);
/*  48 */     this.startMark = startMark;
/*  49 */     this.endMark = endMark;
/*  50 */     this.type = Object.class;
/*  51 */     this.twoStepsConstruction = false;
/*  52 */     this.resolved = true;
/*  53 */     this.useClassConstructor = null;
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
/*  64 */   public Tag getTag() { return this.tag; }
/*     */ 
/*     */ 
/*     */   
/*  68 */   public Mark getEndMark() { return this.endMark; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract NodeId getNodeId();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public Mark getStartMark() { return this.startMark; }
/*     */ 
/*     */   
/*     */   public void setTag(Tag tag) {
/*  84 */     if (tag == null) {
/*  85 */       throw new NullPointerException("tag in a Node is required.");
/*     */     }
/*  87 */     this.tag = tag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   public final boolean equals(Object obj) { return super.equals(obj); }
/*     */ 
/*     */ 
/*     */   
/*  99 */   public Class<? extends Object> getType() { return this.type; }
/*     */ 
/*     */ 
/*     */   
/* 103 */   public void setType(Class<? extends Object> type) { this.type = type; }
/*     */ 
/*     */ 
/*     */   
/* 107 */   public void setTwoStepsConstruction(boolean twoStepsConstruction) { this.twoStepsConstruction = twoStepsConstruction; }
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
/* 128 */   public boolean isTwoStepsConstruction() { return this.twoStepsConstruction; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 133 */   public final int hashCode() { return super.hashCode(); }
/*     */ 
/*     */   
/*     */   public boolean useClassConstructor() {
/* 137 */     if (this.useClassConstructor == null) {
/* 138 */       if (isResolved() && !Object.class.equals(this.type) && !this.tag.equals(Tag.NULL))
/* 139 */         return true; 
/* 140 */       if (this.tag.isCompatible(getType()))
/*     */       {
/*     */         
/* 143 */         return true;
/*     */       }
/* 145 */       return false;
/*     */     } 
/*     */     
/* 148 */     return this.useClassConstructor.booleanValue();
/*     */   }
/*     */ 
/*     */   
/* 152 */   public void setUseClassConstructor(Boolean useClassConstructor) { this.useClassConstructor = useClassConstructor; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 162 */   public boolean isResolved() { return this.resolved; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\nodes\Node.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */