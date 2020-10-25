package com.avaje.ebeaninternal.server.lucene;

import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import java.util.Set;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;

public interface LIndexField {
  String getName();
  
  DocFieldWriter createDocFieldWriter();
  
  void addIndexResolvePropertyNames(Set<String> paramSet);
  
  void addIndexRestorePropertyNames(Set<String> paramSet);
  
  void addIndexRequiredPropertyNames(Set<String> paramSet);
  
  String getSortableProperty();
  
  int getSortType();
  
  boolean isIndexed();
  
  boolean isStored();
  
  boolean isBeanProperty();
  
  ElPropertyValue getElBeanProperty();
  
  void readValue(Document paramDocument, Object paramObject);
  
  QueryParser createQueryParser();
  
  int getPropertyOrder();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\LIndexField.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */