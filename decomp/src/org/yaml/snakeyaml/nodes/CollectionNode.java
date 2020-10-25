/*    */ package org.yaml.snakeyaml.nodes;
/*    */ 
/*    */ import org.yaml.snakeyaml.error.Mark;
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
/*    */ public abstract class CollectionNode
/*    */   extends Node
/*    */ {
/*    */   private Boolean flowStyle;
/*    */   
/*    */   public CollectionNode(Tag tag, Mark startMark, Mark endMark, Boolean flowStyle) {
/* 29 */     super(tag, startMark, endMark);
/* 30 */     this.flowStyle = flowStyle;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 40 */   public Boolean getFlowStyle() { return this.flowStyle; }
/*    */ 
/*    */ 
/*    */   
/* 44 */   public void setFlowStyle(Boolean flowStyle) { this.flowStyle = flowStyle; }
/*    */ 
/*    */ 
/*    */   
/* 48 */   public void setEndMark(Mark endMark) { this.endMark = endMark; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\nodes\CollectionNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */