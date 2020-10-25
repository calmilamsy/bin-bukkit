package com.avaje.ebean.text.json;

import java.util.Map;

public interface JsonReadBeanVisitor<T> {
  void visit(T paramT, Map<String, JsonElement> paramMap);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\text\json\JsonReadBeanVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */