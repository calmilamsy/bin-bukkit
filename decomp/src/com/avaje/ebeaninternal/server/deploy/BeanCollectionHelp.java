package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.InvalidValue;
import com.avaje.ebean.Query;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.bean.BeanCollectionAdd;
import com.avaje.ebean.bean.BeanCollectionLoader;
import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
import java.util.ArrayList;
import java.util.Iterator;

public interface BeanCollectionHelp<T> {
  Object copyCollection(Object paramObject1, CopyContext paramCopyContext, int paramInt, Object paramObject2);
  
  void setLoader(BeanCollectionLoader paramBeanCollectionLoader);
  
  BeanCollectionAdd getBeanCollectionAdd(Object paramObject, String paramString);
  
  Object createEmpty(boolean paramBoolean);
  
  Iterator<?> getIterator(Object paramObject);
  
  void add(BeanCollection<?> paramBeanCollection, Object paramObject);
  
  BeanCollection<T> createReference(Object paramObject, String paramString);
  
  ArrayList<InvalidValue> validate(Object paramObject);
  
  void refresh(EbeanServer paramEbeanServer, Query<?> paramQuery, Transaction paramTransaction, Object paramObject);
  
  void refresh(BeanCollection<?> paramBeanCollection, Object paramObject);
  
  void jsonWrite(WriteJsonContext paramWriteJsonContext, String paramString, Object paramObject, boolean paramBoolean);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\BeanCollectionHelp.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */