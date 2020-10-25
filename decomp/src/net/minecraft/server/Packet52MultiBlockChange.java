/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ public class Packet52MultiBlockChange
/*    */   extends Packet
/*    */ {
/*    */   public int a;
/*    */   public int b;
/*    */   public short[] c;
/*    */   public byte[] d;
/*    */   public byte[] e;
/*    */   public int f;
/*    */   
/* 16 */   public Packet52MultiBlockChange() { this.k = true; }
/*    */ 
/*    */   
/*    */   public Packet52MultiBlockChange(int paramInt1, int paramInt2, short[] paramArrayOfShort, int paramInt3, World paramWorld) {
/* 20 */     this.k = true;
/* 21 */     this.a = paramInt1;
/* 22 */     this.b = paramInt2;
/* 23 */     this.f = paramInt3;
/* 24 */     this.c = new short[paramInt3];
/* 25 */     this.d = new byte[paramInt3];
/* 26 */     this.e = new byte[paramInt3];
/* 27 */     Chunk chunk = paramWorld.getChunkAt(paramInt1, paramInt2);
/* 28 */     for (byte b1 = 0; b1 < paramInt3; b1++) {
/* 29 */       short s1 = paramArrayOfShort[b1] >> 12 & 0xF;
/* 30 */       short s2 = paramArrayOfShort[b1] >> 8 & 0xF;
/* 31 */       short s3 = paramArrayOfShort[b1] & 0xFF;
/*    */       
/* 33 */       this.c[b1] = paramArrayOfShort[b1];
/* 34 */       this.d[b1] = (byte)chunk.getTypeId(s1, s3, s2);
/* 35 */       this.e[b1] = (byte)chunk.getData(s1, s3, s2);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 40 */     this.a = paramDataInputStream.readInt();
/* 41 */     this.b = paramDataInputStream.readInt();
/*    */     
/* 43 */     this.f = paramDataInputStream.readShort() & 0xFFFF;
/* 44 */     this.c = new short[this.f];
/* 45 */     this.d = new byte[this.f];
/* 46 */     this.e = new byte[this.f];
/* 47 */     for (byte b1 = 0; b1 < this.f; b1++) {
/* 48 */       this.c[b1] = paramDataInputStream.readShort();
/*    */     }
/* 50 */     paramDataInputStream.readFully(this.d);
/* 51 */     paramDataInputStream.readFully(this.e);
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 55 */     paramDataOutputStream.writeInt(this.a);
/* 56 */     paramDataOutputStream.writeInt(this.b);
/* 57 */     paramDataOutputStream.writeShort((short)this.f);
/* 58 */     for (byte b1 = 0; b1 < this.f; b1++) {
/* 59 */       paramDataOutputStream.writeShort(this.c[b1]);
/*    */     }
/* 61 */     paramDataOutputStream.write(this.d);
/* 62 */     paramDataOutputStream.write(this.e);
/*    */   }
/*    */ 
/*    */   
/* 66 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 70 */   public int a() { return 10 + this.f * 4; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet52MultiBlockChange.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */