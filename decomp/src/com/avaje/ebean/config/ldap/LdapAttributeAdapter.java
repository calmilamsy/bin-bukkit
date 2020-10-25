package com.avaje.ebean.config.ldap;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;

public interface LdapAttributeAdapter {
  Object readAttribute(Attribute paramAttribute) throws NamingException;
  
  Attribute createAttribute(Object paramObject);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\ldap\LdapAttributeAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */