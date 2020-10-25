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
/*    */ public final class SequenceEndEvent
/*    */   extends CollectionEndEvent
/*    */ {
/* 29 */   public SequenceEndEvent(Mark startMark, Mark endMark) { super(startMark, endMark); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 34 */   public boolean is(Event.ID id) { return (Event.ID.SequenceEnd == id); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\events\SequenceEndEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */