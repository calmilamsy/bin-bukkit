/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public class TileEntityPiston
/*     */   extends TileEntity {
/*     */   private int a;
/*     */   private int b;
/*     */   private int c;
/*     */   private boolean i;
/*     */   private boolean j;
/*     */   private float k;
/*     */   private float l;
/*  16 */   private static List m = new ArrayList();
/*     */   
/*     */   public TileEntityPiston() {}
/*     */   
/*     */   public TileEntityPiston(int i, int j, int k, boolean flag, boolean flag1) {
/*  21 */     this.a = i;
/*  22 */     this.b = j;
/*  23 */     this.c = k;
/*  24 */     this.i = flag;
/*  25 */     this.j = flag1;
/*     */   }
/*     */ 
/*     */   
/*  29 */   public int a() { return this.a; }
/*     */ 
/*     */ 
/*     */   
/*  33 */   public int e() { return this.b; }
/*     */ 
/*     */ 
/*     */   
/*  37 */   public boolean c() { return this.i; }
/*     */ 
/*     */ 
/*     */   
/*  41 */   public int d() { return this.c; }
/*     */ 
/*     */   
/*     */   public float a(float f) {
/*  45 */     if (f > 1.0F) {
/*  46 */       f = 1.0F;
/*     */     }
/*     */     
/*  49 */     return this.l + (this.k - this.l) * f;
/*     */   }
/*     */   
/*     */   private void a(float f, float f1) {
/*  53 */     if (!this.i) {
/*  54 */       f--;
/*     */     } else {
/*  56 */       f = 1.0F - f;
/*     */     } 
/*     */     
/*  59 */     AxisAlignedBB axisalignedbb = Block.PISTON_MOVING.a(this.world, this.x, this.y, this.z, this.a, f, this.c);
/*     */     
/*  61 */     if (axisalignedbb != null) {
/*  62 */       List list = this.world.b((Entity)null, axisalignedbb);
/*     */       
/*  64 */       if (!list.isEmpty()) {
/*  65 */         m.addAll(list);
/*  66 */         Iterator iterator = m.iterator();
/*     */         
/*  68 */         while (iterator.hasNext()) {
/*  69 */           Entity entity = (Entity)iterator.next();
/*     */           
/*  71 */           entity.move((f1 * PistonBlockTextures.b[this.c]), (f1 * PistonBlockTextures.c[this.c]), (f1 * PistonBlockTextures.d[this.c]));
/*     */         } 
/*     */         
/*  74 */         m.clear();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void k() {
/*  80 */     if (this.l < 1.0F) {
/*  81 */       this.l = this.k = 1.0F;
/*  82 */       this.world.o(this.x, this.y, this.z);
/*  83 */       h();
/*  84 */       if (this.world.getTypeId(this.x, this.y, this.z) == Block.PISTON_MOVING.id) {
/*  85 */         this.world.setTypeIdAndData(this.x, this.y, this.z, this.a, this.b);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void g_() {
/*  92 */     if (this.world == null)
/*  93 */       return;  this.l = this.k;
/*  94 */     if (this.l >= 1.0F) {
/*  95 */       a(1.0F, 0.25F);
/*  96 */       this.world.o(this.x, this.y, this.z);
/*  97 */       h();
/*  98 */       if (this.world.getTypeId(this.x, this.y, this.z) == Block.PISTON_MOVING.id) {
/*  99 */         this.world.setTypeIdAndData(this.x, this.y, this.z, this.a, this.b);
/*     */       }
/*     */     } else {
/* 102 */       this.k += 0.5F;
/* 103 */       if (this.k >= 1.0F) {
/* 104 */         this.k = 1.0F;
/*     */       }
/*     */       
/* 107 */       if (this.i) {
/* 108 */         a(this.k, this.k - this.l + 0.0625F);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 114 */     super.a(nbttagcompound);
/* 115 */     this.a = nbttagcompound.e("blockId");
/* 116 */     this.b = nbttagcompound.e("blockData");
/* 117 */     this.c = nbttagcompound.e("facing");
/* 118 */     this.l = this.k = nbttagcompound.g("progress");
/* 119 */     this.i = nbttagcompound.m("extending");
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 123 */     super.b(nbttagcompound);
/* 124 */     nbttagcompound.a("blockId", this.a);
/* 125 */     nbttagcompound.a("blockData", this.b);
/* 126 */     nbttagcompound.a("facing", this.c);
/* 127 */     nbttagcompound.a("progress", this.l);
/* 128 */     nbttagcompound.a("extending", this.i);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\TileEntityPiston.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */