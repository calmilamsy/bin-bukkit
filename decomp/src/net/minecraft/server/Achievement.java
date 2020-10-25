/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Achievement
/*    */   extends Statistic
/*    */ {
/*    */   public final int a;
/*    */   public final int b;
/*    */   public final Achievement c;
/*    */   private final String l;
/*    */   public final ItemStack d;
/*    */   private boolean m;
/*    */   
/* 17 */   public Achievement(int paramInt1, String paramString, int paramInt2, int paramInt3, Item paramItem, Achievement paramAchievement) { this(paramInt1, paramString, paramInt2, paramInt3, new ItemStack(paramItem), paramAchievement); }
/*    */ 
/*    */ 
/*    */   
/* 21 */   public Achievement(int paramInt1, String paramString, int paramInt2, int paramInt3, Block paramBlock, Achievement paramAchievement) { this(paramInt1, paramString, paramInt2, paramInt3, new ItemStack(paramBlock), paramAchievement); }
/*    */ 
/*    */   
/*    */   public Achievement(int paramInt1, String paramString, int paramInt2, int paramInt3, ItemStack paramItemStack, Achievement paramAchievement) {
/* 25 */     super(5242880 + paramInt1, StatisticCollector.a("achievement." + paramString));
/* 26 */     this.d = paramItemStack;
/*    */     
/* 28 */     this.l = StatisticCollector.a("achievement." + paramString + ".desc");
/* 29 */     this.a = paramInt2;
/* 30 */     this.b = paramInt3;
/*    */     
/* 32 */     if (paramInt2 < AchievementList.a) AchievementList.a = paramInt2; 
/* 33 */     if (paramInt3 < AchievementList.b) AchievementList.b = paramInt3; 
/* 34 */     if (paramInt2 > AchievementList.c) AchievementList.c = paramInt2; 
/* 35 */     if (paramInt3 > AchievementList.d) AchievementList.d = paramInt3; 
/* 36 */     this.c = paramAchievement;
/*    */   }
/*    */   
/*    */   public Achievement a() {
/* 40 */     this.g = true;
/* 41 */     return this;
/*    */   }
/*    */   
/*    */   public Achievement b() {
/* 45 */     this.m = true;
/* 46 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public Achievement c() {
/* 51 */     super.d();
/*    */     
/* 53 */     AchievementList.e.add(this);
/*    */     
/* 55 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Achievement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */