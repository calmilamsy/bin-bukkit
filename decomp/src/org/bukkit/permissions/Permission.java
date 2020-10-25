/*     */ package org.bukkit.permissions;
/*     */ 
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.bukkit.Bukkit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Permission
/*     */ {
/*     */   private final String name;
/*     */   private final Map<String, Boolean> children;
/*     */   private PermissionDefault defaultValue;
/*     */   private String description;
/*     */   
/*  20 */   public Permission(String name) { this(name, null, null, null); }
/*     */ 
/*     */ 
/*     */   
/*  24 */   public Permission(String name, String description) { this(name, description, null, null); }
/*     */ 
/*     */ 
/*     */   
/*  28 */   public Permission(String name, PermissionDefault defaultValue) { this(name, null, defaultValue, null); }
/*     */ 
/*     */ 
/*     */   
/*  32 */   public Permission(String name, String description, PermissionDefault defaultValue) { this(name, description, defaultValue, null); }
/*     */ 
/*     */ 
/*     */   
/*  36 */   public Permission(String name, Map<String, Boolean> children) { this(name, null, null, children); }
/*     */ 
/*     */ 
/*     */   
/*  40 */   public Permission(String name, String description, Map<String, Boolean> children) { this(name, description, null, children); }
/*     */ 
/*     */ 
/*     */   
/*  44 */   public Permission(String name, PermissionDefault defaultValue, Map<String, Boolean> children) { this(name, null, defaultValue, children); }
/*     */   public Permission(String name, String description, PermissionDefault defaultValue, Map<String, Boolean> children) {
/*     */     this.children = new LinkedHashMap();
/*     */     this.defaultValue = PermissionDefault.FALSE;
/*  48 */     this.name = name;
/*  49 */     this.description = (description == null) ? "" : description;
/*  50 */     defaultValue; this.defaultValue = (defaultValue == null) ? PermissionDefault.FALSE : defaultValue;
/*     */     
/*  52 */     if (children != null) {
/*  53 */       this.children.putAll(children);
/*     */     }
/*     */     
/*  56 */     recalculatePermissibles();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   public String getName() { return this.name; }
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
/*  76 */   public Map<String, Boolean> getChildren() { return this.children; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   public PermissionDefault getDefault() { return this.defaultValue; }
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
/*     */   public void setDefault(PermissionDefault value) {
/*  97 */     if (this.defaultValue == null) {
/*  98 */       throw new IllegalArgumentException("Default value cannot be null");
/*     */     }
/*     */     
/* 101 */     this.defaultValue = value;
/* 102 */     recalculatePermissibles();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   public String getDescription() { return this.description; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDescription(String value) {
/* 122 */     if (value == null) {
/* 123 */       this.description = "";
/*     */     } else {
/* 125 */       this.description = value;
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
/* 137 */   public Set<Permissible> getPermissibles() { return Bukkit.getServer().getPluginManager().getPermissionSubscriptions(this.name); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void recalculatePermissibles() {
/* 146 */     Set<Permissible> perms = getPermissibles();
/*     */     
/* 148 */     Bukkit.getServer().getPluginManager().recalculatePermissionDefaults(this);
/*     */     
/* 150 */     for (Permissible p : perms) {
/* 151 */       p.recalculatePermissions();
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
/*     */   public static Permission loadPermission(String name, Map<String, Object> data) {
/* 168 */     if (name == null) {
/* 169 */       throw new IllegalArgumentException("Name cannot be null");
/*     */     }
/* 171 */     if (data == null) {
/* 172 */       throw new IllegalArgumentException("Data cannot be null");
/*     */     }
/* 174 */     String desc = null;
/* 175 */     PermissionDefault def = null;
/* 176 */     Map<String, Boolean> children = null;
/*     */     
/* 178 */     if (data.containsKey("default")) {
/*     */       try {
/* 180 */         PermissionDefault value = PermissionDefault.getByName(data.get("default").toString());
/* 181 */         if (value != null) {
/* 182 */           def = value;
/*     */         } else {
/* 184 */           throw new IllegalArgumentException("'default' key contained unknown value");
/*     */         } 
/* 186 */       } catch (ClassCastException ex) {
/* 187 */         throw new IllegalArgumentException("'default' key is of wrong type", ex);
/*     */       } 
/*     */     }
/*     */     
/* 191 */     if (data.containsKey("children")) {
/*     */       try {
/* 193 */         children = extractChildren(data);
/* 194 */       } catch (ClassCastException ex) {
/* 195 */         throw new IllegalArgumentException("'children' key is of wrong type", ex);
/*     */       } 
/*     */     }
/*     */     
/* 199 */     if (data.containsKey("description")) {
/*     */       try {
/* 201 */         desc = (String)data.get("description");
/* 202 */       } catch (ClassCastException ex) {
/* 203 */         throw new IllegalArgumentException("'description' key is of wrong type", ex);
/*     */       } 
/*     */     }
/*     */     
/* 207 */     return new Permission(name, desc, def, children);
/*     */   }
/*     */   
/*     */   private static Map<String, Boolean> extractChildren(Map<String, Object> data) {
/* 211 */     Map<String, Boolean> input = (Map)data.get("children");
/* 212 */     Set<Map.Entry<String, Boolean>> entries = input.entrySet();
/*     */     
/* 214 */     for (Map.Entry<String, Boolean> entry : entries) {
/* 215 */       if (!(entry.getValue() instanceof Boolean)) {
/* 216 */         throw new IllegalArgumentException("Child '" + (String)entry.getKey() + "' contains invalid value");
/*     */       }
/*     */     } 
/*     */     
/* 220 */     return input;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\permissions\Permission.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */