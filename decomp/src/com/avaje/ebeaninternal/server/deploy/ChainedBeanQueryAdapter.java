/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebean.event.BeanQueryAdapter;
/*     */ import com.avaje.ebean.event.BeanQueryRequest;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChainedBeanQueryAdapter
/*     */   implements BeanQueryAdapter
/*     */ {
/*  16 */   private static final Sorter SORTER = new Sorter(null);
/*     */ 
/*     */   
/*     */   private final List<BeanQueryAdapter> list;
/*     */ 
/*     */   
/*     */   private final BeanQueryAdapter[] chain;
/*     */ 
/*     */   
/*  25 */   public ChainedBeanQueryAdapter(BeanQueryAdapter c1, BeanQueryAdapter c2) { this(addList(c1, c2)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<BeanQueryAdapter> addList(BeanQueryAdapter c1, BeanQueryAdapter c2) {
/*  32 */     ArrayList<BeanQueryAdapter> addList = new ArrayList<BeanQueryAdapter>(2);
/*  33 */     addList.add(c1);
/*  34 */     addList.add(c2);
/*  35 */     return addList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChainedBeanQueryAdapter(List<BeanQueryAdapter> list) {
/*  43 */     this.list = list;
/*  44 */     BeanQueryAdapter[] c = (BeanQueryAdapter[])list.toArray(new BeanQueryAdapter[list.size()]);
/*  45 */     Arrays.sort(c, SORTER);
/*  46 */     this.chain = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChainedBeanQueryAdapter register(BeanQueryAdapter c) {
/*  53 */     if (this.list.contains(c)) {
/*  54 */       return this;
/*     */     }
/*  56 */     List<BeanQueryAdapter> newList = new ArrayList<BeanQueryAdapter>();
/*  57 */     newList.addAll(this.list);
/*  58 */     newList.add(c);
/*     */     
/*  60 */     return new ChainedBeanQueryAdapter(newList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChainedBeanQueryAdapter deregister(BeanQueryAdapter c) {
/*  68 */     if (!this.list.contains(c)) {
/*  69 */       return this;
/*     */     }
/*  71 */     ArrayList<BeanQueryAdapter> newList = new ArrayList<BeanQueryAdapter>();
/*  72 */     newList.addAll(this.list);
/*  73 */     newList.remove(c);
/*     */     
/*  75 */     return new ChainedBeanQueryAdapter(newList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   public int getExecutionOrder() { return 0; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   public boolean isRegisterFor(Class<?> cls) { return false; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void preQuery(BeanQueryRequest<?> request) {
/*  96 */     for (int i = 0; i < this.chain.length; i++)
/*  97 */       this.chain[i].preQuery(request); 
/*     */   }
/*     */   
/*     */   private static class Sorter
/*     */     extends Object
/*     */     implements Comparator<BeanQueryAdapter>
/*     */   {
/*     */     private Sorter() {}
/*     */     
/*     */     public int compare(BeanQueryAdapter o1, BeanQueryAdapter o2) {
/* 107 */       int i1 = o1.getExecutionOrder();
/* 108 */       int i2 = o2.getExecutionOrder();
/* 109 */       return (i1 < i2) ? -1 : ((i1 == i2) ? 0 : 1);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\ChainedBeanQueryAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */