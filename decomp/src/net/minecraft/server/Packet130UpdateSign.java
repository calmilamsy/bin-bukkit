/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ public class Packet130UpdateSign extends Packet {
/*    */   public int x;
/*    */   public int y;
/*    */   public int z;
/*    */   public String[] lines;
/*    */   
/* 12 */   public Packet130UpdateSign() { this.k = true; }
/*    */ 
/*    */   
/*    */   public Packet130UpdateSign(int paramInt1, int paramInt2, int paramInt3, String[] paramArrayOfString) {
/* 16 */     this.k = true;
/* 17 */     this.x = paramInt1;
/* 18 */     this.y = paramInt2;
/* 19 */     this.z = paramInt3;
/* 20 */     this.lines = paramArrayOfString;
/*    */   }
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 24 */     this.x = paramDataInputStream.readInt();
/* 25 */     this.y = paramDataInputStream.readShort();
/* 26 */     this.z = paramDataInputStream.readInt();
/* 27 */     this.lines = new String[4];
/* 28 */     for (byte b = 0; b < 4; b++)
/* 29 */       this.lines[b] = a(paramDataInputStream, 15); 
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 33 */     paramDataOutputStream.writeInt(this.x);
/* 34 */     paramDataOutputStream.writeShort(this.y);
/* 35 */     paramDataOutputStream.writeInt(this.z);
/* 36 */     for (byte b = 0; b < 4; b++) {
/* 37 */       a(this.lines[b], paramDataOutputStream);
/*    */     }
/*    */   }
/*    */   
/* 41 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */   
/*    */   public int a() {
/* 45 */     int i = 0;
/* 46 */     for (byte b = 0; b < 4; b++)
/* 47 */       i += this.lines[b].length(); 
/* 48 */     return i;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet130UpdateSign.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */