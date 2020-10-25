/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Packet12PlayerLook
/*     */   extends Packet10Flying
/*     */ {
/*     */   public void a(DataInputStream paramDataInputStream) {
/*  97 */     this.yaw = paramDataInputStream.readFloat();
/*  98 */     this.pitch = paramDataInputStream.readFloat();
/*  99 */     super.a(paramDataInputStream);
/*     */   }
/*     */   
/*     */   public void a(DataOutputStream paramDataOutputStream) {
/* 103 */     paramDataOutputStream.writeFloat(this.yaw);
/* 104 */     paramDataOutputStream.writeFloat(this.pitch);
/* 105 */     super.a(paramDataOutputStream);
/*     */   }
/*     */ 
/*     */   
/* 109 */   public int a() { return 9; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet12PlayerLook.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */