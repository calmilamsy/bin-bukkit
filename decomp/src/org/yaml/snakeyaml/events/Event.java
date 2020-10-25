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
/*    */ public abstract class Event
/*    */ {
/*    */   private final Mark startMark;
/*    */   private final Mark endMark;
/*    */   
/*    */   public enum ID
/*    */   {
/* 27 */     Alias, DocumentEnd, DocumentStart, MappingEnd, MappingStart, Scalar, SequenceEnd, SequenceStart, StreamEnd, StreamStart;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Event(Mark startMark, Mark endMark) {
/* 34 */     this.startMark = startMark;
/* 35 */     this.endMark = endMark;
/*    */   }
/*    */ 
/*    */   
/* 39 */   public String toString() { return "<" + getClass().getName() + "(" + getArguments() + ")>"; }
/*    */ 
/*    */ 
/*    */   
/* 43 */   public Mark getStartMark() { return this.startMark; }
/*    */ 
/*    */ 
/*    */   
/* 47 */   public Mark getEndMark() { return this.endMark; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 54 */   protected String getArguments() { return ""; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract boolean is(ID paramID);
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 64 */     if (obj instanceof Event) {
/* 65 */       return toString().equals(obj.toString());
/*    */     }
/* 67 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\events\Event.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */