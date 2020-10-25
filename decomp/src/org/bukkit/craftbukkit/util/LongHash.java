/*    */ package org.bukkit.craftbukkit.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class LongHash
/*    */ {
/* 14 */   static long toLong(int msw, int lsw) { return (msw << 32) + lsw - -2147483648L; }
/*    */ 
/*    */ 
/*    */   
/* 18 */   static int msw(long l) { return (int)(l >> 32); }
/*    */ 
/*    */ 
/*    */   
/* 22 */   static int lsw(long l) { return (int)(l & 0xFFFFFFFFFFFFFFFFL) + Integer.MIN_VALUE; }
/*    */ 
/*    */ 
/*    */   
/* 26 */   public boolean containsKey(int msw, int lsw) { return containsKey(toLong(msw, lsw)); }
/*    */ 
/*    */ 
/*    */   
/* 30 */   public void remove(int msw, int lsw) { remove(toLong(msw, lsw)); }
/*    */   
/*    */   public abstract boolean containsKey(long paramLong);
/*    */   
/*    */   public abstract void remove(long paramLong);
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukki\\util\LongHash.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */