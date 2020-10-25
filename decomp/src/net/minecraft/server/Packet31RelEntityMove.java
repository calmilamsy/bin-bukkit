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
/*    */ public class Packet31RelEntityMove
/*    */   extends Packet30Entity
/*    */ {
/*    */   public Packet31RelEntityMove() {}
/*    */   
/*    */   public Packet31RelEntityMove(int paramInt, byte paramByte1, byte paramByte2, byte paramByte3) {
/* 49 */     super(paramInt);
/* 50 */     this.b = paramByte1;
/* 51 */     this.c = paramByte2;
/* 52 */     this.d = paramByte3;
/*    */   }
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 56 */     super.a(paramDataInputStream);
/* 57 */     this.b = paramDataInputStream.readByte();
/* 58 */     this.c = paramDataInputStream.readByte();
/* 59 */     this.d = paramDataInputStream.readByte();
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 63 */     super.a(paramDataOutputStream);
/* 64 */     paramDataOutputStream.writeByte(this.b);
/* 65 */     paramDataOutputStream.writeByte(this.c);
/* 66 */     paramDataOutputStream.writeByte(this.d);
/*    */   }
/*    */ 
/*    */   
/* 70 */   public int a() { return 7; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet31RelEntityMove.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */