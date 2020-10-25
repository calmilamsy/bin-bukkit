/*    */ package org.bukkit.material;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ public class Jukebox
/*    */   extends MaterialData {
/*  8 */   private static HashSet<Material> recordTypes = new HashSet();
/*    */   static  {
/* 10 */     recordTypes.add(Material.GOLD_RECORD);
/* 11 */     recordTypes.add(Material.GREEN_RECORD);
/*    */   }
/*    */ 
/*    */   
/* 15 */   public Jukebox() { super(Material.JUKEBOX); }
/*    */ 
/*    */ 
/*    */   
/* 19 */   public Jukebox(int type) { super(type); }
/*    */ 
/*    */   
/*    */   public Jukebox(Material type) {
/* 23 */     super(recordTypes.contains(type) ? Material.JUKEBOX : type);
/* 24 */     if (recordTypes.contains(type)) {
/* 25 */       setPlaying(type);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/* 30 */   public Jukebox(int type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */   
/* 34 */   public Jukebox(Material type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Material getPlaying() {
/* 43 */     switch (getData()) {
/*    */       
/*    */       default:
/* 46 */         return null;
/*    */       
/*    */       case 1:
/* 49 */         return Material.GOLD_RECORD;
/*    */       case 2:
/*    */         break;
/* 52 */     }  return Material.GREEN_RECORD;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setPlaying(Material rec) {
/* 62 */     if (rec == null) {
/* 63 */       setData((byte)0);
/*    */     } else {
/* 65 */       switch (rec) {
/*    */         case GOLD_RECORD:
/* 67 */           setData((byte)1);
/*    */           return;
/*    */         
/*    */         case GREEN_RECORD:
/* 71 */           setData((byte)2);
/*    */           return;
/*    */       } 
/*    */       
/* 75 */       setData((byte)0);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 82 */   public String toString() { return super.toString() + " playing " + getPlaying(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\Jukebox.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */