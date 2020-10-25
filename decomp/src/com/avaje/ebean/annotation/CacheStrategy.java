package com.avaje.ebean.annotation;

import com.avaje.ebean.Query;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheStrategy {
  boolean useBeanCache() default true;
  
  boolean readOnly() default false;
  
  String warmingQuery() default "";
  
  Query.UseIndex useIndex() default Query.UseIndex.DEFAULT;
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\annotation\CacheStrategy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */