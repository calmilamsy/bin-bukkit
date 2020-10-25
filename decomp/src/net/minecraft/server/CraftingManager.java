/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ 
/*     */ public class CraftingManager
/*     */ {
/*  10 */   private static final CraftingManager a = new CraftingManager();
/*     */   
/*     */   private List b;
/*     */   
/*  14 */   public static final CraftingManager getInstance() { return a; }
/*     */   
/*     */   private CraftingManager() {
/*     */     this.b = new ArrayList();
/*  18 */     (new RecipesTools()).a(this);
/*  19 */     (new RecipesWeapons()).a(this);
/*  20 */     (new RecipeIngots()).a(this);
/*  21 */     (new RecipesFood()).a(this);
/*  22 */     (new RecipesCrafting()).a(this);
/*  23 */     (new RecipesArmor()).a(this);
/*  24 */     (new RecipesDyes()).a(this);
/*  25 */     registerShapedRecipe(new ItemStack(Item.PAPER, 3), new Object[] { "###", Character.valueOf('#'), Item.SUGAR_CANE });
/*  26 */     registerShapedRecipe(new ItemStack(Item.BOOK, true), new Object[] { "#", "#", "#", Character.valueOf('#'), Item.PAPER });
/*  27 */     registerShapedRecipe(new ItemStack(Block.FENCE, 2), new Object[] { "###", "###", Character.valueOf('#'), Item.STICK });
/*  28 */     registerShapedRecipe(new ItemStack(Block.JUKEBOX, true), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Block.WOOD, Character.valueOf('X'), Item.DIAMOND });
/*  29 */     registerShapedRecipe(new ItemStack(Block.NOTE_BLOCK, true), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Block.WOOD, Character.valueOf('X'), Item.REDSTONE });
/*  30 */     registerShapedRecipe(new ItemStack(Block.BOOKSHELF, true), new Object[] { "###", "XXX", "###", Character.valueOf('#'), Block.WOOD, Character.valueOf('X'), Item.BOOK });
/*  31 */     registerShapedRecipe(new ItemStack(Block.SNOW_BLOCK, true), new Object[] { "##", "##", Character.valueOf('#'), Item.SNOW_BALL });
/*  32 */     registerShapedRecipe(new ItemStack(Block.CLAY, true), new Object[] { "##", "##", Character.valueOf('#'), Item.CLAY_BALL });
/*  33 */     registerShapedRecipe(new ItemStack(Block.BRICK, true), new Object[] { "##", "##", Character.valueOf('#'), Item.CLAY_BRICK });
/*  34 */     registerShapedRecipe(new ItemStack(Block.GLOWSTONE, true), new Object[] { "##", "##", Character.valueOf('#'), Item.GLOWSTONE_DUST });
/*  35 */     registerShapedRecipe(new ItemStack(Block.WOOL, true), new Object[] { "##", "##", Character.valueOf('#'), Item.STRING });
/*  36 */     registerShapedRecipe(new ItemStack(Block.TNT, true), new Object[] { "X#X", "#X#", "X#X", Character.valueOf('X'), Item.SULPHUR, Character.valueOf('#'), Block.SAND });
/*  37 */     registerShapedRecipe(new ItemStack(Block.STEP, 3, 3), new Object[] { "###", Character.valueOf('#'), Block.COBBLESTONE });
/*  38 */     registerShapedRecipe(new ItemStack(Block.STEP, 3, false), new Object[] { "###", Character.valueOf('#'), Block.STONE });
/*  39 */     registerShapedRecipe(new ItemStack(Block.STEP, 3, true), new Object[] { "###", Character.valueOf('#'), Block.SANDSTONE });
/*  40 */     registerShapedRecipe(new ItemStack(Block.STEP, 3, 2), new Object[] { "###", Character.valueOf('#'), Block.WOOD });
/*  41 */     registerShapedRecipe(new ItemStack(Block.LADDER, 2), new Object[] { "# #", "###", "# #", Character.valueOf('#'), Item.STICK });
/*  42 */     registerShapedRecipe(new ItemStack(Item.WOOD_DOOR, true), new Object[] { "##", "##", "##", Character.valueOf('#'), Block.WOOD });
/*  43 */     registerShapedRecipe(new ItemStack(Block.TRAP_DOOR, 2), new Object[] { "###", "###", Character.valueOf('#'), Block.WOOD });
/*  44 */     registerShapedRecipe(new ItemStack(Item.IRON_DOOR, true), new Object[] { "##", "##", "##", Character.valueOf('#'), Item.IRON_INGOT });
/*  45 */     registerShapedRecipe(new ItemStack(Item.SIGN, true), new Object[] { "###", "###", " X ", Character.valueOf('#'), Block.WOOD, Character.valueOf('X'), Item.STICK });
/*  46 */     registerShapedRecipe(new ItemStack(Item.CAKE, true), new Object[] { "AAA", "BEB", "CCC", Character.valueOf('A'), Item.MILK_BUCKET, Character.valueOf('B'), Item.SUGAR, Character.valueOf('C'), Item.WHEAT, Character.valueOf('E'), Item.EGG });
/*  47 */     registerShapedRecipe(new ItemStack(Item.SUGAR, true), new Object[] { "#", Character.valueOf('#'), Item.SUGAR_CANE });
/*  48 */     registerShapedRecipe(new ItemStack(Block.WOOD, 4), new Object[] { "#", Character.valueOf('#'), Block.LOG });
/*  49 */     registerShapedRecipe(new ItemStack(Item.STICK, 4), new Object[] { "#", "#", Character.valueOf('#'), Block.WOOD });
/*  50 */     registerShapedRecipe(new ItemStack(Block.TORCH, 4), new Object[] { "X", "#", Character.valueOf('X'), Item.COAL, Character.valueOf('#'), Item.STICK });
/*  51 */     registerShapedRecipe(new ItemStack(Block.TORCH, 4), new Object[] { "X", "#", Character.valueOf('X'), new ItemStack(Item.COAL, true, true), Character.valueOf('#'), Item.STICK });
/*  52 */     registerShapedRecipe(new ItemStack(Item.BOWL, 4), new Object[] { "# #", " # ", Character.valueOf('#'), Block.WOOD });
/*  53 */     registerShapedRecipe(new ItemStack(Block.RAILS, 16), new Object[] { "X X", "X#X", "X X", Character.valueOf('X'), Item.IRON_INGOT, Character.valueOf('#'), Item.STICK });
/*  54 */     registerShapedRecipe(new ItemStack(Block.GOLDEN_RAIL, 6), new Object[] { "X X", "X#X", "XRX", Character.valueOf('X'), Item.GOLD_INGOT, Character.valueOf('R'), Item.REDSTONE, Character.valueOf('#'), Item.STICK });
/*  55 */     registerShapedRecipe(new ItemStack(Block.DETECTOR_RAIL, 6), new Object[] { "X X", "X#X", "XRX", Character.valueOf('X'), Item.IRON_INGOT, Character.valueOf('R'), Item.REDSTONE, Character.valueOf('#'), Block.STONE_PLATE });
/*  56 */     registerShapedRecipe(new ItemStack(Item.MINECART, true), new Object[] { "# #", "###", Character.valueOf('#'), Item.IRON_INGOT });
/*  57 */     registerShapedRecipe(new ItemStack(Block.JACK_O_LANTERN, true), new Object[] { "A", "B", Character.valueOf('A'), Block.PUMPKIN, Character.valueOf('B'), Block.TORCH });
/*  58 */     registerShapedRecipe(new ItemStack(Item.STORAGE_MINECART, true), new Object[] { "A", "B", Character.valueOf('A'), Block.CHEST, Character.valueOf('B'), Item.MINECART });
/*  59 */     registerShapedRecipe(new ItemStack(Item.POWERED_MINECART, true), new Object[] { "A", "B", Character.valueOf('A'), Block.FURNACE, Character.valueOf('B'), Item.MINECART });
/*  60 */     registerShapedRecipe(new ItemStack(Item.BOAT, true), new Object[] { "# #", "###", Character.valueOf('#'), Block.WOOD });
/*  61 */     registerShapedRecipe(new ItemStack(Item.BUCKET, true), new Object[] { "# #", " # ", Character.valueOf('#'), Item.IRON_INGOT });
/*  62 */     registerShapedRecipe(new ItemStack(Item.FLINT_AND_STEEL, true), new Object[] { "A ", " B", Character.valueOf('A'), Item.IRON_INGOT, Character.valueOf('B'), Item.FLINT });
/*  63 */     registerShapedRecipe(new ItemStack(Item.BREAD, true), new Object[] { "###", Character.valueOf('#'), Item.WHEAT });
/*  64 */     registerShapedRecipe(new ItemStack(Block.WOOD_STAIRS, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Block.WOOD });
/*  65 */     registerShapedRecipe(new ItemStack(Item.FISHING_ROD, true), new Object[] { "  #", " #X", "# X", Character.valueOf('#'), Item.STICK, Character.valueOf('X'), Item.STRING });
/*  66 */     registerShapedRecipe(new ItemStack(Block.COBBLESTONE_STAIRS, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Block.COBBLESTONE });
/*  67 */     registerShapedRecipe(new ItemStack(Item.PAINTING, true), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Item.STICK, Character.valueOf('X'), Block.WOOL });
/*  68 */     registerShapedRecipe(new ItemStack(Item.GOLDEN_APPLE, true), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Block.GOLD_BLOCK, Character.valueOf('X'), Item.APPLE });
/*  69 */     registerShapedRecipe(new ItemStack(Block.LEVER, true), new Object[] { "X", "#", Character.valueOf('#'), Block.COBBLESTONE, Character.valueOf('X'), Item.STICK });
/*  70 */     registerShapedRecipe(new ItemStack(Block.REDSTONE_TORCH_ON, true), new Object[] { "X", "#", Character.valueOf('#'), Item.STICK, Character.valueOf('X'), Item.REDSTONE });
/*  71 */     registerShapedRecipe(new ItemStack(Item.DIODE, true), new Object[] { "#X#", "III", Character.valueOf('#'), Block.REDSTONE_TORCH_ON, Character.valueOf('X'), Item.REDSTONE, Character.valueOf('I'), Block.STONE });
/*  72 */     registerShapedRecipe(new ItemStack(Item.WATCH, true), new Object[] { " # ", "#X#", " # ", Character.valueOf('#'), Item.GOLD_INGOT, Character.valueOf('X'), Item.REDSTONE });
/*  73 */     registerShapedRecipe(new ItemStack(Item.COMPASS, true), new Object[] { " # ", "#X#", " # ", Character.valueOf('#'), Item.IRON_INGOT, Character.valueOf('X'), Item.REDSTONE });
/*  74 */     registerShapedRecipe(new ItemStack(Item.MAP, true), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Item.PAPER, Character.valueOf('X'), Item.COMPASS });
/*  75 */     registerShapedRecipe(new ItemStack(Block.STONE_BUTTON, true), new Object[] { "#", "#", Character.valueOf('#'), Block.STONE });
/*  76 */     registerShapedRecipe(new ItemStack(Block.STONE_PLATE, true), new Object[] { "##", Character.valueOf('#'), Block.STONE });
/*  77 */     registerShapedRecipe(new ItemStack(Block.WOOD_PLATE, true), new Object[] { "##", Character.valueOf('#'), Block.WOOD });
/*  78 */     registerShapedRecipe(new ItemStack(Block.DISPENSER, true), new Object[] { "###", "#X#", "#R#", Character.valueOf('#'), Block.COBBLESTONE, Character.valueOf('X'), Item.BOW, Character.valueOf('R'), Item.REDSTONE });
/*  79 */     registerShapedRecipe(new ItemStack(Block.PISTON, true), new Object[] { "TTT", "#X#", "#R#", Character.valueOf('#'), Block.COBBLESTONE, Character.valueOf('X'), Item.IRON_INGOT, Character.valueOf('R'), Item.REDSTONE, Character.valueOf('T'), Block.WOOD });
/*  80 */     registerShapedRecipe(new ItemStack(Block.PISTON_STICKY, true), new Object[] { "S", "P", Character.valueOf('S'), Item.SLIME_BALL, Character.valueOf('P'), Block.PISTON });
/*  81 */     registerShapedRecipe(new ItemStack(Item.BED, true), new Object[] { "###", "XXX", Character.valueOf('#'), Block.WOOL, Character.valueOf('X'), Block.WOOD });
/*  82 */     Collections.sort(this.b, new RecipeSorter(this));
/*  83 */     System.out.println(this.b.size() + " recipes");
/*     */   }
/*     */   
/*     */   public void registerShapedRecipe(ItemStack itemstack, Object... aobject) {
/*  87 */     String s = "";
/*  88 */     int i = 0;
/*  89 */     int j = 0;
/*  90 */     int k = 0;
/*     */     
/*  92 */     if (aobject[i] instanceof String[]) {
/*  93 */       String[] astring = (String[])aobject[i++];
/*     */       
/*  95 */       for (int l = 0; l < astring.length; l++) {
/*  96 */         String s1 = astring[l];
/*     */         
/*  98 */         k++;
/*  99 */         j = s1.length();
/* 100 */         s = s + s1;
/*     */       } 
/*     */     } else {
/* 103 */       while (aobject[i] instanceof String) {
/* 104 */         String s2 = (String)aobject[i++];
/*     */         
/* 106 */         k++;
/* 107 */         j = s2.length();
/* 108 */         s = s + s2;
/*     */       } 
/*     */     } 
/*     */     
/*     */     HashMap hashmap;
/*     */     
/* 114 */     for (hashmap = new HashMap(); i < aobject.length; i += 2) {
/* 115 */       Character character = (Character)aobject[i];
/* 116 */       ItemStack itemstack1 = null;
/*     */       
/* 118 */       if (aobject[i + 1] instanceof Item) {
/* 119 */         itemstack1 = new ItemStack((Item)aobject[i + 1]);
/* 120 */       } else if (aobject[i + 1] instanceof Block) {
/* 121 */         itemstack1 = new ItemStack((Block)aobject[i + 1], true, -1);
/* 122 */       } else if (aobject[i + 1] instanceof ItemStack) {
/* 123 */         itemstack1 = (ItemStack)aobject[i + 1];
/*     */       } 
/*     */       
/* 126 */       hashmap.put(character, itemstack1);
/*     */     } 
/*     */     
/* 129 */     ItemStack[] aitemstack = new ItemStack[j * k];
/*     */     
/* 131 */     for (int i1 = 0; i1 < j * k; i1++) {
/* 132 */       char c0 = s.charAt(i1);
/*     */       
/* 134 */       if (hashmap.containsKey(Character.valueOf(c0))) {
/* 135 */         aitemstack[i1] = ((ItemStack)hashmap.get(Character.valueOf(c0))).cloneItemStack();
/*     */       } else {
/* 137 */         aitemstack[i1] = null;
/*     */       } 
/*     */     } 
/*     */     
/* 141 */     this.b.add(new ShapedRecipes(j, k, aitemstack, itemstack));
/*     */   }
/*     */   
/*     */   public void registerShapelessRecipe(ItemStack itemstack, Object... aobject) {
/* 145 */     ArrayList arraylist = new ArrayList();
/* 146 */     Object[] aobject1 = aobject;
/* 147 */     int i = aobject.length;
/*     */     
/* 149 */     for (int j = 0; j < i; j++) {
/* 150 */       Object object = aobject1[j];
/*     */       
/* 152 */       if (object instanceof ItemStack) {
/* 153 */         arraylist.add(((ItemStack)object).cloneItemStack());
/* 154 */       } else if (object instanceof Item) {
/* 155 */         arraylist.add(new ItemStack((Item)object));
/*     */       } else {
/* 157 */         if (!(object instanceof Block)) {
/* 158 */           throw new RuntimeException("Invalid shapeless recipy!");
/*     */         }
/*     */         
/* 161 */         arraylist.add(new ItemStack((Block)object));
/*     */       } 
/*     */     } 
/*     */     
/* 165 */     this.b.add(new ShapelessRecipes(itemstack, arraylist));
/*     */   }
/*     */   
/*     */   public ItemStack craft(InventoryCrafting inventorycrafting) {
/* 169 */     for (int i = 0; i < this.b.size(); i++) {
/* 170 */       CraftingRecipe craftingrecipe = (CraftingRecipe)this.b.get(i);
/*     */       
/* 172 */       if (craftingrecipe.a(inventorycrafting)) {
/* 173 */         return craftingrecipe.b(inventorycrafting);
/*     */       }
/*     */     } 
/*     */     
/* 177 */     return null;
/*     */   }
/*     */ 
/*     */   
/* 181 */   public List b() { return this.b; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\CraftingManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */