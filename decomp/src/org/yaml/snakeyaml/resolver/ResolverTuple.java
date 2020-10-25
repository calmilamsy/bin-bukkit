/*    */ package org.yaml.snakeyaml.resolver;
/*    */ 
/*    */ import java.util.regex.Pattern;
/*    */ import org.yaml.snakeyaml.nodes.Tag;
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
/*    */ final class ResolverTuple
/*    */ {
/*    */   private final Tag tag;
/*    */   private final Pattern regexp;
/*    */   
/*    */   public ResolverTuple(Tag tag, Pattern regexp) {
/* 28 */     this.tag = tag;
/* 29 */     this.regexp = regexp;
/*    */   }
/*    */ 
/*    */   
/* 33 */   public Tag getTag() { return this.tag; }
/*    */ 
/*    */ 
/*    */   
/* 37 */   public Pattern getRegexp() { return this.regexp; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 42 */   public String toString() { return "Tuple tag=" + this.tag + " regexp=" + this.regexp; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\resolver\ResolverTuple.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */