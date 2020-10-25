package javax.persistence;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public interface Query {
  List getResultList();
  
  Object getSingleResult();
  
  int executeUpdate();
  
  Query setMaxResults(int paramInt);
  
  Query setFirstResult(int paramInt);
  
  Query setHint(String paramString, Object paramObject);
  
  Query setParameter(String paramString, Object paramObject);
  
  Query setParameter(String paramString, Date paramDate, TemporalType paramTemporalType);
  
  Query setParameter(String paramString, Calendar paramCalendar, TemporalType paramTemporalType);
  
  Query setParameter(int paramInt, Object paramObject);
  
  Query setParameter(int paramInt, Date paramDate, TemporalType paramTemporalType);
  
  Query setParameter(int paramInt, Calendar paramCalendar, TemporalType paramTemporalType);
  
  Query setFlushMode(FlushModeType paramFlushModeType);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\javax\persistence\Query.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */