package javax.persistence;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JoinColumn {
  String name() default "";
  
  String referencedColumnName() default "";
  
  boolean unique() default false;
  
  boolean nullable() default true;
  
  boolean insertable() default true;
  
  boolean updatable() default true;
  
  String columnDefinition() default "";
  
  String table() default "";
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\javax\persistence\JoinColumn.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */