package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import javax.annotation.Nullable;

@GwtCompatible
public interface Predicate<T> {
  boolean apply(@Nullable T paramT);
  
  boolean equals(@Nullable Object paramObject);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\base\Predicate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */