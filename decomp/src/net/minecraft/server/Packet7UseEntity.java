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
/*    */ public class Packet7UseEntity
/*    */   extends Packet
/*    */ {
/*    */   public int a;
/*    */   public int target;
/*    */   public int c;
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 21 */     this.a = paramDataInputStream.readInt();
/* 22 */     this.target = paramDataInputStream.readInt();
/* 23 */     this.c = paramDataInputStream.readByte();
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 27 */     paramDataOutputStream.writeInt(this.a);
/* 28 */     paramDataOutputStream.writeInt(this.target);
/* 29 */     paramDataOutputStream.writeByte(this.c);
/*    */   }
/*    */ 
/*    */   
/* 33 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 37 */   public int a() { return 9; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet7UseEntity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */