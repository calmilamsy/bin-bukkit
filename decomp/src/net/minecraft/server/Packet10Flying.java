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
/*     */ public class Packet10Flying
/*     */   extends Packet
/*     */ {
/*     */   public double x;
/*     */   public double y;
/*     */   public double z;
/*     */   public double stance;
/*     */   public float yaw;
/*     */   public float pitch;
/*     */   public boolean g;
/*     */   public boolean h;
/*     */   public boolean hasLook;
/*     */   
/* 126 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*     */ 
/*     */ 
/*     */   
/* 130 */   public void a(DataInputStream paramDataInputStream) { this.g = (paramDataInputStream.read() != 0); }
/*     */ 
/*     */ 
/*     */   
/* 134 */   public void a(DataOutputStream paramDataOutputStream) { paramDataOutputStream.write(this.g ? 1 : 0); }
/*     */ 
/*     */ 
/*     */   
/* 138 */   public int a() { return 1; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet10Flying.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */