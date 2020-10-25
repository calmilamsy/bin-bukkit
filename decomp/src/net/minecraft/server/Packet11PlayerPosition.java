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
/*    */ public class Packet11PlayerPosition
/*    */   extends Packet10Flying
/*    */ {
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 64 */     this.x = paramDataInputStream.readDouble();
/* 65 */     this.y = paramDataInputStream.readDouble();
/* 66 */     this.stance = paramDataInputStream.readDouble();
/* 67 */     this.z = paramDataInputStream.readDouble();
/* 68 */     super.a(paramDataInputStream);
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 72 */     paramDataOutputStream.writeDouble(this.x);
/* 73 */     paramDataOutputStream.writeDouble(this.y);
/* 74 */     paramDataOutputStream.writeDouble(this.stance);
/* 75 */     paramDataOutputStream.writeDouble(this.z);
/* 76 */     super.a(paramDataOutputStream);
/*    */   }
/*    */ 
/*    */   
/* 80 */   public int a() { return 33; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet11PlayerPosition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */