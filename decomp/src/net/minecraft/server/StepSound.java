/*    */ package net.minecraft.server;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StepSound
/*    */ {
/*    */   public final String a;
/*    */   public final float b;
/*    */   public final float c;
/*    */   
/*    */   public StepSound(String paramString, float paramFloat1, float paramFloat2) {
/* 28 */     this.a = paramString;
/* 29 */     this.b = paramFloat1;
/* 30 */     this.c = paramFloat2;
/*    */   }
/*    */ 
/*    */   
/* 34 */   public float getVolume1() { return this.b; }
/*    */ 
/*    */ 
/*    */   
/* 38 */   public float getVolume2() { return this.c; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 46 */   public String getName() { return "step." + this.a; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\StepSound.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */