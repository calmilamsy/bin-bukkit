/*    */ package org.yaml.snakeyaml.util;
/*    */ 
/*    */ import com.google.gdata.util.common.base.Escaper;
/*    */ import com.google.gdata.util.common.base.PercentEscaper;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.net.URLDecoder;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.CharBuffer;
/*    */ import java.nio.charset.CharacterCodingException;
/*    */ import java.nio.charset.Charset;
/*    */ import java.nio.charset.CharsetDecoder;
/*    */ import java.nio.charset.CodingErrorAction;
/*    */ import org.yaml.snakeyaml.error.YAMLException;
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
/*    */ public class UriEncoder
/*    */ {
/* 34 */   private static final CharsetDecoder UTF8Decoder = Charset.forName("UTF-8").newDecoder().onMalformedInput(CodingErrorAction.REPORT);
/*    */ 
/*    */   
/*    */   private static final String SAFE_CHARS = "-_.!~*'()@:$&,;=[]/";
/*    */ 
/*    */   
/* 40 */   private static final Escaper escaper = new PercentEscaper("-_.!~*'()@:$&,;=[]/", false);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 46 */   public static String encode(String uri) { return escaper.escape(uri); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String decode(ByteBuffer buff) throws CharacterCodingException {
/* 53 */     CharBuffer chars = UTF8Decoder.decode(buff);
/* 54 */     return chars.toString();
/*    */   }
/*    */   
/*    */   public static String decode(String buff) {
/*    */     try {
/* 59 */       return URLDecoder.decode(buff, "UTF-8");
/* 60 */     } catch (UnsupportedEncodingException e) {
/* 61 */       throw new YAMLException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyam\\util\UriEncoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */