/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ public class Packet5EntityEquipment
/*    */   extends Packet
/*    */ {
/*    */   public int a;
/*    */   public int b;
/*    */   public int c;
/*    */   public int d;
/*    */   
/*    */   public Packet5EntityEquipment() {}
/*    */   
/*    */   public Packet5EntityEquipment(int paramInt1, int paramInt2, ItemStack paramItemStack) {
/* 17 */     this.a = paramInt1;
/* 18 */     this.b = paramInt2;
/* 19 */     if (paramItemStack == null) {
/* 20 */       this.c = -1;
/* 21 */       this.d = 0;
/*    */     } else {
/* 23 */       this.c = paramItemStack.id;
/* 24 */       this.d = paramItemStack.getData();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 29 */     this.a = paramDataInputStream.readInt();
/* 30 */     this.b = paramDataInputStream.readShort();
/* 31 */     this.c = paramDataInputStream.readShort();
/* 32 */     this.d = paramDataInputStream.readShort();
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 36 */     paramDataOutputStream.writeInt(this.a);
/* 37 */     paramDataOutputStream.writeShort(this.b);
/* 38 */     paramDataOutputStream.writeShort(this.c);
/* 39 */     paramDataOutputStream.writeShort(this.d);
/*    */   }
/*    */ 
/*    */   
/* 43 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 47 */   public int a() { return 8; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet5EntityEquipment.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */