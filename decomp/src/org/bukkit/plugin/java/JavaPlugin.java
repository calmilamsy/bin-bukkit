/*     */ package org.bukkit.plugin.java;
/*     */ 
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.EbeanServerFactory;
/*     */ import com.avaje.ebean.config.DataSourceConfig;
/*     */ import com.avaje.ebean.config.ServerConfig;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.server.ddl.DdlGenerator;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.command.PluginCommand;
/*     */ import org.bukkit.generator.ChunkGenerator;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ import org.bukkit.plugin.PluginLoader;
/*     */ import org.bukkit.util.config.Configuration;
/*     */ 
/*     */ 
/*     */ public abstract class JavaPlugin
/*     */   implements Plugin
/*     */ {
/*     */   private boolean isEnabled = false;
/*     */   private boolean initialized = false;
/*  28 */   private PluginLoader loader = null;
/*  29 */   private Server server = null;
/*  30 */   private File file = null;
/*  31 */   private PluginDescriptionFile description = null;
/*  32 */   private File dataFolder = null;
/*  33 */   private ClassLoader classLoader = null;
/*  34 */   private Configuration config = null;
/*     */   private boolean naggable = true;
/*  36 */   private EbeanServer ebean = null;
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
/*  47 */   public File getDataFolder() { return this.dataFolder; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   public final PluginLoader getPluginLoader() { return this.loader; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   public final Server getServer() { return this.server; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   public final boolean isEnabled() { return this.isEnabled; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   protected File getFile() { return this.file; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   public PluginDescriptionFile getDescription() { return this.description; }
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
/* 104 */   public Configuration getConfiguration() { return this.config; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 113 */   protected ClassLoader getClassLoader() { return this.classLoader; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setEnabled(boolean enabled) {
/* 122 */     if (this.isEnabled != enabled) {
/* 123 */       this.isEnabled = enabled;
/*     */       
/* 125 */       if (this.isEnabled) {
/* 126 */         onEnable();
/*     */       } else {
/* 128 */         onDisable();
/*     */       } 
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
/*     */ 
/*     */   
/*     */   protected final void initialize(PluginLoader loader, Server server, PluginDescriptionFile description, File dataFolder, File file, ClassLoader classLoader) {
/* 148 */     if (!this.initialized) {
/* 149 */       this.initialized = true;
/* 150 */       this.loader = loader;
/* 151 */       this.server = server;
/* 152 */       this.file = file;
/* 153 */       this.description = description;
/* 154 */       this.dataFolder = dataFolder;
/* 155 */       this.classLoader = classLoader;
/* 156 */       this.config = new Configuration(new File(dataFolder, "config.yml"));
/* 157 */       this.config.load();
/*     */       
/* 159 */       if (description.isDatabaseEnabled()) {
/* 160 */         ServerConfig db = new ServerConfig();
/*     */         
/* 162 */         db.setDefaultServer(false);
/* 163 */         db.setRegister(false);
/* 164 */         db.setClasses(getDatabaseClasses());
/* 165 */         db.setName(description.getName());
/* 166 */         server.configureDbConfig(db);
/*     */         
/* 168 */         DataSourceConfig ds = db.getDataSourceConfig();
/*     */         
/* 170 */         ds.setUrl(replaceDatabaseString(ds.getUrl()));
/* 171 */         getDataFolder().mkdirs();
/*     */         
/* 173 */         ClassLoader previous = Thread.currentThread().getContextClassLoader();
/*     */         
/* 175 */         Thread.currentThread().setContextClassLoader(classLoader);
/* 176 */         this.ebean = EbeanServerFactory.create(db);
/* 177 */         Thread.currentThread().setContextClassLoader(previous);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 188 */   public List<Class<?>> getDatabaseClasses() { return new ArrayList(); }
/*     */ 
/*     */   
/*     */   private String replaceDatabaseString(String input) {
/* 192 */     input = input.replaceAll("\\{DIR\\}", getDataFolder().getPath().replaceAll("\\\\", "/") + "/");
/* 193 */     return input.replaceAll("\\{NAME\\}", getDescription().getName().replaceAll("[^\\w_-]", ""));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 203 */   public boolean isInitialized() { return this.initialized; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 210 */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) { return false; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PluginCommand getCommand(String name) {
/* 220 */     String alias = name.toLowerCase();
/* 221 */     PluginCommand command = getServer().getPluginCommand(alias);
/*     */     
/* 223 */     if (command != null && command.getPlugin() != this) {
/* 224 */       command = getServer().getPluginCommand(getDescription().getName().toLowerCase() + ":" + alias);
/*     */     }
/*     */     
/* 227 */     if (command != null && command.getPlugin() == this) {
/* 228 */       return command;
/*     */     }
/* 230 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onLoad() {}
/*     */   
/*     */   public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
/* 237 */     getServer().getLogger().severe("Plugin " + getDescription().getFullName() + " does not contain any generators that may be used in the default world!");
/* 238 */     return null;
/*     */   }
/*     */ 
/*     */   
/* 242 */   public final boolean isNaggable() { return this.naggable; }
/*     */ 
/*     */ 
/*     */   
/* 246 */   public final void setNaggable(boolean canNag) { this.naggable = canNag; }
/*     */ 
/*     */ 
/*     */   
/* 250 */   public EbeanServer getDatabase() { return this.ebean; }
/*     */ 
/*     */   
/*     */   protected void installDDL() {
/* 254 */     SpiEbeanServer serv = (SpiEbeanServer)getDatabase();
/* 255 */     DdlGenerator gen = serv.getDdlGenerator();
/*     */     
/* 257 */     gen.runScript(false, gen.generateCreateDdl());
/*     */   }
/*     */   
/*     */   protected void removeDDL() {
/* 261 */     SpiEbeanServer serv = (SpiEbeanServer)getDatabase();
/* 262 */     DdlGenerator gen = serv.getDdlGenerator();
/*     */     
/* 264 */     gen.runScript(true, gen.generateDropDdl());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 269 */   public String toString() { return getDescription().getFullName(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\plugin\java\JavaPlugin.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */