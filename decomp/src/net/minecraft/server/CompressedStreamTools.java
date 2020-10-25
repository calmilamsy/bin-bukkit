/*    */ package net.minecraft.server;
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ public class CompressedStreamTools {
/*    */   public static NBTTagCompound a(InputStream paramInputStream) {
/*  8 */     dataInputStream = new DataInputStream(new GZIPInputStream(paramInputStream));
/*    */     try {
/* 10 */       return a(dataInputStream);
/*    */     } finally {
/* 12 */       dataInputStream.close();
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void a(NBTTagCompound paramNBTTagCompound, OutputStream paramOutputStream) {
/* 17 */     dataOutputStream = new DataOutputStream(new GZIPOutputStream(paramOutputStream));
/*    */     try {
/* 19 */       a(paramNBTTagCompound, dataOutputStream);
/*    */     } finally {
/* 21 */       dataOutputStream.close();
/*    */     } 
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
/*    */   public static NBTTagCompound a(DataInput paramDataInput) {
/* 46 */     NBTBase nBTBase = NBTBase.b(paramDataInput);
/* 47 */     if (nBTBase instanceof NBTTagCompound) return (NBTTagCompound)nBTBase; 
/* 48 */     throw new IOException("Root tag must be a named compound tag");
/*    */   }
/*    */ 
/*    */   
/* 52 */   public static void a(NBTTagCompound paramNBTTagCompound, DataOutput paramDataOutput) { NBTBase.a(paramNBTTagCompound, paramDataOutput); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\CompressedStreamTools.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */