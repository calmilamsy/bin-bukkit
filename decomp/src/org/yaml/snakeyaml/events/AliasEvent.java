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
/*    */ public final class AliasEvent
/*    */   extends NodeEvent
/*    */ {
/* 26 */   public AliasEvent(String anchor, Mark startMark, Mark endMark) { super(anchor, startMark, endMark); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 31 */   public boolean is(Event.ID id) { return (Event.ID.Alias == id); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\events\AliasEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */