/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ 
/*    */ public class Packet29DestroyEntity
/*    */   extends Packet
/*    */ {
/*    */   public int a;
/*    */   
/*    */   public Packet29DestroyEntity() {}
/*    */   
/* 14 */   public Packet29DestroyEntity(int paramInt) { this.a = paramInt; }
/*    */ 
/*    */ 
/*    */   
/* 18 */   public void a(DataInputStream paramDataInputStream) { this.a = paramDataInputStream.readInt(); }
/*    */ 
/*    */ 
/*    */   
/* 22 */   public void a(DataOutputStream paramDataOutputStream) { paramDataOutputStream.writeInt(this.a); }
/*    */ 
/*    */ 
/*    */   
/* 26 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 30 */   public int a() { return 4; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet29DestroyEntity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */