/*    */ package com.avaje.ebean.bean;public interface BeanCollection<E> extends Serializable { public static final int DEFAULT = 0; public static final int READONLY = 2; public static final int SHARED = 3;
/*    */   Object getOwnerBean();
/*    */   
/*    */   String getPropertyName();
/*    */   
/*    */   int getLoaderIndex();
/*    */   
/*    */   boolean checkEmptyLazyLoad();
/*    */   
/*    */   ExpressionList<?> getFilterMany();
/*    */   
/*    */   void setFilterMany(ExpressionList<?> paramExpressionList);
/*    */   
/*    */   void setBackgroundFetch(Future<Integer> paramFuture);
/*    */   
/*    */   void backgroundFetchWait(long paramLong, TimeUnit paramTimeUnit);
/*    */   
/*    */   void backgroundFetchWait();
/*    */   
/*    */   boolean isSharedInstance();
/*    */   
/*    */   void setSharedInstance();
/*    */   
/*    */   void setBeanCollectionTouched(BeanCollectionTouched paramBeanCollectionTouched);
/*    */   
/*    */   void setLoader(int paramInt, BeanCollectionLoader paramBeanCollectionLoader);
/*    */   
/*    */   void setReadOnly(boolean paramBoolean);
/*    */   
/*    */   boolean isReadOnly();
/*    */   
/*    */   void internalAdd(Object paramObject);
/*    */   
/*    */   Object getActualCollection();
/*    */   
/*    */   int size();
/*    */   
/*    */   boolean isEmpty();
/*    */   
/*    */   Collection<E> getActualDetails();
/*    */   
/*    */   boolean hasMoreRows();
/*    */   
/*    */   void setHasMoreRows(boolean paramBoolean);
/*    */   
/*    */   boolean isFinishedFetch();
/*    */   
/*    */   void setFinishedFetch(boolean paramBoolean);
/*    */   
/*    */   boolean isPopulated();
/*    */   
/*    */   boolean isReference();
/*    */   
/*    */   void setModifyListening(ModifyListenMode paramModifyListenMode);
/*    */   
/*    */   void modifyAddition(E paramE);
/*    */   
/*    */   void modifyRemoval(Object paramObject);
/*    */   
/*    */   Set<E> getModifyAdditions();
/*    */   
/*    */   Set<E> getModifyRemovals();
/*    */   
/*    */   void modifyReset();
/*    */   
/* 66 */   public enum ModifyListenMode { NONE,
/*    */     
/* 68 */     REMOVALS,
/*    */     
/* 70 */     ALL; }
/*    */    }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\bean\BeanCollection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */