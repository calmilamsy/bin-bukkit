/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Packet40EntityMetadata
/*    */   extends Packet
/*    */ {
/*    */   public int a;
/*    */   private List b;
/*    */   
/*    */   public Packet40EntityMetadata() {}
/*    */   
/*    */   public Packet40EntityMetadata(int paramInt, DataWatcher paramDataWatcher) {
/* 19 */     this.a = paramInt;
/* 20 */     this.b = paramDataWatcher.b();
/*    */   }
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 24 */     this.a = paramDataInputStream.readInt();
/* 25 */     this.b = DataWatcher.a(paramDataInputStream);
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 29 */     paramDataOutputStream.writeInt(this.a);
/* 30 */     DataWatcher.a(this.b, paramDataOutputStream);
/*    */   }
/*    */ 
/*    */   
/* 34 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 38 */   public int a() { return 5; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet40EntityMetadata.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */