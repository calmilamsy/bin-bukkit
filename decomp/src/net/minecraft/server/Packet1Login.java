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
/*    */ public class Packet1Login
/*    */   extends Packet
/*    */ {
/*    */   public int a;
/*    */   public String name;
/*    */   public long c;
/*    */   public byte d;
/*    */   
/*    */   public Packet1Login() {}
/*    */   
/*    */   public Packet1Login(String paramString, int paramInt, long paramLong, byte paramByte) {
/* 22 */     this.name = paramString;
/* 23 */     this.a = paramInt;
/* 24 */     this.c = paramLong;
/* 25 */     this.d = paramByte;
/*    */   }
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 29 */     this.a = paramDataInputStream.readInt();
/* 30 */     this.name = a(paramDataInputStream, 16);
/* 31 */     this.c = paramDataInputStream.readLong();
/* 32 */     this.d = paramDataInputStream.readByte();
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 36 */     paramDataOutputStream.writeInt(this.a);
/* 37 */     a(this.name, paramDataOutputStream);
/* 38 */     paramDataOutputStream.writeLong(this.c);
/* 39 */     paramDataOutputStream.writeByte(this.d);
/*    */   }
/*    */ 
/*    */   
/* 43 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 47 */   public int a() { return 4 + this.name.length() + 4 + 5; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet1Login.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */