/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ public class Packet25EntityPainting
/*    */   extends Packet {
/*    */   public int a;
/*    */   public int b;
/*    */   public int c;
/*    */   public int d;
/*    */   public int e;
/*    */   public String f;
/*    */   
/*    */   public Packet25EntityPainting() {}
/*    */   
/*    */   public Packet25EntityPainting(EntityPainting paramEntityPainting) {
/* 18 */     this.a = paramEntityPainting.id;
/* 19 */     this.b = paramEntityPainting.b;
/* 20 */     this.c = paramEntityPainting.c;
/* 21 */     this.d = paramEntityPainting.d;
/* 22 */     this.e = paramEntityPainting.a;
/* 23 */     this.f = paramEntityPainting.e.A;
/*    */   }
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 27 */     this.a = paramDataInputStream.readInt();
/* 28 */     this.f = a(paramDataInputStream, EnumArt.z);
/* 29 */     this.b = paramDataInputStream.readInt();
/* 30 */     this.c = paramDataInputStream.readInt();
/* 31 */     this.d = paramDataInputStream.readInt();
/* 32 */     this.e = paramDataInputStream.readInt();
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 36 */     paramDataOutputStream.writeInt(this.a);
/* 37 */     a(this.f, paramDataOutputStream);
/* 38 */     paramDataOutputStream.writeInt(this.b);
/* 39 */     paramDataOutputStream.writeInt(this.c);
/* 40 */     paramDataOutputStream.writeInt(this.d);
/* 41 */     paramDataOutputStream.writeInt(this.e);
/*    */   }
/*    */ 
/*    */   
/* 45 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 49 */   public int a() { return 24; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet25EntityPainting.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */