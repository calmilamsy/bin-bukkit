/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Packet71Weather
/*    */   extends Packet
/*    */ {
/*    */   public int a;
/*    */   public int b;
/*    */   public int c;
/*    */   public int d;
/*    */   public int e;
/*    */   
/*    */   public Packet71Weather() {}
/*    */   
/*    */   public Packet71Weather(Entity paramEntity) {
/* 20 */     this.a = paramEntity.id;
/* 21 */     this.b = MathHelper.floor(paramEntity.locX * 32.0D);
/* 22 */     this.c = MathHelper.floor(paramEntity.locY * 32.0D);
/* 23 */     this.d = MathHelper.floor(paramEntity.locZ * 32.0D);
/* 24 */     if (paramEntity instanceof EntityWeatherStorm) {
/* 25 */       this.e = 1;
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 30 */     this.a = paramDataInputStream.readInt();
/* 31 */     this.e = paramDataInputStream.readByte();
/* 32 */     this.b = paramDataInputStream.readInt();
/* 33 */     this.c = paramDataInputStream.readInt();
/* 34 */     this.d = paramDataInputStream.readInt();
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 38 */     paramDataOutputStream.writeInt(this.a);
/* 39 */     paramDataOutputStream.writeByte(this.e);
/* 40 */     paramDataOutputStream.writeInt(this.b);
/* 41 */     paramDataOutputStream.writeInt(this.c);
/* 42 */     paramDataOutputStream.writeInt(this.d);
/*    */   }
/*    */ 
/*    */   
/* 46 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 50 */   public int a() { return 17; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet71Weather.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */