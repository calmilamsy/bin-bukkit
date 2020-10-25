package joptsimple;

interface OptionSpecVisitor {
  void visit(NoArgumentOptionSpec paramNoArgumentOptionSpec);
  
  void visit(RequiredArgumentOptionSpec<?> paramRequiredArgumentOptionSpec);
  
  void visit(OptionalArgumentOptionSpec<?> paramOptionalArgumentOptionSpec);
  
  void visit(AlternativeLongOptionSpec paramAlternativeLongOptionSpec);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\joptsimple\OptionSpecVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */