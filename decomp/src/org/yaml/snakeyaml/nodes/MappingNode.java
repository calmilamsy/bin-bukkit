/*     */ package org.yaml.snakeyaml.nodes;
/*     */ 
/*     */ import java.util.List;
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
/*     */ public class MappingNode
/*     */   extends CollectionNode
/*     */ {
/*     */   private Class<? extends Object> keyType;
/*     */   private Class<? extends Object> valueType;
/*     */   private List<NodeTuple> value;
/*     */   private boolean need2setTypes = true;
/*     */   
/*     */   public MappingNode(Tag tag, boolean resolved, List<NodeTuple> value, Mark startMark, Mark endMark, Boolean flowStyle) {
/*  37 */     super(tag, startMark, endMark, flowStyle);
/*  38 */     if (value == null) {
/*  39 */       throw new NullPointerException("value in a Node is required.");
/*     */     }
/*  41 */     this.value = value;
/*  42 */     this.keyType = Object.class;
/*  43 */     this.valueType = Object.class;
/*  44 */     this.resolved = resolved;
/*     */   }
/*     */ 
/*     */   
/*  48 */   public MappingNode(Tag tag, List<NodeTuple> value, Boolean flowStyle) { this(tag, true, value, null, null, flowStyle); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   public NodeId getNodeId() { return NodeId.mapping; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<NodeTuple> getValue() {
/*  62 */     if (this.need2setTypes) {
/*  63 */       for (NodeTuple nodes : this.value) {
/*  64 */         nodes.getKeyNode().setType(this.keyType);
/*  65 */         nodes.getValueNode().setType(this.valueType);
/*     */       } 
/*  67 */       this.need2setTypes = false;
/*     */     } 
/*  69 */     return this.value;
/*     */   }
/*     */   
/*     */   public void setValue(List<NodeTuple> merge) {
/*  73 */     this.value = merge;
/*  74 */     this.need2setTypes = true;
/*     */   }
/*     */   
/*     */   public void setKeyType(Class<? extends Object> keyType) {
/*  78 */     this.keyType = keyType;
/*  79 */     this.need2setTypes = true;
/*     */   }
/*     */   
/*     */   public void setValueType(Class<? extends Object> valueType) {
/*  83 */     this.valueType = valueType;
/*  84 */     this.need2setTypes = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  90 */     StringBuilder buf = new StringBuilder();
/*  91 */     for (NodeTuple node : getValue()) {
/*  92 */       buf.append("{ key=");
/*  93 */       buf.append(node.getKeyNode());
/*  94 */       buf.append("; value=");
/*  95 */       if (node.getValueNode() instanceof CollectionNode) {
/*     */         
/*  97 */         buf.append(System.identityHashCode(node.getValueNode()));
/*     */       } else {
/*  99 */         buf.append(node.toString());
/*     */       } 
/* 101 */       buf.append(" }");
/*     */     } 
/* 103 */     String values = buf.toString();
/* 104 */     return "<" + getClass().getName() + " (tag=" + getTag() + ", values=" + values + ")>";
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\nodes\MappingNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */