/*    */ package org.bukkit.fillr;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FilenameFilter;
/*    */ 
/*    */ 
/*    */ public class PluginFilter
/*    */   implements FilenameFilter
/*    */ {
/*    */   public boolean accept(File file, String name) {
/* 11 */     if (name.endsWith(".jar")) {
/* 12 */       return true;
/*    */     }
/* 14 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\fillr\PluginFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */