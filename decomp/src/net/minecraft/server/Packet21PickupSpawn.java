/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ public class Packet21PickupSpawn extends Packet {
/*    */   public int a;
/*    */   public int b;
/*    */   public int c;
/*    */   public int d;
/*    */   public byte e;
/*    */   public byte f;
/*    */   public byte g;
/*    */   public int h;
/*    */   public int i;
/*    */   public int l;
/*    */   
/*    */   public Packet21PickupSpawn() {}
/*    */   
/*    */   public Packet21PickupSpawn(EntityItem paramEntityItem) {
/* 21 */     this.a = paramEntityItem.id;
/* 22 */     this.h = paramEntityItem.itemStack.id;
/* 23 */     this.i = paramEntityItem.itemStack.count;
/* 24 */     this.l = paramEntityItem.itemStack.getData();
/* 25 */     this.b = MathHelper.floor(paramEntityItem.locX * 32.0D);
/* 26 */     this.c = MathHelper.floor(paramEntityItem.locY * 32.0D);
/* 27 */     this.d = MathHelper.floor(paramEntityItem.locZ * 32.0D);
/* 28 */     this.e = (byte)(int)(paramEntityItem.motX * 128.0D);
/* 29 */     this.f = (byte)(int)(paramEntityItem.motY * 128.0D);
/* 30 */     this.g = (byte)(int)(paramEntityItem.motZ * 128.0D);
/*    */   }
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 34 */     this.a = paramDataInputStream.readInt();
/* 35 */     this.h = paramDataInputStream.readShort();
/* 36 */     this.i = paramDataInputStream.readByte();
/* 37 */     this.l = paramDataInputStream.readShort();
/* 38 */     this.b = paramDataInputStream.readInt();
/* 39 */     this.c = paramDataInputStream.readInt();
/* 40 */     this.d = paramDataInputStream.readInt();
/* 41 */     this.e = paramDataInputStream.readByte();
/* 42 */     this.f = paramDataInputStream.readByte();
/* 43 */     this.g = paramDataInputStream.readByte();
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 47 */     paramDataOutputStream.writeInt(this.a);
/* 48 */     paramDataOutputStream.writeShort(this.h);
/* 49 */     paramDataOutputStream.writeByte(this.i);
/* 50 */     paramDataOutputStream.writeShort(this.l);
/* 51 */     paramDataOutputStream.writeInt(this.b);
/* 52 */     paramDataOutputStream.writeInt(this.c);
/* 53 */     paramDataOutputStream.writeInt(this.d);
/* 54 */     paramDataOutputStream.writeByte(this.e);
/* 55 */     paramDataOutputStream.writeByte(this.f);
/* 56 */     paramDataOutputStream.writeByte(this.g);
/*    */   }
/*    */ 
/*    */   
/* 60 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 64 */   public int a() { return 24; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet21PickupSpawn.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */