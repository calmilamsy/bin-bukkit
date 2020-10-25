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
/*     */ class EntityListEntry
/*     */ {
/*     */   final int a;
/*     */   Object b;
/*     */   EntityListEntry c;
/*     */   final int d;
/*     */   
/*     */   EntityListEntry(int paramInt1, int paramInt2, Object paramObject, EntityListEntry paramEntityListEntry) {
/* 177 */     this.b = paramObject;
/* 178 */     this.c = paramEntityListEntry;
/* 179 */     this.a = paramInt2;
/* 180 */     this.d = paramInt1;
/*     */   }
/*     */ 
/*     */   
/* 184 */   public final int a() { return this.a; }
/*     */ 
/*     */ 
/*     */   
/* 188 */   public final Object b() { return this.b; }
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean equals(Object paramObject) {
/* 193 */     if (!(paramObject instanceof EntityListEntry)) return false; 
/* 194 */     EntityListEntry entityListEntry = (EntityListEntry)paramObject;
/* 195 */     Integer integer1 = Integer.valueOf(a());
/* 196 */     Integer integer2 = Integer.valueOf(entityListEntry.a());
/* 197 */     if (integer1 == integer2 || (integer1 != null && integer1.equals(integer2))) {
/* 198 */       Object object1 = b();
/* 199 */       Object object2 = entityListEntry.b();
/* 200 */       if (object1 == object2 || (object1 != null && object1.equals(object2))) return true; 
/*     */     } 
/* 202 */     return false;
/*     */   }
/*     */ 
/*     */   
/* 206 */   public final int hashCode() { return EntityList.f(this.a); }
/*     */ 
/*     */ 
/*     */   
/* 210 */   public final String toString() { return a() + "=" + b(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntityListEntry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */