/*     */ package org.bukkit.permissions;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class PermissibleBase implements Permissible {
/*     */   private ServerOperator opable;
/*     */   private Permissible parent;
/*     */   
/*     */   public PermissibleBase(ServerOperator opable) {
/*  17 */     this.opable = null;
/*  18 */     this.parent = this;
/*  19 */     this.attachments = new LinkedList();
/*  20 */     this.permissions = new HashMap();
/*     */ 
/*     */     
/*  23 */     this.opable = opable;
/*     */     
/*  25 */     if (opable instanceof Permissible) {
/*  26 */       this.parent = (Permissible)opable;
/*     */     }
/*     */     
/*  29 */     recalculatePermissions();
/*     */   }
/*     */   private final List<PermissionAttachment> attachments; private final Map<String, PermissionAttachmentInfo> permissions;
/*     */   public boolean isOp() {
/*  33 */     if (this.opable == null) {
/*  34 */       return false;
/*     */     }
/*  36 */     return this.opable.isOp();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOp(boolean value) {
/*  41 */     if (this.opable == null) {
/*  42 */       throw new UnsupportedOperationException("Cannot change op value as no ServerOperator is set");
/*     */     }
/*  44 */     this.opable.setOp(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPermissionSet(String name) {
/*  49 */     if (name == null) {
/*  50 */       throw new IllegalArgumentException("Permission name cannot be null");
/*     */     }
/*     */     
/*  53 */     return this.permissions.containsKey(name.toLowerCase());
/*     */   }
/*     */   
/*     */   public boolean isPermissionSet(Permission perm) {
/*  57 */     if (perm == null) {
/*  58 */       throw new IllegalArgumentException("Permission cannot be null");
/*     */     }
/*     */     
/*  61 */     return isPermissionSet(perm.getName());
/*     */   }
/*     */   
/*     */   public boolean hasPermission(String inName) {
/*  65 */     if (inName == null) {
/*  66 */       throw new IllegalArgumentException("Permission name cannot be null");
/*     */     }
/*     */     
/*  69 */     String name = inName.toLowerCase();
/*     */     
/*  71 */     if (isPermissionSet(name)) {
/*  72 */       return ((PermissionAttachmentInfo)this.permissions.get(name)).getValue();
/*     */     }
/*  74 */     Permission perm = Bukkit.getServer().getPluginManager().getPermission(name);
/*     */     
/*  76 */     if (perm != null) {
/*  77 */       return perm.getDefault().getValue(isOp());
/*     */     }
/*  79 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasPermission(Permission perm) {
/*  85 */     if (perm == null) {
/*  86 */       throw new IllegalArgumentException("Permission cannot be null");
/*     */     }
/*     */     
/*  89 */     String name = perm.getName().toLowerCase();
/*     */     
/*  91 */     if (isPermissionSet(name))
/*  92 */       return ((PermissionAttachmentInfo)this.permissions.get(name)).getValue(); 
/*  93 */     if (perm != null) {
/*  94 */       return perm.getDefault().getValue(isOp());
/*     */     }
/*  96 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
/* 101 */     if (name == null)
/* 102 */       throw new IllegalArgumentException("Permission name cannot be null"); 
/* 103 */     if (plugin == null)
/* 104 */       throw new IllegalArgumentException("Plugin cannot be null"); 
/* 105 */     if (!plugin.isEnabled()) {
/* 106 */       throw new IllegalArgumentException("Plugin " + plugin.getDescription().getFullName() + " is disabled");
/*     */     }
/*     */     
/* 109 */     PermissionAttachment result = addAttachment(plugin);
/* 110 */     result.setPermission(name, value);
/*     */     
/* 112 */     recalculatePermissions();
/*     */     
/* 114 */     return result;
/*     */   }
/*     */   
/*     */   public PermissionAttachment addAttachment(Plugin plugin) {
/* 118 */     if (plugin == null)
/* 119 */       throw new IllegalArgumentException("Plugin cannot be null"); 
/* 120 */     if (!plugin.isEnabled()) {
/* 121 */       throw new IllegalArgumentException("Plugin " + plugin.getDescription().getFullName() + " is disabled");
/*     */     }
/*     */     
/* 124 */     PermissionAttachment result = new PermissionAttachment(plugin, this.parent);
/*     */     
/* 126 */     this.attachments.add(result);
/* 127 */     recalculatePermissions();
/*     */     
/* 129 */     return result;
/*     */   }
/*     */   
/*     */   public void removeAttachment(PermissionAttachment attachment) {
/* 133 */     if (attachment == null) {
/* 134 */       throw new IllegalArgumentException("Attachment cannot be null");
/*     */     }
/*     */     
/* 137 */     if (this.attachments.contains(attachment)) {
/* 138 */       this.attachments.remove(attachment);
/* 139 */       PermissionRemovedExecutor ex = attachment.getRemovalCallback();
/*     */       
/* 141 */       if (ex != null) {
/* 142 */         ex.attachmentRemoved(attachment);
/*     */       }
/*     */       
/* 145 */       recalculatePermissions();
/*     */     } else {
/* 147 */       throw new IllegalArgumentException("Given attachment is not part of Permissible object " + this.parent);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void recalculatePermissions() {
/* 152 */     clearPermissions();
/* 153 */     Set<Permission> defaults = Bukkit.getServer().getPluginManager().getDefaultPermissions(isOp());
/* 154 */     Bukkit.getServer().getPluginManager().subscribeToDefaultPerms(isOp(), this.parent);
/*     */     
/* 156 */     for (Permission perm : defaults) {
/* 157 */       String name = perm.getName().toLowerCase();
/* 158 */       this.permissions.put(name, new PermissionAttachmentInfo(this.parent, name, null, true));
/* 159 */       Bukkit.getServer().getPluginManager().subscribeToPermission(name, this.parent);
/* 160 */       calculateChildPermissions(perm.getChildren(), false, null);
/*     */     } 
/*     */     
/* 163 */     for (PermissionAttachment attachment : this.attachments) {
/* 164 */       calculateChildPermissions(attachment.getPermissions(), false, attachment);
/*     */     }
/*     */   }
/*     */   
/*     */   private void clearPermissions() {
/* 169 */     Set<String> perms = this.permissions.keySet();
/*     */     
/* 171 */     for (String name : perms) {
/* 172 */       Bukkit.getServer().getPluginManager().unsubscribeFromPermission(name, this.parent);
/*     */     }
/*     */     
/* 175 */     Bukkit.getServer().getPluginManager().unsubscribeFromDefaultPerms(false, this.parent);
/* 176 */     Bukkit.getServer().getPluginManager().unsubscribeFromDefaultPerms(true, this.parent);
/*     */     
/* 178 */     this.permissions.clear();
/*     */   }
/*     */   
/*     */   private void calculateChildPermissions(Map<String, Boolean> children, boolean invert, PermissionAttachment attachment) {
/* 182 */     Set<String> keys = children.keySet();
/*     */     
/* 184 */     for (String name : keys) {
/* 185 */       Permission perm = Bukkit.getServer().getPluginManager().getPermission(name);
/* 186 */       boolean value = ((Boolean)children.get(name)).booleanValue() ^ invert;
/* 187 */       String lname = name.toLowerCase();
/*     */       
/* 189 */       this.permissions.put(lname, new PermissionAttachmentInfo(this.parent, lname, attachment, value));
/* 190 */       Bukkit.getServer().getPluginManager().subscribeToPermission(name, this.parent);
/*     */       
/* 192 */       if (perm != null) {
/* 193 */         calculateChildPermissions(perm.getChildren(), !value, attachment);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
/* 199 */     if (name == null)
/* 200 */       throw new IllegalArgumentException("Permission name cannot be null"); 
/* 201 */     if (plugin == null)
/* 202 */       throw new IllegalArgumentException("Plugin cannot be null"); 
/* 203 */     if (!plugin.isEnabled()) {
/* 204 */       throw new IllegalArgumentException("Plugin " + plugin.getDescription().getFullName() + " is disabled");
/*     */     }
/*     */     
/* 207 */     PermissionAttachment result = addAttachment(plugin, ticks);
/*     */     
/* 209 */     if (result != null) {
/* 210 */       result.setPermission(name, value);
/*     */     }
/*     */     
/* 213 */     return result;
/*     */   }
/*     */   
/*     */   public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
/* 217 */     if (plugin == null)
/* 218 */       throw new IllegalArgumentException("Plugin cannot be null"); 
/* 219 */     if (!plugin.isEnabled()) {
/* 220 */       throw new IllegalArgumentException("Plugin " + plugin.getDescription().getFullName() + " is disabled");
/*     */     }
/*     */     
/* 223 */     PermissionAttachment result = addAttachment(plugin);
/*     */     
/* 225 */     if (Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new RemoveAttachmentRunnable(result), ticks) == -1) {
/* 226 */       Bukkit.getServer().getLogger().log(Level.WARNING, "Could not add PermissionAttachment to " + this.parent + " for plugin " + plugin.getDescription().getFullName() + ": Scheduler returned -1");
/* 227 */       result.remove();
/* 228 */       return null;
/*     */     } 
/* 230 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 235 */   public Set<PermissionAttachmentInfo> getEffectivePermissions() { return new HashSet(this.permissions.values()); }
/*     */   
/*     */   private class RemoveAttachmentRunnable
/*     */     implements Runnable
/*     */   {
/*     */     private PermissionAttachment attachment;
/*     */     
/* 242 */     public RemoveAttachmentRunnable(PermissionAttachment attachment) { this.attachment = attachment; }
/*     */ 
/*     */ 
/*     */     
/* 246 */     public void run() { this.attachment.remove(); }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\permissions\PermissibleBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */