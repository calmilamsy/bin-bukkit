/*    */ package joptsimple;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
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
/*    */ 
/*    */ 
/*    */ public abstract class OptionException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = -1L;
/*    */   private final List<String> options;
/*    */   
/*    */   protected OptionException(Collection<String> options) {
/* 45 */     this.options = new ArrayList();
/*    */ 
/*    */     
/* 48 */     this.options.addAll(options);
/*    */   }
/*    */   
/*    */   protected OptionException(Collection<String> options, Throwable cause) {
/* 52 */     super(cause);
/*    */     this.options = new ArrayList();
/* 54 */     this.options.addAll(options);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 63 */   public Collection<String> options() { return Collections.unmodifiableCollection(this.options); }
/*    */ 
/*    */ 
/*    */   
/* 67 */   protected final String singleOptionMessage() { return singleOptionMessage((String)this.options.get(0)); }
/*    */ 
/*    */ 
/*    */   
/* 71 */   protected final String singleOptionMessage(String option) { return "'" + option + "'"; }
/*    */ 
/*    */   
/*    */   protected final String multipleOptionMessage() {
/* 75 */     StringBuilder buffer = new StringBuilder("[");
/*    */     
/* 77 */     for (Iterator<String> iter = this.options.iterator(); iter.hasNext(); ) {
/* 78 */       buffer.append(singleOptionMessage((String)iter.next()));
/* 79 */       if (iter.hasNext()) {
/* 80 */         buffer.append(", ");
/*    */       }
/*    */     } 
/* 83 */     buffer.append(']');
/*    */     
/* 85 */     return buffer.toString();
/*    */   }
/*    */ 
/*    */   
/* 89 */   static OptionException illegalOptionCluster(String option) { return new IllegalOptionClusterException(option); }
/*    */ 
/*    */ 
/*    */   
/* 93 */   static OptionException unrecognizedOption(String option) { return new UnrecognizedOptionException(option); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\joptsimple\OptionException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */