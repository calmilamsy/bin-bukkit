/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Packet70Bed
/*    */   extends Packet
/*    */ {
/* 11 */   public static final String[] a = { "tile.bed.notValid", null, null };
/*    */ 
/*    */   
/*    */   public int b;
/*    */ 
/*    */ 
/*    */   
/*    */   public Packet70Bed() {}
/*    */ 
/*    */   
/* 21 */   public Packet70Bed(int paramInt) { this.b = paramInt; }
/*    */ 
/*    */ 
/*    */   
/* 25 */   public void a(DataInputStream paramDataInputStream) { this.b = paramDataInputStream.readByte(); }
/*    */ 
/*    */ 
/*    */   
/* 29 */   public void a(DataOutputStream paramDataOutputStream) { paramDataOutputStream.writeByte(this.b); }
/*    */ 
/*    */ 
/*    */   
/* 33 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 37 */   public int a() { return 1; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet70Bed.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */