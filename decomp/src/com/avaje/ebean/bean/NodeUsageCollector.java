/*     */ package com.avaje.ebean.bean;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.HashSet;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NodeUsageCollector
/*     */ {
/*     */   private final ObjectGraphNode node;
/*     */   private final WeakReference<NodeUsageListener> managerRef;
/*     */   private final HashSet<String> used;
/*     */   private boolean modified;
/*     */   private String loadProperty;
/*     */   
/*     */   public NodeUsageCollector(ObjectGraphNode node, WeakReference<NodeUsageListener> managerRef) {
/*  51 */     this.used = new HashSet();
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
/*  64 */     this.node = node;
/*     */     
/*  66 */     this.managerRef = managerRef;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   public void setModified() { this.modified = true; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public void addUsed(String property) { this.used.add(property); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   public void setLoadProperty(String loadProperty) { this.loadProperty = loadProperty; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void publishUsageInfo() {
/*  94 */     NodeUsageListener manager = (NodeUsageListener)this.managerRef.get();
/*  95 */     if (manager != null) {
/*  96 */       manager.collectNodeUsage(this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() {
/* 105 */     publishUsageInfo();
/* 106 */     super.finalize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 113 */   public ObjectGraphNode getNode() { return this.node; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   public boolean isEmpty() { return this.used.isEmpty(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   public HashSet<String> getUsed() { return this.used; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 134 */   public boolean isModified() { return this.modified; }
/*     */ 
/*     */ 
/*     */   
/* 138 */   public String getLoadProperty() { return this.loadProperty; }
/*     */ 
/*     */ 
/*     */   
/* 142 */   public String toString() { return this.node + " read:" + this.used + " modified:" + this.modified; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\bean\NodeUsageCollector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */