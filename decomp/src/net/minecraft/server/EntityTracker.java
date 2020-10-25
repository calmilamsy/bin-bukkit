/*     */ package net.minecraft.server;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public class EntityTracker {
/*     */   private Set a;
/*     */   private EntityList b;
/*     */   
/*     */   public EntityTracker(MinecraftServer minecraftserver, int i) {
/*  10 */     this.a = new HashSet();
/*  11 */     this.b = new EntityList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  17 */     this.c = minecraftserver;
/*  18 */     this.e = i;
/*  19 */     this.d = minecraftserver.serverConfigurationManager.a();
/*     */   }
/*     */   private MinecraftServer c; private int d; private int e;
/*     */   
/*     */   public void track(Entity entity) {
/*  24 */     if (entity instanceof EntityPlayer) {
/*  25 */       a(entity, 512, 2);
/*  26 */       EntityPlayer entityplayer = (EntityPlayer)entity;
/*  27 */       Iterator iterator = this.a.iterator();
/*     */       
/*  29 */       while (iterator.hasNext()) {
/*  30 */         EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)iterator.next();
/*     */         
/*  32 */         if (entitytrackerentry.tracker != entityplayer) {
/*  33 */           entitytrackerentry.b(entityplayer);
/*     */         }
/*     */       } 
/*  36 */     } else if (entity instanceof EntityFish) {
/*  37 */       a(entity, 64, 5, true);
/*  38 */     } else if (entity instanceof EntityArrow) {
/*  39 */       a(entity, 64, 20, false);
/*  40 */     } else if (entity instanceof EntityFireball) {
/*  41 */       a(entity, 64, 10, false);
/*  42 */     } else if (entity instanceof EntitySnowball) {
/*  43 */       a(entity, 64, 10, true);
/*  44 */     } else if (entity instanceof EntityEgg) {
/*  45 */       a(entity, 64, 10, true);
/*  46 */     } else if (entity instanceof EntityItem) {
/*  47 */       a(entity, 64, 20, true);
/*  48 */     } else if (entity instanceof EntityMinecart) {
/*  49 */       a(entity, 160, 5, true);
/*  50 */     } else if (entity instanceof EntityBoat) {
/*  51 */       a(entity, 160, 5, true);
/*  52 */     } else if (entity instanceof EntitySquid) {
/*  53 */       a(entity, 160, 3, true);
/*  54 */     } else if (entity instanceof IAnimal) {
/*  55 */       a(entity, 160, 3);
/*  56 */     } else if (entity instanceof EntityTNTPrimed) {
/*  57 */       a(entity, 160, 10, true);
/*  58 */     } else if (entity instanceof EntityFallingSand) {
/*  59 */       a(entity, 160, 20, true);
/*  60 */     } else if (entity instanceof EntityPainting) {
/*  61 */       a(entity, 160, 2147483647, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  66 */   public void a(Entity entity, int i, int j) { a(entity, i, j, false); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(Entity entity, int i, int j, boolean flag) {
/*  71 */     if (i > this.d) {
/*  72 */       i = this.d;
/*     */     }
/*     */     
/*  75 */     if (!this.b.b(entity.id)) {
/*     */ 
/*     */ 
/*     */       
/*  79 */       EntityTrackerEntry entitytrackerentry = new EntityTrackerEntry(entity, i, j, flag);
/*     */       
/*  81 */       this.a.add(entitytrackerentry);
/*  82 */       this.b.a(entity.id, entitytrackerentry);
/*  83 */       entitytrackerentry.scanPlayers((this.c.getWorldServer(this.e)).players);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void untrackEntity(Entity entity) {
/*  89 */     if (entity instanceof EntityPlayer) {
/*  90 */       EntityPlayer entityplayer = (EntityPlayer)entity;
/*  91 */       Iterator iterator = this.a.iterator();
/*     */       
/*  93 */       while (iterator.hasNext()) {
/*  94 */         EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)iterator.next();
/*     */         
/*  96 */         entitytrackerentry.a(entityplayer);
/*     */       } 
/*     */     } 
/*     */     
/* 100 */     EntityTrackerEntry entitytrackerentry1 = (EntityTrackerEntry)this.b.d(entity.id);
/*     */     
/* 102 */     if (entitytrackerentry1 != null) {
/* 103 */       this.a.remove(entitytrackerentry1);
/* 104 */       entitytrackerentry1.a();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updatePlayers() {
/* 110 */     ArrayList arraylist = new ArrayList();
/* 111 */     Iterator iterator = this.a.iterator();
/*     */     
/* 113 */     while (iterator.hasNext()) {
/* 114 */       EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)iterator.next();
/*     */       
/* 116 */       entitytrackerentry.track((this.c.getWorldServer(this.e)).players);
/* 117 */       if (entitytrackerentry.m && entitytrackerentry.tracker instanceof EntityPlayer) {
/* 118 */         arraylist.add((EntityPlayer)entitytrackerentry.tracker);
/*     */       }
/*     */     } 
/*     */     
/* 122 */     for (int i = 0; i < arraylist.size(); i++) {
/* 123 */       EntityPlayer entityplayer = (EntityPlayer)arraylist.get(i);
/* 124 */       Iterator iterator1 = this.a.iterator();
/*     */       
/* 126 */       while (iterator1.hasNext()) {
/* 127 */         EntityTrackerEntry entitytrackerentry1 = (EntityTrackerEntry)iterator1.next();
/*     */         
/* 129 */         if (entitytrackerentry1.tracker != entityplayer) {
/* 130 */           entitytrackerentry1.b(entityplayer);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(Entity entity, Packet packet) {
/* 138 */     EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)this.b.a(entity.id);
/*     */     
/* 140 */     if (entitytrackerentry != null) {
/* 141 */       entitytrackerentry.a(packet);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendPacketToEntity(Entity entity, Packet packet) {
/* 147 */     EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)this.b.a(entity.id);
/*     */     
/* 149 */     if (entitytrackerentry != null) {
/* 150 */       entitytrackerentry.b(packet);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void untrackPlayer(EntityPlayer entityplayer) {
/* 156 */     Iterator iterator = this.a.iterator();
/*     */     
/* 158 */     while (iterator.hasNext()) {
/* 159 */       EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)iterator.next();
/*     */       
/* 161 */       entitytrackerentry.c(entityplayer);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntityTracker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */