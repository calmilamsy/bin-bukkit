/*     */ package org.bukkit.craftbukkit.map;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.server.WorldMap;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.craftbukkit.CraftWorld;
/*     */ import org.bukkit.craftbukkit.entity.CraftPlayer;
/*     */ import org.bukkit.map.MapRenderer;
/*     */ import org.bukkit.map.MapView;
/*     */ 
/*     */ public final class CraftMapView implements MapView {
/*     */   private final Map<CraftPlayer, RenderData> renderCache;
/*     */   private final List<MapRenderer> renderers;
/*     */   
/*     */   public CraftMapView(WorldMap worldMap) {
/*  20 */     this.renderCache = new HashMap();
/*  21 */     this.renderers = new ArrayList();
/*  22 */     this.canvases = new HashMap();
/*     */ 
/*     */ 
/*     */     
/*  26 */     this.worldMap = worldMap;
/*  27 */     addRenderer(new CraftMapRenderer(this, worldMap));
/*     */   }
/*     */   private final Map<MapRenderer, Map<CraftPlayer, CraftMapCanvas>> canvases; protected final WorldMap worldMap;
/*     */   public short getId() {
/*  31 */     String text = this.worldMap.a;
/*  32 */     if (text.startsWith("map_")) {
/*     */       try {
/*  34 */         return Short.parseShort(text.substring("map_".length()));
/*     */       }
/*  36 */       catch (NumberFormatException ex) {
/*  37 */         throw new IllegalStateException("Map has non-numeric ID");
/*     */       } 
/*     */     }
/*  40 */     throw new IllegalStateException("Map has invalid ID");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  45 */   public boolean isVirtual() { return (this.renderers.size() > 0 && !(this.renderers.get(0) instanceof CraftMapRenderer)); }
/*     */ 
/*     */ 
/*     */   
/*  49 */   public MapView.Scale getScale() { return MapView.Scale.valueOf(this.worldMap.e); }
/*     */ 
/*     */ 
/*     */   
/*  53 */   public void setScale(MapView.Scale scale) { this.worldMap.e = scale.getValue(); }
/*     */ 
/*     */   
/*     */   public World getWorld() {
/*  57 */     byte dimension = this.worldMap.map;
/*  58 */     for (World world : Bukkit.getServer().getWorlds()) {
/*  59 */       if ((((CraftWorld)world).getHandle()).dimension == dimension) {
/*  60 */         return world;
/*     */       }
/*     */     } 
/*  63 */     return null;
/*     */   }
/*     */ 
/*     */   
/*  67 */   public void setWorld(World world) { this.worldMap.map = (byte)(((CraftWorld)world).getHandle()).dimension; }
/*     */ 
/*     */ 
/*     */   
/*  71 */   public int getCenterX() { return this.worldMap.b; }
/*     */ 
/*     */ 
/*     */   
/*  75 */   public int getCenterZ() { return this.worldMap.c; }
/*     */ 
/*     */ 
/*     */   
/*  79 */   public void setCenterX(int x) { this.worldMap.b = x; }
/*     */ 
/*     */ 
/*     */   
/*  83 */   public void setCenterZ(int z) { this.worldMap.c = z; }
/*     */ 
/*     */ 
/*     */   
/*  87 */   public List<MapRenderer> getRenderers() { return new ArrayList(this.renderers); }
/*     */ 
/*     */   
/*     */   public void addRenderer(MapRenderer renderer) {
/*  91 */     if (!this.renderers.contains(renderer)) {
/*  92 */       this.renderers.add(renderer);
/*  93 */       this.canvases.put(renderer, new HashMap());
/*  94 */       renderer.initialize(this);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean removeRenderer(MapRenderer renderer) {
/*  99 */     if (this.renderers.contains(renderer)) {
/* 100 */       this.renderers.remove(renderer);
/* 101 */       for (Map.Entry<CraftPlayer, CraftMapCanvas> entry : ((Map)this.canvases.get(renderer)).entrySet()) {
/* 102 */         for (int x = 0; x < 128; x++) {
/* 103 */           for (int y = 0; y < 128; y++) {
/* 104 */             ((CraftMapCanvas)entry.getValue()).setPixel(x, y, (byte)-1);
/*     */           }
/*     */         } 
/*     */       } 
/* 108 */       this.canvases.remove(renderer);
/* 109 */       return true;
/*     */     } 
/* 111 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isContextual() {
/* 116 */     for (MapRenderer renderer : this.renderers) {
/* 117 */       if (renderer.isContextual()) return true; 
/*     */     } 
/* 119 */     return false;
/*     */   }
/*     */   
/*     */   public RenderData render(CraftPlayer player) {
/* 123 */     boolean context = isContextual();
/* 124 */     RenderData render = (RenderData)this.renderCache.get(context ? player : null);
/*     */     
/* 126 */     if (render == null) {
/* 127 */       render = new RenderData();
/* 128 */       this.renderCache.put(context ? player : null, render);
/*     */     } 
/*     */     
/* 131 */     if (context && this.renderCache.containsKey(null)) {
/* 132 */       this.renderCache.remove(null);
/*     */     }
/*     */     
/* 135 */     Arrays.fill(render.buffer, (byte)0);
/* 136 */     render.cursors.clear();
/*     */     
/* 138 */     for (MapRenderer renderer : this.renderers) {
/* 139 */       CraftMapCanvas canvas = (CraftMapCanvas)((Map)this.canvases.get(renderer)).get(renderer.isContextual() ? player : null);
/* 140 */       if (canvas == null) {
/* 141 */         canvas = new CraftMapCanvas(this);
/* 142 */         ((Map)this.canvases.get(renderer)).put(renderer.isContextual() ? player : null, canvas);
/*     */       } 
/*     */       
/* 145 */       canvas.setBase(render.buffer);
/* 146 */       renderer.render(this, canvas, player);
/*     */       
/* 148 */       byte[] buf = canvas.getBuffer();
/* 149 */       for (i = 0; i < buf.length; i++) {
/* 150 */         if (buf[i] >= 0) render.buffer[i] = buf[i];
/*     */       
/*     */       } 
/* 153 */       for (int i = 0; i < canvas.getCursors().size(); i++) {
/* 154 */         render.cursors.add(canvas.getCursors().getCursor(i));
/*     */       }
/*     */     } 
/*     */     
/* 158 */     return render;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\map\CraftMapView.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */