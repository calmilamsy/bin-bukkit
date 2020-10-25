/*    */ package joptsimple.util;
/*    */ 
/*    */ import java.text.DateFormat;
/*    */ import java.text.ParsePosition;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import joptsimple.ValueConversionException;
/*    */ import joptsimple.ValueConverter;
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
/*    */ 
/*    */ public class DateConverter
/*    */   extends Object
/*    */   implements ValueConverter<Date>
/*    */ {
/*    */   private final DateFormat formatter;
/*    */   
/*    */   public DateConverter(DateFormat formatter) {
/* 51 */     if (formatter == null) {
/* 52 */       throw new NullPointerException("illegal null formatter");
/*    */     }
/* 54 */     this.formatter = formatter;
/*    */   }
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
/*    */   public static DateConverter datePattern(String pattern) {
/* 68 */     SimpleDateFormat formatter = new SimpleDateFormat(pattern);
/* 69 */     formatter.setLenient(false);
/*    */     
/* 71 */     return new DateConverter(formatter);
/*    */   }
/*    */   
/*    */   public Date convert(String value) {
/* 75 */     ParsePosition position = new ParsePosition(false);
/*    */     
/* 77 */     Date date = this.formatter.parse(value, position);
/* 78 */     if (position.getIndex() != value.length()) {
/* 79 */       throw new ValueConversionException(message(value));
/*    */     }
/* 81 */     return date;
/*    */   }
/*    */ 
/*    */   
/* 85 */   public Class<Date> valueType() { return Date.class; }
/*    */ 
/*    */ 
/*    */   
/* 89 */   public String valuePattern() { return (this.formatter instanceof SimpleDateFormat) ? ((SimpleDateFormat)this.formatter).toLocalizedPattern() : ""; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private String message(String value) {
/* 95 */     String message = "Value [" + value + "] does not match date/time pattern";
/* 96 */     if (this.formatter instanceof SimpleDateFormat) {
/* 97 */       message = message + " [" + ((SimpleDateFormat)this.formatter).toLocalizedPattern() + ']';
/*    */     }
/* 99 */     return message;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\joptsimpl\\util\DateConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */