/*    */ package org.yaml.snakeyaml.nodes;
/*    */ 
/*    */ import java.util.List;
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
/*    */ 
/*    */ public class SequenceNode
/*    */   extends CollectionNode
/*    */ {
/*    */   private Class<? extends Object> listType;
/*    */   private List<Node> value;
/*    */   
/*    */   public SequenceNode(Tag tag, boolean resolved, List<Node> value, Mark startMark, Mark endMark, Boolean flowStyle) {
/* 35 */     super(tag, startMark, endMark, flowStyle);
/* 36 */     if (value == null) {
/* 37 */       throw new NullPointerException("value in a Node is required.");
/*    */     }
/* 39 */     this.value = value;
/* 40 */     this.listType = Object.class;
/* 41 */     this.resolved = resolved;
/*    */   }
/*    */ 
/*    */   
/* 45 */   public SequenceNode(Tag tag, List<Node> value, Boolean flowStyle) { this(tag, true, value, null, null, flowStyle); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 50 */   public NodeId getNodeId() { return NodeId.sequence; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<Node> getValue() {
/* 59 */     for (Node node : this.value) {
/* 60 */       node.setType(this.listType);
/*    */     }
/* 62 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/* 66 */   public void setListType(Class<? extends Object> listType) { this.listType = listType; }
/*    */ 
/*    */ 
/*    */   
/* 70 */   public String toString() { return "<" + getClass().getName() + " (tag=" + getTag() + ", value=" + getValue() + ")>"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\nodes\SequenceNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */