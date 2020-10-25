/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ public class Packet53BlockChange
/*    */   extends Packet
/*    */ {
/*    */   public int a;
/*    */   public int b;
/*    */   
/* 12 */   public Packet53BlockChange() { this.k = true; }
/*    */   public int c; public int material; public int data;
/*    */   
/*    */   public Packet53BlockChange(int paramInt1, int paramInt2, int paramInt3, World paramWorld) {
/* 16 */     this.k = true;
/* 17 */     this.a = paramInt1;
/* 18 */     this.b = paramInt2;
/* 19 */     this.c = paramInt3;
/* 20 */     this.material = paramWorld.getTypeId(paramInt1, paramInt2, paramInt3);
/* 21 */     this.data = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/*    */   }
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 25 */     this.a = paramDataInputStream.readInt();
/* 26 */     this.b = paramDataInputStream.read();
/* 27 */     this.c = paramDataInputStream.readInt();
/* 28 */     this.material = paramDataInputStream.read();
/* 29 */     this.data = paramDataInputStream.read();
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 33 */     paramDataOutputStream.writeInt(this.a);
/* 34 */     paramDataOutputStream.write(this.b);
/* 35 */     paramDataOutputStream.writeInt(this.c);
/* 36 */     paramDataOutputStream.write(this.material);
/* 37 */     paramDataOutputStream.write(this.data);
/*    */   }
/*    */ 
/*    */   
/* 41 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 45 */   public int a() { return 11; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet53BlockChange.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */