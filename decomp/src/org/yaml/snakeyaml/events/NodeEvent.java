/*    */ package org.yaml.snakeyaml.events;
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
/*    */ public abstract class NodeEvent
/*    */   extends Event
/*    */ {
/*    */   private final String anchor;
/*    */   
/*    */   public NodeEvent(String anchor, Mark startMark, Mark endMark) {
/* 29 */     super(startMark, endMark);
/* 30 */     this.anchor = anchor;
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
/*    */ 
/*    */   
/* 43 */   public String getAnchor() { return this.anchor; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 48 */   protected String getArguments() { return "anchor=" + this.anchor; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\events\NodeEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */