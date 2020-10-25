/*     */ package net.minecraft.server;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityList
/*     */ {
/*  18 */   private final float d = 0.75F;
/*  19 */   private int c = 12;
/*  20 */   private EntityListEntry[] a = new EntityListEntry[16];
/*     */   private int b;
/*     */   
/*     */   private static int g(int paramInt) {
/*  24 */     paramInt ^= paramInt >>> 20 ^ paramInt >>> 12;
/*  25 */     return paramInt ^ paramInt >>> 7 ^ paramInt >>> 4;
/*     */   }
/*     */ 
/*     */   
/*  29 */   private static int a(int paramInt1, int paramInt2) { return paramInt1 & paramInt2 - 1; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object a(int paramInt) {
/*  41 */     int i = g(paramInt);
/*  42 */     for (EntityListEntry entityListEntry = this.a[a(i, this.a.length)]; entityListEntry != null; entityListEntry = entityListEntry.c) {
/*  43 */       if (entityListEntry.a == paramInt) return entityListEntry.b; 
/*     */     } 
/*  45 */     return null;
/*     */   }
/*     */ 
/*     */   
/*  49 */   public boolean b(int paramInt) { return (c(paramInt) != null); }
/*     */ 
/*     */   
/*     */   final EntityListEntry c(int paramInt) {
/*  53 */     int i = g(paramInt);
/*  54 */     for (EntityListEntry entityListEntry = this.a[a(i, this.a.length)]; entityListEntry != null; entityListEntry = entityListEntry.c) {
/*  55 */       if (entityListEntry.a == paramInt) return entityListEntry; 
/*     */     } 
/*  57 */     return null;
/*     */   }
/*     */   
/*     */   public void a(int paramInt, Object paramObject) {
/*  61 */     int i = g(paramInt);
/*  62 */     int j = a(i, this.a.length);
/*  63 */     for (EntityListEntry entityListEntry = this.a[j]; entityListEntry != null; entityListEntry = entityListEntry.c) {
/*  64 */       if (entityListEntry.a == paramInt) {
/*  65 */         entityListEntry.b = paramObject;
/*     */       }
/*     */     } 
/*     */     
/*  69 */     this.e++;
/*  70 */     a(i, paramInt, paramObject, j);
/*     */   }
/*     */ 
/*     */   
/*     */   private void h(int paramInt) {
/*  75 */     EntityListEntry[] arrayOfEntityListEntry1 = this.a;
/*  76 */     int i = arrayOfEntityListEntry1.length;
/*  77 */     if (i == 1073741824) {
/*  78 */       this.c = Integer.MAX_VALUE;
/*     */       
/*     */       return;
/*     */     } 
/*  82 */     EntityListEntry[] arrayOfEntityListEntry2 = new EntityListEntry[paramInt];
/*  83 */     a(arrayOfEntityListEntry2);
/*  84 */     this.a = arrayOfEntityListEntry2;
/*  85 */     this.c = (int)(paramInt * this.d);
/*     */   }
/*     */   
/*     */   private void a(EntityListEntry[] paramArrayOfEntityListEntry) {
/*  89 */     EntityListEntry[] arrayOfEntityListEntry = this.a;
/*  90 */     int i = paramArrayOfEntityListEntry.length;
/*  91 */     for (byte b1 = 0; b1 < arrayOfEntityListEntry.length; b1++) {
/*  92 */       EntityListEntry entityListEntry = arrayOfEntityListEntry[b1];
/*  93 */       if (entityListEntry != null) {
/*  94 */         arrayOfEntityListEntry[b1] = null;
/*     */         do {
/*  96 */           EntityListEntry entityListEntry1 = entityListEntry.c;
/*  97 */           int j = a(entityListEntry.d, i);
/*  98 */           entityListEntry.c = paramArrayOfEntityListEntry[j];
/*  99 */           paramArrayOfEntityListEntry[j] = entityListEntry;
/* 100 */           entityListEntry = entityListEntry1;
/* 101 */         } while (entityListEntry != null);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object d(int paramInt) {
/* 107 */     EntityListEntry entityListEntry = e(paramInt);
/* 108 */     return (entityListEntry == null) ? null : entityListEntry.b;
/*     */   }
/*     */   
/*     */   final EntityListEntry e(int paramInt) {
/* 112 */     int i = g(paramInt);
/* 113 */     int j = a(i, this.a.length);
/* 114 */     EntityListEntry entityListEntry1 = this.a[j];
/* 115 */     EntityListEntry entityListEntry2 = entityListEntry1;
/*     */     
/* 117 */     while (entityListEntry2 != null) {
/* 118 */       EntityListEntry entityListEntry = entityListEntry2.c;
/* 119 */       if (entityListEntry2.a == paramInt) {
/* 120 */         this.e++;
/* 121 */         this.b--;
/* 122 */         if (entityListEntry1 == entityListEntry2) { this.a[j] = entityListEntry; }
/* 123 */         else { entityListEntry1.c = entityListEntry; }
/* 124 */          return entityListEntry2;
/*     */       } 
/* 126 */       entityListEntry1 = entityListEntry2;
/* 127 */       entityListEntry2 = entityListEntry;
/*     */     } 
/*     */     
/* 130 */     return entityListEntry2;
/*     */   }
/*     */   
/*     */   public void a() {
/* 134 */     this.e++;
/* 135 */     EntityListEntry[] arrayOfEntityListEntry = this.a;
/* 136 */     for (byte b1 = 0; b1 < arrayOfEntityListEntry.length; b1++)
/* 137 */       arrayOfEntityListEntry[b1] = null; 
/* 138 */     this.b = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void a(int paramInt1, int paramInt2, Object paramObject, int paramInt3) {
/* 215 */     EntityListEntry entityListEntry = this.a[paramInt3];
/* 216 */     this.a[paramInt3] = new EntityListEntry(paramInt1, paramInt2, paramObject, entityListEntry);
/* 217 */     if (this.b++ >= this.c) h(2 * this.a.length); 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntityList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */