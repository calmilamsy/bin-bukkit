/*    */ package net.minecraft.server;
/*    */ 
/*    */ class PacketCounter
/*    */ {
/*    */   private int a;
/*    */   private long b;
/*    */   
/*    */   private PacketCounter() {}
/*    */   
/*    */   public void a(int i) {
/* 11 */     this.a++;
/* 12 */     this.b += i;
/*    */   }
/*    */ 
/*    */   
/* 16 */   PacketCounter(EmptyClass1 emptyclass1) { this(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\PacketCounter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */