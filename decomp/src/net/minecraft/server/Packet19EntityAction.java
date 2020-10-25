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
/*    */ public class Packet19EntityAction
/*    */   extends Packet
/*    */ {
/*    */   public int a;
/*    */   public int animation;
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 24 */     this.a = paramDataInputStream.readInt();
/* 25 */     this.animation = paramDataInputStream.readByte();
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 29 */     paramDataOutputStream.writeInt(this.a);
/* 30 */     paramDataOutputStream.writeByte(this.animation);
/*    */   }
/*    */ 
/*    */   
/* 34 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 38 */   public int a() { return 5; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet19EntityAction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */