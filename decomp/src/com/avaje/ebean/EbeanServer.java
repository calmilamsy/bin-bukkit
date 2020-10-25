package com.avaje.ebean;

import com.avaje.ebean.cache.ServerCacheManager;
import com.avaje.ebean.config.lucene.LuceneIndex;
import com.avaje.ebean.text.csv.CsvReader;
import com.avaje.ebean.text.json.JsonContext;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.OptimisticLockException;

public interface EbeanServer {
  AdminLogging getAdminLogging();
  
  AdminAutofetch getAdminAutofetch();
  
  LuceneIndex getLuceneIndex(Class<?> paramClass);
  
  String getName();
  
  ExpressionFactory getExpressionFactory();
  
  BeanState getBeanState(Object paramObject);
  
  Object getBeanId(Object paramObject);
  
  Map<String, ValuePair> diff(Object paramObject1, Object paramObject2);
  
  InvalidValue validate(Object paramObject);
  
  InvalidValue[] validate(Object paramObject1, String paramString, Object paramObject2);
  
  <T> T createEntityBean(Class<T> paramClass);
  
  ObjectInputStream createProxyObjectInputStream(InputStream paramInputStream);
  
  <T> CsvReader<T> createCsvReader(Class<T> paramClass);
  
  <T> Query<T> createNamedQuery(Class<T> paramClass, String paramString);
  
  <T> Query<T> createQuery(Class<T> paramClass, String paramString);
  
  <T> Query<T> createQuery(Class<T> paramClass);
  
  <T> Query<T> find(Class<T> paramClass);
  
  Object nextId(Class<?> paramClass);
  
  <T> Filter<T> filter(Class<T> paramClass);
  
  <T> void sort(List<T> paramList, String paramString);
  
  <T> Update<T> createNamedUpdate(Class<T> paramClass, String paramString);
  
  <T> Update<T> createUpdate(Class<T> paramClass, String paramString);
  
  SqlQuery createSqlQuery(String paramString);
  
  SqlQuery createNamedSqlQuery(String paramString);
  
  SqlUpdate createSqlUpdate(String paramString);
  
  CallableSql createCallableSql(String paramString);
  
  SqlUpdate createNamedSqlUpdate(String paramString);
  
  Transaction createTransaction();
  
  Transaction createTransaction(TxIsolation paramTxIsolation);
  
  Transaction beginTransaction();
  
  Transaction beginTransaction(TxIsolation paramTxIsolation);
  
  Transaction currentTransaction();
  
  void commitTransaction();
  
  void rollbackTransaction();
  
  void endTransaction();
  
  void logComment(String paramString);
  
  void refresh(Object paramObject);
  
  void refreshMany(Object paramObject, String paramString);
  
  <T> T find(Class<T> paramClass, Object paramObject);
  
  <T> T getReference(Class<T> paramClass, Object paramObject);
  
  <T> int findRowCount(Query<T> paramQuery, Transaction paramTransaction);
  
  <T> List<Object> findIds(Query<T> paramQuery, Transaction paramTransaction);
  
  <T> QueryIterator<T> findIterate(Query<T> paramQuery, Transaction paramTransaction);
  
  <T> void findVisit(Query<T> paramQuery, QueryResultVisitor<T> paramQueryResultVisitor, Transaction paramTransaction);
  
  <T> List<T> findList(Query<T> paramQuery, Transaction paramTransaction);
  
  <T> FutureRowCount<T> findFutureRowCount(Query<T> paramQuery, Transaction paramTransaction);
  
  <T> FutureIds<T> findFutureIds(Query<T> paramQuery, Transaction paramTransaction);
  
  <T> FutureList<T> findFutureList(Query<T> paramQuery, Transaction paramTransaction);
  
  SqlFutureList findFutureList(SqlQuery paramSqlQuery, Transaction paramTransaction);
  
  <T> PagingList<T> findPagingList(Query<T> paramQuery, Transaction paramTransaction, int paramInt);
  
  <T> Set<T> findSet(Query<T> paramQuery, Transaction paramTransaction);
  
  <T> Map<?, T> findMap(Query<T> paramQuery, Transaction paramTransaction);
  
  <T> T findUnique(Query<T> paramQuery, Transaction paramTransaction);
  
  List<SqlRow> findList(SqlQuery paramSqlQuery, Transaction paramTransaction);
  
  Set<SqlRow> findSet(SqlQuery paramSqlQuery, Transaction paramTransaction);
  
  Map<?, SqlRow> findMap(SqlQuery paramSqlQuery, Transaction paramTransaction);
  
  SqlRow findUnique(SqlQuery paramSqlQuery, Transaction paramTransaction);
  
  void save(Object paramObject);
  
  int save(Iterator<?> paramIterator) throws OptimisticLockException;
  
  int save(Collection<?> paramCollection) throws OptimisticLockException;
  
  void delete(Object paramObject);
  
  int delete(Iterator<?> paramIterator) throws OptimisticLockException;
  
  int delete(Collection<?> paramCollection) throws OptimisticLockException;
  
  int delete(Class<?> paramClass, Object paramObject);
  
  int delete(Class<?> paramClass, Object paramObject, Transaction paramTransaction);
  
  void delete(Class<?> paramClass, Collection<?> paramCollection);
  
  void delete(Class<?> paramClass, Collection<?> paramCollection, Transaction paramTransaction);
  
  int execute(SqlUpdate paramSqlUpdate);
  
  int execute(Update<?> paramUpdate);
  
  int execute(Update<?> paramUpdate, Transaction paramTransaction);
  
  int execute(CallableSql paramCallableSql);
  
  void externalModification(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);
  
  <T> T find(Class<T> paramClass, Object paramObject, Transaction paramTransaction);
  
  void save(Object paramObject, Transaction paramTransaction) throws OptimisticLockException;
  
  int save(Iterator<?> paramIterator, Transaction paramTransaction) throws OptimisticLockException;
  
  void update(Object paramObject);
  
  void update(Object paramObject, Transaction paramTransaction) throws OptimisticLockException;
  
  void update(Object paramObject, Set<String> paramSet);
  
  void update(Object paramObject, Set<String> paramSet, Transaction paramTransaction);
  
  void update(Object paramObject, Set<String> paramSet, Transaction paramTransaction, boolean paramBoolean1, boolean paramBoolean2);
  
  void insert(Object paramObject);
  
  void insert(Object paramObject, Transaction paramTransaction) throws OptimisticLockException;
  
  int deleteManyToManyAssociations(Object paramObject, String paramString);
  
  int deleteManyToManyAssociations(Object paramObject, String paramString, Transaction paramTransaction);
  
  void saveManyToManyAssociations(Object paramObject, String paramString);
  
  void saveManyToManyAssociations(Object paramObject, String paramString, Transaction paramTransaction);
  
  void saveAssociation(Object paramObject, String paramString);
  
  void saveAssociation(Object paramObject, String paramString, Transaction paramTransaction);
  
  void delete(Object paramObject, Transaction paramTransaction) throws OptimisticLockException;
  
  int delete(Iterator<?> paramIterator, Transaction paramTransaction) throws OptimisticLockException;
  
  int execute(SqlUpdate paramSqlUpdate, Transaction paramTransaction);
  
  int execute(CallableSql paramCallableSql, Transaction paramTransaction);
  
  void execute(TxScope paramTxScope, TxRunnable paramTxRunnable);
  
  void execute(TxRunnable paramTxRunnable);
  
  <T> T execute(TxScope paramTxScope, TxCallable<T> paramTxCallable);
  
  <T> T execute(TxCallable<T> paramTxCallable);
  
  ServerCacheManager getServerCacheManager();
  
  BackgroundExecutor getBackgroundExecutor();
  
  void runCacheWarming();
  
  void runCacheWarming(Class<?> paramClass);
  
  JsonContext createJsonContext();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\EbeanServer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */