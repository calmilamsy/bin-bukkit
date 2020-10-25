package com.avaje.ebeaninternal.server.lucene;

import com.avaje.ebean.config.lucene.IndexDefnBuilder;
import com.avaje.ebean.config.lucene.IndexFieldDefn;
import org.apache.lucene.document.Field;

public interface SpiIndexDefnHelper extends IndexDefnBuilder {
  IndexFieldDefn addPrefixField(String paramString1, String paramString2, Field.Store paramStore, Field.Index paramIndex, IndexFieldDefn.Sortable paramSortable);
  
  IndexFieldDefn addPrefixFieldConcat(String paramString1, String paramString2, Field.Store paramStore, Field.Index paramIndex, String[] paramArrayOfString);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\SpiIndexDefnHelper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */