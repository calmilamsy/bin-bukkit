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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class StreamStartEvent
/*    */   extends Event
/*    */ {
/* 35 */   public StreamStartEvent(Mark startMark, Mark endMark) { super(startMark, endMark); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 40 */   public boolean is(Event.ID id) { return (Event.ID.StreamStart == id); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\events\StreamStartEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */