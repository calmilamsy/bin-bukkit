/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AchievementList
/*    */ {
/*    */   public static int a;
/*    */   public static int b;
/*    */   public static int c;
/*    */   public static int d;
/* 18 */   public static List e = new ArrayList();
/*    */   
/* 20 */   public static Achievement f = (new Achievement(false, "openInventory", false, false, Item.BOOK, null)).a().c();
/* 21 */   public static Achievement g = (new Achievement(true, "mineWood", 2, true, Block.LOG, f)).c();
/* 22 */   public static Achievement h = (new Achievement(2, "buildWorkBench", 4, -1, Block.WORKBENCH, g)).c();
/* 23 */   public static Achievement i = (new Achievement(3, "buildPickaxe", 4, 2, Item.WOOD_PICKAXE, h)).c();
/* 24 */   public static Achievement j = (new Achievement(4, "buildFurnace", 3, 4, Block.BURNING_FURNACE, i)).c();
/* 25 */   public static Achievement k = (new Achievement(5, "acquireIron", true, 4, Item.IRON_INGOT, j)).c();
/* 26 */   public static Achievement l = (new Achievement(6, "buildHoe", 2, -3, Item.WOOD_HOE, h)).c();
/* 27 */   public static Achievement m = (new Achievement(7, "makeBread", -1, -3, Item.BREAD, l)).c();
/* 28 */   public static Achievement n = (new Achievement(8, "bakeCake", false, -5, Item.CAKE, l)).c();
/* 29 */   public static Achievement o = (new Achievement(9, "buildBetterPickaxe", 6, 2, Item.STONE_PICKAXE, i)).c();
/* 30 */   public static Achievement p = (new Achievement(10, "cookFish", 2, 6, Item.COOKED_FISH, j)).c();
/* 31 */   public static Achievement q = (new Achievement(11, "onARail", 2, 3, Block.RAILS, k)).b().c();
/* 32 */   public static Achievement r = (new Achievement(12, "buildSword", 6, -1, Item.WOOD_SWORD, h)).c();
/* 33 */   public static Achievement s = (new Achievement(13, "killEnemy", 8, -1, Item.BONE, r)).c();
/* 34 */   public static Achievement t = (new Achievement(14, "killCow", 7, -3, Item.LEATHER, r)).c();
/* 35 */   public static Achievement u = (new Achievement(15, "flyPig", 8, -4, Item.SADDLE, t)).b().c();
/*    */   
/*    */   static  {
/* 38 */     System.out.println(e.size() + " achievements");
/*    */   }
/*    */   
/*    */   public static void a() {}
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\AchievementList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */