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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Packet32EntityLook
/*    */   extends Packet30Entity
/*    */ {
/* 76 */   public Packet32EntityLook() { this.g = true; }
/*    */ 
/*    */   
/*    */   public Packet32EntityLook(int paramInt, byte paramByte1, byte paramByte2) {
/* 80 */     super(paramInt);
/* 81 */     this.e = paramByte1;
/* 82 */     this.f = paramByte2;
/* 83 */     this.g = true;
/*    */   }
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 87 */     super.a(paramDataInputStream);
/* 88 */     this.e = paramDataInputStream.readByte();
/* 89 */     this.f = paramDataInputStream.readByte();
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 93 */     super.a(paramDataOutputStream);
/* 94 */     paramDataOutputStream.writeByte(this.e);
/* 95 */     paramDataOutputStream.writeByte(this.f);
/*    */   }
/*    */ 
/*    */   
/* 99 */   public int a() { return 6; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet32EntityLook.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */