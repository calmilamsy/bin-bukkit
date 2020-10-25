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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Packet102WindowClick
/*    */   extends Packet
/*    */ {
/*    */   public int a;
/*    */   public int b;
/*    */   public int c;
/*    */   public short d;
/*    */   public ItemStack e;
/*    */   public boolean f;
/*    */   
/* 28 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 32 */     this.a = paramDataInputStream.readByte();
/* 33 */     this.b = paramDataInputStream.readShort();
/* 34 */     this.c = paramDataInputStream.readByte();
/* 35 */     this.d = paramDataInputStream.readShort();
/* 36 */     this.f = paramDataInputStream.readBoolean();
/*    */     
/* 38 */     short s = paramDataInputStream.readShort();
/* 39 */     if (s >= 0) {
/* 40 */       byte b1 = paramDataInputStream.readByte();
/* 41 */       short s1 = paramDataInputStream.readShort();
/*    */       
/* 43 */       this.e = new ItemStack(s, b1, s1);
/*    */     } else {
/* 45 */       this.e = null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 50 */     paramDataOutputStream.writeByte(this.a);
/* 51 */     paramDataOutputStream.writeShort(this.b);
/* 52 */     paramDataOutputStream.writeByte(this.c);
/* 53 */     paramDataOutputStream.writeShort(this.d);
/* 54 */     paramDataOutputStream.writeBoolean(this.f);
/*    */     
/* 56 */     if (this.e == null) {
/* 57 */       paramDataOutputStream.writeShort(-1);
/*    */     } else {
/* 59 */       paramDataOutputStream.writeShort(this.e.id);
/* 60 */       paramDataOutputStream.writeByte(this.e.count);
/* 61 */       paramDataOutputStream.writeShort(this.e.getData());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 66 */   public int a() { return 11; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet102WindowClick.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */