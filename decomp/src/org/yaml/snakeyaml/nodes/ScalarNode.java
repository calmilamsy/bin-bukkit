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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScalarNode
/*    */   extends Node
/*    */ {
/*    */   private Character style;
/*    */   private String value;
/*    */   
/* 32 */   public ScalarNode(Tag tag, String value, Mark startMark, Mark endMark, Character style) { this(tag, true, value, startMark, endMark, style); }
/*    */ 
/*    */ 
/*    */   
/*    */   public ScalarNode(Tag tag, boolean resolved, String value, Mark startMark, Mark endMark, Character style) {
/* 37 */     super(tag, startMark, endMark);
/* 38 */     if (value == null) {
/* 39 */       throw new NullPointerException("value in a Node is required.");
/*    */     }
/* 41 */     this.value = value;
/* 42 */     this.style = style;
/* 43 */     this.resolved = resolved;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 54 */   public Character getStyle() { return this.style; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 59 */   public NodeId getNodeId() { return NodeId.scalar; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 68 */   public String getValue() { return this.value; }
/*    */ 
/*    */ 
/*    */   
/* 72 */   public String toString() { return "<" + getClass().getName() + " (tag=" + getTag() + ", value=" + getValue() + ")>"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\nodes\ScalarNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */