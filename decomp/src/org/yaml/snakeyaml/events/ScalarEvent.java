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
/*    */ public final class ScalarEvent
/*    */   extends NodeEvent
/*    */ {
/*    */   private final String tag;
/*    */   private final Character style;
/*    */   private final String value;
/*    */   private final ImplicitTuple implicit;
/*    */   
/*    */   public ScalarEvent(String anchor, String tag, ImplicitTuple implicit, String value, Mark startMark, Mark endMark, Character style) {
/* 37 */     super(anchor, startMark, endMark);
/* 38 */     this.tag = tag;
/* 39 */     this.implicit = implicit;
/* 40 */     this.value = value;
/* 41 */     this.style = style;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 51 */   public String getTag() { return this.tag; }
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
/* 73 */   public Character getStyle() { return this.style; }
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
/* 85 */   public String getValue() { return this.value; }
/*    */ 
/*    */ 
/*    */   
/* 89 */   public ImplicitTuple getImplicit() { return this.implicit; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 94 */   protected String getArguments() { return super.getArguments() + ", tag=" + this.tag + ", " + this.implicit + ", value=" + this.value; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 99 */   public boolean is(Event.ID id) { return (Event.ID.Scalar == id); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\events\ScalarEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */