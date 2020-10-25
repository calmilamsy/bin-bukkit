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
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class CollectionStartEvent
/*    */   extends NodeEvent
/*    */ {
/*    */   private final String tag;
/*    */   private final boolean implicit;
/*    */   private final Boolean flowStyle;
/*    */   
/*    */   public CollectionStartEvent(String anchor, String tag, boolean implicit, Mark startMark, Mark endMark, Boolean flowStyle) {
/* 34 */     super(anchor, startMark, endMark);
/* 35 */     this.tag = tag;
/* 36 */     this.implicit = implicit;
/* 37 */     this.flowStyle = flowStyle;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 47 */   public String getTag() { return this.tag; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 57 */   public boolean getImplicit() { return this.implicit; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 67 */   public Boolean getFlowStyle() { return this.flowStyle; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 72 */   protected String getArguments() { return super.getArguments() + ", tag=" + this.tag + ", implicit=" + this.implicit; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\events\CollectionStartEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */