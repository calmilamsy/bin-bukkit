package javax.persistence;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityResult {
  Class entityClass();
  
  FieldResult[] fields() default {};
  
  String discriminatorColumn() default "";
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\javax\persistence\EntityResult.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */