package javax.persistence;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NamedNativeQuery {
  String name() default "";
  
  String query();
  
  QueryHint[] hints() default {};
  
  Class resultClass() default V.class;
  
  String resultSetMapping() default "";
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\javax\persistence\NamedNativeQuery.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */