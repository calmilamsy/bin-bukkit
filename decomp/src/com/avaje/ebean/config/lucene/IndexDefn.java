package com.avaje.ebean.config.lucene;

import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;

public interface IndexDefn<T> {
  void initialise(IndexDefnBuilder paramIndexDefnBuilder);
  
  String getDefaultField();
  
  List<IndexFieldDefn> getFields();
  
  boolean isUpdateSinceSupported();
  
  String[] getUpdateSinceProperties();
  
  Analyzer getAnalyzer();
  
  IndexWriter.MaxFieldLength getMaxFieldLength();
  
  int getMaxBufferedDocs();
  
  double getRAMBufferSizeMB();
  
  int getTermIndexInterval();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\lucene\IndexDefn.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */