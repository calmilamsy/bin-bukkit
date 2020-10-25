/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ public class BlockRegister
/*    */ {
/*  6 */   private static byte[] a = new byte[256];
/*    */   
/*    */   static  {
/*    */     try {
/* 10 */       for (byte b = 0; b < 'Ā'; b++) {
/* 11 */         byte b1 = (byte)b;
/* 12 */         if (b1 != 0 && Block.byId[b1 & 0xFF] == null) {
/* 13 */           b1 = 0;
/*    */         }
/* 15 */         a[b] = b1;
/*    */       } 
/* 17 */     } catch (Exception exception) {
/* 18 */       exception.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void a(byte[] paramArrayOfByte) {
/* 23 */     for (byte b = 0; b < paramArrayOfByte.length; b++)
/* 24 */       paramArrayOfByte[b] = a[paramArrayOfByte[b] & 0xFF]; 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockRegister.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */