/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class Block
/*     */ {
/*   8 */   public static final StepSound d = new StepSound("stone", 1.0F, 1.0F);
/*   9 */   public static final StepSound e = new StepSound("wood", 1.0F, 1.0F);
/*  10 */   public static final StepSound f = new StepSound("gravel", 1.0F, 1.0F);
/*  11 */   public static final StepSound g = new StepSound("grass", 1.0F, 1.0F);
/*  12 */   public static final StepSound h = new StepSound("stone", 1.0F, 1.0F);
/*  13 */   public static final StepSound i = new StepSound("stone", 1.0F, 1.5F);
/*  14 */   public static final StepSound j = new StepSoundStone("stone", 1.0F, 1.0F);
/*  15 */   public static final StepSound k = new StepSound("cloth", 1.0F, 1.0F);
/*  16 */   public static final StepSound l = new StepSoundSand("sand", 1.0F, 1.0F);
/*  17 */   public static final Block[] byId = new Block[256];
/*  18 */   public static final boolean[] n = new boolean[256];
/*  19 */   public static final boolean[] o = new boolean[256];
/*  20 */   public static final boolean[] isTileEntity = new boolean[256];
/*  21 */   public static final int[] q = new int[256];
/*  22 */   public static final boolean[] r = new boolean[256];
/*  23 */   public static final int[] s = new int[256];
/*  24 */   public static final boolean[] t = new boolean[256];
/*  25 */   public static final Block STONE = (new BlockStone(true, true)).c(1.5F).b(10.0F).a(h).a("stone");
/*  26 */   public static final BlockGrass GRASS = (BlockGrass)(new BlockGrass(2)).c(0.6F).a(g).a("grass");
/*  27 */   public static final Block DIRT = (new BlockDirt(3, 2)).c(0.5F).a(f).a("dirt");
/*  28 */   public static final Block COBBLESTONE = (new Block(4, 16, Material.STONE)).c(2.0F).b(10.0F).a(h).a("stonebrick");
/*  29 */   public static final Block WOOD = (new Block(5, 4, Material.WOOD)).c(2.0F).b(5.0F).a(e).a("wood").g();
/*  30 */   public static final Block SAPLING = (new BlockSapling(6, 15)).c(0.0F).a(g).a("sapling").g();
/*  31 */   public static final Block BEDROCK = (new Block(7, 17, Material.STONE)).i().b(6000000.0F).a(h).a("bedrock").n();
/*  32 */   public static final Block WATER = (new BlockFlowing(8, Material.WATER)).c(100.0F).f(3).a("water").n().g();
/*  33 */   public static final Block STATIONARY_WATER = (new BlockStationary(9, Material.WATER)).c(100.0F).f(3).a("water").n().g();
/*  34 */   public static final Block LAVA = (new BlockFlowing(10, Material.LAVA)).c(0.0F).a(1.0F).f(255).a("lava").n().g();
/*  35 */   public static final Block STATIONARY_LAVA = (new BlockStationary(11, Material.LAVA)).c(100.0F).a(1.0F).f(255).a("lava").n().g();
/*  36 */   public static final Block SAND = (new BlockSand(12, 18)).c(0.5F).a(l).a("sand");
/*  37 */   public static final Block GRAVEL = (new BlockGravel(13, 19)).c(0.6F).a(f).a("gravel");
/*  38 */   public static final Block GOLD_ORE = (new BlockOre(14, 32)).c(3.0F).b(5.0F).a(h).a("oreGold");
/*  39 */   public static final Block IRON_ORE = (new BlockOre(15, 33)).c(3.0F).b(5.0F).a(h).a("oreIron");
/*  40 */   public static final Block COAL_ORE = (new BlockOre(16, 34)).c(3.0F).b(5.0F).a(h).a("oreCoal");
/*  41 */   public static final Block LOG = (new BlockLog(17)).c(2.0F).a(e).a("log").g();
/*  42 */   public static final BlockLeaves LEAVES = (BlockLeaves)(new BlockLeaves(18, 52)).c(0.2F).f(1).a(g).a("leaves").n().g();
/*  43 */   public static final Block SPONGE = (new BlockSponge(19)).c(0.6F).a(g).a("sponge");
/*  44 */   public static final Block GLASS = (new BlockGlass(20, 49, Material.SHATTERABLE, false)).c(0.3F).a(j).a("glass");
/*  45 */   public static final Block LAPIS_ORE = (new BlockOre(21, ' ')).c(3.0F).b(5.0F).a(h).a("oreLapis");
/*  46 */   public static final Block LAPIS_BLOCK = (new Block(22, '', Material.STONE)).c(3.0F).b(5.0F).a(h).a("blockLapis");
/*  47 */   public static final Block DISPENSER = (new BlockDispenser(23)).c(3.5F).a(h).a("dispenser").g();
/*  48 */   public static final Block SANDSTONE = (new BlockSandStone(24)).a(h).c(0.8F).a("sandStone");
/*  49 */   public static final Block NOTE_BLOCK = (new BlockNote(25)).c(0.8F).a("musicBlock").g();
/*  50 */   public static final Block BED = (new BlockBed(26)).c(0.2F).a("bed").n().g();
/*  51 */   public static final Block GOLDEN_RAIL = (new BlockMinecartTrack(27, '³', true)).c(0.7F).a(Block.i).a("goldenRail").g();
/*  52 */   public static final Block DETECTOR_RAIL = (new BlockMinecartDetector(28, 'Ã')).c(0.7F).a(Block.i).a("detectorRail").g();
/*  53 */   public static final Block PISTON_STICKY = (new BlockPiston(29, 106, true)).a("pistonStickyBase").g();
/*  54 */   public static final Block WEB = (new BlockWeb(30, 11)).f(1).c(4.0F).a("web");
/*  55 */   public static final BlockLongGrass LONG_GRASS = (BlockLongGrass)(new BlockLongGrass(31, 39)).c(0.0F).a(g).a("tallgrass");
/*  56 */   public static final BlockDeadBush DEAD_BUSH = (BlockDeadBush)(new BlockDeadBush(32, 55)).c(0.0F).a(g).a("deadbush");
/*  57 */   public static final Block PISTON = (new BlockPiston(33, 107, false)).a("pistonBase").g();
/*  58 */   public static final BlockPistonExtension PISTON_EXTENSION = (BlockPistonExtension)(new BlockPistonExtension(34, 107)).g();
/*  59 */   public static final Block WOOL = (new BlockCloth()).c(0.8F).a(k).a("cloth").g();
/*  60 */   public static final BlockPistonMoving PISTON_MOVING = new BlockPistonMoving(36);
/*  61 */   public static final BlockFlower YELLOW_FLOWER = (BlockFlower)(new BlockFlower(37, 13)).c(0.0F).a(g).a("flower");
/*  62 */   public static final BlockFlower RED_ROSE = (BlockFlower)(new BlockFlower(38, 12)).c(0.0F).a(g).a("rose");
/*  63 */   public static final BlockFlower BROWN_MUSHROOM = (BlockFlower)(new BlockMushroom(39, 29)).c(0.0F).a(g).a(0.125F).a("mushroom");
/*  64 */   public static final BlockFlower RED_MUSHROOM = (BlockFlower)(new BlockMushroom(40, 28)).c(0.0F).a(g).a("mushroom");
/*  65 */   public static final Block GOLD_BLOCK = (new BlockOreBlock(41, 23)).c(3.0F).b(10.0F).a(Block.i).a("blockGold");
/*  66 */   public static final Block IRON_BLOCK = (new BlockOreBlock(42, 22)).c(5.0F).b(10.0F).a(Block.i).a("blockIron");
/*  67 */   public static final Block DOUBLE_STEP = (new BlockStep(43, true)).c(2.0F).b(10.0F).a(h).a("stoneSlab");
/*  68 */   public static final Block STEP = (new BlockStep(44, false)).c(2.0F).b(10.0F).a(h).a("stoneSlab");
/*  69 */   public static final Block BRICK = (new Block(45, 7, Material.STONE)).c(2.0F).b(10.0F).a(h).a("brick");
/*  70 */   public static final Block TNT = (new BlockTNT(46, 8)).c(0.0F).a(g).a("tnt");
/*  71 */   public static final Block BOOKSHELF = (new BlockBookshelf(47, 35)).c(1.5F).a(e).a("bookshelf");
/*  72 */   public static final Block MOSSY_COBBLESTONE = (new Block(48, 36, Material.STONE)).c(2.0F).b(10.0F).a(h).a("stoneMoss");
/*  73 */   public static final Block OBSIDIAN = (new BlockObsidian(49, 37)).c(10.0F).b(2000.0F).a(h).a("obsidian");
/*  74 */   public static final Block TORCH = (new BlockTorch(50, 80)).c(0.0F).a(0.9375F).a(e).a("torch").g();
/*  75 */   public static final BlockFire FIRE = (BlockFire)(new BlockFire(51, 31)).c(0.0F).a(1.0F).a(e).a("fire").n().g();
/*  76 */   public static final Block MOB_SPAWNER = (new BlockMobSpawner(52, 65)).c(5.0F).a(Block.i).a("mobSpawner").n();
/*  77 */   public static final Block WOOD_STAIRS = (new BlockStairs(53, WOOD)).a("stairsWood").g();
/*  78 */   public static final Block CHEST = (new BlockChest(54)).c(2.5F).a(e).a("chest").g();
/*  79 */   public static final Block REDSTONE_WIRE = (new BlockRedstoneWire(55, '¤')).c(0.0F).a(d).a("redstoneDust").n().g();
/*  80 */   public static final Block DIAMOND_ORE = (new BlockOre(56, 50)).c(3.0F).b(5.0F).a(h).a("oreDiamond");
/*  81 */   public static final Block DIAMOND_BLOCK = (new BlockOreBlock(57, 24)).c(5.0F).b(10.0F).a(Block.i).a("blockDiamond");
/*  82 */   public static final Block WORKBENCH = (new BlockWorkbench(58)).c(2.5F).a(e).a("workbench");
/*  83 */   public static final Block CROPS = (new BlockCrops(59, 88)).c(0.0F).a(g).a("crops").n().g();
/*  84 */   public static final Block SOIL = (new BlockSoil(60)).c(0.6F).a(f).a("farmland");
/*  85 */   public static final Block FURNACE = (new BlockFurnace(61, false)).c(3.5F).a(h).a("furnace").g();
/*  86 */   public static final Block BURNING_FURNACE = (new BlockFurnace(62, true)).c(3.5F).a(h).a(0.875F).a("furnace").g();
/*  87 */   public static final Block SIGN_POST = (new BlockSign(63, TileEntitySign.class, true)).c(1.0F).a(e).a("sign").n().g();
/*  88 */   public static final Block WOODEN_DOOR = (new BlockDoor(64, Material.WOOD)).c(3.0F).a(e).a("doorWood").n().g();
/*  89 */   public static final Block LADDER = (new BlockLadder(65, 83)).c(0.4F).a(e).a("ladder").g();
/*  90 */   public static final Block RAILS = (new BlockMinecartTrack(66, '', false)).c(0.7F).a(Block.i).a("rail").g();
/*  91 */   public static final Block COBBLESTONE_STAIRS = (new BlockStairs(67, COBBLESTONE)).a("stairsStone").g();
/*  92 */   public static final Block WALL_SIGN = (new BlockSign(68, TileEntitySign.class, false)).c(1.0F).a(e).a("sign").n().g();
/*  93 */   public static final Block LEVER = (new BlockLever(69, 96)).c(0.5F).a(e).a("lever").g();
/*  94 */   public static final Block STONE_PLATE = (new BlockPressurePlate(70, STONE.textureId, EnumMobType.MOBS, Material.STONE)).c(0.5F).a(h).a("pressurePlate").g();
/*  95 */   public static final Block IRON_DOOR_BLOCK = (new BlockDoor(71, Material.ORE)).c(5.0F).a(Block.i).a("doorIron").n().g();
/*  96 */   public static final Block WOOD_PLATE = (new BlockPressurePlate(72, WOOD.textureId, EnumMobType.EVERYTHING, Material.WOOD)).c(0.5F).a(e).a("pressurePlate").g();
/*  97 */   public static final Block REDSTONE_ORE = (new BlockRedstoneOre(73, 51, false)).c(3.0F).b(5.0F).a(h).a("oreRedstone").g();
/*  98 */   public static final Block GLOWING_REDSTONE_ORE = (new BlockRedstoneOre(74, 51, true)).a(0.625F).c(3.0F).b(5.0F).a(h).a("oreRedstone").g();
/*  99 */   public static final Block REDSTONE_TORCH_OFF = (new BlockRedstoneTorch(75, 115, false)).c(0.0F).a(e).a("notGate").g();
/* 100 */   public static final Block REDSTONE_TORCH_ON = (new BlockRedstoneTorch(76, 99, true)).c(0.0F).a(0.5F).a(e).a("notGate").g();
/* 101 */   public static final Block STONE_BUTTON = (new BlockButton(77, STONE.textureId)).c(0.5F).a(h).a("button").g();
/* 102 */   public static final Block SNOW = (new BlockSnow(78, 66)).c(0.1F).a(k).a("snow");
/* 103 */   public static final Block ICE = (new BlockIce(79, 67)).c(0.5F).f(3).a(j).a("ice");
/* 104 */   public static final Block SNOW_BLOCK = (new BlockSnowBlock(80, 66)).c(0.2F).a(k).a("snow");
/* 105 */   public static final Block CACTUS = (new BlockCactus(81, 70)).c(0.4F).a(k).a("cactus");
/* 106 */   public static final Block CLAY = (new BlockClay(82, 72)).c(0.6F).a(f).a("clay");
/* 107 */   public static final Block SUGAR_CANE_BLOCK = (new BlockReed(83, 73)).c(0.0F).a(g).a("reeds").n();
/* 108 */   public static final Block JUKEBOX = (new BlockJukeBox(84, 74)).c(2.0F).b(10.0F).a(h).a("jukebox").g();
/* 109 */   public static final Block FENCE = (new BlockFence(85, 4)).c(2.0F).b(5.0F).a(e).a("fence").g();
/* 110 */   public static final Block PUMPKIN = (new BlockPumpkin(86, 102, false)).c(1.0F).a(e).a("pumpkin").g();
/* 111 */   public static final Block NETHERRACK = (new BlockBloodStone(87, 103)).c(0.4F).a(h).a("hellrock");
/* 112 */   public static final Block SOUL_SAND = (new BlockSlowSand(88, 104)).c(0.5F).a(l).a("hellsand");
/* 113 */   public static final Block GLOWSTONE = (new BlockLightStone(89, 105, Material.STONE)).c(0.3F).a(j).a(1.0F).a("lightgem");
/* 114 */   public static final BlockPortal PORTAL = (BlockPortal)(new BlockPortal(90, 14)).c(-1.0F).a(j).a(0.75F).a("portal");
/* 115 */   public static final Block JACK_O_LANTERN = (new BlockPumpkin(91, 102, true)).c(1.0F).a(e).a(1.0F).a("litpumpkin").g();
/* 116 */   public static final Block CAKE_BLOCK = (new BlockCake(92, 121)).c(0.5F).a(k).a("cake").n().g();
/* 117 */   public static final Block DIODE_OFF = (new BlockDiode(93, false)).c(0.0F).a(e).a("diode").n().g();
/* 118 */   public static final Block DIODE_ON = (new BlockDiode(94, true)).c(0.0F).a(0.625F).a(e).a("diode").n().g();
/* 119 */   public static final Block LOCKED_CHEST = (new BlockLockedChest(95)).c(0.0F).a(1.0F).a(e).a("lockedchest").a(true).g();
/* 120 */   public static final Block TRAP_DOOR = (new BlockTrapdoor(96, Material.WOOD)).c(3.0F).a(e).a("trapdoor").n().g();
/*     */   
/*     */   public int textureId;
/*     */   
/*     */   public final int id;
/*     */   
/*     */   protected float strength;
/*     */   
/*     */   protected float durability;
/*     */   
/*     */   protected boolean bq = true;
/*     */   
/*     */   protected boolean br = true;
/*     */   
/*     */   public double minX;
/*     */   
/*     */   public double minY;
/*     */   
/*     */   public double minZ;
/*     */   public double maxX;
/*     */   public double maxY;
/*     */   public double maxZ;
/* 142 */   public StepSound stepSound = d;
/* 143 */   public float bz = 1.0F; public final Material material;
/* 144 */   public float frictionFactor = 0.6F; protected Block(int i, Material material) {
/* 145 */     if (byId[i] != null) {
/* 146 */       throw new IllegalArgumentException("Slot " + i + " is already occupied by " + byId[i] + " when adding " + this);
/*     */     }
/* 148 */     this.material = material;
/* 149 */     byId[i] = this;
/* 150 */     this.id = i;
/* 151 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 152 */     o[i] = a();
/* 153 */     q[i] = a() ? 255 : 0;
/* 154 */     r[i] = !material.blocksLight();
/* 155 */     isTileEntity[i] = false;
/*     */   }
/*     */   private String name;
/*     */   
/*     */   protected Block g() {
/* 160 */     t[this.id] = true;
/* 161 */     return this;
/*     */   }
/*     */   
/*     */   protected void h() {}
/*     */   
/*     */   protected Block(int i, int j, Material material) {
/* 167 */     this(i, material);
/* 168 */     this.textureId = j;
/*     */   }
/*     */   
/*     */   protected Block a(StepSound stepsound) {
/* 172 */     this.stepSound = stepsound;
/* 173 */     return this;
/*     */   }
/*     */   
/*     */   protected Block f(int i) {
/* 177 */     q[this.id] = i;
/* 178 */     return this;
/*     */   }
/*     */   
/*     */   protected Block a(float f) {
/* 182 */     s[this.id] = (int)(15.0F * f);
/* 183 */     return this;
/*     */   }
/*     */   
/*     */   protected Block b(float f) {
/* 187 */     this.durability = f * 3.0F;
/* 188 */     return this;
/*     */   }
/*     */ 
/*     */   
/* 192 */   public boolean b() { return true; }
/*     */ 
/*     */   
/*     */   protected Block c(float f) {
/* 196 */     this.strength = f;
/* 197 */     if (this.durability < f * 5.0F) {
/* 198 */       this.durability = f * 5.0F;
/*     */     }
/*     */     
/* 201 */     return this;
/*     */   }
/*     */   
/*     */   protected Block i() {
/* 205 */     c(-1.0F);
/* 206 */     return this;
/*     */   }
/*     */ 
/*     */   
/* 210 */   public float j() { return this.strength; }
/*     */ 
/*     */   
/*     */   protected Block a(boolean flag) {
/* 214 */     n[this.id] = flag;
/* 215 */     return this;
/*     */   }
/*     */   
/*     */   public void a(float f, float f1, float f2, float f3, float f4, float f5) {
/* 219 */     this.minX = f;
/* 220 */     this.minY = f1;
/* 221 */     this.minZ = f2;
/* 222 */     this.maxX = f3;
/* 223 */     this.maxY = f4;
/* 224 */     this.maxZ = f5;
/*     */   }
/*     */ 
/*     */   
/* 228 */   public boolean b(IBlockAccess iblockaccess, int i, int j, int k, int l) { return iblockaccess.getMaterial(i, j, k).isBuildable(); }
/*     */ 
/*     */ 
/*     */   
/* 232 */   public int a(int i, int j) { return a(i); }
/*     */ 
/*     */ 
/*     */   
/* 236 */   public int a(int i) { return this.textureId; }
/*     */ 
/*     */   
/*     */   public void a(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, ArrayList arraylist) {
/* 240 */     AxisAlignedBB axisalignedbb1 = e(world, i, j, k);
/*     */     
/* 242 */     if (axisalignedbb1 != null && axisalignedbb.a(axisalignedbb1)) {
/* 243 */       arraylist.add(axisalignedbb1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 248 */   public AxisAlignedBB e(World world, int i, int j, int k) { return AxisAlignedBB.b(i + this.minX, j + this.minY, k + this.minZ, i + this.maxX, j + this.maxY, k + this.maxZ); }
/*     */ 
/*     */ 
/*     */   
/* 252 */   public boolean a() { return true; }
/*     */ 
/*     */ 
/*     */   
/* 256 */   public boolean a(int i, boolean flag) { return k_(); }
/*     */ 
/*     */ 
/*     */   
/* 260 */   public boolean k_() { return true; }
/*     */ 
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {}
/*     */ 
/*     */   
/*     */   public void postBreak(World world, int i, int j, int k, int l) {}
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, int l) {}
/*     */   
/* 270 */   public int c() { return 10; }
/*     */ 
/*     */   
/*     */   public void c(World world, int i, int j, int k) {}
/*     */ 
/*     */   
/*     */   public void remove(World world, int i, int j, int k) {}
/*     */   
/* 278 */   public int a(Random random) { return 1; }
/*     */ 
/*     */ 
/*     */   
/* 282 */   public int a(int i, Random random) { return this.id; }
/*     */ 
/*     */ 
/*     */   
/* 286 */   public float getDamage(EntityHuman entityhuman) { return (this.strength < 0.0F) ? 0.0F : (!entityhuman.b(this) ? (1.0F / this.strength / 100.0F) : (entityhuman.a(this) / this.strength / 30.0F)); }
/*     */ 
/*     */ 
/*     */   
/* 290 */   public final void g(World world, int i, int j, int k, int l) { dropNaturally(world, i, j, k, l, 1.0F); }
/*     */ 
/*     */   
/*     */   public void dropNaturally(World world, int i, int j, int k, int l, float f) {
/* 294 */     if (!world.isStatic) {
/* 295 */       int i1 = a(world.random);
/*     */       
/* 297 */       for (int j1 = 0; j1 < i1; j1++) {
/*     */         
/* 299 */         if (world.random.nextFloat() < f) {
/* 300 */           int k1 = a(l, world.random);
/*     */           
/* 302 */           if (k1 > 0) {
/* 303 */             a(world, i, j, k, new ItemStack(k1, true, a_(l)));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void a(World world, int i, int j, int k, ItemStack itemstack) {
/* 311 */     if (!world.isStatic) {
/* 312 */       float f = 0.7F;
/* 313 */       double d0 = (world.random.nextFloat() * f) + (1.0F - f) * 0.5D;
/* 314 */       double d1 = (world.random.nextFloat() * f) + (1.0F - f) * 0.5D;
/* 315 */       double d2 = (world.random.nextFloat() * f) + (1.0F - f) * 0.5D;
/* 316 */       EntityItem entityitem = new EntityItem(world, i + d0, j + d1, k + d2, itemstack);
/*     */       
/* 318 */       entityitem.pickupDelay = 10;
/* 319 */       world.addEntity(entityitem);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 324 */   protected int a_(int i) { return 0; }
/*     */ 
/*     */ 
/*     */   
/* 328 */   public float a(Entity entity) { return this.durability / 5.0F; }
/*     */ 
/*     */   
/*     */   public MovingObjectPosition a(World world, int i, int j, int k, Vec3D vec3d, Vec3D vec3d1) {
/* 332 */     a(world, i, j, k);
/* 333 */     vec3d = vec3d.add(-i, -j, -k);
/* 334 */     vec3d1 = vec3d1.add(-i, -j, -k);
/* 335 */     Vec3D vec3d2 = vec3d.a(vec3d1, this.minX);
/* 336 */     Vec3D vec3d3 = vec3d.a(vec3d1, this.maxX);
/* 337 */     Vec3D vec3d4 = vec3d.b(vec3d1, this.minY);
/* 338 */     Vec3D vec3d5 = vec3d.b(vec3d1, this.maxY);
/* 339 */     Vec3D vec3d6 = vec3d.c(vec3d1, this.minZ);
/* 340 */     Vec3D vec3d7 = vec3d.c(vec3d1, this.maxZ);
/*     */     
/* 342 */     if (!a(vec3d2)) {
/* 343 */       vec3d2 = null;
/*     */     }
/*     */     
/* 346 */     if (!a(vec3d3)) {
/* 347 */       vec3d3 = null;
/*     */     }
/*     */     
/* 350 */     if (!b(vec3d4)) {
/* 351 */       vec3d4 = null;
/*     */     }
/*     */     
/* 354 */     if (!b(vec3d5)) {
/* 355 */       vec3d5 = null;
/*     */     }
/*     */     
/* 358 */     if (!c(vec3d6)) {
/* 359 */       vec3d6 = null;
/*     */     }
/*     */     
/* 362 */     if (!c(vec3d7)) {
/* 363 */       vec3d7 = null;
/*     */     }
/*     */     
/* 366 */     Vec3D vec3d8 = null;
/*     */     
/* 368 */     if (vec3d2 != null && (vec3d8 == null || vec3d.a(vec3d2) < vec3d.a(vec3d8))) {
/* 369 */       vec3d8 = vec3d2;
/*     */     }
/*     */     
/* 372 */     if (vec3d3 != null && (vec3d8 == null || vec3d.a(vec3d3) < vec3d.a(vec3d8))) {
/* 373 */       vec3d8 = vec3d3;
/*     */     }
/*     */     
/* 376 */     if (vec3d4 != null && (vec3d8 == null || vec3d.a(vec3d4) < vec3d.a(vec3d8))) {
/* 377 */       vec3d8 = vec3d4;
/*     */     }
/*     */     
/* 380 */     if (vec3d5 != null && (vec3d8 == null || vec3d.a(vec3d5) < vec3d.a(vec3d8))) {
/* 381 */       vec3d8 = vec3d5;
/*     */     }
/*     */     
/* 384 */     if (vec3d6 != null && (vec3d8 == null || vec3d.a(vec3d6) < vec3d.a(vec3d8))) {
/* 385 */       vec3d8 = vec3d6;
/*     */     }
/*     */     
/* 388 */     if (vec3d7 != null && (vec3d8 == null || vec3d.a(vec3d7) < vec3d.a(vec3d8))) {
/* 389 */       vec3d8 = vec3d7;
/*     */     }
/*     */     
/* 392 */     if (vec3d8 == null) {
/* 393 */       return null;
/*     */     }
/* 395 */     byte b0 = -1;
/*     */     
/* 397 */     if (vec3d8 == vec3d2) {
/* 398 */       b0 = 4;
/*     */     }
/*     */     
/* 401 */     if (vec3d8 == vec3d3) {
/* 402 */       b0 = 5;
/*     */     }
/*     */     
/* 405 */     if (vec3d8 == vec3d4) {
/* 406 */       b0 = 0;
/*     */     }
/*     */     
/* 409 */     if (vec3d8 == vec3d5) {
/* 410 */       b0 = 1;
/*     */     }
/*     */     
/* 413 */     if (vec3d8 == vec3d6) {
/* 414 */       b0 = 2;
/*     */     }
/*     */     
/* 417 */     if (vec3d8 == vec3d7) {
/* 418 */       b0 = 3;
/*     */     }
/*     */     
/* 421 */     return new MovingObjectPosition(i, j, k, b0, vec3d8.add(i, j, k));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 426 */   private boolean a(Vec3D vec3d) { return (vec3d == null) ? false : ((vec3d.b >= this.minY && vec3d.b <= this.maxY && vec3d.c >= this.minZ && vec3d.c <= this.maxZ)); }
/*     */ 
/*     */ 
/*     */   
/* 430 */   private boolean b(Vec3D vec3d) { return (vec3d == null) ? false : ((vec3d.a >= this.minX && vec3d.a <= this.maxX && vec3d.c >= this.minZ && vec3d.c <= this.maxZ)); }
/*     */ 
/*     */ 
/*     */   
/* 434 */   private boolean c(Vec3D vec3d) { return (vec3d == null) ? false : ((vec3d.a >= this.minX && vec3d.a <= this.maxX && vec3d.b >= this.minY && vec3d.b <= this.maxY)); }
/*     */ 
/*     */   
/*     */   public void d(World world, int i, int j, int k) {}
/*     */ 
/*     */   
/* 440 */   public boolean canPlace(World world, int i, int j, int k, int l) { return canPlace(world, i, j, k); }
/*     */ 
/*     */   
/*     */   public boolean canPlace(World world, int i, int j, int k) {
/* 444 */     int l = world.getTypeId(i, j, k);
/*     */     
/* 446 */     return (l == 0 || (byId[l]).material.isReplacable());
/*     */   }
/*     */ 
/*     */   
/* 450 */   public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman) { return false; }
/*     */ 
/*     */   
/*     */   public void b(World world, int i, int j, int k, Entity entity) {}
/*     */ 
/*     */   
/*     */   public void postPlace(World world, int i, int j, int k, int l) {}
/*     */   
/*     */   public void b(World world, int i, int j, int k, EntityHuman entityhuman) {}
/*     */   
/*     */   public void a(World world, int i, int j, int k, Entity entity, Vec3D vec3d) {}
/*     */   
/*     */   public void a(IBlockAccess iblockaccess, int i, int j, int k) {}
/*     */   
/* 464 */   public boolean a(IBlockAccess iblockaccess, int i, int j, int k, int l) { return false; }
/*     */ 
/*     */ 
/*     */   
/* 468 */   public boolean isPowerSource() { return false; }
/*     */ 
/*     */   
/*     */   public void a(World world, int i, int j, int k, Entity entity) {}
/*     */ 
/*     */   
/* 474 */   public boolean d(World world, int i, int j, int k, int l) { return false; }
/*     */ 
/*     */   
/*     */   public void a(World world, EntityHuman entityhuman, int i, int j, int k, int l) {
/* 478 */     entityhuman.a(StatisticList.C[this.id], 1);
/* 479 */     g(world, i, j, k, l);
/*     */   }
/*     */ 
/*     */   
/* 483 */   public boolean f(World world, int i, int j, int k) { return true; }
/*     */ 
/*     */   
/*     */   public void postPlace(World world, int i, int j, int k, EntityLiving entityliving) {}
/*     */   
/*     */   public Block a(String s) {
/* 489 */     this.name = "tile." + s;
/* 490 */     return this;
/*     */   }
/*     */ 
/*     */   
/* 494 */   public String k() { return StatisticCollector.a(l() + ".name"); }
/*     */ 
/*     */ 
/*     */   
/* 498 */   public String l() { return this.name; }
/*     */ 
/*     */   
/*     */   public void a(World world, int i, int j, int k, int l, int i1) {}
/*     */ 
/*     */   
/* 504 */   public boolean m() { return this.br; }
/*     */ 
/*     */   
/*     */   protected Block n() {
/* 508 */     this.br = false;
/* 509 */     return this;
/*     */   }
/*     */ 
/*     */   
/* 513 */   public int e() { return this.material.j(); }
/*     */ 
/*     */   
/*     */   static  {
/* 517 */     Item.byId[WOOL.id] = (new ItemCloth(WOOL.id - 256)).a("cloth");
/* 518 */     Item.byId[LOG.id] = (new ItemLog(LOG.id - 256)).a("log");
/* 519 */     Item.byId[STEP.id] = (new ItemStep(STEP.id - 256)).a("stoneSlab");
/* 520 */     Item.byId[SAPLING.id] = (new ItemSapling(SAPLING.id - 256)).a("sapling");
/* 521 */     Item.byId[LEAVES.id] = (new ItemLeaves(LEAVES.id - 256)).a("leaves");
/* 522 */     Item.byId[PISTON.id] = new ItemPiston(PISTON.id - 256);
/* 523 */     Item.byId[PISTON_STICKY.id] = new ItemPiston(PISTON_STICKY.id - 256);
/*     */     
/* 525 */     for (i = 0; i < 256; i++) {
/* 526 */       if (byId[i] != null && Item.byId[i] == null) {
/* 527 */         Item.byId[i] = new ItemBlock(i - 256);
/* 528 */         byId[i].h();
/*     */       } 
/*     */     } 
/*     */     
/* 532 */     r[0] = true;
/* 533 */     StatisticList.b();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Block.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */