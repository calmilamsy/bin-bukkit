/*     */ package org.bukkit;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.bukkit.material.MaterialData;
/*     */ 
/*     */ 
/*     */ 
/*     */ public static enum Material
/*     */ {
/*  15 */   AIR(false),
/*  16 */   STONE(true),
/*  17 */   GRASS(2),
/*  18 */   DIRT(3),
/*  19 */   COBBLESTONE(4),
/*  20 */   WOOD(5),
/*  21 */   SAPLING(6, org.bukkit.material.Tree.class),
/*  22 */   BEDROCK(7),
/*  23 */   WATER(8, MaterialData.class),
/*  24 */   STATIONARY_WATER(9, MaterialData.class),
/*  25 */   LAVA(10, MaterialData.class),
/*  26 */   STATIONARY_LAVA(11, MaterialData.class),
/*  27 */   SAND(12),
/*  28 */   GRAVEL(13),
/*  29 */   GOLD_ORE(14),
/*  30 */   IRON_ORE(15),
/*  31 */   COAL_ORE(16),
/*  32 */   LOG(17, org.bukkit.material.Tree.class),
/*  33 */   LEAVES(18, org.bukkit.material.Tree.class),
/*  34 */   SPONGE(19),
/*  35 */   GLASS(20),
/*  36 */   LAPIS_ORE(21),
/*  37 */   LAPIS_BLOCK(22),
/*  38 */   DISPENSER(23, org.bukkit.material.Dispenser.class),
/*  39 */   SANDSTONE(24),
/*  40 */   NOTE_BLOCK(25),
/*  41 */   BED_BLOCK(26, org.bukkit.material.Bed.class),
/*  42 */   POWERED_RAIL(27, org.bukkit.material.PoweredRail.class),
/*  43 */   DETECTOR_RAIL(28, org.bukkit.material.DetectorRail.class),
/*  44 */   PISTON_STICKY_BASE(29, org.bukkit.material.PistonBaseMaterial.class),
/*  45 */   WEB(30),
/*  46 */   LONG_GRASS(31, org.bukkit.material.LongGrass.class),
/*  47 */   DEAD_BUSH(32),
/*  48 */   PISTON_BASE(33, org.bukkit.material.PistonBaseMaterial.class),
/*  49 */   PISTON_EXTENSION(34, org.bukkit.material.PistonExtensionMaterial.class),
/*  50 */   WOOL(35, org.bukkit.material.Wool.class),
/*  51 */   PISTON_MOVING_PIECE(36),
/*  52 */   YELLOW_FLOWER(37),
/*  53 */   RED_ROSE(38),
/*  54 */   BROWN_MUSHROOM(39),
/*  55 */   RED_MUSHROOM(40),
/*  56 */   GOLD_BLOCK(41),
/*  57 */   IRON_BLOCK(42),
/*  58 */   DOUBLE_STEP(43, org.bukkit.material.Step.class),
/*  59 */   STEP(44, org.bukkit.material.Step.class),
/*  60 */   BRICK(45),
/*  61 */   TNT(46),
/*  62 */   BOOKSHELF(47),
/*  63 */   MOSSY_COBBLESTONE(48),
/*  64 */   OBSIDIAN(49),
/*  65 */   TORCH(50, org.bukkit.material.Torch.class),
/*  66 */   FIRE(51),
/*  67 */   MOB_SPAWNER(52),
/*  68 */   WOOD_STAIRS(53, org.bukkit.material.Stairs.class),
/*  69 */   CHEST(54),
/*  70 */   REDSTONE_WIRE(55, org.bukkit.material.RedstoneWire.class),
/*  71 */   DIAMOND_ORE(56),
/*  72 */   DIAMOND_BLOCK(57),
/*  73 */   WORKBENCH(58),
/*  74 */   CROPS(59, org.bukkit.material.Crops.class),
/*  75 */   SOIL(60, MaterialData.class),
/*  76 */   FURNACE(61, org.bukkit.material.Furnace.class),
/*  77 */   BURNING_FURNACE(62, org.bukkit.material.Furnace.class),
/*  78 */   SIGN_POST(63, true, org.bukkit.material.Sign.class),
/*  79 */   WOODEN_DOOR(64, org.bukkit.material.Door.class),
/*  80 */   LADDER(65, org.bukkit.material.Ladder.class),
/*  81 */   RAILS(66, org.bukkit.material.Rails.class),
/*  82 */   COBBLESTONE_STAIRS(67, org.bukkit.material.Stairs.class),
/*  83 */   WALL_SIGN(68, true, org.bukkit.material.Sign.class),
/*  84 */   LEVER(69, org.bukkit.material.Lever.class),
/*  85 */   STONE_PLATE(70, org.bukkit.material.PressurePlate.class),
/*  86 */   IRON_DOOR_BLOCK(71, org.bukkit.material.Door.class),
/*  87 */   WOOD_PLATE(72, org.bukkit.material.PressurePlate.class),
/*  88 */   REDSTONE_ORE(73),
/*  89 */   GLOWING_REDSTONE_ORE(74),
/*  90 */   REDSTONE_TORCH_OFF(75, org.bukkit.material.RedstoneTorch.class),
/*  91 */   REDSTONE_TORCH_ON(76, org.bukkit.material.RedstoneTorch.class),
/*  92 */   STONE_BUTTON(77, org.bukkit.material.Button.class),
/*  93 */   SNOW(78),
/*  94 */   ICE(79),
/*  95 */   SNOW_BLOCK(80),
/*  96 */   CACTUS(81, MaterialData.class),
/*  97 */   CLAY(82),
/*  98 */   SUGAR_CANE_BLOCK(83, MaterialData.class),
/*  99 */   JUKEBOX(84, org.bukkit.material.Jukebox.class),
/* 100 */   FENCE(85),
/* 101 */   PUMPKIN(86, org.bukkit.material.Pumpkin.class),
/* 102 */   NETHERRACK(87),
/* 103 */   SOUL_SAND(88),
/* 104 */   GLOWSTONE(89),
/* 105 */   PORTAL(90),
/* 106 */   JACK_O_LANTERN(91, org.bukkit.material.Pumpkin.class),
/* 107 */   CAKE_BLOCK(92, true, org.bukkit.material.Cake.class),
/* 108 */   DIODE_BLOCK_OFF(93, org.bukkit.material.Diode.class),
/* 109 */   DIODE_BLOCK_ON(94, org.bukkit.material.Diode.class),
/* 110 */   LOCKED_CHEST(95),
/* 111 */   TRAP_DOOR(96, org.bukkit.material.TrapDoor.class),
/*     */   
/* 113 */   IRON_SPADE('Ā', true, 'ú'),
/* 114 */   IRON_PICKAXE('ā', true, 'ú'),
/* 115 */   IRON_AXE('Ă', true, 'ú'),
/* 116 */   FLINT_AND_STEEL('ă', true, 64),
/* 117 */   APPLE('Ą', true),
/* 118 */   BOW('ą', true),
/* 119 */   ARROW('Ć'),
/* 120 */   COAL('ć', org.bukkit.material.Coal.class),
/* 121 */   DIAMOND('Ĉ'),
/* 122 */   IRON_INGOT('ĉ'),
/* 123 */   GOLD_INGOT('Ċ'),
/* 124 */   IRON_SWORD('ċ', true, 'ú'),
/* 125 */   WOOD_SWORD('Č', true, 59),
/* 126 */   WOOD_SPADE('č', true, 59),
/* 127 */   WOOD_PICKAXE('Ď', true, 59),
/* 128 */   WOOD_AXE('ď', true, 59),
/* 129 */   STONE_SWORD('Đ', true, ''),
/* 130 */   STONE_SPADE('đ', true, ''),
/* 131 */   STONE_PICKAXE('Ē', true, ''),
/* 132 */   STONE_AXE('ē', true, ''),
/* 133 */   DIAMOND_SWORD('Ĕ', true, 'ؙ'),
/* 134 */   DIAMOND_SPADE('ĕ', true, 'ؙ'),
/* 135 */   DIAMOND_PICKAXE('Ė', true, 'ؙ'),
/* 136 */   DIAMOND_AXE('ė', true, 'ؙ'),
/* 137 */   STICK('Ę'),
/* 138 */   BOWL('ę'),
/* 139 */   MUSHROOM_SOUP('Ě', true),
/* 140 */   GOLD_SWORD('ě', true, 32),
/* 141 */   GOLD_SPADE('Ĝ', true, 32),
/* 142 */   GOLD_PICKAXE('ĝ', true, 32),
/* 143 */   GOLD_AXE('Ğ', true, 32),
/* 144 */   STRING('ğ'),
/* 145 */   FEATHER('Ġ'),
/* 146 */   SULPHUR('ġ'),
/* 147 */   WOOD_HOE('Ģ', true, 59),
/* 148 */   STONE_HOE('ģ', true, ''),
/* 149 */   IRON_HOE('Ĥ', true, 'ú'),
/* 150 */   DIAMOND_HOE('ĥ', true, 'ؙ'),
/* 151 */   GOLD_HOE('Ħ', true, 32),
/* 152 */   SEEDS('ħ'),
/* 153 */   WHEAT('Ĩ'),
/* 154 */   BREAD('ĩ', true),
/* 155 */   LEATHER_HELMET('Ī', true, 33),
/* 156 */   LEATHER_CHESTPLATE('ī', true, 47),
/* 157 */   LEATHER_LEGGINGS('Ĭ', true, 45),
/* 158 */   LEATHER_BOOTS('ĭ', true, 39),
/* 159 */   CHAINMAIL_HELMET('Į', true, 66),
/* 160 */   CHAINMAIL_CHESTPLATE('į', true, 95),
/* 161 */   CHAINMAIL_LEGGINGS('İ', true, 91),
/* 162 */   CHAINMAIL_BOOTS('ı', true, 78),
/* 163 */   IRON_HELMET('Ĳ', true, ''),
/* 164 */   IRON_CHESTPLATE('ĳ', true, '¿'),
/* 165 */   IRON_LEGGINGS('Ĵ', true, '·'),
/* 166 */   IRON_BOOTS('ĵ', true, ''),
/* 167 */   DIAMOND_HELMET('Ķ', true, 'ď'),
/* 168 */   DIAMOND_CHESTPLATE('ķ', true, 'ſ'),
/* 169 */   DIAMOND_LEGGINGS('ĸ', true, 'ů'),
/* 170 */   DIAMOND_BOOTS('Ĺ', true, 'Ŀ'),
/* 171 */   GOLD_HELMET('ĺ', true, 67),
/* 172 */   GOLD_CHESTPLATE('Ļ', true, 95),
/* 173 */   GOLD_LEGGINGS('ļ', true, 91),
/* 174 */   GOLD_BOOTS('Ľ', true, 79),
/* 175 */   FLINT('ľ'),
/* 176 */   PORK('Ŀ', true),
/* 177 */   GRILLED_PORK('ŀ', true),
/* 178 */   PAINTING('Ł'),
/* 179 */   GOLDEN_APPLE('ł', true),
/* 180 */   SIGN('Ń', true),
/* 181 */   WOOD_DOOR('ń', true),
/* 182 */   BUCKET('Ņ', true),
/* 183 */   WATER_BUCKET('ņ', true),
/* 184 */   LAVA_BUCKET('Ň', true),
/* 185 */   MINECART('ň', true),
/* 186 */   SADDLE('ŉ', true),
/* 187 */   IRON_DOOR('Ŋ', true),
/* 188 */   REDSTONE('ŋ'),
/* 189 */   SNOW_BALL('Ō', 16),
/* 190 */   BOAT('ō', true),
/* 191 */   LEATHER('Ŏ'),
/* 192 */   MILK_BUCKET('ŏ', true),
/* 193 */   CLAY_BRICK('Ő'),
/* 194 */   CLAY_BALL('ő'),
/* 195 */   SUGAR_CANE('Œ'),
/* 196 */   PAPER('œ'),
/* 197 */   BOOK('Ŕ'),
/* 198 */   SLIME_BALL('ŕ'),
/* 199 */   STORAGE_MINECART('Ŗ', true),
/* 200 */   POWERED_MINECART('ŗ', true),
/* 201 */   EGG('Ř', 16),
/* 202 */   COMPASS('ř'),
/* 203 */   FISHING_ROD('Ś', true, 64),
/* 204 */   WATCH('ś'),
/* 205 */   GLOWSTONE_DUST('Ŝ'),
/* 206 */   RAW_FISH('ŝ', true),
/* 207 */   COOKED_FISH('Ş', true),
/* 208 */   INK_SACK('ş', org.bukkit.material.Dye.class),
/* 209 */   BONE('Š'),
/* 210 */   SUGAR('š'),
/* 211 */   CAKE('Ţ', true),
/* 212 */   BED('ţ', true),
/* 213 */   DIODE('Ť'),
/* 214 */   COOKIE('ť', 8),
/* 215 */   MAP('Ŧ', true, MaterialData.class),
/* 216 */   SHEARS('ŧ', true, 'î'),
/* 217 */   GOLD_RECORD('࣐', true),
/* 218 */   GREEN_RECORD('࣑', true);
/*     */   private final int id;
/*     */   
/*     */   static  {
/* 222 */     lookupId = new HashMap();
/* 223 */     lookupName = new HashMap();
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
/*     */ 
/*     */ 
/*     */     
/* 380 */     for (Material material : values()) {
/* 381 */       lookupId.put(Integer.valueOf(material.getId()), material);
/* 382 */       lookupName.put(material.name(), material);
/*     */     } 
/*     */   }
/*     */   
/*     */   private final Class<? extends MaterialData> data;
/*     */   private static final Map<Integer, Material> lookupId;
/*     */   private static final Map<String, Material> lookupName;
/*     */   private final int maxStack;
/*     */   private final short durability;
/*     */   
/*     */   Material(int id, int stack, int durability, Class<? extends MaterialData> data) {
/*     */     this.id = id;
/*     */     this.durability = (short)durability;
/*     */     this.maxStack = stack;
/*     */     this.data = data;
/*     */   }
/*     */   
/*     */   public int getId() { return this.id; }
/*     */   
/*     */   public int getMaxStackSize() { return this.maxStack; }
/*     */   
/*     */   public short getMaxDurability() { return this.durability; }
/*     */   
/*     */   public Class<? extends MaterialData> getData() { return this.data; }
/*     */   
/*     */   public MaterialData getNewData(byte raw) {
/*     */     if (this.data == null)
/*     */       return null; 
/*     */     try {
/*     */       Constructor<? extends MaterialData> ctor = this.data.getConstructor(new Class[] { int.class, byte.class });
/*     */       return (MaterialData)ctor.newInstance(new Object[] { Integer.valueOf(this.id), Byte.valueOf(raw) });
/*     */     } catch (InstantiationException ex) {
/*     */       Logger.getLogger(Material.class.getName()).log(Level.SEVERE, null, ex);
/*     */     } catch (IllegalAccessException ex) {
/*     */       Logger.getLogger(Material.class.getName()).log(Level.SEVERE, null, ex);
/*     */     } catch (IllegalArgumentException ex) {
/*     */       Logger.getLogger(Material.class.getName()).log(Level.SEVERE, null, ex);
/*     */     } catch (InvocationTargetException ex) {
/*     */       Logger.getLogger(Material.class.getName()).log(Level.SEVERE, null, ex);
/*     */     } catch (NoSuchMethodException ex) {
/*     */       Logger.getLogger(Material.class.getName()).log(Level.SEVERE, null, ex);
/*     */     } catch (SecurityException ex) {
/*     */       Logger.getLogger(Material.class.getName()).log(Level.SEVERE, null, ex);
/*     */     } 
/*     */     return null;
/*     */   }
/*     */   
/*     */   public boolean isBlock() { return (this.id < 256); }
/*     */   
/*     */   public static Material getMaterial(int id) { return (Material)lookupId.get(Integer.valueOf(id)); }
/*     */   
/*     */   public static Material getMaterial(String name) { return (Material)lookupName.get(name); }
/*     */   
/*     */   public static Material matchMaterial(String name) {
/*     */     Material result = null;
/*     */     try {
/*     */       result = getMaterial(Integer.parseInt(name));
/*     */     } catch (NumberFormatException ex) {}
/*     */     if (result == null) {
/*     */       String filtered = name.toUpperCase();
/*     */       filtered = filtered.replaceAll("\\s+", "_").replaceAll("\\W", "");
/*     */       result = (Material)lookupName.get(filtered);
/*     */     } 
/*     */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\Material.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */