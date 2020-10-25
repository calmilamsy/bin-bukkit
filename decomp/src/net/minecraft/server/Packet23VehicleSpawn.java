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
/*    */ 
/*    */ public class Packet23VehicleSpawn
/*    */   extends Packet
/*    */ {
/*    */   public int a;
/*    */   public int b;
/*    */   public int c;
/*    */   public int d;
/*    */   public int e;
/*    */   public int f;
/*    */   public int g;
/*    */   public int h;
/*    */   public int i;
/*    */   
/*    */   public Packet23VehicleSpawn() {}
/*    */   
/* 34 */   public Packet23VehicleSpawn(Entity paramEntity, int paramInt) { this(paramEntity, paramInt, 0); }
/*    */ 
/*    */   
/*    */   public Packet23VehicleSpawn(Entity paramEntity, int paramInt1, int paramInt2) {
/* 38 */     this.a = paramEntity.id;
/* 39 */     this.b = MathHelper.floor(paramEntity.locX * 32.0D);
/* 40 */     this.c = MathHelper.floor(paramEntity.locY * 32.0D);
/* 41 */     this.d = MathHelper.floor(paramEntity.locZ * 32.0D);
/* 42 */     this.h = paramInt1;
/* 43 */     this.i = paramInt2;
/* 44 */     if (paramInt2 > 0) {
/* 45 */       double d1 = paramEntity.motX;
/* 46 */       double d2 = paramEntity.motY;
/* 47 */       double d3 = paramEntity.motZ;
/* 48 */       double d4 = 3.9D;
/* 49 */       if (d1 < -d4) d1 = -d4; 
/* 50 */       if (d2 < -d4) d2 = -d4; 
/* 51 */       if (d3 < -d4) d3 = -d4; 
/* 52 */       if (d1 > d4) d1 = d4; 
/* 53 */       if (d2 > d4) d2 = d4; 
/* 54 */       if (d3 > d4) d3 = d4; 
/* 55 */       this.e = (int)(d1 * 8000.0D);
/* 56 */       this.f = (int)(d2 * 8000.0D);
/* 57 */       this.g = (int)(d3 * 8000.0D);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 62 */     this.a = paramDataInputStream.readInt();
/* 63 */     this.h = paramDataInputStream.readByte();
/* 64 */     this.b = paramDataInputStream.readInt();
/* 65 */     this.c = paramDataInputStream.readInt();
/* 66 */     this.d = paramDataInputStream.readInt();
/* 67 */     this.i = paramDataInputStream.readInt();
/* 68 */     if (this.i > 0) {
/* 69 */       this.e = paramDataInputStream.readShort();
/* 70 */       this.f = paramDataInputStream.readShort();
/* 71 */       this.g = paramDataInputStream.readShort();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 76 */     paramDataOutputStream.writeInt(this.a);
/* 77 */     paramDataOutputStream.writeByte(this.h);
/* 78 */     paramDataOutputStream.writeInt(this.b);
/* 79 */     paramDataOutputStream.writeInt(this.c);
/* 80 */     paramDataOutputStream.writeInt(this.d);
/* 81 */     paramDataOutputStream.writeInt(this.i);
/* 82 */     if (this.i > 0) {
/* 83 */       paramDataOutputStream.writeShort(this.e);
/* 84 */       paramDataOutputStream.writeShort(this.f);
/* 85 */       paramDataOutputStream.writeShort(this.g);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 90 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 94 */   public int a() { return (21 + this.i > 0) ? 6 : 0; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet23VehicleSpawn.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */