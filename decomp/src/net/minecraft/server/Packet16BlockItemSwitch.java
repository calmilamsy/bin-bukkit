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
/*    */ public class Packet16BlockItemSwitch
/*    */   extends Packet
/*    */ {
/*    */   public int itemInHandIndex;
/*    */   
/* 16 */   public void a(DataInputStream paramDataInputStream) { this.itemInHandIndex = paramDataInputStream.readShort(); }
/*    */ 
/*    */ 
/*    */   
/* 20 */   public void a(DataOutputStream paramDataOutputStream) { paramDataOutputStream.writeShort(this.itemInHandIndex); }
/*    */ 
/*    */ 
/*    */   
/* 24 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 28 */   public int a() { return 2; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet16BlockItemSwitch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */