package com.avaje.ebean;

public interface ExampleExpression extends Expression {
  ExampleExpression includeZeros();
  
  ExampleExpression caseInsensitive();
  
  ExampleExpression useStartsWith();
  
  ExampleExpression useContains();
  
  ExampleExpression useEndsWith();
  
  ExampleExpression useEqualTo();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\ExampleExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */