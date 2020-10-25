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
/*    */ public final class DocumentEndEvent
/*    */   extends Event
/*    */ {
/*    */   private final boolean explicit;
/*    */   
/*    */   public DocumentEndEvent(Mark startMark, Mark endMark, boolean explicit) {
/* 31 */     super(startMark, endMark);
/* 32 */     this.explicit = explicit;
/*    */   }
/*    */ 
/*    */   
/* 36 */   public boolean getExplicit() { return this.explicit; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   public boolean is(Event.ID id) { return (Event.ID.DocumentEnd == id); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\events\DocumentEndEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */