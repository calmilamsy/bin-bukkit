/*    */ package org.yaml.snakeyaml.events;
/*    */ 
/*    */ import java.util.Map;
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
/*    */ public final class DocumentStartEvent
/*    */   extends Event
/*    */ {
/*    */   private final boolean explicit;
/*    */   private final Integer[] version;
/*    */   private final Map<String, String> tags;
/*    */   
/*    */   public DocumentStartEvent(Mark startMark, Mark endMark, boolean explicit, Integer[] version, Map<String, String> tags) {
/* 36 */     super(startMark, endMark);
/* 37 */     this.explicit = explicit;
/* 38 */     this.version = version;
/* 39 */     this.tags = tags;
/*    */   }
/*    */ 
/*    */   
/* 43 */   public boolean getExplicit() { return this.explicit; }
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
/* 55 */   public Integer[] getVersion() { return this.version; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 65 */   public Map<String, String> getTags() { return this.tags; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 70 */   public boolean is(Event.ID id) { return (Event.ID.DocumentStart == id); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\events\DocumentStartEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */