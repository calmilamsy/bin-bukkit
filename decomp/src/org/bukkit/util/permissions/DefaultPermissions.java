/*    */ package org.bukkit.util.permissions;
/*    */ 
/*    */ import java.util.Map;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.permissions.Permission;
/*    */ import org.bukkit.permissions.PermissionDefault;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class DefaultPermissions
/*    */ {
/*    */   private static final String ROOT = "craftbukkit";
/*    */   private static final String PREFIX = "craftbukkit.";
/*    */   private static final String LEGACY_PREFIX = "craft";
/*    */   
/* 16 */   public static Permission registerPermission(Permission perm) { return registerPermission(perm, true); }
/*    */ 
/*    */   
/*    */   public static Permission registerPermission(Permission perm, boolean withLegacy) {
/* 20 */     Permission result = perm;
/*    */     
/*    */     try {
/* 23 */       Bukkit.getPluginManager().addPermission(perm);
/* 24 */     } catch (IllegalArgumentException ex) {
/* 25 */       result = Bukkit.getPluginManager().getPermission(perm.getName());
/*    */     } 
/*    */     
/* 28 */     if (withLegacy) {
/* 29 */       Permission legacy = new Permission("craft" + result.getName(), result.getDescription(), PermissionDefault.FALSE);
/* 30 */       legacy.getChildren().put(result.getName(), Boolean.valueOf(true));
/* 31 */       registerPermission(perm, false);
/*    */     } 
/*    */     
/* 34 */     return result;
/*    */   }
/*    */   
/*    */   public static Permission registerPermission(Permission perm, Permission parent) {
/* 38 */     parent.getChildren().put(perm.getName(), Boolean.valueOf(true));
/* 39 */     return registerPermission(perm);
/*    */   }
/*    */ 
/*    */   
/* 43 */   public static Permission registerPermission(String name, String desc) { return registerPermission(new Permission(name, desc)); }
/*    */ 
/*    */ 
/*    */   
/*    */   public static Permission registerPermission(String name, String desc, Permission parent) {
/* 48 */     Permission perm = registerPermission(name, desc);
/* 49 */     parent.getChildren().put(perm.getName(), Boolean.valueOf(true));
/* 50 */     return perm;
/*    */   }
/*    */ 
/*    */   
/* 54 */   public static Permission registerPermission(String name, String desc, PermissionDefault def) { return registerPermission(new Permission(name, desc, def)); }
/*    */ 
/*    */ 
/*    */   
/*    */   public static Permission registerPermission(String name, String desc, PermissionDefault def, Permission parent) {
/* 59 */     Permission perm = registerPermission(name, desc, def);
/* 60 */     parent.getChildren().put(perm.getName(), Boolean.valueOf(true));
/* 61 */     return perm;
/*    */   }
/*    */ 
/*    */   
/* 65 */   public static Permission registerPermission(String name, String desc, PermissionDefault def, Map<String, Boolean> children) { return registerPermission(new Permission(name, desc, def, children)); }
/*    */ 
/*    */ 
/*    */   
/*    */   public static Permission registerPermission(String name, String desc, PermissionDefault def, Map<String, Boolean> children, Permission parent) {
/* 70 */     Permission perm = registerPermission(name, desc, def, children);
/* 71 */     parent.getChildren().put(perm.getName(), Boolean.valueOf(true));
/* 72 */     return perm;
/*    */   }
/*    */   
/*    */   public static void registerCorePermissions() {
/* 76 */     parent = registerPermission("craftbukkit", "Gives the user the ability to use all Craftbukkit utilities and commands");
/*    */     
/* 78 */     CommandPermissions.registerPermissions(parent);
/* 79 */     BroadcastPermissions.registerPermissions(parent);
/*    */     
/* 81 */     parent.recalculatePermissibles();
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukki\\util\permissions\DefaultPermissions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */