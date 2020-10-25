package joptsimple;

import java.util.Collection;
import java.util.List;

public interface OptionSpec<V> {
  List<V> values(OptionSet paramOptionSet);
  
  V value(OptionSet paramOptionSet);
  
  Collection<String> options();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\joptsimple\OptionSpec.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */