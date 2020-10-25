/*     */ package org.bukkit.plugin;
/*     */ import java.io.InputStream;
/*     */ import java.io.Writer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.bukkit.permissions.Permission;
/*     */ import org.yaml.snakeyaml.Yaml;
/*     */ 
/*     */ public final class PluginDescriptionFile {
/*     */   private String name;
/*     */   private String main;
/*     */   private ArrayList<String> depend;
/*     */   private ArrayList<String> softDepend;
/*     */   private String version;
/*     */   private Object commands;
/*     */   private String description;
/*     */   private ArrayList<String> authors;
/*     */   private String website;
/*  20 */   private static final Yaml yaml = new Yaml(new SafeConstructor()); private boolean database; private PluginLoadOrder order; private ArrayList<Permission> permissions; public PluginDescriptionFile(InputStream stream) throws InvalidDescriptionException {
/*  21 */     this.name = null;
/*  22 */     this.main = null;
/*  23 */     this.depend = null;
/*  24 */     this.softDepend = null;
/*  25 */     this.version = null;
/*  26 */     this.commands = null;
/*  27 */     this.description = null;
/*  28 */     this.authors = new ArrayList();
/*  29 */     this.website = null;
/*  30 */     this.database = false;
/*  31 */     this.order = PluginLoadOrder.POSTWORLD;
/*  32 */     this.permissions = new ArrayList();
/*     */ 
/*     */ 
/*     */     
/*  36 */     loadMap((Map)yaml.load(stream)); } public PluginDescriptionFile(Reader reader) throws InvalidDescriptionException { this.name = null; this.main = null; this.depend = null; this.softDepend = null;
/*     */     this.version = null;
/*     */     this.commands = null;
/*     */     this.description = null;
/*     */     this.authors = new ArrayList();
/*     */     this.website = null;
/*     */     this.database = false;
/*     */     this.order = PluginLoadOrder.POSTWORLD;
/*     */     this.permissions = new ArrayList();
/*  45 */     loadMap((Map)yaml.load(reader)); } public PluginDescriptionFile(String pluginName, String pluginVersion, String mainClass) { this.name = null; this.main = null; this.depend = null;
/*     */     this.softDepend = null;
/*     */     this.version = null;
/*     */     this.commands = null;
/*     */     this.description = null;
/*     */     this.authors = new ArrayList();
/*     */     this.website = null;
/*     */     this.database = false;
/*     */     this.order = PluginLoadOrder.POSTWORLD;
/*     */     this.permissions = new ArrayList();
/*  55 */     this.name = pluginName;
/*  56 */     this.version = pluginVersion;
/*  57 */     this.main = mainClass; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   public void save(Writer writer) { yaml.dump(saveMap(), writer); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   public String getName() { return this.name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   public String getVersion() { return this.version; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   public String getFullName() { return this.name + " v" + this.version; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   public String getMain() { return this.main; }
/*     */ 
/*     */ 
/*     */   
/* 106 */   public Object getCommands() { return this.commands; }
/*     */ 
/*     */ 
/*     */   
/* 110 */   public Object getDepend() { return this.depend; }
/*     */ 
/*     */ 
/*     */   
/* 114 */   public Object getSoftDepend() { return this.softDepend; }
/*     */ 
/*     */ 
/*     */   
/* 118 */   public PluginLoadOrder getLoad() { return this.order; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   public String getDescription() { return this.description; }
/*     */ 
/*     */ 
/*     */   
/* 131 */   public ArrayList<String> getAuthors() { return this.authors; }
/*     */ 
/*     */ 
/*     */   
/* 135 */   public String getWebsite() { return this.website; }
/*     */ 
/*     */ 
/*     */   
/* 139 */   public boolean isDatabaseEnabled() { return this.database; }
/*     */ 
/*     */ 
/*     */   
/* 143 */   public void setDatabaseEnabled(boolean database) { this.database = database; }
/*     */ 
/*     */ 
/*     */   
/* 147 */   public ArrayList<Permission> getPermissions() { return this.permissions; }
/*     */ 
/*     */   
/*     */   private void loadMap(Map<String, Object> map) throws InvalidDescriptionException {
/*     */     try {
/* 152 */       this.name = map.get("name").toString();
/*     */       
/* 154 */       if (!this.name.matches("^[A-Za-z0-9 _.-]+$")) {
/* 155 */         throw new InvalidDescriptionException("name '" + this.name + "' contains invalid characters.");
/*     */       }
/* 157 */     } catch (NullPointerException ex) {
/* 158 */       throw new InvalidDescriptionException(ex, "name is not defined");
/* 159 */     } catch (ClassCastException ex) {
/* 160 */       throw new InvalidDescriptionException(ex, "name is of wrong type");
/*     */     } 
/*     */     
/*     */     try {
/* 164 */       this.version = map.get("version").toString();
/* 165 */     } catch (NullPointerException ex) {
/* 166 */       throw new InvalidDescriptionException(ex, "version is not defined");
/* 167 */     } catch (ClassCastException ex) {
/* 168 */       throw new InvalidDescriptionException(ex, "version is of wrong type");
/*     */     } 
/*     */     
/*     */     try {
/* 172 */       this.main = map.get("main").toString();
/* 173 */       if (this.main.startsWith("org.bukkit.")) {
/* 174 */         throw new InvalidDescriptionException("main may not be within the org.bukkit namespace");
/*     */       }
/* 176 */     } catch (NullPointerException ex) {
/* 177 */       throw new InvalidDescriptionException(ex, "main is not defined");
/* 178 */     } catch (ClassCastException ex) {
/* 179 */       throw new InvalidDescriptionException(ex, "main is of wrong type");
/*     */     } 
/*     */     
/* 182 */     if (map.containsKey("commands")) {
/*     */       try {
/* 184 */         this.commands = map.get("commands");
/* 185 */       } catch (ClassCastException ex) {
/* 186 */         throw new InvalidDescriptionException(ex, "commands are of wrong type");
/*     */       } 
/*     */     }
/*     */     
/* 190 */     if (map.containsKey("depend")) {
/*     */       try {
/* 192 */         this.depend = (ArrayList)map.get("depend");
/* 193 */       } catch (ClassCastException ex) {
/* 194 */         throw new InvalidDescriptionException(ex, "depend is of wrong type");
/*     */       } 
/*     */     }
/*     */     
/* 198 */     if (map.containsKey("softdepend")) {
/*     */       try {
/* 200 */         this.softDepend = (ArrayList)map.get("softdepend");
/* 201 */       } catch (ClassCastException ex) {
/* 202 */         throw new InvalidDescriptionException(ex, "softdepend is of wrong type");
/*     */       } 
/*     */     }
/*     */     
/* 206 */     if (map.containsKey("database")) {
/*     */       try {
/* 208 */         this.database = ((Boolean)map.get("database")).booleanValue();
/* 209 */       } catch (ClassCastException ex) {
/* 210 */         throw new InvalidDescriptionException(ex, "database is of wrong type");
/*     */       } 
/*     */     }
/*     */     
/* 214 */     if (map.containsKey("website")) {
/*     */       try {
/* 216 */         this.website = (String)map.get("website");
/* 217 */       } catch (ClassCastException ex) {
/* 218 */         throw new InvalidDescriptionException(ex, "website is of wrong type");
/*     */       } 
/*     */     }
/*     */     
/* 222 */     if (map.containsKey("description")) {
/*     */       try {
/* 224 */         this.description = (String)map.get("description");
/* 225 */       } catch (ClassCastException ex) {
/* 226 */         throw new InvalidDescriptionException(ex, "description is of wrong type");
/*     */       } 
/*     */     }
/*     */     
/* 230 */     if (map.containsKey("load")) {
/*     */       try {
/* 232 */         this.order = PluginLoadOrder.valueOf(((String)map.get("load")).toUpperCase().replaceAll("\\W", ""));
/* 233 */       } catch (ClassCastException ex) {
/* 234 */         throw new InvalidDescriptionException(ex, "load is of wrong type");
/* 235 */       } catch (IllegalArgumentException ex) {
/* 236 */         throw new InvalidDescriptionException(ex, "load is not a valid choice");
/*     */       } 
/*     */     }
/*     */     
/* 240 */     if (map.containsKey("author")) {
/*     */       try {
/* 242 */         String extra = (String)map.get("author");
/*     */         
/* 244 */         this.authors.add(extra);
/* 245 */       } catch (ClassCastException ex) {
/* 246 */         throw new InvalidDescriptionException(ex, "author is of wrong type");
/*     */       } 
/*     */     }
/*     */     
/* 250 */     if (map.containsKey("authors")) {
/*     */       try {
/* 252 */         ArrayList<String> extra = (ArrayList)map.get("authors");
/*     */         
/* 254 */         this.authors.addAll(extra);
/* 255 */       } catch (ClassCastException ex) {
/* 256 */         throw new InvalidDescriptionException(ex, "authors are of wrong type");
/*     */       } 
/*     */     }
/*     */     
/* 260 */     if (map.containsKey("permissions")) {
/*     */       try {
/* 262 */         Map<String, Map<String, Object>> perms = (Map)map.get("permissions");
/*     */         
/* 264 */         loadPermissions(perms);
/* 265 */       } catch (ClassCastException ex) {
/* 266 */         throw new InvalidDescriptionException(ex, "permissions are of wrong type");
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private Map<String, Object> saveMap() {
/* 272 */     Map<String, Object> map = new HashMap<String, Object>();
/*     */     
/* 274 */     map.put("name", this.name);
/* 275 */     map.put("main", this.main);
/* 276 */     map.put("version", this.version);
/* 277 */     map.put("database", Boolean.valueOf(this.database));
/* 278 */     map.put("order", this.order.toString());
/*     */     
/* 280 */     if (this.commands != null) {
/* 281 */       map.put("command", this.commands);
/*     */     }
/* 283 */     if (this.depend != null) {
/* 284 */       map.put("depend", this.depend);
/*     */     }
/* 286 */     if (this.softDepend != null) {
/* 287 */       map.put("softdepend", this.softDepend);
/*     */     }
/* 289 */     if (this.website != null) {
/* 290 */       map.put("website", this.website);
/*     */     }
/* 292 */     if (this.description != null) {
/* 293 */       map.put("description", this.description);
/*     */     }
/*     */     
/* 296 */     if (this.authors.size() == 1) {
/* 297 */       map.put("author", this.authors.get(0));
/* 298 */     } else if (this.authors.size() > 1) {
/* 299 */       map.put("authors", this.authors);
/*     */     } 
/*     */     
/* 302 */     return map;
/*     */   }
/*     */   
/*     */   private void loadPermissions(Map<String, Map<String, Object>> perms) {
/* 306 */     Set<String> keys = perms.keySet();
/*     */     
/* 308 */     for (String name : keys) {
/*     */       try {
/* 310 */         this.permissions.add(Permission.loadPermission(name, (Map)perms.get(name)));
/* 311 */       } catch (Throwable ex) {
/* 312 */         Bukkit.getServer().getLogger().log(Level.SEVERE, "Permission node '" + name + "' in plugin description file for " + getFullName() + " is invalid", ex);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\plugin\PluginDescriptionFile.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */