/*    */ package org.yaml.snakeyaml.nodes;
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
/*    */ public class NodeTuple
/*    */ {
/*    */   private final Node keyNode;
/*    */   private final Node valueNode;
/*    */   
/*    */   public NodeTuple(Node keyNode, Node valueNode) {
/* 28 */     if (keyNode == null || valueNode == null) {
/* 29 */       throw new NullPointerException("Nodes must be provided.");
/*    */     }
/* 31 */     this.keyNode = keyNode;
/* 32 */     this.valueNode = valueNode;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 39 */   public Node getKeyNode() { return this.keyNode; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 48 */   public Node getValueNode() { return this.valueNode; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 53 */   public String toString() { return "<NodeTuple keyNode=" + this.keyNode.toString() + "; valueNode=" + this.valueNode.toString() + ">"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\nodes\NodeTuple.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */