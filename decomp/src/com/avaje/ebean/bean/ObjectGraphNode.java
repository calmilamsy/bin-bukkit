/*    */ package com.avaje.ebean.bean;
/*    */ 
/*    */ import java.io.Serializable;
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
/*    */ public final class ObjectGraphNode
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 2087081778650228996L;
/*    */   private final ObjectGraphOrigin originQueryPoint;
/*    */   private final String path;
/*    */   
/*    */   public ObjectGraphNode(ObjectGraphNode parent, String path) {
/* 50 */     this.originQueryPoint = parent.getOriginQueryPoint();
/* 51 */     this.path = parent.getChildPath(path);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ObjectGraphNode(ObjectGraphOrigin originQueryPoint, String path) {
/* 58 */     this.originQueryPoint = originQueryPoint;
/* 59 */     this.path = path;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 66 */   public ObjectGraphOrigin getOriginQueryPoint() { return this.originQueryPoint; }
/*    */ 
/*    */   
/*    */   private String getChildPath(String childPath) {
/* 70 */     if (this.path == null)
/* 71 */       return childPath; 
/* 72 */     if (childPath == null) {
/* 73 */       return this.path;
/*    */     }
/* 75 */     return this.path + "." + childPath;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 83 */   public String getPath() { return this.path; }
/*    */ 
/*    */ 
/*    */   
/* 87 */   public String toString() { return "origin:" + this.originQueryPoint + " " + ":" + this.path + ":" + this.path; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\bean\ObjectGraphNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */