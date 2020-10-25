/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ public class Packet101CloseWindow
/*    */   extends Packet {
/*    */   public int a;
/*    */   
/*    */   public Packet101CloseWindow() {}
/*    */   
/* 12 */   public Packet101CloseWindow(int paramInt) { this.a = paramInt; }
/*    */ 
/*    */ 
/*    */   
/* 16 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 20 */   public void a(DataInputStream paramDataInputStream) { this.a = paramDataInputStream.readByte(); }
/*    */ 
/*    */ 
/*    */   
/* 24 */   public void a(DataOutputStream paramDataOutputStream) { paramDataOutputStream.writeByte(this.a); }
/*    */ 
/*    */ 
/*    */   
/* 28 */   public int a() { return 1; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet101CloseWindow.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */