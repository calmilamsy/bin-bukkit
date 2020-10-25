/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityTypes
/*     */ {
/*  14 */   private static Map a = new HashMap();
/*  15 */   private static Map b = new HashMap();
/*  16 */   private static Map c = new HashMap();
/*  17 */   private static Map d = new HashMap();
/*     */   
/*     */   private static void a(Class paramClass, String paramString, int paramInt) {
/*  20 */     a.put(paramString, paramClass);
/*  21 */     b.put(paramClass, paramString);
/*  22 */     c.put(Integer.valueOf(paramInt), paramClass);
/*  23 */     d.put(paramClass, Integer.valueOf(paramInt));
/*     */   }
/*     */   
/*     */   static  {
/*  27 */     a(EntityArrow.class, "Arrow", 10);
/*  28 */     a(EntitySnowball.class, "Snowball", 11);
/*  29 */     a(EntityItem.class, "Item", 1);
/*  30 */     a(EntityPainting.class, "Painting", 9);
/*     */     
/*  32 */     a(EntityLiving.class, "Mob", 48);
/*  33 */     a(EntityMonster.class, "Monster", 49);
/*     */     
/*  35 */     a(EntityCreeper.class, "Creeper", 50);
/*  36 */     a(EntitySkeleton.class, "Skeleton", 51);
/*  37 */     a(EntitySpider.class, "Spider", 52);
/*  38 */     a(EntityGiantZombie.class, "Giant", 53);
/*  39 */     a(EntityZombie.class, "Zombie", 54);
/*  40 */     a(EntitySlime.class, "Slime", 55);
/*  41 */     a(EntityGhast.class, "Ghast", 56);
/*  42 */     a(EntityPigZombie.class, "PigZombie", 57);
/*     */     
/*  44 */     a(EntityPig.class, "Pig", 90);
/*  45 */     a(EntitySheep.class, "Sheep", 91);
/*  46 */     a(EntityCow.class, "Cow", 92);
/*  47 */     a(EntityChicken.class, "Chicken", 93);
/*  48 */     a(EntitySquid.class, "Squid", 94);
/*  49 */     a(EntityWolf.class, "Wolf", 95);
/*     */     
/*  51 */     a(EntityTNTPrimed.class, "PrimedTnt", 20);
/*  52 */     a(EntityFallingSand.class, "FallingSand", 21);
/*     */     
/*  54 */     a(EntityMinecart.class, "Minecart", 40);
/*  55 */     a(EntityBoat.class, "Boat", 41);
/*     */   }
/*     */   
/*     */   public static Entity a(String paramString, World paramWorld) {
/*  59 */     Entity entity = null;
/*     */     try {
/*  61 */       Class clazz = (Class)a.get(paramString);
/*  62 */       if (clazz != null) entity = (Entity)clazz.getConstructor(new Class[] { World.class }).newInstance(new Object[] { paramWorld });
/*     */     
/*  64 */     } catch (Exception exception) {
/*  65 */       exception.printStackTrace();
/*     */     } 
/*  67 */     return entity;
/*     */   }
/*     */   
/*     */   public static Entity a(NBTTagCompound paramNBTTagCompound, World paramWorld) {
/*  71 */     Entity entity = null;
/*     */     try {
/*  73 */       Class clazz = (Class)a.get(paramNBTTagCompound.getString("id"));
/*  74 */       if (clazz != null) entity = (Entity)clazz.getConstructor(new Class[] { World.class }).newInstance(new Object[] { paramWorld });
/*     */     
/*  76 */     } catch (Exception exception) {
/*  77 */       exception.printStackTrace();
/*     */     } 
/*  79 */     if (entity != null) {
/*  80 */       entity.e(paramNBTTagCompound);
/*     */     } else {
/*  82 */       System.out.println("Skipping Entity with id " + paramNBTTagCompound.getString("id"));
/*     */     } 
/*  84 */     return entity;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 104 */   public static int a(Entity paramEntity) { return ((Integer)d.get(paramEntity.getClass())).intValue(); }
/*     */ 
/*     */ 
/*     */   
/* 108 */   public static String b(Entity paramEntity) { return (String)b.get(paramEntity.getClass()); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntityTypes.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */