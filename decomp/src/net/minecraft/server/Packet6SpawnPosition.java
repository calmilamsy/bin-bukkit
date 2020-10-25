/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ public class Packet6SpawnPosition
/*    */   extends Packet {
/*    */   public int x;
/*    */   public int y;
/*    */   
/*    */   public Packet6SpawnPosition(int paramInt1, int paramInt2, int paramInt3) {
/* 12 */     this.x = paramInt1;
/* 13 */     this.y = paramInt2;
/* 14 */     this.z = paramInt3;
/*    */   } public int z;
/*    */   public Packet6SpawnPosition() {}
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 18 */     this.x = paramDataInputStream.readInt();
/* 19 */     this.y = paramDataInputStream.readInt();
/* 20 */     this.z = paramDataInputStream.readInt();
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 24 */     paramDataOutputStream.writeInt(this.x);
/* 25 */     paramDataOutputStream.writeInt(this.y);
/* 26 */     paramDataOutputStream.writeInt(this.z);
/*    */   }
/*    */ 
/*    */   
/* 30 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 34 */   public int a() { return 12; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet6SpawnPosition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */