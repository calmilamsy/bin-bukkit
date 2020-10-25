package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Iterator;

@GwtCompatible
public interface PeekingIterator<E> extends Iterator<E> {
  E peek();
  
  E next();
  
  void remove();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\PeekingIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */