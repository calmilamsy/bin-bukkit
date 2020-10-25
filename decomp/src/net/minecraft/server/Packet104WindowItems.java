/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Packet104WindowItems
/*    */   extends Packet
/*    */ {
/*    */   public int a;
/*    */   public ItemStack[] b;
/*    */   
/*    */   public Packet104WindowItems() {}
/*    */   
/*    */   public Packet104WindowItems(int paramInt, List paramList) {
/* 18 */     this.a = paramInt;
/* 19 */     this.b = new ItemStack[paramList.size()];
/* 20 */     for (byte b1 = 0; b1 < this.b.length; b1++) {
/* 21 */       ItemStack itemStack = (ItemStack)paramList.get(b1);
/* 22 */       this.b[b1] = (itemStack == null) ? null : itemStack.cloneItemStack();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 27 */     this.a = paramDataInputStream.readByte();
/* 28 */     short s = paramDataInputStream.readShort();
/* 29 */     this.b = new ItemStack[s];
/* 30 */     for (byte b1 = 0; b1 < s; b1++) {
/* 31 */       short s1 = paramDataInputStream.readShort();
/* 32 */       if (s1 >= 0) {
/* 33 */         byte b2 = paramDataInputStream.readByte();
/* 34 */         short s2 = paramDataInputStream.readShort();
/* 35 */         this.b[b1] = new ItemStack(s1, b2, s2);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 41 */     paramDataOutputStream.writeByte(this.a);
/* 42 */     paramDataOutputStream.writeShort(this.b.length);
/* 43 */     for (byte b1 = 0; b1 < this.b.length; b1++) {
/* 44 */       if (this.b[b1] == null) {
/* 45 */         paramDataOutputStream.writeShort(-1);
/*    */       } else {
/* 47 */         paramDataOutputStream.writeShort((short)(this.b[b1]).id);
/* 48 */         paramDataOutputStream.writeByte((byte)(this.b[b1]).count);
/* 49 */         paramDataOutputStream.writeShort((short)this.b[b1].getData());
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 55 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 59 */   public int a() { return 3 + this.b.length * 5; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet104WindowItems.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */