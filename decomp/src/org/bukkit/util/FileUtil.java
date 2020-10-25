/*    */ package org.bukkit.util;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.nio.channels.FileChannel;
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
/*    */ public class FileUtil
/*    */ {
/*    */   public static boolean copy(File inFile, File outFile) {
/* 24 */     if (!inFile.exists()) {
/* 25 */       return false;
/*    */     }
/*    */     
/* 28 */     in = null;
/* 29 */     out = null;
/*    */     
/*    */     try {
/* 32 */       in = (new FileInputStream(inFile)).getChannel();
/* 33 */       out = (new FileOutputStream(outFile)).getChannel();
/*    */       
/* 35 */       pos = 0L;
/* 36 */       long size = in.size();
/*    */       
/* 38 */       while (pos < size) {
/* 39 */         pos += in.transferTo(pos, 10485760L, out);
/*    */       }
/* 41 */     } catch (IOException ioe) {
/* 42 */       return false;
/*    */     } finally {
/*    */       try {
/* 45 */         if (in != null) {
/* 46 */           in.close();
/*    */         }
/* 48 */         if (out != null) {
/* 49 */           out.close();
/*    */         }
/* 51 */       } catch (IOException ioe) {
/* 52 */         return false;
/*    */       } 
/*    */     } 
/*    */     
/* 56 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukki\\util\FileUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */