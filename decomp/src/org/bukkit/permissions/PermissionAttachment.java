/*     */ package org.bukkit.permissions;
/*     */ 
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PermissionAttachment
/*     */ {
/*     */   private PermissionRemovedExecutor removed;
/*  13 */   private final TreeMap<String, Boolean> permissions = new TreeMap();
/*     */   private final Permissible permissible;
/*     */   private final Plugin plugin;
/*     */   
/*     */   public PermissionAttachment(Plugin plugin, Permissible Permissible) {
/*  18 */     if (plugin == null)
/*  19 */       throw new IllegalArgumentException("Plugin cannot be null"); 
/*  20 */     if (!plugin.isEnabled()) {
/*  21 */       throw new IllegalArgumentException("Plugin " + plugin.getDescription().getFullName() + " is disabled");
/*     */     }
/*     */     
/*  24 */     this.permissible = Permissible;
/*  25 */     this.plugin = plugin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  34 */   public Plugin getPlugin() { return this.plugin; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   public void setRemovalCallback(PermissionRemovedExecutor ex) { this.removed = ex; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   public PermissionRemovedExecutor getRemovalCallback() { return this.removed; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   public Permissible getPermissible() { return this.permissible; }
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
/*  72 */   public Map<String, Boolean> getPermissions() { return (Map)this.permissions.clone(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPermission(String name, boolean value) {
/*  82 */     this.permissions.put(name.toLowerCase(), Boolean.valueOf(value));
/*  83 */     this.permissible.recalculatePermissions();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPermission(Permission perm, boolean value) {
/*  93 */     setPermission(perm.getName(), value);
/*  94 */     this.permissible.recalculatePermissions();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetPermission(String name) {
/* 105 */     this.permissions.remove(name.toLowerCase());
/* 106 */     this.permissible.recalculatePermissions();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetPermission(Permission perm) {
/* 117 */     unsetPermission(perm.getName());
/* 118 */     this.permissible.recalculatePermissions();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remove() {
/*     */     try {
/* 128 */       this.permissible.removeAttachment(this);
/* 129 */       return true;
/* 130 */     } catch (IllegalArgumentException ex) {
/* 131 */       return false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\permissions\PermissionAttachment.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */