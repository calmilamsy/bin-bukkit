/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ 
/*    */ public class NBTTagShort
/*    */   extends NBTBase {
/*    */   public short a;
/*    */   
/*    */   public NBTTagShort() {}
/*    */   
/* 12 */   public NBTTagShort(short paramShort) { this.a = paramShort; }
/*    */ 
/*    */ 
/*    */   
/* 16 */   void a(DataOutput paramDataOutput) { paramDataOutput.writeShort(this.a); }
/*    */ 
/*    */ 
/*    */   
/* 20 */   void a(DataInput paramDataInput) { this.a = paramDataInput.readShort(); }
/*    */ 
/*    */ 
/*    */   
/* 24 */   public byte a() { return 2; }
/*    */ 
/*    */ 
/*    */   
/* 28 */   public String toString() { return "" + this.a; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\NBTTagShort.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */