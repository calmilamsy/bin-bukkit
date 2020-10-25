/*    */ package net.minecraft.server;
/*    */ 
/*    */ import org.bukkit.craftbukkit.entity.CraftPlayer;
/*    */ import org.bukkit.craftbukkit.map.RenderData;
/*    */ import org.bukkit.map.MapCursor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldMapHumanTracker
/*    */ {
/*    */   public final EntityHuman trackee;
/*    */   public int[] b;
/*    */   public int[] c;
/*    */   private int e;
/*    */   private int f;
/*    */   private byte[] g;
/*    */   final WorldMap d;
/*    */   
/*    */   public WorldMapHumanTracker(WorldMap worldmap, EntityHuman entityhuman) {
/* 21 */     this.d = worldmap;
/* 22 */     this.b = new int[128];
/* 23 */     this.c = new int[128];
/* 24 */     this.e = 0;
/* 25 */     this.f = 0;
/* 26 */     this.trackee = entityhuman;
/*    */     
/* 28 */     for (int i = 0; i < this.b.length; i++) {
/* 29 */       this.b[i] = 0;
/* 30 */       this.c[i] = 127;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] a(ItemStack itemstack) {
/* 38 */     RenderData render = this.d.mapView.render((CraftPlayer)this.trackee.getBukkitEntity());
/*    */     
/* 40 */     if (--this.f < 0) {
/* 41 */       this.f = 4;
/* 42 */       byte[] abyte = new byte[render.cursors.size() * 3 + 1];
/*    */       
/* 44 */       abyte[0] = 1;
/*    */ 
/*    */       
/* 47 */       for (int i = 0; i < render.cursors.size(); i++) {
/* 48 */         MapCursor cursor = (MapCursor)render.cursors.get(i);
/* 49 */         if (cursor.isVisible()) {
/*    */           
/* 51 */           byte value = (byte)((((cursor.getRawType() == 0 || cursor.getDirection() < 8) ? cursor.getDirection() : (cursor.getDirection() - 1)) & 0xF) * 16);
/* 52 */           abyte[i * 3 + 1] = (byte)(value | ((cursor.getRawType() != 0 && value < 0) ? (16 - cursor.getRawType()) : cursor.getRawType()));
/* 53 */           abyte[i * 3 + 2] = cursor.getX();
/* 54 */           abyte[i * 3 + 3] = cursor.getY();
/*    */         } 
/*    */       } 
/*    */       
/* 58 */       boolean flag = true;
/*    */       
/* 60 */       if (this.g != null && this.g.length == abyte.length) {
/* 61 */         for (int j = 0; j < abyte.length; j++) {
/* 62 */           if (abyte[j] != this.g[j]) {
/* 63 */             flag = false;
/*    */             break;
/*    */           } 
/*    */         } 
/*    */       } else {
/* 68 */         flag = false;
/*    */       } 
/*    */       
/* 71 */       if (!flag) {
/* 72 */         this.g = abyte;
/* 73 */         return abyte;
/*    */       } 
/*    */     } 
/*    */     
/* 77 */     for (int k = 0; k < 10; k++) {
/* 78 */       int i = this.e * 11 % 128;
/* 79 */       this.e++;
/* 80 */       if (this.b[i] >= 0) {
/* 81 */         int j = this.c[i] - this.b[i] + 1;
/* 82 */         int l = this.b[i];
/* 83 */         byte[] abyte1 = new byte[j + 3];
/*    */         
/* 85 */         abyte1[0] = 0;
/* 86 */         abyte1[1] = (byte)i;
/* 87 */         abyte1[2] = (byte)l;
/*    */         
/* 89 */         for (int i1 = 0; i1 < abyte1.length - 3; i1++) {
/* 90 */           abyte1[i1 + 3] = render.buffer[(i1 + l) * 128 + i];
/*    */         }
/*    */         
/* 93 */         this.c[i] = -1;
/* 94 */         this.b[i] = -1;
/* 95 */         return abyte1;
/*    */       } 
/*    */     } 
/*    */     
/* 99 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\WorldMapHumanTracker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */