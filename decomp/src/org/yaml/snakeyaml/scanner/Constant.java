/*    */ package org.yaml.snakeyaml.scanner;
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
/*    */ public final class Constant
/*    */ {
/*    */   static final String ALPHA = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-_";
/*    */   private static final String LINEBR_S = "\n  ";
/*    */   private static final String FULL_LINEBR_S = "\r\n  ";
/*    */   private static final String NULL_OR_LINEBR_S = "\000\r\n  ";
/*    */   private static final String NULL_BL_LINEBR_S = " \000\r\n  ";
/*    */   private static final String NULL_BL_T_LINEBR_S = "\t \000\r\n  ";
/*    */   private static final String URI_CHARS_S = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-_-;/?:@&=+$,_.!~*'()[]%";
/* 29 */   public static final Constant LINEBR = new Constant("\n  ");
/* 30 */   public static final Constant FULL_LINEBR = new Constant("\r\n  ");
/* 31 */   public static final Constant NULL_OR_LINEBR = new Constant("\000\r\n  ");
/* 32 */   public static final Constant NULL_BL_LINEBR = new Constant(" \000\r\n  ");
/* 33 */   public static final Constant NULL_BL_T_LINEBR = new Constant("\t \000\r\n  ");
/* 34 */   public static final Constant URI_CHARS = new Constant("abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-_-;/?:@&=+$,_.!~*'()[]%");
/*    */   
/*    */   private String content;
/*    */ 
/*    */   
/* 39 */   private Constant(String content) { this.content = content; }
/*    */ 
/*    */ 
/*    */   
/* 43 */   public boolean has(char ch) { return (this.content.indexOf(ch) != -1); }
/*    */ 
/*    */ 
/*    */   
/* 47 */   public boolean hasNo(char ch) { return !has(ch); }
/*    */ 
/*    */ 
/*    */   
/* 51 */   public boolean has(char ch, String additional) { return (additional.indexOf(ch) != -1 || this.content.indexOf(ch) != -1); }
/*    */ 
/*    */ 
/*    */   
/* 55 */   public boolean hasNo(char ch, String additional) { return !has(ch, additional); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\scanner\Constant.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */