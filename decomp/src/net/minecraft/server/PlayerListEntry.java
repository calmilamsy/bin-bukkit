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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PlayerListEntry
/*     */ {
/*     */   final long a;
/*     */   Object b;
/*     */   PlayerListEntry c;
/*     */   final int d;
/*     */   
/*     */   PlayerListEntry(int paramInt, long paramLong, Object paramObject, PlayerListEntry paramPlayerListEntry) {
/* 181 */     this.b = paramObject;
/* 182 */     this.c = paramPlayerListEntry;
/* 183 */     this.a = paramLong;
/* 184 */     this.d = paramInt;
/*     */   }
/*     */ 
/*     */   
/* 188 */   public final long a() { return this.a; }
/*     */ 
/*     */ 
/*     */   
/* 192 */   public final Object b() { return this.b; }
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean equals(Object paramObject) {
/* 197 */     if (!(paramObject instanceof PlayerListEntry)) return false; 
/* 198 */     PlayerListEntry playerListEntry = (PlayerListEntry)paramObject;
/* 199 */     Long long1 = Long.valueOf(a());
/* 200 */     Long long2 = Long.valueOf(playerListEntry.a());
/* 201 */     if (long1 == long2 || (long1 != null && long1.equals(long2))) {
/* 202 */       Object object1 = b();
/* 203 */       Object object2 = playerListEntry.b();
/* 204 */       if (object1 == object2 || (object1 != null && object1.equals(object2))) return true; 
/*     */     } 
/* 206 */     return false;
/*     */   }
/*     */ 
/*     */   
/* 210 */   public final int hashCode() { return PlayerList.d(this.a); }
/*     */ 
/*     */ 
/*     */   
/* 214 */   public final String toString() { return a() + "=" + b(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\PlayerListEntry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */