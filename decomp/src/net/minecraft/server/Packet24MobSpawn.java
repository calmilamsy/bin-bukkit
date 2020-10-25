/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Packet24MobSpawn
/*    */   extends Packet
/*    */ {
/*    */   public int a;
/*    */   public byte b;
/*    */   public int c;
/*    */   public int d;
/*    */   public int e;
/*    */   public byte f;
/*    */   public byte g;
/*    */   private DataWatcher h;
/*    */   private List i;
/*    */   
/*    */   public Packet24MobSpawn() {}
/*    */   
/*    */   public Packet24MobSpawn(EntityLiving paramEntityLiving) {
/* 27 */     this.a = paramEntityLiving.id;
/*    */     
/* 29 */     this.b = (byte)EntityTypes.a(paramEntityLiving);
/* 30 */     this.c = MathHelper.floor(paramEntityLiving.locX * 32.0D);
/* 31 */     this.d = MathHelper.floor(paramEntityLiving.locY * 32.0D);
/* 32 */     this.e = MathHelper.floor(paramEntityLiving.locZ * 32.0D);
/* 33 */     this.f = (byte)(int)(paramEntityLiving.yaw * 256.0F / 360.0F);
/* 34 */     this.g = (byte)(int)(paramEntityLiving.pitch * 256.0F / 360.0F);
/*    */     
/* 36 */     this.h = paramEntityLiving.aa();
/*    */   }
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 40 */     this.a = paramDataInputStream.readInt();
/* 41 */     this.b = paramDataInputStream.readByte();
/* 42 */     this.c = paramDataInputStream.readInt();
/* 43 */     this.d = paramDataInputStream.readInt();
/* 44 */     this.e = paramDataInputStream.readInt();
/* 45 */     this.f = paramDataInputStream.readByte();
/* 46 */     this.g = paramDataInputStream.readByte();
/* 47 */     this.i = DataWatcher.a(paramDataInputStream);
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 51 */     paramDataOutputStream.writeInt(this.a);
/* 52 */     paramDataOutputStream.writeByte(this.b);
/* 53 */     paramDataOutputStream.writeInt(this.c);
/* 54 */     paramDataOutputStream.writeInt(this.d);
/* 55 */     paramDataOutputStream.writeInt(this.e);
/* 56 */     paramDataOutputStream.writeByte(this.f);
/* 57 */     paramDataOutputStream.writeByte(this.g);
/* 58 */     this.h.a(paramDataOutputStream);
/*    */   }
/*    */ 
/*    */   
/* 62 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 66 */   public int a() { return 20; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet24MobSpawn.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */