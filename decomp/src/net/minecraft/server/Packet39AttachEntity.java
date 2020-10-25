/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ public class Packet39AttachEntity extends Packet {
/*    */   public int a;
/*    */   public int b;
/*    */   
/*    */   public Packet39AttachEntity() {}
/*    */   
/*    */   public Packet39AttachEntity(Entity paramEntity1, Entity paramEntity2) {
/* 13 */     this.a = paramEntity1.id;
/* 14 */     this.b = (paramEntity2 != null) ? paramEntity2.id : -1;
/*    */   }
/*    */ 
/*    */   
/* 18 */   public int a() { return 8; }
/*    */ 
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 22 */     this.a = paramDataInputStream.readInt();
/* 23 */     this.b = paramDataInputStream.readInt();
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 27 */     paramDataOutputStream.writeInt(this.a);
/* 28 */     paramDataOutputStream.writeInt(this.b);
/*    */   }
/*    */ 
/*    */   
/* 32 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet39AttachEntity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */