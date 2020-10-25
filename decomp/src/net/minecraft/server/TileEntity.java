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
/*     */ public class TileEntity
/*     */ {
/*  13 */   private static Map a = new HashMap();
/*  14 */   private static Map b = new HashMap(); public World world; public int x;
/*     */   
/*     */   private static void a(Class paramClass, String paramString) {
/*  17 */     if (b.containsKey(paramString)) throw new IllegalArgumentException("Duplicate id: " + paramString); 
/*  18 */     a.put(paramString, paramClass);
/*  19 */     b.put(paramClass, paramString);
/*     */   }
/*     */   public int y; public int z; protected boolean h;
/*     */   static  {
/*  23 */     a(TileEntityFurnace.class, "Furnace");
/*  24 */     a(TileEntityChest.class, "Chest");
/*  25 */     a(TileEntityRecordPlayer.class, "RecordPlayer");
/*  26 */     a(TileEntityDispenser.class, "Trap");
/*  27 */     a(TileEntitySign.class, "Sign");
/*  28 */     a(TileEntityMobSpawner.class, "MobSpawner");
/*  29 */     a(TileEntityNote.class, "Music");
/*  30 */     a(TileEntityPiston.class, "Piston");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(NBTTagCompound paramNBTTagCompound) {
/*  38 */     this.x = paramNBTTagCompound.e("x");
/*  39 */     this.y = paramNBTTagCompound.e("y");
/*  40 */     this.z = paramNBTTagCompound.e("z");
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound paramNBTTagCompound) {
/*  44 */     String str = (String)b.get(getClass());
/*  45 */     if (str == null) {
/*  46 */       throw new RuntimeException(getClass() + " is missing a mapping! This is a bug!");
/*     */     }
/*  48 */     paramNBTTagCompound.setString("id", str);
/*  49 */     paramNBTTagCompound.a("x", this.x);
/*  50 */     paramNBTTagCompound.a("y", this.y);
/*  51 */     paramNBTTagCompound.a("z", this.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public void g_() {}
/*     */   
/*     */   public static TileEntity c(NBTTagCompound paramNBTTagCompound) {
/*  58 */     TileEntity tileEntity = null;
/*     */     try {
/*  60 */       Class clazz = (Class)a.get(paramNBTTagCompound.getString("id"));
/*  61 */       if (clazz != null) tileEntity = (TileEntity)clazz.newInstance(); 
/*  62 */     } catch (Exception exception) {
/*  63 */       exception.printStackTrace();
/*     */     } 
/*  65 */     if (tileEntity != null) {
/*  66 */       tileEntity.a(paramNBTTagCompound);
/*     */     } else {
/*  68 */       System.out.println("Skipping TileEntity with id " + paramNBTTagCompound.getString("id"));
/*     */     } 
/*  70 */     return tileEntity;
/*     */   }
/*     */ 
/*     */   
/*  74 */   public int e() { return this.world.getData(this.x, this.y, this.z); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/*  82 */     if (this.world != null) {
/*  83 */       this.world.b(this.x, this.y, this.z, this);
/*     */     }
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
/*  99 */   public Packet f() { return null; }
/*     */ 
/*     */ 
/*     */   
/* 103 */   public boolean g() { return this.h; }
/*     */ 
/*     */ 
/*     */   
/* 107 */   public void h() { this.h = true; }
/*     */ 
/*     */ 
/*     */   
/* 111 */   public void j() { this.h = false; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\TileEntity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */