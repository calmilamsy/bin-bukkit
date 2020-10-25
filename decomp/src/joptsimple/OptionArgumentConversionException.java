/*    */ package joptsimple;
/*    */ 
/*    */ import java.util.Collection;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class OptionArgumentConversionException
/*    */   extends OptionException
/*    */ {
/*    */   private static final long serialVersionUID = -1L;
/*    */   private final String argument;
/*    */   private final Class<?> valueType;
/*    */   
/*    */   OptionArgumentConversionException(Collection<String> options, String argument, Class<?> valueType, Throwable cause) {
/* 46 */     super(options, cause);
/*    */     
/* 48 */     this.argument = argument;
/* 49 */     this.valueType = valueType;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 54 */   public String getMessage() { return "Cannot convert argument '" + this.argument + "' of option " + multipleOptionMessage() + " to " + this.valueType; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\joptsimple\OptionArgumentConversionException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */