/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ 
/*    */ public class NBTTagByte
/*    */   extends NBTBase {
/*    */   public byte a;
/*    */   
/*    */   public NBTTagByte() {}
/*    */   
/* 12 */   public NBTTagByte(byte paramByte) { this.a = paramByte; }
/*    */ 
/*    */ 
/*    */   
/* 16 */   void a(DataOutput paramDataOutput) { paramDataOutput.writeByte(this.a); }
/*    */ 
/*    */ 
/*    */   
/* 20 */   void a(DataInput paramDataInput) { this.a = paramDataInput.readByte(); }
/*    */ 
/*    */ 
/*    */   
/* 24 */   public byte a() { return 1; }
/*    */ 
/*    */ 
/*    */   
/* 28 */   public String toString() { return "" + this.a; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\NBTTagByte.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */