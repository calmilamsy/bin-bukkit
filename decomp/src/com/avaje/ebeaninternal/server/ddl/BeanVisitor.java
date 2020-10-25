package com.avaje.ebeaninternal.server.ddl;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;

public interface BeanVisitor {
  void visitBegin();
  
  boolean visitBean(BeanDescriptor<?> paramBeanDescriptor);
  
  PropertyVisitor visitProperty(BeanProperty paramBeanProperty);
  
  void visitBeanEnd(BeanDescriptor<?> paramBeanDescriptor);
  
  void visitEnd();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\ddl\BeanVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */