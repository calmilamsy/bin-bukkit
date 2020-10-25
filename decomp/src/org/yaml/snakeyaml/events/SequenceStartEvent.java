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
/*    */ public final class SequenceStartEvent
/*    */   extends CollectionStartEvent
/*    */ {
/* 33 */   public SequenceStartEvent(String anchor, String tag, boolean implicit, Mark startMark, Mark endMark, Boolean flowStyle) { super(anchor, tag, implicit, startMark, endMark, flowStyle); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 38 */   public boolean is(Event.ID id) { return (Event.ID.SequenceStart == id); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\events\SequenceStartEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */