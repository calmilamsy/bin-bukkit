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
/*    */ public final class StreamEndEvent
/*    */   extends Event
/*    */ {
/* 34 */   public StreamEndEvent(Mark startMark, Mark endMark) { super(startMark, endMark); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 39 */   public boolean is(Event.ID id) { return (Event.ID.StreamEnd == id); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\events\StreamEndEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */