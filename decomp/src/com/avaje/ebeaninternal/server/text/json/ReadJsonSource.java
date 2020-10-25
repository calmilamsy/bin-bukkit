package com.avaje.ebeaninternal.server.text.json;

public interface ReadJsonSource {
  char nextChar(String paramString);
  
  void ignoreWhiteSpace();
  
  void back();
  
  int pos();
  
  String getErrorHelp();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\text\json\ReadJsonSource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */