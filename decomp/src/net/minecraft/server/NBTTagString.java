/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ 
/*    */ public class NBTTagString extends NBTBase {
/*    */   public String a;
/*    */   
/*    */   public NBTTagString() {}
/*    */   
/*    */   public NBTTagString(String paramString) {
/* 12 */     this.a = paramString;
/* 13 */     if (paramString == null) throw new IllegalArgumentException("Empty string not allowed");
/*    */   
/*    */   }
/*    */   
/* 17 */   void a(DataOutput paramDataOutput) { paramDataOutput.writeUTF(this.a); }
/*    */ 
/*    */ 
/*    */   
/* 21 */   void a(DataInput paramDataInput) { this.a = paramDataInput.readUTF(); }
/*    */ 
/*    */ 
/*    */   
/* 25 */   public byte a() { return 8; }
/*    */ 
/*    */ 
/*    */   
/* 29 */   public String toString() { return "" + this.a; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\NBTTagString.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */