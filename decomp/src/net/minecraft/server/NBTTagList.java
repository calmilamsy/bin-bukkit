/*    */ package net.minecraft.server;
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class NBTTagList extends NBTBase {
/*  7 */   private List a = new ArrayList();
/*    */   private byte b;
/*    */   
/*    */   void a(DataOutput paramDataOutput) {
/* 11 */     if (this.a.size() > 0) { this.b = ((NBTBase)this.a.get(0)).a(); }
/* 12 */     else { this.b = 1; }
/*    */     
/* 14 */     paramDataOutput.writeByte(this.b);
/* 15 */     paramDataOutput.writeInt(this.a.size());
/* 16 */     for (byte b1 = 0; b1 < this.a.size(); b1++) {
/* 17 */       ((NBTBase)this.a.get(b1)).a(paramDataOutput);
/*    */     }
/*    */   }
/*    */   
/*    */   void a(DataInput paramDataInput) {
/* 22 */     this.b = paramDataInput.readByte();
/* 23 */     int i = paramDataInput.readInt();
/*    */     
/* 25 */     this.a = new ArrayList();
/* 26 */     for (byte b1 = 0; b1 < i; b1++) {
/* 27 */       NBTBase nBTBase = NBTBase.a(this.b);
/* 28 */       nBTBase.a(paramDataInput);
/* 29 */       this.a.add(nBTBase);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 34 */   public byte a() { return 9; }
/*    */ 
/*    */ 
/*    */   
/* 38 */   public String toString() { return "" + this.a.size() + " entries of type " + NBTBase.b(this.b); }
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
/*    */   public void a(NBTBase paramNBTBase) {
/* 53 */     this.b = paramNBTBase.a();
/* 54 */     this.a.add(paramNBTBase);
/*    */   }
/*    */ 
/*    */   
/* 58 */   public NBTBase a(int paramInt) { return (NBTBase)this.a.get(paramInt); }
/*    */ 
/*    */ 
/*    */   
/* 62 */   public int c() { return this.a.size(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\NBTTagList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */