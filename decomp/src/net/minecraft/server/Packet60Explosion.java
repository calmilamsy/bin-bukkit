/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ public class Packet60Explosion
/*    */   extends Packet
/*    */ {
/*    */   public double a;
/*    */   public double b;
/*    */   public double c;
/*    */   
/*    */   public Packet60Explosion(double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat, Set paramSet) {
/* 17 */     this.a = paramDouble1;
/* 18 */     this.b = paramDouble2;
/* 19 */     this.c = paramDouble3;
/* 20 */     this.d = paramFloat;
/* 21 */     this.e = new HashSet(paramSet);
/*    */   } public float d; public Set e;
/*    */   public Packet60Explosion() {}
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 25 */     this.a = paramDataInputStream.readDouble();
/* 26 */     this.b = paramDataInputStream.readDouble();
/* 27 */     this.c = paramDataInputStream.readDouble();
/* 28 */     this.d = paramDataInputStream.readFloat();
/* 29 */     int i = paramDataInputStream.readInt();
/*    */     
/* 31 */     this.e = new HashSet();
/*    */     
/* 33 */     int j = (int)this.a;
/* 34 */     int k = (int)this.b;
/* 35 */     int m = (int)this.c;
/* 36 */     for (byte b1 = 0; b1 < i; b1++) {
/* 37 */       byte b2 = paramDataInputStream.readByte() + j;
/* 38 */       byte b3 = paramDataInputStream.readByte() + k;
/* 39 */       byte b4 = paramDataInputStream.readByte() + m;
/* 40 */       this.e.add(new ChunkPosition(b2, b3, b4));
/*    */     } 
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 45 */     paramDataOutputStream.writeDouble(this.a);
/* 46 */     paramDataOutputStream.writeDouble(this.b);
/* 47 */     paramDataOutputStream.writeDouble(this.c);
/* 48 */     paramDataOutputStream.writeFloat(this.d);
/* 49 */     paramDataOutputStream.writeInt(this.e.size());
/*    */     
/* 51 */     int i = (int)this.a;
/* 52 */     int j = (int)this.b;
/* 53 */     int k = (int)this.c;
/* 54 */     for (ChunkPosition chunkPosition : this.e) {
/* 55 */       int m = chunkPosition.x - i;
/* 56 */       int n = chunkPosition.y - j;
/* 57 */       int i1 = chunkPosition.z - k;
/* 58 */       paramDataOutputStream.writeByte(m);
/* 59 */       paramDataOutputStream.writeByte(n);
/* 60 */       paramDataOutputStream.writeByte(i1);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 65 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 69 */   public int a() { return 32 + this.e.size() * 3; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet60Explosion.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */