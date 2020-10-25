package com.avaje.ebeaninternal.api;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Query;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.TxScope;
import com.avaje.ebean.bean.BeanCollectionLoader;
import com.avaje.ebean.bean.BeanLoader;
import com.avaje.ebean.bean.CallStack;
import com.avaje.ebean.config.dbplatform.DatabasePlatform;
import com.avaje.ebeaninternal.server.autofetch.AutoFetchManager;
import com.avaje.ebeaninternal.server.core.PstmtBatch;
import com.avaje.ebeaninternal.server.core.SpiOrmQueryRequest;
import com.avaje.ebeaninternal.server.ddl.DdlGenerator;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.lucene.LuceneIndexManager;
import com.avaje.ebeaninternal.server.query.CQuery;
import com.avaje.ebeaninternal.server.query.CQueryEngine;
import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
import java.lang.reflect.Type;
import java.util.List;

public interface SpiEbeanServer extends EbeanServer, BeanLoader, BeanCollectionLoader {
  boolean isDefaultDeleteMissingChildren();
  
  boolean isDefaultUpdateNullProperties();
  
  boolean isVanillaMode();
  
  DatabasePlatform getDatabasePlatform();
  
  PstmtBatch getPstmtBatch();
  
  CallStack createCallStack();
  
  DdlGenerator getDdlGenerator();
  
  AutoFetchManager getAutoFetchManager();
  
  LuceneIndexManager getLuceneIndexManager();
  
  void clearQueryStatistics();
  
  List<BeanDescriptor<?>> getBeanDescriptors();
  
  <T> BeanDescriptor<T> getBeanDescriptor(Class<T> paramClass);
  
  BeanDescriptor<?> getBeanDescriptorById(String paramString);
  
  List<BeanDescriptor<?>> getBeanDescriptors(String paramString);
  
  void externalModification(TransactionEventTable paramTransactionEventTable);
  
  SpiTransaction createServerTransaction(boolean paramBoolean, int paramInt);
  
  SpiTransaction getCurrentServerTransaction();
  
  ScopeTrans createScopeTrans(TxScope paramTxScope);
  
  SpiTransaction createQueryTransaction();
  
  void remoteTransactionEvent(RemoteTransactionEvent paramRemoteTransactionEvent);
  
  <T> SpiOrmQueryRequest<T> createQueryRequest(Query.Type paramType, Query<T> paramQuery, Transaction paramTransaction);
  
  <T> CQuery<T> compileQuery(Query<T> paramQuery, Transaction paramTransaction);
  
  CQueryEngine getQueryEngine();
  
  <T> List<Object> findIdsWithCopy(Query<T> paramQuery, Transaction paramTransaction);
  
  <T> int findRowCountWithCopy(Query<T> paramQuery, Transaction paramTransaction);
  
  void loadBean(LoadBeanRequest paramLoadBeanRequest);
  
  void loadMany(LoadManyRequest paramLoadManyRequest);
  
  int getLazyLoadBatchSize();
  
  boolean isSupportedType(Type paramType);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\api\SpiEbeanServer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */