/*    */ package com.avaje.ebeaninternal.server.resource;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.lib.resource.ResourceSource;
/*    */ import java.io.File;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ResourceManager
/*    */ {
/*    */   final ResourceSource resourceSource;
/*    */   final File autofetchDir;
/*    */   
/*    */   public ResourceManager(ResourceSource resourceSource, File autofetchDir) {
/* 36 */     this.resourceSource = resourceSource;
/* 37 */     this.autofetchDir = autofetchDir;
/*    */   }
/*    */ 
/*    */   
/* 41 */   public ResourceSource getResourceSource() { return this.resourceSource; }
/*    */ 
/*    */ 
/*    */   
/* 45 */   public File getAutofetchDirectory() { return this.autofetchDir; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\resource\ResourceManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */