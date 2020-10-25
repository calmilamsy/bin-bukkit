/*    */ package com.avaje.ebeaninternal.server.lib.resource;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.lib.util.GeneralException;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
/*    */ import javax.servlet.ServletContext;
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
/*    */ 
/*    */ 
/*    */ public class UrlResourceSource
/*    */   extends AbstractResourceSource
/*    */   implements ResourceSource
/*    */ {
/*    */   ServletContext sc;
/*    */   String basePath;
/*    */   String realPath;
/*    */   
/*    */   public UrlResourceSource(ServletContext sc, String basePath) {
/* 43 */     this.sc = sc;
/* 44 */     if (basePath == null) {
/* 45 */       this.basePath = "/";
/*    */     } else {
/* 47 */       this.basePath = "/" + basePath + "/";
/*    */     } 
/* 49 */     this.realPath = sc.getRealPath(basePath);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 59 */   public String getRealPath() { return this.realPath; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ResourceContent getContent(String entry) {
/*    */     try {
/* 71 */       URL url = this.sc.getResource(this.basePath + entry);
/* 72 */       if (url != null) {
/* 73 */         return new UrlResourceContent(url, entry);
/*    */       }
/* 75 */       return null;
/*    */     }
/* 77 */     catch (MalformedURLException ex) {
/* 78 */       throw new GeneralException(ex);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\resource\UrlResourceSource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */