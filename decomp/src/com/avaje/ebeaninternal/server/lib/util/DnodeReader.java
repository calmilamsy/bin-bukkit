/*    */ package com.avaje.ebeaninternal.server.lib.util;
/*    */ 
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStreamWriter;
/*    */ import java.io.StringReader;
/*    */ import org.xml.sax.InputSource;
/*    */ import org.xml.sax.XMLReader;
/*    */ import org.xml.sax.helpers.XMLReaderFactory;
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
/*    */ public class DnodeReader
/*    */ {
/*    */   public Dnode parseXml(String str) {
/*    */     try {
/* 39 */       ByteArrayOutputStream bao = new ByteArrayOutputStream(str.length());
/* 40 */       OutputStreamWriter osw = new OutputStreamWriter(bao);
/*    */       
/* 42 */       StringReader sr = new StringReader(str);
/*    */       
/* 44 */       int charBufferSize = 1024;
/* 45 */       char[] buf = new char[charBufferSize];
/*    */       int len;
/* 47 */       while ((len = sr.read(buf, 0, buf.length)) != -1) {
/* 48 */         osw.write(buf, 0, len);
/*    */       }
/* 50 */       sr.close();
/* 51 */       osw.flush();
/* 52 */       osw.close();
/*    */       
/* 54 */       bao.flush();
/* 55 */       bao.close();
/*    */       
/* 57 */       InputStream is = new ByteArrayInputStream(bao.toByteArray());
/* 58 */       return parseXml(is);
/*    */     }
/* 60 */     catch (IOException ex) {
/* 61 */       throw new RuntimeException(ex);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Dnode parseXml(InputStream in) {
/*    */     try {
/* 71 */       InputSource inSource = new InputSource(in);
/*    */       
/* 73 */       DnodeParser parser = new DnodeParser();
/*    */       
/* 75 */       XMLReader myReader = XMLReaderFactory.createXMLReader();
/* 76 */       myReader.setContentHandler(parser);
/*    */       
/* 78 */       myReader.parse(inSource);
/*    */       
/* 80 */       return parser.getRoot();
/*    */     }
/* 82 */     catch (Exception e) {
/* 83 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\li\\util\DnodeReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */