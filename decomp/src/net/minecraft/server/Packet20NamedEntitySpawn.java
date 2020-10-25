/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ public class Packet20NamedEntitySpawn
/*    */   extends Packet {
/*    */   public int a;
/*    */   public String b;
/*    */   public int c;
/*    */   public int d;
/*    */   public int e;
/*    */   public byte f;
/*    */   public byte g;
/*    */   public int h;
/*    */   
/*    */   public Packet20NamedEntitySpawn() {}
/*    */   
/*    */   public Packet20NamedEntitySpawn(EntityHuman paramEntityHuman) {
/* 20 */     this.a = paramEntityHuman.id;
/* 21 */     this.b = paramEntityHuman.name;
/* 22 */     this.c = MathHelper.floor(paramEntityHuman.locX * 32.0D);
/* 23 */     this.d = MathHelper.floor(paramEntityHuman.locY * 32.0D);
/* 24 */     this.e = MathHelper.floor(paramEntityHuman.locZ * 32.0D);
/* 25 */     this.f = (byte)(int)(paramEntityHuman.yaw * 256.0F / 360.0F);
/* 26 */     this.g = (byte)(int)(paramEntityHuman.pitch * 256.0F / 360.0F);
/*    */     
/* 28 */     ItemStack itemStack = paramEntityHuman.inventory.getItemInHand();
/* 29 */     this.h = (itemStack == null) ? 0 : itemStack.id;
/*    */   }
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 33 */     this.a = paramDataInputStream.readInt();
/* 34 */     this.b = a(paramDataInputStream, 16);
/* 35 */     this.c = paramDataInputStream.readInt();
/* 36 */     this.d = paramDataInputStream.readInt();
/* 37 */     this.e = paramDataInputStream.readInt();
/* 38 */     this.f = paramDataInputStream.readByte();
/* 39 */     this.g = paramDataInputStream.readByte();
/* 40 */     this.h = paramDataInputStream.readShort();
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 44 */     paramDataOutputStream.writeInt(this.a);
/* 45 */     a(this.b, paramDataOutputStream);
/* 46 */     paramDataOutputStream.writeInt(this.c);
/* 47 */     paramDataOutputStream.writeInt(this.d);
/* 48 */     paramDataOutputStream.writeInt(this.e);
/* 49 */     paramDataOutputStream.writeByte(this.f);
/* 50 */     paramDataOutputStream.writeByte(this.g);
/* 51 */     paramDataOutputStream.writeShort(this.h);
/*    */   }
/*    */ 
/*    */   
/* 55 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 59 */   public int a() { return 28; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet20NamedEntitySpawn.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */