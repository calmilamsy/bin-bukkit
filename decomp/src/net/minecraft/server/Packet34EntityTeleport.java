/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ public class Packet34EntityTeleport
/*    */   extends Packet {
/*    */   public int a;
/*    */   public int b;
/*    */   public int c;
/*    */   public int d;
/*    */   public byte e;
/*    */   public byte f;
/*    */   
/*    */   public Packet34EntityTeleport() {}
/*    */   
/*    */   public Packet34EntityTeleport(Entity paramEntity) {
/* 18 */     this.a = paramEntity.id;
/* 19 */     this.b = MathHelper.floor(paramEntity.locX * 32.0D);
/* 20 */     this.c = MathHelper.floor(paramEntity.locY * 32.0D);
/* 21 */     this.d = MathHelper.floor(paramEntity.locZ * 32.0D);
/* 22 */     this.e = (byte)(int)(paramEntity.yaw * 256.0F / 360.0F);
/* 23 */     this.f = (byte)(int)(paramEntity.pitch * 256.0F / 360.0F);
/*    */   }
/*    */   
/*    */   public Packet34EntityTeleport(int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte paramByte1, byte paramByte2) {
/* 27 */     this.a = paramInt1;
/* 28 */     this.b = paramInt2;
/* 29 */     this.c = paramInt3;
/* 30 */     this.d = paramInt4;
/* 31 */     this.e = paramByte1;
/* 32 */     this.f = paramByte2;
/*    */   }
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 36 */     this.a = paramDataInputStream.readInt();
/* 37 */     this.b = paramDataInputStream.readInt();
/* 38 */     this.c = paramDataInputStream.readInt();
/* 39 */     this.d = paramDataInputStream.readInt();
/* 40 */     this.e = (byte)paramDataInputStream.read();
/* 41 */     this.f = (byte)paramDataInputStream.read();
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 45 */     paramDataOutputStream.writeInt(this.a);
/* 46 */     paramDataOutputStream.writeInt(this.b);
/* 47 */     paramDataOutputStream.writeInt(this.c);
/* 48 */     paramDataOutputStream.writeInt(this.d);
/* 49 */     paramDataOutputStream.write(this.e);
/* 50 */     paramDataOutputStream.write(this.f);
/*    */   }
/*    */ 
/*    */   
/* 54 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 58 */   public int a() { return 34; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet34EntityTeleport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */