package com.avaje.ebean.config.lucene;

import java.util.List;
import org.apache.lucene.document.Field;

public interface IndexDefnBuilder {
  void addAllFields();
  
  IndexDefnBuilder assocOne(String paramString);
  
  IndexFieldDefn addField(IndexFieldDefn paramIndexFieldDefn);
  
  IndexFieldDefn addField(String paramString);
  
  IndexFieldDefn addField(String paramString, IndexFieldDefn.Sortable paramSortable);
  
  IndexFieldDefn addField(String paramString, Field.Store paramStore, Field.Index paramIndex, IndexFieldDefn.Sortable paramSortable);
  
  IndexFieldDefn addFieldConcat(String paramString, String... paramVarArgs);
  
  IndexFieldDefn addFieldConcat(String paramString, Field.Store paramStore, Field.Index paramIndex, String... paramVarArgs);
  
  IndexFieldDefn getField(String paramString);
  
  List<IndexFieldDefn> getFields();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\lucene\IndexDefnBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */