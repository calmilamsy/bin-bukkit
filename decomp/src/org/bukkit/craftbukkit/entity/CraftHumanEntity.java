/*     */ package org.bukkit.craftbukkit.entity;
/*     */ 
/*     */ import java.util.Set;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EntityLiving;
/*     */ import org.bukkit.GameMode;
/*     */ import org.bukkit.craftbukkit.CraftServer;
/*     */ import org.bukkit.craftbukkit.inventory.CraftInventoryPlayer;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.PlayerInventory;
/*     */ import org.bukkit.permissions.PermissibleBase;
/*     */ import org.bukkit.permissions.Permission;
/*     */ import org.bukkit.permissions.PermissionAttachment;
/*     */ import org.bukkit.permissions.PermissionAttachmentInfo;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class CraftHumanEntity extends CraftLivingEntity implements HumanEntity {
/*  20 */   protected final PermissibleBase perm = new PermissibleBase(this); private CraftInventoryPlayer inventory;
/*     */   private boolean op;
/*     */   
/*     */   public CraftHumanEntity(CraftServer server, EntityHuman entity) {
/*  24 */     super(server, entity);
/*  25 */     this.inventory = new CraftInventoryPlayer(entity.inventory);
/*     */   }
/*     */ 
/*     */   
/*  29 */   public String getName() { return (getHandle()).name; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  34 */   public EntityHuman getHandle() { return (EntityHuman)this.entity; }
/*     */ 
/*     */   
/*     */   public void setHandle(EntityHuman entity) {
/*  38 */     setHandle(entity);
/*  39 */     this.entity = entity;
/*  40 */     this.inventory = new CraftInventoryPlayer(entity.inventory);
/*     */   }
/*     */ 
/*     */   
/*  44 */   public PlayerInventory getInventory() { return this.inventory; }
/*     */ 
/*     */ 
/*     */   
/*  48 */   public ItemStack getItemInHand() { return getInventory().getItemInHand(); }
/*     */ 
/*     */ 
/*     */   
/*  52 */   public void setItemInHand(ItemStack item) { getInventory().setItemInHand(item); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   public String toString() { return "CraftHumanEntity{id=" + getEntityId() + "name=" + getName() + '}'; }
/*     */ 
/*     */ 
/*     */   
/*  61 */   public boolean isSleeping() { return (getHandle()).sleeping; }
/*     */ 
/*     */ 
/*     */   
/*  65 */   public int getSleepTicks() { return (getHandle()).sleepTicks; }
/*     */ 
/*     */ 
/*     */   
/*  69 */   public boolean isOp() { return this.op; }
/*     */ 
/*     */ 
/*     */   
/*  73 */   public boolean isPermissionSet(String name) { return this.perm.isPermissionSet(name); }
/*     */ 
/*     */ 
/*     */   
/*  77 */   public boolean isPermissionSet(Permission perm) { return this.perm.isPermissionSet(perm); }
/*     */ 
/*     */ 
/*     */   
/*  81 */   public boolean hasPermission(String name) { return this.perm.hasPermission(name); }
/*     */ 
/*     */ 
/*     */   
/*  85 */   public boolean hasPermission(Permission perm) { return this.perm.hasPermission(perm); }
/*     */ 
/*     */ 
/*     */   
/*  89 */   public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) { return this.perm.addAttachment(plugin, name, value); }
/*     */ 
/*     */ 
/*     */   
/*  93 */   public PermissionAttachment addAttachment(Plugin plugin) { return this.perm.addAttachment(plugin); }
/*     */ 
/*     */ 
/*     */   
/*  97 */   public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) { return this.perm.addAttachment(plugin, name, value, ticks); }
/*     */ 
/*     */ 
/*     */   
/* 101 */   public PermissionAttachment addAttachment(Plugin plugin, int ticks) { return this.perm.addAttachment(plugin, ticks); }
/*     */ 
/*     */ 
/*     */   
/* 105 */   public void removeAttachment(PermissionAttachment attachment) { this.perm.removeAttachment(attachment); }
/*     */ 
/*     */ 
/*     */   
/* 109 */   public void recalculatePermissions() { this.perm.recalculatePermissions(); }
/*     */ 
/*     */   
/*     */   public void setOp(boolean value) {
/* 113 */     this.op = value;
/* 114 */     this.perm.recalculatePermissions();
/*     */   }
/*     */ 
/*     */   
/* 118 */   public Set<PermissionAttachmentInfo> getEffectivePermissions() { return this.perm.getEffectivePermissions(); }
/*     */ 
/*     */ 
/*     */   
/* 122 */   public GameMode getGameMode() { return GameMode.SURVIVAL; }
/*     */ 
/*     */ 
/*     */   
/* 126 */   public void setGameMode(GameMode mode) { throw new UnsupportedOperationException("Not supported yet."); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftHumanEntity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */