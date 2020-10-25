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
/*    */ public class Packet27
/*    */   extends Packet
/*    */ {
/*    */   private float a;
/*    */   private float b;
/*    */   private boolean c;
/*    */   private boolean d;
/*    */   private float e;
/*    */   private float f;
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 29 */     this.a = paramDataInputStream.readFloat();
/* 30 */     this.b = paramDataInputStream.readFloat();
/* 31 */     this.e = paramDataInputStream.readFloat();
/* 32 */     this.f = paramDataInputStream.readFloat();
/* 33 */     this.c = paramDataInputStream.readBoolean();
/* 34 */     this.d = paramDataInputStream.readBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 39 */     paramDataOutputStream.writeFloat(this.a);
/* 40 */     paramDataOutputStream.writeFloat(this.b);
/* 41 */     paramDataOutputStream.writeFloat(this.e);
/* 42 */     paramDataOutputStream.writeFloat(this.f);
/* 43 */     paramDataOutputStream.writeBoolean(this.c);
/* 44 */     paramDataOutputStream.writeBoolean(this.d);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 49 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 54 */   public int a() { return 18; }
/*    */ 
/*    */ 
/*    */   
/* 58 */   public float c() { return this.a; }
/*    */ 
/*    */ 
/*    */   
/* 62 */   public float d() { return this.e; }
/*    */ 
/*    */ 
/*    */   
/* 66 */   public float e() { return this.b; }
/*    */ 
/*    */ 
/*    */   
/* 70 */   public float f() { return this.f; }
/*    */ 
/*    */ 
/*    */   
/* 74 */   public boolean g() { return this.c; }
/*    */ 
/*    */ 
/*    */   
/* 78 */   public boolean h() { return this.d; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet27.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */