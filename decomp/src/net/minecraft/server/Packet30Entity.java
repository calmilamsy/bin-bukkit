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
/*     */ public class Packet30Entity
/*     */   extends Packet
/*     */ {
/*     */   public int a;
/*     */   public byte b;
/*     */   public byte c;
/*     */   public byte d;
/*     */   public byte e;
/*     */   public byte f;
/*     */   public boolean g = false;
/*     */   
/*     */   public Packet30Entity() {}
/*     */   
/* 111 */   public Packet30Entity(int paramInt) { this.a = paramInt; }
/*     */ 
/*     */ 
/*     */   
/* 115 */   public void a(DataInputStream paramDataInputStream) { this.a = paramDataInputStream.readInt(); }
/*     */ 
/*     */ 
/*     */   
/* 119 */   public void a(DataOutputStream paramDataOutputStream) { paramDataOutputStream.writeInt(this.a); }
/*     */ 
/*     */ 
/*     */   
/* 123 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*     */ 
/*     */ 
/*     */   
/* 127 */   public int a() { return 4; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet30Entity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */