/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StatisticList
/*     */ {
/*  24 */   protected static Map a = new HashMap();
/*     */   
/*  26 */   public static List b = new ArrayList();
/*  27 */   public static List c = new ArrayList();
/*  28 */   public static List d = new ArrayList();
/*  29 */   public static List e = new ArrayList();
/*     */   
/*  31 */   public static Statistic f = (new CounterStatistic('Ϩ', StatisticCollector.a("stat.startGame"))).e().d();
/*  32 */   public static Statistic g = (new CounterStatistic('ϩ', StatisticCollector.a("stat.createWorld"))).e().d();
/*  33 */   public static Statistic h = (new CounterStatistic('Ϫ', StatisticCollector.a("stat.loadWorld"))).e().d();
/*  34 */   public static Statistic i = (new CounterStatistic('ϫ', StatisticCollector.a("stat.joinMultiplayer"))).e().d();
/*  35 */   public static Statistic j = (new CounterStatistic('Ϭ', StatisticCollector.a("stat.leaveGame"))).e().d();
/*     */   
/*  37 */   public static Statistic k = (new CounterStatistic('ь', StatisticCollector.a("stat.playOneMinute"), Statistic.j)).e().d();
/*     */   
/*  39 */   public static Statistic l = (new CounterStatistic('ߐ', StatisticCollector.a("stat.walkOneCm"), Statistic.k)).e().d();
/*  40 */   public static Statistic m = (new CounterStatistic('ߑ', StatisticCollector.a("stat.swimOneCm"), Statistic.k)).e().d();
/*  41 */   public static Statistic n = (new CounterStatistic('ߒ', StatisticCollector.a("stat.fallOneCm"), Statistic.k)).e().d();
/*  42 */   public static Statistic o = (new CounterStatistic('ߓ', StatisticCollector.a("stat.climbOneCm"), Statistic.k)).e().d();
/*  43 */   public static Statistic p = (new CounterStatistic('ߔ', StatisticCollector.a("stat.flyOneCm"), Statistic.k)).e().d();
/*  44 */   public static Statistic q = (new CounterStatistic('ߕ', StatisticCollector.a("stat.diveOneCm"), Statistic.k)).e().d();
/*  45 */   public static Statistic r = (new CounterStatistic('ߖ', StatisticCollector.a("stat.minecartOneCm"), Statistic.k)).e().d();
/*  46 */   public static Statistic s = (new CounterStatistic('ߗ', StatisticCollector.a("stat.boatOneCm"), Statistic.k)).e().d();
/*  47 */   public static Statistic t = (new CounterStatistic('ߘ', StatisticCollector.a("stat.pigOneCm"), Statistic.k)).e().d();
/*     */   
/*  49 */   public static Statistic u = (new CounterStatistic('ߚ', StatisticCollector.a("stat.jump"))).e().d();
/*  50 */   public static Statistic v = (new CounterStatistic('ߛ', StatisticCollector.a("stat.drop"))).e().d();
/*     */   
/*  52 */   public static Statistic w = (new CounterStatistic('ߤ', StatisticCollector.a("stat.damageDealt"))).d();
/*  53 */   public static Statistic x = (new CounterStatistic('ߥ', StatisticCollector.a("stat.damageTaken"))).d();
/*  54 */   public static Statistic y = (new CounterStatistic('ߦ', StatisticCollector.a("stat.deaths"))).d();
/*  55 */   public static Statistic z = (new CounterStatistic('ߧ', StatisticCollector.a("stat.mobKills"))).d();
/*  56 */   public static Statistic A = (new CounterStatistic('ߨ', StatisticCollector.a("stat.playerKills"))).d();
/*  57 */   public static Statistic B = (new CounterStatistic('ߩ', StatisticCollector.a("stat.fishCaught"))).d();
/*     */   
/*  59 */   public static Statistic[] C = a("stat.mineBlock", 16777216); public static Statistic[] D;
/*     */   public static Statistic[] E;
/*     */   public static Statistic[] F;
/*     */   private static boolean G;
/*     */   private static boolean H;
/*     */   
/*  65 */   static  { AchievementList.a();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  75 */     G = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     H = false; }
/*     */   
/*     */   public static void a() {}
/*  88 */   public static void c() { E = a(E, "stat.useItem", 16908288, Block.byId.length, 32000);
/*  89 */     F = b(F, "stat.breakItem", 16973824, Block.byId.length, 32000);
/*     */     
/*  91 */     H = true;
/*  92 */     d(); }
/*     */   public static void b() { E = a(E, "stat.useItem", 16908288, 0, Block.byId.length);
/*     */     F = b(F, "stat.breakItem", 16973824, 0, Block.byId.length);
/*     */     G = true;
/*  96 */     d(); } public static void d() { if (!G || !H) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 101 */     HashSet hashSet = new HashSet();
/*     */     
/* 103 */     for (CraftingRecipe craftingRecipe : CraftingManager.getInstance().b()) {
/* 104 */       hashSet.add(Integer.valueOf((craftingRecipe.b()).id));
/*     */     }
/* 106 */     for (ItemStack itemStack : FurnaceRecipes.getInstance().b().values()) {
/* 107 */       hashSet.add(Integer.valueOf(itemStack.id));
/*     */     }
/*     */     
/* 110 */     D = new Statistic[32000];
/* 111 */     for (Integer integer : hashSet) {
/* 112 */       if (Item.byId[integer.intValue()] != null) {
/* 113 */         String str = StatisticCollector.a("stat.craftItem", new Object[] { Item.byId[integer.intValue()].j() });
/* 114 */         D[integer.intValue()] = (new CraftingStatistic(16842752 + integer.intValue(), str, integer.intValue())).d();
/*     */       } 
/*     */     } 
/*     */     
/* 118 */     a(D); }
/*     */ 
/*     */   
/*     */   private static Statistic[] a(String paramString, int paramInt) {
/* 122 */     Statistic[] arrayOfStatistic = new Statistic[256];
/* 123 */     for (int i1 = 0; i1 < 256; i1++) {
/* 124 */       if (Block.byId[i1] != null && Block.byId[i1].m()) {
/* 125 */         String str = StatisticCollector.a(paramString, new Object[] { Block.byId[i1].k() });
/* 126 */         arrayOfStatistic[i1] = (new CraftingStatistic(paramInt + i1, str, i1)).d();
/* 127 */         e.add((CraftingStatistic)arrayOfStatistic[i1]);
/*     */       } 
/*     */     } 
/*     */     
/* 131 */     a(arrayOfStatistic);
/* 132 */     return arrayOfStatistic;
/*     */   }
/*     */   
/*     */   private static Statistic[] a(Statistic[] paramArrayOfStatistic, String paramString, int paramInt1, int paramInt2, int paramInt3) {
/* 136 */     if (paramArrayOfStatistic == null) {
/* 137 */       paramArrayOfStatistic = new Statistic[32000];
/*     */     }
/* 139 */     for (int i1 = paramInt2; i1 < paramInt3; i1++) {
/* 140 */       if (Item.byId[i1] != null) {
/* 141 */         String str = StatisticCollector.a(paramString, new Object[] { Item.byId[i1].j() });
/* 142 */         paramArrayOfStatistic[i1] = (new CraftingStatistic(paramInt1 + i1, str, i1)).d();
/*     */         
/* 144 */         if (i1 >= Block.byId.length) {
/* 145 */           d.add((CraftingStatistic)paramArrayOfStatistic[i1]);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 150 */     a(paramArrayOfStatistic);
/* 151 */     return paramArrayOfStatistic;
/*     */   }
/*     */   
/*     */   private static Statistic[] b(Statistic[] paramArrayOfStatistic, String paramString, int paramInt1, int paramInt2, int paramInt3) {
/* 155 */     if (paramArrayOfStatistic == null) {
/* 156 */       paramArrayOfStatistic = new Statistic[32000];
/*     */     }
/* 158 */     for (int i1 = paramInt2; i1 < paramInt3; i1++) {
/* 159 */       if (Item.byId[i1] != null && Item.byId[i1].f()) {
/* 160 */         String str = StatisticCollector.a(paramString, new Object[] { Item.byId[i1].j() });
/* 161 */         paramArrayOfStatistic[i1] = (new CraftingStatistic(paramInt1 + i1, str, i1)).d();
/*     */       } 
/*     */     } 
/*     */     
/* 165 */     a(paramArrayOfStatistic);
/* 166 */     return paramArrayOfStatistic;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void a(Statistic[] paramArrayOfStatistic) {
/* 171 */     a(paramArrayOfStatistic, Block.STATIONARY_WATER.id, Block.WATER.id);
/* 172 */     a(paramArrayOfStatistic, Block.STATIONARY_LAVA.id, Block.STATIONARY_LAVA.id);
/*     */     
/* 174 */     a(paramArrayOfStatistic, Block.JACK_O_LANTERN.id, Block.PUMPKIN.id);
/* 175 */     a(paramArrayOfStatistic, Block.BURNING_FURNACE.id, Block.FURNACE.id);
/* 176 */     a(paramArrayOfStatistic, Block.GLOWING_REDSTONE_ORE.id, Block.REDSTONE_ORE.id);
/*     */     
/* 178 */     a(paramArrayOfStatistic, Block.DIODE_ON.id, Block.DIODE_OFF.id);
/* 179 */     a(paramArrayOfStatistic, Block.REDSTONE_TORCH_ON.id, Block.REDSTONE_TORCH_OFF.id);
/*     */     
/* 181 */     a(paramArrayOfStatistic, Block.RED_MUSHROOM.id, Block.BROWN_MUSHROOM.id);
/* 182 */     a(paramArrayOfStatistic, Block.DOUBLE_STEP.id, Block.STEP.id);
/*     */     
/* 184 */     a(paramArrayOfStatistic, Block.GRASS.id, Block.DIRT.id);
/* 185 */     a(paramArrayOfStatistic, Block.SOIL.id, Block.DIRT.id);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void a(Statistic[] paramArrayOfStatistic, int paramInt1, int paramInt2) {
/* 190 */     if (paramArrayOfStatistic[paramInt1] != null && paramArrayOfStatistic[paramInt2] == null) {
/*     */       
/* 192 */       paramArrayOfStatistic[paramInt2] = paramArrayOfStatistic[paramInt1];
/*     */       
/*     */       return;
/*     */     } 
/* 196 */     b.remove(paramArrayOfStatistic[paramInt1]);
/* 197 */     e.remove(paramArrayOfStatistic[paramInt1]);
/* 198 */     c.remove(paramArrayOfStatistic[paramInt1]);
/* 199 */     paramArrayOfStatistic[paramInt1] = paramArrayOfStatistic[paramInt2];
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\StatisticList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */