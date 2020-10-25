/*      */ package com.avaje.ebeaninternal.server.persist;
/*      */ 
/*      */ import com.avaje.ebean.CallableSql;
/*      */ import com.avaje.ebean.Query;
/*      */ import com.avaje.ebean.SqlUpdate;
/*      */ import com.avaje.ebean.Transaction;
/*      */ import com.avaje.ebean.Update;
/*      */ import com.avaje.ebean.bean.BeanCollection;
/*      */ import com.avaje.ebean.bean.EntityBean;
/*      */ import com.avaje.ebean.bean.EntityBeanIntercept;
/*      */ import com.avaje.ebean.config.ldap.LdapContextFactory;
/*      */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*      */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*      */ import com.avaje.ebeaninternal.api.SpiUpdate;
/*      */ import com.avaje.ebeaninternal.server.core.ConcurrencyMode;
/*      */ import com.avaje.ebeaninternal.server.core.Message;
/*      */ import com.avaje.ebeaninternal.server.core.PersistRequest;
/*      */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*      */ import com.avaje.ebeaninternal.server.core.PersistRequestCallableSql;
/*      */ import com.avaje.ebeaninternal.server.core.PersistRequestOrmUpdate;
/*      */ import com.avaje.ebeaninternal.server.core.PersistRequestUpdateSql;
/*      */ import com.avaje.ebeaninternal.server.core.Persister;
/*      */ import com.avaje.ebeaninternal.server.core.PstmtBatch;
/*      */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*      */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptorManager;
/*      */ import com.avaje.ebeaninternal.server.deploy.BeanManager;
/*      */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*      */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*      */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*      */ import com.avaje.ebeaninternal.server.deploy.IntersectionRow;
/*      */ import com.avaje.ebeaninternal.server.deploy.ManyType;
/*      */ import com.avaje.ebeaninternal.server.ldap.DefaultLdapPersister;
/*      */ import com.avaje.ebeaninternal.server.ldap.LdapPersistBeanRequest;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.persistence.PersistenceException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class DefaultPersister
/*      */   implements Persister
/*      */ {
/*   84 */   private static final Logger logger = Logger.getLogger(DefaultPersister.class.getName());
/*      */ 
/*      */   
/*      */   private final PersistExecute persistExecute;
/*      */ 
/*      */   
/*      */   private final DefaultLdapPersister ldapPersister;
/*      */ 
/*      */   
/*      */   private final SpiEbeanServer server;
/*      */   
/*      */   private final BeanDescriptorManager beanDescriptorManager;
/*      */   
/*      */   private final boolean defaultUpdateNullProperties;
/*      */   
/*      */   private final boolean defaultDeleteMissingChildren;
/*      */ 
/*      */   
/*      */   public DefaultPersister(SpiEbeanServer server, boolean validate, Binder binder, BeanDescriptorManager descMgr, PstmtBatch pstmtBatch, LdapContextFactory contextFactory) {
/*  103 */     this.server = server;
/*  104 */     this.beanDescriptorManager = descMgr;
/*      */     
/*  106 */     this.persistExecute = new DefaultPersistExecute(validate, binder, pstmtBatch);
/*  107 */     this.ldapPersister = new DefaultLdapPersister(contextFactory);
/*      */     
/*  109 */     this.defaultUpdateNullProperties = server.isDefaultUpdateNullProperties();
/*  110 */     this.defaultDeleteMissingChildren = server.isDefaultDeleteMissingChildren();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int executeCallable(CallableSql callSql, Transaction t) {
/*  118 */     PersistRequestCallableSql request = new PersistRequestCallableSql(this.server, callSql, (SpiTransaction)t, this.persistExecute);
/*      */     try {
/*  120 */       request.initTransIfRequired();
/*  121 */       int rc = request.executeOrQueue();
/*  122 */       request.commitTransIfRequired();
/*  123 */       return rc;
/*      */     }
/*  125 */     catch (RuntimeException e) {
/*  126 */       request.rollbackTransIfRequired();
/*  127 */       throw e;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int executeOrmUpdate(Update<?> update, Transaction t) {
/*  136 */     SpiUpdate<?> ormUpdate = (SpiUpdate)update;
/*      */     
/*  138 */     BeanManager<?> mgr = this.beanDescriptorManager.getBeanManager(ormUpdate.getBeanType());
/*      */     
/*  140 */     if (mgr == null) {
/*  141 */       String msg = "No BeanManager found for type [" + ormUpdate.getBeanType() + "]. Is it an entity?";
/*  142 */       throw new PersistenceException(msg);
/*      */     } 
/*      */     
/*  145 */     PersistRequestOrmUpdate request = new PersistRequestOrmUpdate(this.server, mgr, ormUpdate, (SpiTransaction)t, this.persistExecute);
/*      */     try {
/*  147 */       request.initTransIfRequired();
/*  148 */       int rc = request.executeOrQueue();
/*  149 */       request.commitTransIfRequired();
/*  150 */       return rc;
/*      */     }
/*  152 */     catch (RuntimeException e) {
/*  153 */       request.rollbackTransIfRequired();
/*  154 */       throw e;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int executeSqlUpdate(SqlUpdate updSql, Transaction t) {
/*  163 */     PersistRequestUpdateSql request = new PersistRequestUpdateSql(this.server, updSql, (SpiTransaction)t, this.persistExecute);
/*      */     try {
/*  165 */       request.initTransIfRequired();
/*  166 */       int rc = request.executeOrQueue();
/*  167 */       request.commitTransIfRequired();
/*  168 */       return rc;
/*      */     }
/*  170 */     catch (RuntimeException e) {
/*  171 */       request.rollbackTransIfRequired();
/*  172 */       throw e;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  181 */   private void deleteRecurse(Object detailBean, Transaction t) { this.server.delete(detailBean, t); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void forceUpdate(Object bean, Set<String> updateProps, Transaction t, boolean deleteMissingChildren, boolean updateNullProperties) {
/*  189 */     if (bean == null) {
/*  190 */       throw new NullPointerException(Message.msg("bean.isnull"));
/*      */     }
/*      */     
/*  193 */     if (updateProps == null)
/*      */     {
/*  195 */       if (bean instanceof EntityBean) {
/*  196 */         EntityBeanIntercept ebi = ((EntityBean)bean)._ebean_getIntercept();
/*  197 */         if (ebi.isDirty() || ebi.isLoaded()) {
/*      */ 
/*      */           
/*  200 */           PersistRequestBean<?> req = createRequest(bean, t, null);
/*      */           try {
/*  202 */             req.initTransIfRequired();
/*  203 */             update(req);
/*  204 */             req.commitTransIfRequired();
/*      */ 
/*      */             
/*      */             return;
/*  208 */           } catch (RuntimeException ex) {
/*  209 */             req.rollbackTransIfRequired();
/*  210 */             throw ex;
/*      */           } 
/*  212 */         }  if (ebi.isReference()) {
/*      */           return;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  218 */         updateProps = ebi.getLoadedProps();
/*      */       } 
/*      */     }
/*      */     
/*  222 */     BeanManager<?> mgr = getBeanManager(bean);
/*  223 */     if (mgr == null) {
/*  224 */       throw new PersistenceException(errNotRegistered(bean.getClass()));
/*      */     }
/*      */     
/*  227 */     forceUpdateStateless(bean, t, null, mgr, updateProps, deleteMissingChildren, updateNullProperties);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void forceUpdateStateless(Object bean, Transaction t, Object parentBean, BeanManager<?> mgr, Set<String> updateProps, boolean deleteMissingChildren, boolean updateNullProperties) {
/*      */     PersistRequestBean<?> req;
/*  237 */     BeanDescriptor<?> descriptor = mgr.getBeanDescriptor();
/*      */ 
/*      */     
/*  240 */     ConcurrencyMode mode = descriptor.determineConcurrencyMode(bean);
/*      */     
/*  242 */     if (updateProps == null) {
/*      */       
/*  244 */       updateProps = updateNullProperties ? null : descriptor.determineLoadedProperties(bean);
/*      */     }
/*  246 */     else if (updateProps.isEmpty()) {
/*      */       
/*  248 */       updateProps = null;
/*      */     }
/*  250 */     else if (ConcurrencyMode.VERSION.equals(mode)) {
/*      */       
/*  252 */       req = descriptor.firstVersionProperty().getName();
/*  253 */       if (!updateProps.contains(req)) {
/*      */         
/*  255 */         updateProps = new HashSet<String>(updateProps);
/*  256 */         updateProps.add(req);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  261 */     if (descriptor.isLdapEntityType()) {
/*  262 */       req = new LdapPersistBeanRequest(this.server, bean, parentBean, mgr, this.ldapPersister, updateProps, mode);
/*      */     }
/*      */     else {
/*      */       
/*  266 */       req = new PersistRequestBean<?>(this.server, bean, parentBean, mgr, (SpiTransaction)t, this.persistExecute, updateProps, mode);
/*  267 */       req.setStatelessUpdate(true, deleteMissingChildren, updateNullProperties);
/*      */     } 
/*      */     
/*      */     try {
/*  271 */       req.initTransIfRequired();
/*  272 */       update(req);
/*  273 */       req.commitTransIfRequired();
/*      */     }
/*  275 */     catch (RuntimeException ex) {
/*  276 */       req.rollbackTransIfRequired();
/*  277 */       throw ex;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*  282 */   public void save(Object bean, Transaction t) { saveRecurse(bean, t, null); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void forceInsert(Object bean, Transaction t) {
/*  290 */     PersistRequestBean<?> req = createRequest(bean, t, null);
/*      */     try {
/*  292 */       req.initTransIfRequired();
/*  293 */       insert(req);
/*  294 */       req.commitTransIfRequired();
/*      */     }
/*  296 */     catch (RuntimeException ex) {
/*  297 */       req.rollbackTransIfRequired();
/*  298 */       throw ex;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void saveRecurse(Object bean, Transaction t, Object parentBean) {
/*  303 */     if (bean == null) {
/*  304 */       throw new NullPointerException(Message.msg("bean.isnull"));
/*      */     }
/*      */     
/*  307 */     if (!(bean instanceof EntityBean)) {
/*  308 */       saveVanillaRecurse(bean, t, parentBean);
/*      */       
/*      */       return;
/*      */     } 
/*  312 */     PersistRequestBean<?> req = createRequest(bean, t, parentBean);
/*      */     try {
/*  314 */       req.initTransIfRequired();
/*  315 */       saveEnhanced(req);
/*  316 */       req.commitTransIfRequired();
/*      */     }
/*  318 */     catch (RuntimeException ex) {
/*  319 */       req.rollbackTransIfRequired();
/*  320 */       throw ex;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void saveEnhanced(PersistRequestBean<?> request) {
/*  329 */     EntityBeanIntercept intercept = request.getEntityBeanIntercept();
/*      */     
/*  331 */     if (intercept.isReference()) {
/*      */       
/*  333 */       if (request.isPersistCascade())
/*      */       {
/*  335 */         intercept.setLoaded();
/*  336 */         saveAssocMany(false, request);
/*  337 */         intercept.setReference();
/*      */       }
/*      */     
/*      */     }
/*  341 */     else if (intercept.isLoaded()) {
/*      */       
/*  343 */       update(request);
/*      */     } else {
/*  345 */       insert(request);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void saveVanillaRecurse(Object bean, Transaction t, Object parentBean) {
/*  355 */     BeanManager<?> mgr = getBeanManager(bean);
/*  356 */     if (mgr == null) {
/*  357 */       throw new RuntimeException("No Mgr found for " + bean + " " + bean.getClass());
/*      */     }
/*      */     
/*  360 */     if (mgr.getBeanDescriptor().isVanillaInsert(bean)) {
/*  361 */       saveVanillaInsert(bean, t, parentBean, mgr);
/*      */     }
/*      */     else {
/*      */       
/*  365 */       forceUpdateStateless(bean, t, parentBean, mgr, null, this.defaultDeleteMissingChildren, this.defaultUpdateNullProperties);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void saveVanillaInsert(Object bean, Transaction t, Object parentBean, BeanManager<?> mgr) {
/*  374 */     PersistRequestBean<?> req = createRequest(bean, t, parentBean, mgr);
/*      */     try {
/*  376 */       req.initTransIfRequired();
/*  377 */       insert(req);
/*  378 */       req.commitTransIfRequired();
/*      */     }
/*  380 */     catch (RuntimeException ex) {
/*  381 */       req.rollbackTransIfRequired();
/*  382 */       throw ex;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void insert(PersistRequestBean<?> request) {
/*  391 */     if (request.isRegisteredVanillaBean()) {
/*      */       return;
/*      */     }
/*      */     
/*  395 */     request.setType(PersistRequest.Type.INSERT);
/*      */     
/*  397 */     if (request.isPersistCascade())
/*      */     {
/*  399 */       saveAssocOne(request);
/*      */     }
/*      */ 
/*      */     
/*  403 */     setIdGenValue(request);
/*  404 */     request.executeOrQueue();
/*  405 */     request.registerVanillaBean();
/*      */     
/*  407 */     if (request.isPersistCascade())
/*      */     {
/*  409 */       saveAssocMany(true, request);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void update(PersistRequestBean<?> request) {
/*  418 */     if (request.isRegisteredVanillaBean()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  423 */     request.setType(PersistRequest.Type.UPDATE);
/*  424 */     if (request.isPersistCascade())
/*      */     {
/*  426 */       saveAssocOne(request);
/*      */     }
/*      */     
/*  429 */     if (request.isDirty()) {
/*  430 */       request.executeOrQueue();
/*  431 */       request.registerVanillaBean();
/*      */ 
/*      */     
/*      */     }
/*  435 */     else if (logger.isLoggable(Level.FINE)) {
/*  436 */       logger.fine(Message.msg("persist.update.skipped", request.getBean()));
/*      */     } 
/*      */ 
/*      */     
/*  440 */     if (request.isPersistCascade())
/*      */     {
/*  442 */       saveAssocMany(false, request);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void delete(Object bean, Transaction t) {
/*  451 */     PersistRequestBean<?> req = createRequest(bean, t, null);
/*  452 */     if (req.isRegistered()) {
/*      */ 
/*      */       
/*  455 */       if (logger.isLoggable(Level.FINE)) {
/*  456 */         logger.fine("skipping delete on alreadyRegistered " + bean);
/*      */       }
/*      */       
/*      */       return;
/*      */     } 
/*  461 */     req.setType(PersistRequest.Type.DELETE);
/*      */     try {
/*  463 */       req.initTransIfRequired();
/*  464 */       delete(req);
/*  465 */       req.commitTransIfRequired();
/*      */     }
/*  467 */     catch (RuntimeException ex) {
/*  468 */       req.rollbackTransIfRequired();
/*  469 */       throw ex;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void deleteList(List<?> beanList, Transaction t) {
/*  474 */     for (int i = 0; i < beanList.size(); i++) {
/*  475 */       Object bean = beanList.get(i);
/*  476 */       delete(bean, t);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deleteMany(Class<?> beanType, Collection<?> ids, Transaction transaction) {
/*  485 */     if (ids == null || ids.size() == 0) {
/*      */       return;
/*      */     }
/*      */     
/*  489 */     BeanDescriptor<?> descriptor = this.beanDescriptorManager.getBeanDescriptor(beanType);
/*      */     
/*  491 */     ArrayList<Object> idList = new ArrayList<Object>(ids.size());
/*  492 */     for (Object id : ids)
/*      */     {
/*  494 */       idList.add(descriptor.convertId(id));
/*      */     }
/*      */     
/*  497 */     delete(descriptor, null, idList, transaction);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int delete(Class<?> beanType, Object id, Transaction transaction) {
/*  505 */     BeanDescriptor<?> descriptor = this.beanDescriptorManager.getBeanDescriptor(beanType);
/*      */ 
/*      */     
/*  508 */     id = descriptor.convertId(id);
/*  509 */     return delete(descriptor, id, null, transaction);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int delete(BeanDescriptor<?> descriptor, Object id, List<Object> idList, Transaction transaction) {
/*  517 */     SpiTransaction t = (SpiTransaction)transaction;
/*  518 */     if (t.isPersistCascade()) {
/*  519 */       BeanPropertyAssocOne[] propImportDelete = descriptor.propertiesOneImportedDelete();
/*  520 */       if (propImportDelete.length > 0) {
/*      */ 
/*      */ 
/*      */         
/*  524 */         Query<?> q = deleteRequiresQuery(descriptor, propImportDelete);
/*  525 */         if (idList != null) {
/*  526 */           q.where().idIn(idList);
/*  527 */           if (t.isLogSummary()) {
/*  528 */             t.logInternal("-- DeleteById of " + descriptor.getName() + " ids[" + idList + "] requires fetch of foreign key values");
/*      */           }
/*  530 */           List<?> beanList = this.server.findList(q, t);
/*  531 */           deleteList(beanList, t);
/*  532 */           return beanList.size();
/*      */         } 
/*      */         
/*  535 */         q.where().idEq(id);
/*  536 */         if (t.isLogSummary()) {
/*  537 */           t.logInternal("-- DeleteById of " + descriptor.getName() + " id[" + id + "] requires fetch of foreign key values");
/*      */         }
/*  539 */         Object bean = this.server.findUnique(q, t);
/*  540 */         if (bean == null) {
/*  541 */           return 0;
/*      */         }
/*  543 */         delete(bean, t);
/*  544 */         return 1;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  550 */     if (t.isPersistCascade()) {
/*      */       
/*  552 */       BeanPropertyAssocOne[] expOnes = descriptor.propertiesOneExportedDelete();
/*  553 */       for (i = 0; i < expOnes.length; i++) {
/*  554 */         BeanDescriptor<?> targetDesc = expOnes[i].getTargetDescriptor();
/*  555 */         if (targetDesc.isDeleteRecurseSkippable() && !targetDesc.isUsingL2Cache()) {
/*  556 */           SqlUpdate sqlDelete = expOnes[i].deleteByParentId(id, idList);
/*  557 */           executeSqlUpdate(sqlDelete, t);
/*      */         } else {
/*  559 */           List<Object> childIds = expOnes[i].findIdsByParentId(id, idList, t);
/*  560 */           delete(targetDesc, null, childIds, t);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  565 */       BeanPropertyAssocMany[] manys = descriptor.propertiesManyDelete();
/*  566 */       for (int i = 0; i < manys.length; i++) {
/*  567 */         BeanDescriptor<?> targetDesc = manys[i].getTargetDescriptor();
/*  568 */         if (targetDesc.isDeleteRecurseSkippable() && !targetDesc.isUsingL2Cache()) {
/*      */           
/*  570 */           SqlUpdate sqlDelete = manys[i].deleteByParentId(id, idList);
/*  571 */           executeSqlUpdate(sqlDelete, t);
/*      */         } else {
/*      */           
/*  574 */           List<Object> childIds = manys[i].findIdsByParentId(id, idList, t, null);
/*  575 */           delete(targetDesc, null, childIds, t);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  581 */     BeanPropertyAssocMany[] manys = descriptor.propertiesManyToMany();
/*  582 */     for (int i = 0; i < manys.length; i++) {
/*  583 */       SqlUpdate sqlDelete = manys[i].deleteByParentId(id, idList);
/*  584 */       if (t.isLogSummary()) {
/*  585 */         t.logInternal("-- Deleting intersection table entries: " + manys[i].getFullBeanName());
/*      */       }
/*  587 */       executeSqlUpdate(sqlDelete, t);
/*      */     } 
/*      */ 
/*      */     
/*  591 */     SqlUpdate deleteById = descriptor.deleteById(id, idList);
/*  592 */     if (t.isLogSummary()) {
/*  593 */       t.logInternal("-- Deleting " + descriptor.getName() + " Ids" + idList);
/*      */     }
/*      */ 
/*      */     
/*  597 */     deleteById.setAutoTableMod(false);
/*  598 */     if (idList != null) {
/*  599 */       t.getEvent().addDeleteByIdList(descriptor, idList);
/*      */     } else {
/*  601 */       t.getEvent().addDeleteById(descriptor, id);
/*      */     } 
/*  603 */     return executeSqlUpdate(deleteById, t);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Query<?> deleteRequiresQuery(BeanDescriptor<?> desc, BeanPropertyAssocOne[] propImportDelete) {
/*  612 */     Query<?> q = this.server.createQuery(desc.getBeanType());
/*  613 */     StringBuilder sb = new StringBuilder(30);
/*  614 */     for (int i = 0; i < propImportDelete.length; i++) {
/*  615 */       sb.append(propImportDelete[i].getName()).append(",");
/*      */     }
/*  617 */     q.setAutofetch(false);
/*  618 */     q.select(sb.toString());
/*  619 */     return q;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void delete(PersistRequestBean<?> request) {
/*  630 */     DeleteUnloadedForeignKeys unloadedForeignKeys = null;
/*      */     
/*  632 */     boolean pauseIndexInvalidate = request.getBeanDescriptor().isLuceneIndexed();
/*  633 */     if (pauseIndexInvalidate)
/*      */     {
/*      */       
/*  636 */       request.pauseIndexInvalidate();
/*      */     }
/*      */     
/*  639 */     if (request.isPersistCascade()) {
/*      */ 
/*      */       
/*  642 */       request.registerBean();
/*  643 */       deleteAssocMany(request);
/*  644 */       request.unregisterBean();
/*      */       
/*  646 */       unloadedForeignKeys = getDeleteUnloadedForeignKeys(request);
/*  647 */       if (unloadedForeignKeys != null)
/*      */       {
/*      */         
/*  650 */         unloadedForeignKeys.queryForeignKeys();
/*      */       }
/*      */     } 
/*      */     
/*  654 */     request.executeOrQueue();
/*      */     
/*  656 */     if (request.isPersistCascade()) {
/*  657 */       deleteAssocOne(request);
/*      */       
/*  659 */       if (unloadedForeignKeys != null) {
/*  660 */         unloadedForeignKeys.deleteCascade();
/*      */       }
/*      */     } 
/*  663 */     if (pauseIndexInvalidate) {
/*  664 */       request.resumeIndexInvalidate();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void saveAssocMany(boolean insertedParent, PersistRequestBean<?> request) {
/*  677 */     Object parentBean = request.getBean();
/*  678 */     BeanDescriptor<?> desc = request.getBeanDescriptor();
/*  679 */     SpiTransaction t = request.getTransaction();
/*      */ 
/*      */     
/*  682 */     BeanPropertyAssocOne[] expOnes = desc.propertiesOneExportedSave();
/*  683 */     for (i = 0; i < expOnes.length; i++) {
/*  684 */       BeanPropertyAssocOne<?> prop = expOnes[i];
/*      */ 
/*      */       
/*  687 */       if (request.isLoadedProperty(prop)) {
/*  688 */         Object detailBean = prop.getValue(parentBean);
/*  689 */         if (detailBean != null && 
/*  690 */           !prop.isSaveRecurseSkippable(detailBean)) {
/*      */ 
/*      */           
/*  693 */           t.depth(1);
/*  694 */           saveRecurse(detailBean, t, parentBean);
/*  695 */           t.depth(-1);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  702 */     BeanPropertyAssocMany[] manys = desc.propertiesManySave();
/*  703 */     for (int i = 0; i < manys.length; i++) {
/*  704 */       saveMany(new SaveManyPropRequest(insertedParent, manys[i], parentBean, request, null));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SaveManyPropRequest
/*      */   {
/*      */     private final boolean insertedParent;
/*      */     
/*      */     private final BeanPropertyAssocMany<?> many;
/*      */     
/*      */     private final Object parentBean;
/*      */     private final SpiTransaction t;
/*      */     private final boolean cascade;
/*      */     private final boolean statelessUpdate;
/*      */     private final boolean deleteMissingChildren;
/*      */     private final boolean updateNullProperties;
/*      */     
/*      */     private SaveManyPropRequest(boolean insertedParent, BeanPropertyAssocMany<?> many, Object parentBean, PersistRequestBean<?> request) {
/*  723 */       this.insertedParent = insertedParent;
/*  724 */       this.many = many;
/*  725 */       this.cascade = many.getCascadeInfo().isSave();
/*  726 */       this.parentBean = parentBean;
/*  727 */       this.t = request.getTransaction();
/*  728 */       this.statelessUpdate = request.isStatelessUpdate();
/*  729 */       this.deleteMissingChildren = request.isDeleteMissingChildren();
/*  730 */       this.updateNullProperties = request.isUpdateNullProperties();
/*      */     }
/*      */     
/*      */     private SaveManyPropRequest(BeanPropertyAssocMany<?> many, Object parentBean, SpiTransaction t) {
/*  734 */       this.insertedParent = false;
/*  735 */       this.many = many;
/*  736 */       this.parentBean = parentBean;
/*  737 */       this.t = t;
/*  738 */       this.cascade = true;
/*  739 */       this.statelessUpdate = false;
/*  740 */       this.deleteMissingChildren = false;
/*  741 */       this.updateNullProperties = false;
/*      */     }
/*      */ 
/*      */     
/*  745 */     private Object getValueUnderlying() { return this.many.getValueUnderlying(this.parentBean); }
/*      */ 
/*      */ 
/*      */     
/*  749 */     private boolean isModifyListenMode() { return BeanCollection.ModifyListenMode.REMOVALS.equals(this.many.getModifyListenMode()); }
/*      */ 
/*      */ 
/*      */     
/*  753 */     private boolean isStatelessUpdate() { return this.statelessUpdate; }
/*      */ 
/*      */ 
/*      */     
/*  757 */     private boolean isDeleteMissingChildren() { return this.deleteMissingChildren; }
/*      */ 
/*      */ 
/*      */     
/*  761 */     private boolean isUpdateNullProperties() { return this.updateNullProperties; }
/*      */ 
/*      */ 
/*      */     
/*  765 */     private boolean isInsertedParent() { return this.insertedParent; }
/*      */ 
/*      */ 
/*      */     
/*  769 */     private BeanPropertyAssocMany<?> getMany() { return this.many; }
/*      */ 
/*      */ 
/*      */     
/*  773 */     private Object getParentBean() { return this.parentBean; }
/*      */ 
/*      */ 
/*      */     
/*  777 */     private SpiTransaction getTransaction() { return this.t; }
/*      */ 
/*      */ 
/*      */     
/*  781 */     private boolean isCascade() { return this.cascade; }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void saveMany(SaveManyPropRequest saveMany) {
/*  787 */     if (saveMany.getMany().isManyToMany()) {
/*      */       
/*  789 */       if (saveMany.isCascade()) {
/*      */         
/*  791 */         saveAssocManyDetails(saveMany, false, saveMany.isUpdateNullProperties());
/*      */ 
/*      */         
/*  794 */         saveAssocManyIntersection(saveMany, saveMany.isDeleteMissingChildren());
/*      */       } 
/*      */     } else {
/*      */       
/*  798 */       if (saveMany.isCascade()) {
/*  799 */         saveAssocManyDetails(saveMany, saveMany.isDeleteMissingChildren(), saveMany.isUpdateNullProperties());
/*      */       }
/*  801 */       if (saveMany.isModifyListenMode()) {
/*  802 */         removeAssocManyPrivateOwned(saveMany);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void removeAssocManyPrivateOwned(SaveManyPropRequest saveMany) {
/*  809 */     Object details = saveMany.getValueUnderlying();
/*      */ 
/*      */ 
/*      */     
/*  813 */     if (details instanceof BeanCollection) {
/*      */       
/*  815 */       BeanCollection<?> c = (BeanCollection)details;
/*  816 */       Set<?> modifyRemovals = c.getModifyRemovals();
/*  817 */       if (modifyRemovals != null && !modifyRemovals.isEmpty()) {
/*      */         
/*  819 */         SpiTransaction t = saveMany.getTransaction();
/*      */         
/*  821 */         t.depth(1);
/*  822 */         for (Object removedBean : modifyRemovals) {
/*  823 */           deleteRecurse(removedBean, t);
/*      */         }
/*  825 */         t.depth(-1);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void saveAssocManyDetails(SaveManyPropRequest saveMany, boolean deleteMissingChildren, boolean updateNullProperties) {
/*  835 */     BeanPropertyAssocMany<?> prop = saveMany.getMany();
/*      */     
/*  837 */     Object details = saveMany.getValueUnderlying();
/*      */ 
/*      */ 
/*      */     
/*  841 */     Collection<?> collection = getDetailsIterator(details);
/*      */     
/*  843 */     if (collection == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  848 */     if (saveMany.isInsertedParent())
/*      */     {
/*  850 */       prop.getTargetDescriptor().preAllocateIds(collection.size());
/*      */     }
/*      */     
/*  853 */     BeanDescriptor<?> targetDescriptor = prop.getTargetDescriptor();
/*  854 */     ArrayList<Object> detailIds = null;
/*  855 */     if (deleteMissingChildren)
/*      */     {
/*  857 */       detailIds = new ArrayList<Object>();
/*      */     }
/*      */ 
/*      */     
/*  861 */     SpiTransaction t = saveMany.getTransaction();
/*  862 */     t.depth(1);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  867 */     boolean isMap = ManyType.JAVA_MAP.equals(prop.getManyType());
/*  868 */     Object parentBean = saveMany.getParentBean();
/*  869 */     Object mapKeyValue = null;
/*      */     
/*  871 */     boolean saveSkippable = prop.isSaveRecurseSkippable();
/*  872 */     boolean skipSavingThisBean = false;
/*      */     
/*  874 */     for (Object detailBean : collection) {
/*  875 */       if (isMap) {
/*      */         
/*  877 */         Map.Entry<?, ?> entry = (Map.Entry)detailBean;
/*  878 */         mapKeyValue = entry.getKey();
/*  879 */         detailBean = entry.getValue();
/*      */       } 
/*      */       
/*  882 */       if (prop.isManyToMany()) {
/*  883 */         if (detailBean instanceof EntityBean) {
/*  884 */           skipSavingThisBean = ((EntityBean)detailBean)._ebean_getIntercept().isReference();
/*      */         
/*      */         }
/*      */       
/*      */       }
/*  889 */       else if (detailBean instanceof EntityBean) {
/*  890 */         EntityBeanIntercept ebi = ((EntityBean)detailBean)._ebean_getIntercept();
/*  891 */         if (ebi.isNewOrDirty()) {
/*      */           
/*  893 */           prop.setJoinValuesToChild(parentBean, detailBean, mapKeyValue);
/*  894 */         } else if (ebi.isReference()) {
/*      */           
/*  896 */           skipSavingThisBean = true;
/*      */         }
/*      */         else {
/*      */           
/*  900 */           skipSavingThisBean = saveSkippable;
/*      */         } 
/*      */       } else {
/*      */         
/*  904 */         prop.setJoinValuesToChild(parentBean, detailBean, mapKeyValue);
/*      */       } 
/*      */ 
/*      */       
/*  908 */       if (skipSavingThisBean) {
/*      */ 
/*      */ 
/*      */         
/*  912 */         skipSavingThisBean = false;
/*      */       }
/*  914 */       else if (!saveMany.isStatelessUpdate()) {
/*      */         
/*  916 */         saveRecurse(detailBean, t, parentBean);
/*      */       
/*      */       }
/*  919 */       else if (targetDescriptor.isStatelessUpdate(detailBean)) {
/*      */ 
/*      */         
/*  922 */         forceUpdate(detailBean, null, t, deleteMissingChildren, updateNullProperties);
/*      */       } else {
/*      */         
/*  925 */         forceInsert(detailBean, t);
/*      */       } 
/*      */ 
/*      */       
/*  929 */       if (detailIds != null) {
/*      */         
/*  931 */         Object id = targetDescriptor.getId(detailBean);
/*  932 */         if (!DmlUtil.isNullOrZero(id)) {
/*  933 */           detailIds.add(id);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  938 */     if (detailIds != null) {
/*  939 */       deleteManyDetails(t, prop.getBeanDescriptor(), parentBean, prop, detailIds);
/*      */     }
/*      */     
/*  942 */     t.depth(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int deleteManyToManyAssociations(Object ownerBean, String propertyName, Transaction t) {
/*  948 */     BeanDescriptor<?> descriptor = this.beanDescriptorManager.getBeanDescriptor(ownerBean.getClass());
/*  949 */     BeanPropertyAssocMany<?> prop = (BeanPropertyAssocMany)descriptor.getBeanProperty(propertyName);
/*  950 */     return deleteAssocManyIntersection(ownerBean, prop, t);
/*      */   }
/*      */ 
/*      */   
/*      */   public void saveManyToManyAssociations(Object ownerBean, String propertyName, Transaction t) {
/*  955 */     BeanDescriptor<?> descriptor = this.beanDescriptorManager.getBeanDescriptor(ownerBean.getClass());
/*  956 */     BeanPropertyAssocMany<?> prop = (BeanPropertyAssocMany)descriptor.getBeanProperty(propertyName);
/*      */     
/*  958 */     saveAssocManyIntersection(new SaveManyPropRequest(prop, ownerBean, (SpiTransaction)t, null), false);
/*      */   }
/*      */ 
/*      */   
/*      */   public void saveAssociation(Object parentBean, String propertyName, Transaction t) {
/*  963 */     BeanDescriptor<?> descriptor = this.beanDescriptorManager.getBeanDescriptor(parentBean.getClass());
/*  964 */     SpiTransaction trans = (SpiTransaction)t;
/*      */     
/*  966 */     BeanProperty prop = descriptor.getBeanProperty(propertyName);
/*  967 */     if (prop == null) {
/*  968 */       String msg = "Could not find property [" + propertyName + "] on bean " + parentBean.getClass();
/*  969 */       throw new PersistenceException(msg);
/*      */     } 
/*      */     
/*  972 */     if (prop instanceof BeanPropertyAssocMany) {
/*  973 */       BeanPropertyAssocMany<?> manyProp = (BeanPropertyAssocMany)prop;
/*  974 */       saveMany(new SaveManyPropRequest(manyProp, parentBean, (SpiTransaction)t, null));
/*      */     }
/*  976 */     else if (prop instanceof BeanPropertyAssocOne) {
/*  977 */       BeanPropertyAssocOne<?> oneProp = (BeanPropertyAssocOne)prop;
/*  978 */       Object assocBean = oneProp.getValue(parentBean);
/*      */       
/*  980 */       int depth = oneProp.isOneToOneExported() ? 1 : -1;
/*  981 */       int revertDepth = -1 * depth;
/*      */       
/*  983 */       trans.depth(depth);
/*  984 */       saveRecurse(assocBean, t, parentBean);
/*  985 */       trans.depth(revertDepth);
/*      */     } else {
/*      */       
/*  988 */       String msg = "Expecting [" + prop.getFullBeanName() + "] to be a OneToMany, OneToOne, ManyToOne or ManyToMany property?";
/*  989 */       throw new PersistenceException(msg);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void saveAssocManyIntersection(SaveManyPropRequest saveManyPropRequest, boolean deleteMissingChildren) {
/* 1003 */     BeanPropertyAssocMany<?> prop = saveManyPropRequest.getMany();
/* 1004 */     Object value = prop.getValueUnderlying(saveManyPropRequest.getParentBean());
/* 1005 */     if (value == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1009 */     SpiTransaction t = saveManyPropRequest.getTransaction();
/* 1010 */     Collection<?> additions = null;
/* 1011 */     Collection<?> deletions = null;
/*      */     
/* 1013 */     boolean vanillaCollection = !(value instanceof BeanCollection);
/*      */     
/* 1015 */     if (deleteMissingChildren)
/*      */     {
/*      */       
/* 1018 */       deleteAssocManyIntersection(saveManyPropRequest.getParentBean(), prop, t);
/*      */     }
/*      */     
/* 1021 */     if (saveManyPropRequest.isInsertedParent() || vanillaCollection || deleteMissingChildren) {
/*      */       
/* 1023 */       if (value instanceof Map) {
/* 1024 */         additions = ((Map)value).values();
/* 1025 */       } else if (value instanceof Collection) {
/* 1026 */         additions = (Collection)value;
/*      */       } else {
/* 1028 */         String msg = "Unhandled ManyToMany type " + value.getClass().getName() + " for " + prop.getFullBeanName();
/* 1029 */         throw new PersistenceException(msg);
/*      */       } 
/* 1031 */       if (!vanillaCollection) {
/* 1032 */         ((BeanCollection)value).modifyReset();
/*      */       }
/*      */     } else {
/*      */       
/* 1036 */       BeanCollection<?> manyValue = (BeanCollection)value;
/* 1037 */       additions = manyValue.getModifyAdditions();
/* 1038 */       deletions = manyValue.getModifyRemovals();
/*      */       
/* 1040 */       manyValue.modifyReset();
/*      */     } 
/*      */     
/* 1043 */     t.depth(1);
/*      */     
/* 1045 */     if (additions != null && !additions.isEmpty()) {
/* 1046 */       for (Object otherBean : additions) {
/*      */         
/* 1048 */         if (deletions != null && deletions.remove(otherBean)) {
/* 1049 */           String m = "Inserting and Deleting same object? " + otherBean;
/* 1050 */           if (t.isLogSummary()) {
/* 1051 */             t.logInternal(m);
/*      */           }
/* 1053 */           logger.log(Level.WARNING, m);
/*      */           continue;
/*      */         } 
/* 1056 */         if (!prop.hasImportedId(otherBean)) {
/* 1057 */           String msg = "ManyToMany bean " + otherBean + " does not have an Id value.";
/* 1058 */           throw new PersistenceException(msg);
/*      */         } 
/*      */ 
/*      */         
/* 1062 */         IntersectionRow intRow = prop.buildManyToManyMapBean(saveManyPropRequest.getParentBean(), otherBean);
/* 1063 */         SqlUpdate sqlInsert = intRow.createInsert(this.server);
/* 1064 */         executeSqlUpdate(sqlInsert, t);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1069 */     if (deletions != null && !deletions.isEmpty()) {
/* 1070 */       for (Object otherDelete : deletions) {
/*      */ 
/*      */         
/* 1073 */         IntersectionRow intRow = prop.buildManyToManyMapBean(saveManyPropRequest.getParentBean(), otherDelete);
/* 1074 */         SqlUpdate sqlDelete = intRow.createDelete(this.server);
/* 1075 */         executeSqlUpdate(sqlDelete, t);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1080 */     t.depth(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int deleteAssocManyIntersection(Object bean, BeanPropertyAssocMany<?> many, Transaction t) {
/* 1086 */     IntersectionRow intRow = many.buildManyToManyDeleteChildren(bean);
/* 1087 */     SqlUpdate sqlDelete = intRow.createDeleteChildren(this.server);
/*      */     
/* 1089 */     return executeSqlUpdate(sqlDelete, t);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void deleteAssocMany(PersistRequestBean<?> request) {
/* 1100 */     SpiTransaction t = request.getTransaction();
/* 1101 */     t.depth(-1);
/*      */     
/* 1103 */     BeanDescriptor<?> desc = request.getBeanDescriptor();
/* 1104 */     Object parentBean = request.getBean();
/*      */     
/* 1106 */     BeanPropertyAssocOne[] expOnes = desc.propertiesOneExportedDelete();
/* 1107 */     if (expOnes.length > 0) {
/*      */       
/* 1109 */       DeleteUnloadedForeignKeys unloaded = null;
/* 1110 */       for (int i = 0; i < expOnes.length; i++) {
/* 1111 */         BeanPropertyAssocOne<?> prop = expOnes[i];
/* 1112 */         if (request.isLoadedProperty(prop)) {
/* 1113 */           Object detailBean = prop.getValue(parentBean);
/* 1114 */           if (detailBean != null) {
/* 1115 */             deleteRecurse(detailBean, t);
/*      */           }
/*      */         } else {
/* 1118 */           if (unloaded == null) {
/* 1119 */             unloaded = new DeleteUnloadedForeignKeys(this.server, request);
/*      */           }
/* 1121 */           unloaded.add(prop);
/*      */         } 
/*      */       } 
/* 1124 */       if (unloaded != null) {
/* 1125 */         unloaded.queryForeignKeys();
/* 1126 */         unloaded.deleteCascade();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1131 */     BeanPropertyAssocMany[] manys = desc.propertiesManyDelete();
/* 1132 */     for (int i = 0; i < manys.length; i++) {
/* 1133 */       if (manys[i].isManyToMany()) {
/*      */         
/* 1135 */         deleteAssocManyIntersection(parentBean, manys[i], t);
/*      */       }
/*      */       else {
/*      */         
/* 1139 */         if (BeanCollection.ModifyListenMode.REMOVALS.equals(manys[i].getModifyListenMode())) {
/*      */           
/* 1141 */           Object details = manys[i].getValueUnderlying(parentBean);
/* 1142 */           if (details instanceof BeanCollection) {
/* 1143 */             Set<?> modifyRemovals = ((BeanCollection)details).getModifyRemovals();
/* 1144 */             if (modifyRemovals != null && !modifyRemovals.isEmpty())
/*      */             {
/*      */               
/* 1147 */               for (Object detailBean : modifyRemovals) {
/* 1148 */                 if (manys[i].hasId(detailBean)) {
/* 1149 */                   deleteRecurse(detailBean, t);
/*      */                 }
/*      */               } 
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/* 1156 */         deleteManyDetails(t, desc, parentBean, manys[i], null);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1161 */     t.depth(1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void deleteManyDetails(SpiTransaction t, BeanDescriptor<?> desc, Object parentBean, BeanPropertyAssocMany<?> many, ArrayList<Object> excludeDetailIds) {
/* 1175 */     if (many.getCascadeInfo().isDelete()) {
/*      */       
/* 1177 */       BeanDescriptor<?> targetDesc = many.getTargetDescriptor();
/* 1178 */       if (targetDesc.isDeleteRecurseSkippable() && !targetDesc.isUsingL2Cache()) {
/*      */         
/* 1180 */         IntersectionRow intRow = many.buildManyDeleteChildren(parentBean, excludeDetailIds);
/* 1181 */         SqlUpdate sqlDelete = intRow.createDelete(this.server);
/* 1182 */         executeSqlUpdate(sqlDelete, t);
/*      */       }
/*      */       else {
/*      */         
/* 1186 */         Object parentId = desc.getId(parentBean);
/* 1187 */         List<Object> idsByParentId = many.findIdsByParentId(parentId, null, t, excludeDetailIds);
/* 1188 */         if (!idsByParentId.isEmpty()) {
/* 1189 */           delete(targetDesc, null, idsByParentId, t);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void saveAssocOne(PersistRequestBean<?> request) {
/* 1200 */     BeanDescriptor<?> desc = request.getBeanDescriptor();
/*      */ 
/*      */     
/* 1203 */     BeanPropertyAssocOne[] ones = desc.propertiesOneImportedSave();
/*      */     
/* 1205 */     for (int i = 0; i < ones.length; i++) {
/* 1206 */       BeanPropertyAssocOne<?> prop = ones[i];
/*      */ 
/*      */       
/* 1209 */       if (request.isLoadedProperty(prop)) {
/* 1210 */         Object detailBean = prop.getValue(request.getBean());
/* 1211 */         if (detailBean != null && 
/* 1212 */           !isReference(detailBean))
/*      */         {
/* 1214 */           if (!request.isParent(detailBean))
/*      */           {
/* 1216 */             if (!prop.isSaveRecurseSkippable(detailBean)) {
/*      */ 
/*      */ 
/*      */               
/* 1220 */               SpiTransaction t = request.getTransaction();
/* 1221 */               t.depth(-1);
/* 1222 */               saveRecurse(detailBean, t, null);
/* 1223 */               t.depth(1);
/*      */             } 
/*      */           }
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1234 */   private boolean isReference(Object bean) { return (bean instanceof EntityBean && ((EntityBean)bean)._ebean_getIntercept().isReference()); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DeleteUnloadedForeignKeys getDeleteUnloadedForeignKeys(PersistRequestBean<?> request) {
/* 1243 */     DeleteUnloadedForeignKeys fkeys = null;
/*      */     
/* 1245 */     BeanPropertyAssocOne[] ones = request.getBeanDescriptor().propertiesOneImportedDelete();
/* 1246 */     for (int i = 0; i < ones.length; i++) {
/* 1247 */       if (!request.isLoadedProperty(ones[i])) {
/*      */ 
/*      */         
/* 1250 */         if (fkeys == null) {
/* 1251 */           fkeys = new DeleteUnloadedForeignKeys(this.server, request);
/*      */         }
/* 1253 */         fkeys.add(ones[i]);
/*      */       } 
/*      */     } 
/*      */     
/* 1257 */     return fkeys;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void deleteAssocOne(PersistRequestBean<?> request) {
/* 1265 */     BeanDescriptor<?> desc = request.getBeanDescriptor();
/* 1266 */     BeanPropertyAssocOne[] ones = desc.propertiesOneImportedDelete();
/*      */     
/* 1268 */     for (int i = 0; i < ones.length; i++) {
/* 1269 */       BeanPropertyAssocOne<?> prop = ones[i];
/* 1270 */       if (request.isLoadedProperty(prop)) {
/*      */ 
/*      */ 
/*      */         
/* 1274 */         Object detailBean = prop.getValue(request.getBean());
/* 1275 */         if (detailBean != null && prop.hasId(detailBean)) {
/* 1276 */           deleteRecurse(detailBean, request.getTransaction());
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setIdGenValue(PersistRequestBean<?> request) {
/* 1287 */     BeanDescriptor<?> desc = request.getBeanDescriptor();
/* 1288 */     if (!desc.isUseIdGenerator()) {
/*      */       return;
/*      */     }
/*      */     
/* 1292 */     BeanProperty idProp = desc.getSingleIdProperty();
/* 1293 */     if (idProp == null || idProp.isEmbedded()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1298 */     Object bean = request.getBean();
/* 1299 */     Object uid = idProp.getValue(bean);
/*      */     
/* 1301 */     if (DmlUtil.isNullOrZero(uid)) {
/*      */ 
/*      */       
/* 1304 */       Object nextId = desc.nextId(request.getTransaction());
/*      */ 
/*      */       
/* 1307 */       desc.convertSetId(nextId, bean);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Collection<?> getDetailsIterator(Object o) {
/* 1316 */     if (o == null) {
/* 1317 */       return null;
/*      */     }
/* 1319 */     if (o instanceof BeanCollection) {
/* 1320 */       BeanCollection<?> bc = (BeanCollection)o;
/* 1321 */       if (!bc.isPopulated()) {
/* 1322 */         return null;
/*      */       }
/* 1324 */       return bc.getActualDetails();
/*      */     } 
/*      */     
/* 1327 */     if (o instanceof Map)
/*      */     {
/* 1329 */       return ((Map)o).entrySet();
/*      */     }
/* 1331 */     if (o instanceof Collection) {
/* 1332 */       return (Collection)o;
/*      */     }
/* 1334 */     String m = "expecting a Map or Collection but got [" + o.getClass().getName() + "]";
/* 1335 */     throw new PersistenceException(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private <T> PersistRequestBean<T> createRequest(T bean, Transaction t, Object parentBean) {
/* 1344 */     BeanManager<T> mgr = getBeanManager(bean);
/* 1345 */     if (mgr == null) {
/* 1346 */       throw new PersistenceException(errNotRegistered(bean.getClass()));
/*      */     }
/* 1348 */     return createRequest(bean, t, parentBean, mgr);
/*      */   }
/*      */   
/*      */   private String errNotRegistered(Class<?> beanClass) {
/* 1352 */     msg = "The type [" + beanClass + "] is not a registered entity?";
/* 1353 */     msg = msg + " If you don't explicitly list the entity classes to use Ebean will search for them in the classpath.";
/* 1354 */     return msg + " If the entity is in a Jar check the ebean.search.jars property in ebean.properties file or check ServerConfig.addJar().";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PersistRequestBean<?> createRequest(Object bean, Transaction t, Object parentBean, BeanManager<?> mgr) {
/* 1365 */     if (mgr.isLdapEntityType()) {
/* 1366 */       return new LdapPersistBeanRequest(this.server, bean, parentBean, mgr, this.ldapPersister);
/*      */     }
/* 1368 */     return new PersistRequestBean(this.server, bean, parentBean, mgr, (SpiTransaction)t, this.persistExecute);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1381 */   private <T> BeanManager<T> getBeanManager(T bean) { return this.beanDescriptorManager.getBeanManager(bean.getClass()); }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\DefaultPersister.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */