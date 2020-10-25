package javax.persistence;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
  String name() default "";
  
  String catalog() default "";
  
  String schema() default "";
  
  UniqueConstraint[] uniqueConstraints() default {};
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\javax\persistence\Table.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */