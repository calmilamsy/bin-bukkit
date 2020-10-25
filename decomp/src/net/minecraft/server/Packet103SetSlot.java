/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Packet103SetSlot
/*    */   extends Packet
/*    */ {
/*    */   public int a;
/*    */   public int b;
/*    */   public ItemStack c;
/*    */   
/*    */   public Packet103SetSlot() {}
/*    */   
/*    */   public Packet103SetSlot(int paramInt1, int paramInt2, ItemStack paramItemStack) {
/* 22 */     this.a = paramInt1;
/* 23 */     this.b = paramInt2;
/* 24 */     this.c = (paramItemStack == null) ? paramItemStack : paramItemStack.cloneItemStack();
/*    */   }
/*    */ 
/*    */   
/* 28 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 32 */     this.a = paramDataInputStream.readByte();
/* 33 */     this.b = paramDataInputStream.readShort();
/* 34 */     short s = paramDataInputStream.readShort();
/* 35 */     if (s >= 0) {
/* 36 */       byte b1 = paramDataInputStream.readByte();
/* 37 */       short s1 = paramDataInputStream.readShort();
/*    */       
/* 39 */       this.c = new ItemStack(s, b1, s1);
/*    */     } else {
/* 41 */       this.c = null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 46 */     paramDataOutputStream.writeByte(this.a);
/* 47 */     paramDataOutputStream.writeShort(this.b);
/* 48 */     if (this.c == null) {
/* 49 */       paramDataOutputStream.writeShort(-1);
/*    */     } else {
/* 51 */       paramDataOutputStream.writeShort(this.c.id);
/* 52 */       paramDataOutputStream.writeByte(this.c.count);
/* 53 */       paramDataOutputStream.writeShort(this.c.getData());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 58 */   public int a() { return 8; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet103SetSlot.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */