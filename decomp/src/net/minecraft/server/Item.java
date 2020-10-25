/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Item
/*     */ {
/*  57 */   protected static Random b = new Random();
/*     */   
/*  59 */   public static Item[] byId = new Item[32000];
/*     */   
/*  61 */   public static Item IRON_SPADE = (new ItemSpade(false, EnumToolMaterial.IRON)).a(2, 5).a("shovelIron");
/*  62 */   public static Item IRON_PICKAXE = (new ItemPickaxe(true, EnumToolMaterial.IRON)).a(2, 6).a("pickaxeIron");
/*  63 */   public static Item IRON_AXE = (new ItemAxe(2, EnumToolMaterial.IRON)).a(2, 7).a("hatchetIron");
/*  64 */   public static Item FLINT_AND_STEEL = (new ItemFlintAndSteel(3)).a(5, 0).a("flintAndSteel");
/*  65 */   public static Item APPLE = (new ItemFood(4, 4, false)).a(10, 0).a("apple");
/*  66 */   public static Item BOW = (new ItemBow(5)).a(5, 1).a("bow");
/*  67 */   public static Item ARROW = (new Item(6)).a(5, 2).a("arrow");
/*  68 */   public static Item COAL = (new ItemCoal(7)).a(7, 0).a("coal");
/*  69 */   public static Item DIAMOND = (new Item(8)).a(7, 3).a("emerald");
/*  70 */   public static Item IRON_INGOT = (new Item(9)).a(7, 1).a("ingotIron");
/*  71 */   public static Item GOLD_INGOT = (new Item(10)).a(7, 2).a("ingotGold");
/*  72 */   public static Item IRON_SWORD = (new ItemSword(11, EnumToolMaterial.IRON)).a(2, 4).a("swordIron");
/*     */   
/*  74 */   public static Item WOOD_SWORD = (new ItemSword(12, EnumToolMaterial.WOOD)).a(0, 4).a("swordWood");
/*  75 */   public static Item WOOD_SPADE = (new ItemSpade(13, EnumToolMaterial.WOOD)).a(0, 5).a("shovelWood");
/*  76 */   public static Item WOOD_PICKAXE = (new ItemPickaxe(14, EnumToolMaterial.WOOD)).a(0, 6).a("pickaxeWood");
/*  77 */   public static Item WOOD_AXE = (new ItemAxe(15, EnumToolMaterial.WOOD)).a(0, 7).a("hatchetWood");
/*     */   
/*  79 */   public static Item STONE_SWORD = (new ItemSword(16, EnumToolMaterial.STONE)).a(1, 4).a("swordStone");
/*  80 */   public static Item STONE_SPADE = (new ItemSpade(17, EnumToolMaterial.STONE)).a(1, 5).a("shovelStone");
/*  81 */   public static Item STONE_PICKAXE = (new ItemPickaxe(18, EnumToolMaterial.STONE)).a(1, 6).a("pickaxeStone");
/*  82 */   public static Item STONE_AXE = (new ItemAxe(19, EnumToolMaterial.STONE)).a(1, 7).a("hatchetStone");
/*     */   
/*  84 */   public static Item DIAMOND_SWORD = (new ItemSword(20, EnumToolMaterial.DIAMOND)).a(3, 4).a("swordDiamond");
/*  85 */   public static Item DIAMOND_SPADE = (new ItemSpade(21, EnumToolMaterial.DIAMOND)).a(3, 5).a("shovelDiamond");
/*  86 */   public static Item DIAMOND_PICKAXE = (new ItemPickaxe(22, EnumToolMaterial.DIAMOND)).a(3, 6).a("pickaxeDiamond");
/*  87 */   public static Item DIAMOND_AXE = (new ItemAxe(23, EnumToolMaterial.DIAMOND)).a(3, 7).a("hatchetDiamond");
/*     */   
/*  89 */   public static Item STICK = (new Item(24)).a(5, 3).g().a("stick");
/*  90 */   public static Item BOWL = (new Item(25)).a(7, 4).a("bowl");
/*  91 */   public static Item MUSHROOM_SOUP = (new ItemSoup(26, 10)).a(8, 4).a("mushroomStew");
/*     */   
/*  93 */   public static Item GOLD_SWORD = (new ItemSword(27, EnumToolMaterial.GOLD)).a(4, 4).a("swordGold");
/*  94 */   public static Item GOLD_SPADE = (new ItemSpade(28, EnumToolMaterial.GOLD)).a(4, 5).a("shovelGold");
/*  95 */   public static Item GOLD_PICKAXE = (new ItemPickaxe(29, EnumToolMaterial.GOLD)).a(4, 6).a("pickaxeGold");
/*  96 */   public static Item GOLD_AXE = (new ItemAxe(30, EnumToolMaterial.GOLD)).a(4, 7).a("hatchetGold");
/*     */   
/*  98 */   public static Item STRING = (new Item(31)).a(8, 0).a("string");
/*  99 */   public static Item FEATHER = (new Item(32)).a(8, 1).a("feather");
/* 100 */   public static Item SULPHUR = (new Item(33)).a(8, 2).a("sulphur");
/*     */   
/* 102 */   public static Item WOOD_HOE = (new ItemHoe(34, EnumToolMaterial.WOOD)).a(0, 8).a("hoeWood");
/* 103 */   public static Item STONE_HOE = (new ItemHoe(35, EnumToolMaterial.STONE)).a(1, 8).a("hoeStone");
/* 104 */   public static Item IRON_HOE = (new ItemHoe(36, EnumToolMaterial.IRON)).a(2, 8).a("hoeIron");
/* 105 */   public static Item DIAMOND_HOE = (new ItemHoe(37, EnumToolMaterial.DIAMOND)).a(3, 8).a("hoeDiamond");
/* 106 */   public static Item GOLD_HOE = (new ItemHoe(38, EnumToolMaterial.GOLD)).a(4, 8).a("hoeGold");
/*     */   
/* 108 */   public static Item SEEDS = (new ItemSeeds(39, Block.CROPS.id)).a(9, 0).a("seeds");
/* 109 */   public static Item WHEAT = (new Item(40)).a(9, 1).a("wheat");
/* 110 */   public static Item BREAD = (new ItemFood(41, 5, false)).a(9, 2).a("bread");
/*     */   
/* 112 */   public static Item LEATHER_HELMET = (new ItemArmor(42, false, false, false)).a(0, 0).a("helmetCloth");
/* 113 */   public static Item LEATHER_CHESTPLATE = (new ItemArmor(43, false, false, true)).a(0, 1).a("chestplateCloth");
/* 114 */   public static Item LEATHER_LEGGINGS = (new ItemArmor(44, false, false, 2)).a(0, 2).a("leggingsCloth");
/* 115 */   public static Item LEATHER_BOOTS = (new ItemArmor(45, false, false, 3)).a(0, 3).a("bootsCloth");
/*     */   
/* 117 */   public static Item CHAINMAIL_HELMET = (new ItemArmor(46, true, true, false)).a(1, 0).a("helmetChain");
/* 118 */   public static Item CHAINMAIL_CHESTPLATE = (new ItemArmor(47, true, true, true)).a(1, 1).a("chestplateChain");
/* 119 */   public static Item CHAINMAIL_LEGGINGS = (new ItemArmor(48, true, true, 2)).a(1, 2).a("leggingsChain");
/* 120 */   public static Item CHAINMAIL_BOOTS = (new ItemArmor(49, true, true, 3)).a(1, 3).a("bootsChain");
/*     */   
/* 122 */   public static Item IRON_HELMET = (new ItemArmor(50, 2, 2, false)).a(2, 0).a("helmetIron");
/* 123 */   public static Item IRON_CHESTPLATE = (new ItemArmor(51, 2, 2, true)).a(2, 1).a("chestplateIron");
/* 124 */   public static Item IRON_LEGGINGS = (new ItemArmor(52, 2, 2, 2)).a(2, 2).a("leggingsIron");
/* 125 */   public static Item IRON_BOOTS = (new ItemArmor(53, 2, 2, 3)).a(2, 3).a("bootsIron");
/*     */   
/* 127 */   public static Item DIAMOND_HELMET = (new ItemArmor(54, 3, 3, false)).a(3, 0).a("helmetDiamond");
/* 128 */   public static Item DIAMOND_CHESTPLATE = (new ItemArmor(55, 3, 3, true)).a(3, 1).a("chestplateDiamond");
/* 129 */   public static Item DIAMOND_LEGGINGS = (new ItemArmor(56, 3, 3, 2)).a(3, 2).a("leggingsDiamond");
/* 130 */   public static Item DIAMOND_BOOTS = (new ItemArmor(57, 3, 3, 3)).a(3, 3).a("bootsDiamond");
/*     */   
/* 132 */   public static Item GOLD_HELMET = (new ItemArmor(58, true, 4, false)).a(4, 0).a("helmetGold");
/* 133 */   public static Item GOLD_CHESTPLATE = (new ItemArmor(59, true, 4, true)).a(4, 1).a("chestplateGold");
/* 134 */   public static Item GOLD_LEGGINGS = (new ItemArmor(60, true, 4, 2)).a(4, 2).a("leggingsGold");
/* 135 */   public static Item GOLD_BOOTS = (new ItemArmor(61, true, 4, 3)).a(4, 3).a("bootsGold");
/*     */   
/* 137 */   public static Item FLINT = (new Item(62)).a(6, 0).a("flint");
/* 138 */   public static Item PORK = (new ItemFood(63, 3, true)).a(7, 5).a("porkchopRaw");
/* 139 */   public static Item GRILLED_PORK = (new ItemFood(64, 8, true)).a(8, 5).a("porkchopCooked");
/* 140 */   public static Item PAINTING = (new ItemPainting(65)).a(10, 1).a("painting");
/*     */   
/* 142 */   public static Item GOLDEN_APPLE = (new ItemFood(66, 42, false)).a(11, 0).a("appleGold");
/*     */   
/* 144 */   public static Item SIGN = (new ItemSign(67)).a(10, 2).a("sign");
/* 145 */   public static Item WOOD_DOOR = (new ItemDoor(68, Material.WOOD)).a(11, 2).a("doorWood");
/*     */   
/* 147 */   public static Item BUCKET = (new ItemBucket(69, false)).a(10, 4).a("bucket");
/* 148 */   public static Item WATER_BUCKET = (new ItemBucket(70, Block.WATER.id)).a(11, 4).a("bucketWater").a(BUCKET);
/* 149 */   public static Item LAVA_BUCKET = (new ItemBucket(71, Block.LAVA.id)).a(12, 4).a("bucketLava").a(BUCKET);
/*     */   
/* 151 */   public static Item MINECART = (new ItemMinecart(72, false)).a(7, 8).a("minecart");
/* 152 */   public static Item SADDLE = (new ItemSaddle(73)).a(8, 6).a("saddle");
/* 153 */   public static Item IRON_DOOR = (new ItemDoor(74, Material.ORE)).a(12, 2).a("doorIron");
/* 154 */   public static Item REDSTONE = (new ItemRedstone(75)).a(8, 3).a("redstone");
/* 155 */   public static Item SNOW_BALL = (new ItemSnowball(76)).a(14, 0).a("snowball");
/*     */   
/* 157 */   public static Item BOAT = (new ItemBoat(77)).a(8, 8).a("boat");
/*     */   
/* 159 */   public static Item LEATHER = (new Item(78)).a(7, 6).a("leather");
/* 160 */   public static Item MILK_BUCKET = (new ItemBucket(79, -1)).a(13, 4).a("milk").a(BUCKET);
/* 161 */   public static Item CLAY_BRICK = (new Item(80)).a(6, 1).a("brick");
/* 162 */   public static Item CLAY_BALL = (new Item(81)).a(9, 3).a("clay");
/* 163 */   public static Item SUGAR_CANE = (new ItemReed(82, Block.SUGAR_CANE_BLOCK)).a(11, 1).a("reeds");
/* 164 */   public static Item PAPER = (new Item(83)).a(10, 3).a("paper");
/* 165 */   public static Item BOOK = (new Item(84)).a(11, 3).a("book");
/* 166 */   public static Item SLIME_BALL = (new Item(85)).a(14, 1).a("slimeball");
/* 167 */   public static Item STORAGE_MINECART = (new ItemMinecart(86, true)).a(7, 9).a("minecartChest");
/* 168 */   public static Item POWERED_MINECART = (new ItemMinecart(87, 2)).a(7, 10).a("minecartFurnace");
/* 169 */   public static Item EGG = (new ItemEgg(88)).a(12, 0).a("egg");
/* 170 */   public static Item COMPASS = (new Item(89)).a(6, 3).a("compass");
/* 171 */   public static Item FISHING_ROD = (new ItemFishingRod(90)).a(5, 4).a("fishingRod");
/* 172 */   public static Item WATCH = (new Item(91)).a(6, 4).a("clock");
/* 173 */   public static Item GLOWSTONE_DUST = (new Item(92)).a(9, 4).a("yellowDust");
/* 174 */   public static Item RAW_FISH = (new ItemFood(93, 2, false)).a(9, 5).a("fishRaw");
/* 175 */   public static Item COOKED_FISH = (new ItemFood(94, 5, false)).a(10, 5).a("fishCooked");
/*     */   
/* 177 */   public static Item INK_SACK = (new ItemDye(95)).a(14, 4).a("dyePowder");
/* 178 */   public static Item BONE = (new Item(96)).a(12, 1).a("bone").g();
/* 179 */   public static Item SUGAR = (new Item(97)).a(13, 0).a("sugar").g();
/* 180 */   public static Item CAKE = (new ItemReed(98, Block.CAKE_BLOCK)).c(1).a(13, 1).a("cake");
/*     */   
/* 182 */   public static Item BED = (new ItemBed(99)).c(1).a(13, 2).a("bed");
/*     */   
/* 184 */   public static Item DIODE = (new ItemReed(100, Block.DIODE_OFF)).a(6, 5).a("diode");
/* 185 */   public static Item COOKIE = (new ItemCookie(101, true, false, 8)).a(12, 5).a("cookie");
/*     */   
/* 187 */   public static ItemWorldMap MAP = (ItemWorldMap)(new ItemWorldMap(102)).a(12, 3).a("map");
/*     */   
/* 189 */   public static ItemShears SHEARS = (ItemShears)(new ItemShears(103)).a(13, 5).a("shears");
/*     */ 
/*     */ 
/*     */   
/* 193 */   public static Item GOLD_RECORD = (new ItemRecord('ߐ', "13")).a(0, 15).a("record");
/* 194 */   public static Item GREEN_RECORD = (new ItemRecord('ߑ', "cat")).a(1, 15).a("record"); public final int id; protected int maxStackSize; private int durability; protected int textureId;
/*     */   
/*     */   static  {
/* 197 */     StatisticList.c();
/*     */   }
/*     */   protected boolean bi; protected boolean bj; private Item craftingResult; private String name;
/*     */   protected Item(int paramInt) {
/* 201 */     this.maxStackSize = 64;
/* 202 */     this.durability = 0;
/*     */ 
/*     */     
/* 205 */     this.bi = false;
/* 206 */     this.bj = false;
/*     */     
/* 208 */     this.craftingResult = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 213 */     this.id = 256 + paramInt;
/* 214 */     if (byId['Ā' + paramInt] != null) {
/* 215 */       System.out.println("CONFLICT @ " + paramInt);
/*     */     }
/*     */     
/* 218 */     byId[256 + paramInt] = this;
/*     */   }
/*     */   
/*     */   public Item b(int paramInt) {
/* 222 */     this.textureId = paramInt;
/* 223 */     return this;
/*     */   }
/*     */   
/*     */   public Item c(int paramInt) {
/* 227 */     this.maxStackSize = paramInt;
/* 228 */     return this;
/*     */   }
/*     */   
/*     */   public Item a(int paramInt1, int paramInt2) {
/* 232 */     this.textureId = paramInt1 + paramInt2 * 16;
/* 233 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 249 */   public boolean a(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) { return false; }
/*     */ 
/*     */ 
/*     */   
/* 253 */   public float a(ItemStack paramItemStack, Block paramBlock) { return 1.0F; }
/*     */ 
/*     */ 
/*     */   
/* 257 */   public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) { return paramItemStack; }
/*     */ 
/*     */ 
/*     */   
/* 261 */   public int getMaxStackSize() { return this.maxStackSize; }
/*     */ 
/*     */ 
/*     */   
/* 265 */   public int filterData(int paramInt) { return 0; }
/*     */ 
/*     */ 
/*     */   
/* 269 */   public boolean d() { return this.bj; }
/*     */ 
/*     */   
/*     */   protected Item a(boolean paramBoolean) {
/* 273 */     this.bj = paramBoolean;
/* 274 */     return this;
/*     */   }
/*     */ 
/*     */   
/* 278 */   public int e() { return this.durability; }
/*     */ 
/*     */   
/*     */   protected Item d(int paramInt) {
/* 282 */     this.durability = paramInt;
/* 283 */     return this;
/*     */   }
/*     */ 
/*     */   
/* 287 */   public boolean f() { return (this.durability > 0 && !this.bj); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 299 */   public boolean a(ItemStack paramItemStack, EntityLiving paramEntityLiving1, EntityLiving paramEntityLiving2) { return false; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 314 */   public boolean a(ItemStack paramItemStack, int paramInt1, int paramInt2, int paramInt3, int paramInt4, EntityLiving paramEntityLiving) { return false; }
/*     */ 
/*     */ 
/*     */   
/* 318 */   public int a(Entity paramEntity) { return 1; }
/*     */ 
/*     */ 
/*     */   
/* 322 */   public boolean a(Block paramBlock) { return false; }
/*     */ 
/*     */   
/*     */   public void a(ItemStack paramItemStack, EntityLiving paramEntityLiving) {}
/*     */ 
/*     */   
/*     */   public Item g() {
/* 329 */     this.bi = true;
/* 330 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item a(String paramString) {
/* 342 */     this.name = "item." + paramString;
/* 343 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 355 */   public String a() { return this.name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item a(Item paramItem) {
/* 363 */     if (this.maxStackSize > 1) {
/* 364 */       throw new IllegalArgumentException("Max stack size must be 1 for items with crafting results");
/*     */     }
/* 366 */     this.craftingResult = paramItem;
/* 367 */     return this;
/*     */   }
/*     */ 
/*     */   
/* 371 */   public Item h() { return this.craftingResult; }
/*     */ 
/*     */ 
/*     */   
/* 375 */   public boolean i() { return (this.craftingResult != null); }
/*     */ 
/*     */ 
/*     */   
/* 379 */   public String j() { return StatisticCollector.a(a() + ".name"); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(ItemStack paramItemStack, World paramWorld, Entity paramEntity, int paramInt, boolean paramBoolean) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void c(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) {}
/*     */ 
/*     */ 
/*     */   
/* 393 */   public boolean b() { return false; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Item.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */