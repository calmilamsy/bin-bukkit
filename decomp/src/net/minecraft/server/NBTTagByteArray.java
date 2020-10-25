/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ 
/*    */ public class NBTTagByteArray extends NBTBase {
/*    */   public byte[] a;
/*    */   
/*    */   public NBTTagByteArray() {}
/*    */   
/* 11 */   public NBTTagByteArray(byte[] paramArrayOfByte) { this.a = paramArrayOfByte; }
/*    */ 
/*    */   
/*    */   void a(DataOutput paramDataOutput) {
/* 15 */     paramDataOutput.writeInt(this.a.length);
/* 16 */     paramDataOutput.write(this.a);
/*    */   }
/*    */   
/*    */   void a(DataInput paramDataInput) {
/* 20 */     int i = paramDataInput.readInt();
/* 21 */     this.a = new byte[i];
/* 22 */     paramDataInput.readFully(this.a);
/*    */   }
/*    */ 
/*    */   
/* 26 */   public byte a() { return 7; }
/*    */ 
/*    */ 
/*    */   
/* 30 */   public String toString() { return "[" + this.a.length + " bytes]"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\NBTTagByteArray.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */