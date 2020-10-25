/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.util.zip.DataFormatException;
/*    */ import java.util.zip.Inflater;
/*    */ 
/*    */ 
/*    */ public class Packet51MapChunk
/*    */   extends Packet
/*    */ {
/*    */   public int a;
/*    */   public int b;
/*    */   public int c;
/*    */   public int d;
/*    */   public int e;
/*    */   public int f;
/*    */   public byte[] g;
/*    */   public int h;
/*    */   public byte[] rawData;
/*    */   
/* 23 */   public Packet51MapChunk() { this.k = true; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 28 */   public Packet51MapChunk(int i, int j, int k, int l, int i1, int j1, World world) { this(i, j, k, l, i1, j1, world.getMultiChunkData(i, j, k, l, i1, j1)); }
/*    */ 
/*    */ 
/*    */   
/*    */   public Packet51MapChunk(int i, int j, int k, int l, int i1, int j1, byte[] data) {
/* 33 */     this.k = true;
/* 34 */     this.a = i;
/* 35 */     this.b = j;
/* 36 */     this.c = k;
/* 37 */     this.d = l;
/* 38 */     this.e = i1;
/* 39 */     this.f = j1;
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
/* 52 */     this.rawData = data;
/*    */   }
/*    */   
/*    */   public void a(DataInputStream datainputstream) throws IOException {
/* 56 */     this.a = datainputstream.readInt();
/* 57 */     this.b = datainputstream.readShort();
/* 58 */     this.c = datainputstream.readInt();
/* 59 */     this.d = datainputstream.read() + 1;
/* 60 */     this.e = datainputstream.read() + 1;
/* 61 */     this.f = datainputstream.read() + 1;
/* 62 */     this.h = datainputstream.readInt();
/* 63 */     byte[] abyte = new byte[this.h];
/*    */     
/* 65 */     datainputstream.readFully(abyte);
/* 66 */     this.g = new byte[this.d * this.e * this.f * 5 / 2];
/* 67 */     inflater = new Inflater();
/*    */     
/* 69 */     inflater.setInput(abyte);
/*    */     
/*    */     try {
/* 72 */       inflater.inflate(this.g);
/* 73 */     } catch (DataFormatException dataformatexception) {
/* 74 */       throw new IOException("Bad compressed data format");
/*    */     } finally {
/* 76 */       inflater.end();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream dataoutputstream) throws IOException {
/* 81 */     dataoutputstream.writeInt(this.a);
/* 82 */     dataoutputstream.writeShort(this.b);
/* 83 */     dataoutputstream.writeInt(this.c);
/* 84 */     dataoutputstream.write(this.d - 1);
/* 85 */     dataoutputstream.write(this.e - 1);
/* 86 */     dataoutputstream.write(this.f - 1);
/* 87 */     dataoutputstream.writeInt(this.h);
/* 88 */     dataoutputstream.write(this.g, 0, this.h);
/*    */   }
/*    */ 
/*    */   
/* 92 */   public void a(NetHandler nethandler) { nethandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 96 */   public int a() { return 17 + this.h; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet51MapChunk.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */