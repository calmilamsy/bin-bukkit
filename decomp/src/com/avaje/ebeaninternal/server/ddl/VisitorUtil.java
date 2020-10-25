/*     */ package com.avaje.ebeaninternal.server.ddl;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompound;
/*     */ import java.util.List;
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
/*     */ public class VisitorUtil
/*     */ {
/*  22 */   public static void visit(SpiEbeanServer server, BeanVisitor visitor) { visit(server.getBeanDescriptors(), visitor); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void visit(List<BeanDescriptor<?>> descriptors, BeanVisitor visitor) {
/*  31 */     visitor.visitBegin();
/*     */     
/*  33 */     for (BeanDescriptor<?> desc : descriptors) {
/*     */       
/*  35 */       if (desc.getBaseTable() != null) {
/*  36 */         visitBean(desc, visitor);
/*     */       }
/*     */     } 
/*     */     
/*  40 */     visitor.visitEnd();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void visitBean(BeanDescriptor<?> desc, BeanVisitor visitor) {
/*  48 */     if (visitor.visitBean(desc)) {
/*     */       
/*  50 */       BeanProperty[] propertiesId = desc.propertiesId();
/*  51 */       for (i = 0; i < propertiesId.length; i++) {
/*  52 */         visit(visitor, propertiesId[i]);
/*     */       }
/*  54 */       BeanPropertyAssocOne<?> unidirectional = desc.getUnidirectional();
/*  55 */       if (unidirectional != null) {
/*  56 */         visit(visitor, unidirectional);
/*     */       }
/*  58 */       BeanProperty[] propertiesNonTransient = desc.propertiesNonTransient();
/*  59 */       for (int i = 0; i < propertiesNonTransient.length; i++) {
/*  60 */         BeanProperty p = propertiesNonTransient[i];
/*  61 */         if (!p.isFormula() && !p.isSecondaryTable()) {
/*  62 */           visit(visitor, p);
/*     */         }
/*     */       } 
/*     */       
/*  66 */       visitor.visitBeanEnd(desc);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void visit(BeanVisitor visitor, BeanProperty p) {
/*  71 */     PropertyVisitor pv = visitor.visitProperty(p);
/*  72 */     if (pv != null) {
/*  73 */       visit(p, pv);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void visit(BeanProperty[] p, PropertyVisitor pv) {
/*  82 */     for (int i = 0; i < p.length; i++) {
/*  83 */       visit(p[i], pv);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void visit(BeanProperty p, PropertyVisitor pv) {
/*  92 */     if (p instanceof BeanPropertyAssocMany) {
/*  93 */       pv.visitMany((BeanPropertyAssocMany)p);
/*     */     }
/*  95 */     else if (p instanceof BeanPropertyAssocOne) {
/*  96 */       BeanPropertyAssocOne<?> assocOne = (BeanPropertyAssocOne)p;
/*  97 */       if (assocOne.isEmbedded()) {
/*  98 */         pv.visitEmbedded(assocOne);
/*  99 */         BeanProperty[] embProps = assocOne.getProperties();
/* 100 */         for (int i = 0; i < embProps.length; i++) {
/* 101 */           pv.visitEmbeddedScalar(embProps[i], assocOne);
/*     */         
/*     */         }
/*     */       }
/* 105 */       else if (assocOne.isOneToOneExported()) {
/* 106 */         pv.visitOneExported(assocOne);
/*     */       } else {
/*     */         
/* 109 */         pv.visitOneImported(assocOne);
/*     */       }
/*     */     
/* 112 */     } else if (p instanceof BeanPropertyCompound) {
/* 113 */       BeanPropertyCompound compound = (BeanPropertyCompound)p;
/* 114 */       pv.visitCompound(compound);
/*     */       
/* 116 */       BeanProperty[] properties = compound.getScalarProperties();
/* 117 */       for (int i = 0; i < properties.length; i++) {
/* 118 */         pv.visitCompoundScalar(compound, properties[i]);
/*     */       }
/*     */     } else {
/*     */       
/* 122 */       pv.visitScalar(p);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\ddl\VisitorUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */