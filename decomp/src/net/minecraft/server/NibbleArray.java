/*    */ package net.minecraft.server;
/*    */ 
/*    */ public class NibbleArray
/*    */ {
/*    */   public final byte[] a;
/*    */   
/*  7 */   public NibbleArray(int paramInt) { this.a = new byte[paramInt >> 1]; }
/*    */ 
/*    */ 
/*    */   
/* 11 */   public NibbleArray(byte[] paramArrayOfByte) { this.a = paramArrayOfByte; }
/*    */ 
/*    */   
/*    */   public int a(int paramInt1, int paramInt2, int paramInt3) {
/* 15 */     int i = paramInt1 << 11 | paramInt3 << 7 | paramInt2;
/* 16 */     int j = i >> 1;
/* 17 */     int k = i & true;
/*    */     
/* 19 */     if (k == 0) {
/* 20 */       return this.a[j] & 0xF;
/*    */     }
/* 22 */     return this.a[j] >> 4 & 0xF;
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 27 */     int i = paramInt1 << 11 | paramInt3 << 7 | paramInt2;
/*    */     
/* 29 */     int j = i >> 1;
/* 30 */     int k = i & true;
/*    */     
/* 32 */     if (k == 0) {
/* 33 */       this.a[j] = (byte)(this.a[j] & 0xF0 | paramInt4 & 0xF);
/*    */     } else {
/* 35 */       this.a[j] = (byte)(this.a[j] & 0xF | (paramInt4 & 0xF) << 4);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 40 */   public boolean a() { return (this.a != null); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\NibbleArray.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */