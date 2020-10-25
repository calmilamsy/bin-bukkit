/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ 
/*    */ public class NBTTagDouble
/*    */   extends NBTBase {
/*    */   public double a;
/*    */   
/*    */   public NBTTagDouble() {}
/*    */   
/* 12 */   public NBTTagDouble(double paramDouble) { this.a = paramDouble; }
/*    */ 
/*    */ 
/*    */   
/* 16 */   void a(DataOutput paramDataOutput) { paramDataOutput.writeDouble(this.a); }
/*    */ 
/*    */ 
/*    */   
/* 20 */   void a(DataInput paramDataInput) { this.a = paramDataInput.readDouble(); }
/*    */ 
/*    */ 
/*    */   
/* 24 */   public byte a() { return 6; }
/*    */ 
/*    */ 
/*    */   
/* 28 */   public String toString() { return "" + this.a; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\NBTTagDouble.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */