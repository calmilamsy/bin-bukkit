/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ 
/*    */ public class NBTTagInt
/*    */   extends NBTBase {
/*    */   public int a;
/*    */   
/*    */   public NBTTagInt() {}
/*    */   
/* 12 */   public NBTTagInt(int paramInt) { this.a = paramInt; }
/*    */ 
/*    */ 
/*    */   
/* 16 */   void a(DataOutput paramDataOutput) { paramDataOutput.writeInt(this.a); }
/*    */ 
/*    */ 
/*    */   
/* 20 */   void a(DataInput paramDataInput) { this.a = paramDataInput.readInt(); }
/*    */ 
/*    */ 
/*    */   
/* 24 */   public byte a() { return 3; }
/*    */ 
/*    */ 
/*    */   
/* 28 */   public String toString() { return "" + this.a; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\NBTTagInt.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */