package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;

@Beta
@GwtCompatible
public interface Constraint<E> {
  E checkElement(E paramE);
  
  String toString();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\Constraint.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */