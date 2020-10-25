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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Packet15Place
/*    */   extends Packet
/*    */ {
/*    */   public int a;
/*    */   public int b;
/*    */   public int c;
/*    */   public int face;
/*    */   public ItemStack itemstack;
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 25 */     this.a = paramDataInputStream.readInt();
/* 26 */     this.b = paramDataInputStream.read();
/* 27 */     this.c = paramDataInputStream.readInt();
/* 28 */     this.face = paramDataInputStream.read();
/*    */     
/* 30 */     short s = paramDataInputStream.readShort();
/* 31 */     if (s >= 0) {
/* 32 */       byte b1 = paramDataInputStream.readByte();
/* 33 */       short s1 = paramDataInputStream.readShort();
/*    */       
/* 35 */       this.itemstack = new ItemStack(s, b1, s1);
/*    */     } else {
/* 37 */       this.itemstack = null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 42 */     paramDataOutputStream.writeInt(this.a);
/* 43 */     paramDataOutputStream.write(this.b);
/* 44 */     paramDataOutputStream.writeInt(this.c);
/* 45 */     paramDataOutputStream.write(this.face);
/*    */     
/* 47 */     if (this.itemstack == null) {
/* 48 */       paramDataOutputStream.writeShort(-1);
/*    */     } else {
/* 50 */       paramDataOutputStream.writeShort(this.itemstack.id);
/* 51 */       paramDataOutputStream.writeByte(this.itemstack.count);
/* 52 */       paramDataOutputStream.writeShort(this.itemstack.getData());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 57 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 61 */   public int a() { return 15; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet15Place.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */