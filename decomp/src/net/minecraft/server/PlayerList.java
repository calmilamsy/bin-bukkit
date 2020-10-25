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
/*     */ public class PlayerList
/*     */ {
/*  18 */   private final float d = 0.75F;
/*  19 */   private int c = 12;
/*  20 */   private PlayerListEntry[] a = new PlayerListEntry[16];
/*     */   
/*     */   private int b;
/*     */   
/*  24 */   private static int e(long paramLong) { return a((int)(paramLong ^ paramLong >>> 32)); }
/*     */ 
/*     */   
/*     */   private static int a(int paramInt) {
/*  28 */     paramInt ^= paramInt >>> 20 ^ paramInt >>> 12;
/*  29 */     return paramInt ^ paramInt >>> 7 ^ paramInt >>> 4;
/*     */   }
/*     */ 
/*     */   
/*  33 */   private static int a(int paramInt1, int paramInt2) { return paramInt1 & paramInt2 - 1; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object a(long paramLong) {
/*  45 */     int i = e(paramLong);
/*  46 */     for (PlayerListEntry playerListEntry = this.a[a(i, this.a.length)]; playerListEntry != null; playerListEntry = playerListEntry.c) {
/*  47 */       if (playerListEntry.a == paramLong) return playerListEntry.b; 
/*     */     } 
/*  49 */     return null;
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
/*     */   public void a(long paramLong, Object paramObject) {
/*  65 */     int i = e(paramLong);
/*  66 */     int j = a(i, this.a.length);
/*  67 */     for (PlayerListEntry playerListEntry = this.a[j]; playerListEntry != null; playerListEntry = playerListEntry.c) {
/*  68 */       if (playerListEntry.a == paramLong) {
/*  69 */         playerListEntry.b = paramObject;
/*     */       }
/*     */     } 
/*     */     
/*  73 */     this.e++;
/*  74 */     a(i, paramLong, paramObject, j);
/*     */   }
/*     */ 
/*     */   
/*     */   private void b(int paramInt) {
/*  79 */     PlayerListEntry[] arrayOfPlayerListEntry1 = this.a;
/*  80 */     int i = arrayOfPlayerListEntry1.length;
/*  81 */     if (i == 1073741824) {
/*  82 */       this.c = Integer.MAX_VALUE;
/*     */       
/*     */       return;
/*     */     } 
/*  86 */     PlayerListEntry[] arrayOfPlayerListEntry2 = new PlayerListEntry[paramInt];
/*  87 */     a(arrayOfPlayerListEntry2);
/*  88 */     this.a = arrayOfPlayerListEntry2;
/*  89 */     this.c = (int)(paramInt * this.d);
/*     */   }
/*     */   
/*     */   private void a(PlayerListEntry[] paramArrayOfPlayerListEntry) {
/*  93 */     PlayerListEntry[] arrayOfPlayerListEntry = this.a;
/*  94 */     int i = paramArrayOfPlayerListEntry.length;
/*  95 */     for (byte b1 = 0; b1 < arrayOfPlayerListEntry.length; b1++) {
/*  96 */       PlayerListEntry playerListEntry = arrayOfPlayerListEntry[b1];
/*  97 */       if (playerListEntry != null) {
/*  98 */         arrayOfPlayerListEntry[b1] = null;
/*     */         do {
/* 100 */           PlayerListEntry playerListEntry1 = playerListEntry.c;
/* 101 */           int j = a(playerListEntry.d, i);
/* 102 */           playerListEntry.c = paramArrayOfPlayerListEntry[j];
/* 103 */           paramArrayOfPlayerListEntry[j] = playerListEntry;
/* 104 */           playerListEntry = playerListEntry1;
/* 105 */         } while (playerListEntry != null);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object b(long paramLong) {
/* 111 */     PlayerListEntry playerListEntry = c(paramLong);
/* 112 */     return (playerListEntry == null) ? null : playerListEntry.b;
/*     */   }
/*     */   
/*     */   final PlayerListEntry c(long paramLong) {
/* 116 */     int i = e(paramLong);
/* 117 */     int j = a(i, this.a.length);
/* 118 */     PlayerListEntry playerListEntry1 = this.a[j];
/* 119 */     PlayerListEntry playerListEntry2 = playerListEntry1;
/*     */     
/* 121 */     while (playerListEntry2 != null) {
/* 122 */       PlayerListEntry playerListEntry = playerListEntry2.c;
/* 123 */       if (playerListEntry2.a == paramLong) {
/* 124 */         this.e++;
/* 125 */         this.b--;
/* 126 */         if (playerListEntry1 == playerListEntry2) { this.a[j] = playerListEntry; }
/* 127 */         else { playerListEntry1.c = playerListEntry; }
/* 128 */          return playerListEntry2;
/*     */       } 
/* 130 */       playerListEntry1 = playerListEntry2;
/* 131 */       playerListEntry2 = playerListEntry;
/*     */     } 
/*     */     
/* 134 */     return playerListEntry2;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void a(int paramInt1, long paramLong, Object paramObject, int paramInt2) {
/* 219 */     PlayerListEntry playerListEntry = this.a[paramInt2];
/* 220 */     this.a[paramInt2] = new PlayerListEntry(paramInt1, paramLong, paramObject, playerListEntry);
/* 221 */     if (this.b++ >= this.c) b(2 * this.a.length); 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\PlayerList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */