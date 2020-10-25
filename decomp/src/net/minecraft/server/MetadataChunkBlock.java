/*     */ package net.minecraft.server;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetadataChunkBlock
/*     */ {
/*     */   public final EnumSkyBlock a;
/*     */   public int b;
/*     */   public int c;
/*     */   
/*     */   public MetadataChunkBlock(EnumSkyBlock paramEnumSkyBlock, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  12 */     this.a = paramEnumSkyBlock;
/*  13 */     this.b = paramInt1;
/*  14 */     this.c = paramInt2;
/*  15 */     this.d = paramInt3;
/*  16 */     this.e = paramInt4;
/*  17 */     this.f = paramInt5;
/*  18 */     this.g = paramInt6;
/*     */   }
/*     */   public int d; public int e; public int f; public int g;
/*     */   public void a(World paramWorld) {
/*  22 */     int i = this.e - this.b + 1;
/*  23 */     int j = this.f - this.c + 1;
/*  24 */     int k = this.g - this.d + 1;
/*  25 */     int m = i * j * k;
/*  26 */     if (m > 32768) {
/*  27 */       System.out.println("Light too large, skipping!");
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  32 */     int n = 0;
/*  33 */     int i1 = 0;
/*  34 */     boolean bool = false;
/*  35 */     byte b1 = 0;
/*     */     
/*  37 */     for (int i2 = this.b; i2 <= this.e; i2++) {
/*  38 */       for (int i3 = this.d; i3 <= this.g; i3++) {
/*  39 */         int i4 = i2 >> 4;
/*  40 */         int i5 = i3 >> 4;
/*  41 */         boolean bool1 = false;
/*     */         
/*  43 */         if (bool && i4 == n && i5 == i1) {
/*  44 */           bool1 = b1;
/*     */         } else {
/*  46 */           bool1 = paramWorld.areChunksLoaded(i2, 0, i3, 1);
/*  47 */           if (bool1) {
/*  48 */             Chunk chunk = paramWorld.getChunkAt(i2 >> 4, i3 >> 4);
/*  49 */             if (chunk.isEmpty()) bool1 = false; 
/*     */           } 
/*  51 */           boolean bool2 = bool1;
/*  52 */           n = i4;
/*  53 */           i1 = i5;
/*     */         } 
/*     */         
/*  56 */         if (bool1) {
/*     */           
/*  58 */           if (this.c < 0) this.c = 0; 
/*  59 */           if (this.f >= 128) this.f = 127;
/*     */           
/*  61 */           for (int i6 = this.c; i6 <= this.f; i6++) {
/*  62 */             int i7 = paramWorld.a(this.a, i2, i6, i3);
/*     */             
/*  64 */             int i8 = 0;
/*  65 */             int i9 = paramWorld.getTypeId(i2, i6, i3);
/*  66 */             int i10 = Block.q[i9];
/*  67 */             if (i10 == 0) i10 = 1; 
/*  68 */             int i11 = 0;
/*  69 */             if (this.a == EnumSkyBlock.SKY) {
/*  70 */               if (paramWorld.m(i2, i6, i3)) i11 = 15; 
/*  71 */             } else if (this.a == EnumSkyBlock.BLOCK) {
/*  72 */               i11 = Block.s[i9];
/*     */             } 
/*     */             
/*  75 */             if (i10 >= 15 && i11 == 0) {
/*  76 */               i8 = 0;
/*     */             } else {
/*     */               
/*  79 */               int i12 = paramWorld.a(this.a, i2 - 1, i6, i3);
/*  80 */               int i13 = paramWorld.a(this.a, i2 + 1, i6, i3);
/*  81 */               int i14 = paramWorld.a(this.a, i2, i6 - 1, i3);
/*  82 */               int i15 = paramWorld.a(this.a, i2, i6 + 1, i3);
/*  83 */               int i16 = paramWorld.a(this.a, i2, i6, i3 - 1);
/*  84 */               int i17 = paramWorld.a(this.a, i2, i6, i3 + 1);
/*     */               
/*  86 */               i8 = i12;
/*  87 */               if (i13 > i8) i8 = i13; 
/*  88 */               if (i14 > i8) i8 = i14; 
/*  89 */               if (i15 > i8) i8 = i15; 
/*  90 */               if (i16 > i8) i8 = i16; 
/*  91 */               if (i17 > i8) i8 = i17; 
/*  92 */               i8 -= i10;
/*  93 */               if (i8 < 0) i8 = 0;
/*     */               
/*  95 */               if (i11 > i8) i8 = i11;
/*     */             
/*     */             } 
/*     */             
/*  99 */             if (i7 != i8) {
/* 100 */               paramWorld.b(this.a, i2, i6, i3, i8);
/* 101 */               int i12 = i8 - 1;
/* 102 */               if (i12 < 0) i12 = 0; 
/* 103 */               paramWorld.a(this.a, i2 - 1, i6, i3, i12);
/* 104 */               paramWorld.a(this.a, i2, i6 - 1, i3, i12);
/* 105 */               paramWorld.a(this.a, i2, i6, i3 - 1, i12);
/*     */               
/* 107 */               if (i2 + 1 >= this.e) paramWorld.a(this.a, i2 + 1, i6, i3, i12); 
/* 108 */               if (i6 + 1 >= this.f) paramWorld.a(this.a, i2, i6 + 1, i3, i12); 
/* 109 */               if (i3 + 1 >= this.g) paramWorld.a(this.a, i2, i6, i3 + 1, i12);
/*     */             
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 123 */     if (paramInt1 >= this.b && paramInt2 >= this.c && paramInt3 >= this.d && paramInt4 <= this.e && paramInt5 <= this.f && paramInt6 <= this.g) return true;
/*     */     
/* 125 */     int i = 1;
/* 126 */     if (paramInt1 >= this.b - i && paramInt2 >= this.c - i && paramInt3 >= this.d - i && paramInt4 <= this.e + i && paramInt5 <= this.f + i && paramInt6 <= this.g + i) {
/* 127 */       int j = this.e - this.b;
/* 128 */       int k = this.f - this.c;
/* 129 */       int m = this.g - this.d;
/*     */ 
/*     */ 
/*     */       
/* 133 */       if (paramInt1 > this.b) paramInt1 = this.b; 
/* 134 */       if (paramInt2 > this.c) paramInt2 = this.c; 
/* 135 */       if (paramInt3 > this.d) paramInt3 = this.d; 
/* 136 */       if (paramInt4 < this.e) paramInt4 = this.e; 
/* 137 */       if (paramInt5 < this.f) paramInt5 = this.f; 
/* 138 */       if (paramInt6 < this.g) paramInt6 = this.g;
/*     */       
/* 140 */       int n = paramInt4 - paramInt1;
/* 141 */       int i1 = paramInt5 - paramInt2;
/* 142 */       int i2 = paramInt6 - paramInt3;
/*     */       
/* 144 */       int i3 = j * k * m;
/* 145 */       int i4 = n * i1 * i2;
/* 146 */       if (i4 - i3 <= 2) {
/* 147 */         this.b = paramInt1;
/* 148 */         this.c = paramInt2;
/* 149 */         this.d = paramInt3;
/* 150 */         this.e = paramInt4;
/* 151 */         this.f = paramInt5;
/* 152 */         this.g = paramInt6;
/* 153 */         return true;
/*     */       } 
/*     */     } 
/* 156 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\MetadataChunkBlock.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */