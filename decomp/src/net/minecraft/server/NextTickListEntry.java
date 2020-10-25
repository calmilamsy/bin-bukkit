/*    */ package net.minecraft.server;
/*    */ 
/*    */ public class NextTickListEntry implements Comparable {
/*  4 */   private static long f = 0L; public int a; public int b; public int c;
/*    */   
/*    */   public NextTickListEntry(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  7 */     this.g = f++;
/*    */ 
/*    */     
/* 10 */     this.a = paramInt1;
/* 11 */     this.b = paramInt2;
/* 12 */     this.c = paramInt3;
/* 13 */     this.d = paramInt4;
/*    */   }
/*    */   public int d; public long e; private long g;
/*    */   public boolean equals(Object paramObject) {
/* 17 */     if (paramObject instanceof NextTickListEntry) {
/* 18 */       NextTickListEntry nextTickListEntry = (NextTickListEntry)paramObject;
/* 19 */       return (this.a == nextTickListEntry.a && this.b == nextTickListEntry.b && this.c == nextTickListEntry.c && this.d == nextTickListEntry.d);
/*    */     } 
/* 21 */     return false;
/*    */   }
/*    */ 
/*    */   
/* 25 */   public int hashCode() { return (this.a * 128 * 1024 + this.c * 128 + this.b) * 256 + this.d; }
/*    */ 
/*    */   
/*    */   public NextTickListEntry a(long paramLong) {
/* 29 */     this.e = paramLong;
/* 30 */     return this;
/*    */   }
/*    */   
/*    */   public int compareTo(NextTickListEntry paramNextTickListEntry) {
/* 34 */     if (this.e < paramNextTickListEntry.e) return -1; 
/* 35 */     if (this.e > paramNextTickListEntry.e) return 1; 
/* 36 */     if (this.g < paramNextTickListEntry.g) return -1; 
/* 37 */     if (this.g > paramNextTickListEntry.g) return 1; 
/* 38 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\NextTickListEntry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */