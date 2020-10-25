/*      */ package com.mysql.jdbc;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.lang.reflect.Field;
/*      */ import java.sql.DriverPropertyInfo;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.TreeMap;
/*      */ import javax.naming.RefAddr;
/*      */ import javax.naming.Reference;
/*      */ import javax.naming.StringRefAddr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ConnectionPropertiesImpl
/*      */   implements Serializable, ConnectionProperties
/*      */ {
/*      */   private static final long serialVersionUID = 4257801713007640580L;
/*      */   
/*      */   class BooleanConnectionProperty
/*      */     extends ConnectionProperty
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 2540132501709159404L;
/*      */     
/*   72 */     BooleanConnectionProperty(String propertyNameToSet, boolean defaultValueToSet, String descriptionToSet, String sinceVersionToSet, String category, int orderInCategory) { super(ConnectionPropertiesImpl.this, propertyNameToSet, Boolean.valueOf(defaultValueToSet), null, 0, 0, descriptionToSet, sinceVersionToSet, category, orderInCategory); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   81 */     String[] getAllowableValues() { return new String[] { "true", "false", "yes", "no" }; }
/*      */ 
/*      */ 
/*      */     
/*   85 */     boolean getValueAsBoolean() { return ((Boolean)this.valueAsObject).booleanValue(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   92 */     boolean hasValueConstraints() { return true; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void initializeFrom(String extractedValue) throws SQLException {
/*   99 */       if (extractedValue != null) {
/*  100 */         validateStringValues(extractedValue);
/*      */         
/*  102 */         this.valueAsObject = Boolean.valueOf((extractedValue.equalsIgnoreCase("TRUE") || extractedValue.equalsIgnoreCase("YES")));
/*      */       }
/*      */       else {
/*      */         
/*  106 */         this.valueAsObject = this.defaultValue;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  114 */     boolean isRangeBased() { return false; }
/*      */ 
/*      */ 
/*      */     
/*  118 */     void setValue(boolean valueFlag) { this.valueAsObject = Boolean.valueOf(valueFlag); }
/*      */   }
/*      */ 
/*      */   
/*      */   abstract class ConnectionProperty
/*      */     implements Serializable
/*      */   {
/*      */     String[] allowableValues;
/*      */     
/*      */     String categoryName;
/*      */     
/*      */     Object defaultValue;
/*      */     
/*      */     int lowerBound;
/*      */     
/*      */     int order;
/*      */     
/*      */     String propertyName;
/*      */     
/*      */     String sinceVersion;
/*      */     
/*      */     int upperBound;
/*      */     
/*      */     Object valueAsObject;
/*      */     
/*      */     boolean required;
/*      */     
/*      */     String description;
/*      */ 
/*      */     
/*      */     public ConnectionProperty() {}
/*      */ 
/*      */     
/*      */     ConnectionProperty(String propertyNameToSet, Object defaultValueToSet, String[] allowableValuesToSet, int lowerBoundToSet, int upperBoundToSet, String descriptionToSet, String sinceVersionToSet, String category, int orderInCategory) {
/*  152 */       this.description = descriptionToSet;
/*  153 */       this.propertyName = propertyNameToSet;
/*  154 */       this.defaultValue = defaultValueToSet;
/*  155 */       this.valueAsObject = defaultValueToSet;
/*  156 */       this.allowableValues = allowableValuesToSet;
/*  157 */       this.lowerBound = lowerBoundToSet;
/*  158 */       this.upperBound = upperBoundToSet;
/*  159 */       this.required = false;
/*  160 */       this.sinceVersion = sinceVersionToSet;
/*  161 */       this.categoryName = category;
/*  162 */       this.order = orderInCategory;
/*      */     }
/*      */ 
/*      */     
/*  166 */     String[] getAllowableValues() { return this.allowableValues; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  173 */     String getCategoryName() throws SQLException { return this.categoryName; }
/*      */ 
/*      */ 
/*      */     
/*  177 */     Object getDefaultValue() { return this.defaultValue; }
/*      */ 
/*      */ 
/*      */     
/*  181 */     int getLowerBound() { return this.lowerBound; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  188 */     int getOrder() { return this.order; }
/*      */ 
/*      */ 
/*      */     
/*  192 */     String getPropertyName() throws SQLException { return this.propertyName; }
/*      */ 
/*      */ 
/*      */     
/*  196 */     int getUpperBound() { return this.upperBound; }
/*      */ 
/*      */ 
/*      */     
/*  200 */     Object getValueAsObject() { return this.valueAsObject; }
/*      */ 
/*      */     
/*      */     abstract boolean hasValueConstraints();
/*      */     
/*      */     void initializeFrom(Properties extractFrom) throws SQLException {
/*  206 */       String extractedValue = extractFrom.getProperty(getPropertyName());
/*  207 */       extractFrom.remove(getPropertyName());
/*  208 */       initializeFrom(extractedValue);
/*      */     }
/*      */     
/*      */     void initializeFrom(Reference ref) throws SQLException {
/*  212 */       RefAddr refAddr = ref.get(getPropertyName());
/*      */       
/*  214 */       if (refAddr != null) {
/*  215 */         String refContentAsString = (String)refAddr.getContent();
/*      */         
/*  217 */         initializeFrom(refContentAsString);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     abstract void initializeFrom(String param1String) throws SQLException;
/*      */ 
/*      */ 
/*      */     
/*      */     abstract boolean isRangeBased();
/*      */ 
/*      */     
/*  230 */     void setCategoryName(String categoryName) throws SQLException { this.categoryName = categoryName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  238 */     void setOrder(int order) { this.order = order; }
/*      */ 
/*      */ 
/*      */     
/*  242 */     void setValueAsObject(Object obj) { this.valueAsObject = obj; }
/*      */ 
/*      */     
/*      */     void storeTo(Reference ref) throws SQLException {
/*  246 */       if (getValueAsObject() != null) {
/*  247 */         ref.add(new StringRefAddr(getPropertyName(), getValueAsObject().toString()));
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     DriverPropertyInfo getAsDriverPropertyInfo() {
/*  253 */       DriverPropertyInfo dpi = new DriverPropertyInfo(this.propertyName, null);
/*  254 */       dpi.choices = getAllowableValues();
/*  255 */       dpi.value = (this.valueAsObject != null) ? this.valueAsObject.toString() : null;
/*  256 */       dpi.required = this.required;
/*  257 */       dpi.description = this.description;
/*      */       
/*  259 */       return dpi;
/*      */     }
/*      */ 
/*      */     
/*      */     void validateStringValues(String valueToValidate) throws SQLException {
/*  264 */       String[] validateAgainst = getAllowableValues();
/*      */       
/*  266 */       if (valueToValidate == null) {
/*      */         return;
/*      */       }
/*      */       
/*  270 */       if (validateAgainst == null || validateAgainst.length == 0) {
/*      */         return;
/*      */       }
/*      */       
/*  274 */       for (i = 0; i < validateAgainst.length; i++) {
/*  275 */         if (validateAgainst[i] != null && validateAgainst[i].equalsIgnoreCase(valueToValidate)) {
/*      */           return;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  281 */       StringBuffer errorMessageBuf = new StringBuffer();
/*      */       
/*  283 */       errorMessageBuf.append("The connection property '");
/*  284 */       errorMessageBuf.append(getPropertyName());
/*  285 */       errorMessageBuf.append("' only accepts values of the form: ");
/*      */       
/*  287 */       if (validateAgainst.length != 0) {
/*  288 */         errorMessageBuf.append("'");
/*  289 */         errorMessageBuf.append(validateAgainst[0]);
/*  290 */         errorMessageBuf.append("'");
/*      */         
/*  292 */         for (int i = 1; i < validateAgainst.length - 1; i++) {
/*  293 */           errorMessageBuf.append(", ");
/*  294 */           errorMessageBuf.append("'");
/*  295 */           errorMessageBuf.append(validateAgainst[i]);
/*  296 */           errorMessageBuf.append("'");
/*      */         } 
/*      */         
/*  299 */         errorMessageBuf.append(" or '");
/*  300 */         errorMessageBuf.append(validateAgainst[validateAgainst.length - 1]);
/*      */         
/*  302 */         errorMessageBuf.append("'");
/*      */       } 
/*      */       
/*  305 */       errorMessageBuf.append(". The value '");
/*  306 */       errorMessageBuf.append(valueToValidate);
/*  307 */       errorMessageBuf.append("' is not in this set.");
/*      */       
/*  309 */       throw SQLError.createSQLException(errorMessageBuf.toString(), "S1009", ConnectionPropertiesImpl.this.getExceptionInterceptor());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   class IntegerConnectionProperty
/*      */     extends ConnectionProperty
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -3004305481796850832L;
/*      */     
/*      */     int multiplier;
/*      */     
/*      */     public IntegerConnectionProperty(String propertyNameToSet, Object defaultValueToSet, String[] allowableValuesToSet, int lowerBoundToSet, int upperBoundToSet, String descriptionToSet, String sinceVersionToSet, String category, int orderInCategory) {
/*  323 */       super(ConnectionPropertiesImpl.this, propertyNameToSet, defaultValueToSet, allowableValuesToSet, lowerBoundToSet, upperBoundToSet, descriptionToSet, sinceVersionToSet, category, orderInCategory);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  328 */       this.multiplier = 1;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     IntegerConnectionProperty(String propertyNameToSet, int defaultValueToSet, int lowerBoundToSet, int upperBoundToSet, String descriptionToSet, String sinceVersionToSet, String category, int orderInCategory) {
/*  334 */       super(ConnectionPropertiesImpl.this, propertyNameToSet, new Integer(defaultValueToSet), null, lowerBoundToSet, upperBoundToSet, descriptionToSet, sinceVersionToSet, category, orderInCategory);
/*      */       this.multiplier = 1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  352 */     IntegerConnectionProperty(String propertyNameToSet, int defaultValueToSet, String descriptionToSet, String sinceVersionToSet, String category, int orderInCategory) { this(propertyNameToSet, defaultValueToSet, 0, 0, descriptionToSet, sinceVersionToSet, category, orderInCategory); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  360 */     String[] getAllowableValues() { return null; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  367 */     int getLowerBound() { return this.lowerBound; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  374 */     int getUpperBound() { return this.upperBound; }
/*      */ 
/*      */ 
/*      */     
/*  378 */     int getValueAsInt() { return ((Integer)this.valueAsObject).intValue(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  385 */     boolean hasValueConstraints() { return false; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void initializeFrom(String extractedValue) throws SQLException {
/*  392 */       if (extractedValue != null) {
/*      */         
/*      */         try {
/*  395 */           int intValue = Double.valueOf(extractedValue).intValue();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  406 */           this.valueAsObject = new Integer(intValue * this.multiplier);
/*  407 */         } catch (NumberFormatException nfe) {
/*  408 */           throw SQLError.createSQLException("The connection property '" + getPropertyName() + "' only accepts integer values. The value '" + extractedValue + "' can not be converted to an integer.", "S1009", ConnectionPropertiesImpl.this.getExceptionInterceptor());
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  416 */         this.valueAsObject = this.defaultValue;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  424 */     boolean isRangeBased() { return (getUpperBound() != getLowerBound()); }
/*      */ 
/*      */ 
/*      */     
/*  428 */     void setValue(int valueFlag) { this.valueAsObject = new Integer(valueFlag); }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public class LongConnectionProperty
/*      */     extends IntegerConnectionProperty
/*      */   {
/*      */     private static final long serialVersionUID = 6068572984340480895L;
/*      */ 
/*      */ 
/*      */     
/*  440 */     LongConnectionProperty(String propertyNameToSet, long defaultValueToSet, long lowerBoundToSet, long upperBoundToSet, String descriptionToSet, String sinceVersionToSet, String category, int orderInCategory) { super(ConnectionPropertiesImpl.this, propertyNameToSet, new Long(defaultValueToSet), null, (int)lowerBoundToSet, (int)upperBoundToSet, descriptionToSet, sinceVersionToSet, category, orderInCategory); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  449 */     LongConnectionProperty(String propertyNameToSet, long defaultValueToSet, String descriptionToSet, String sinceVersionToSet, String category, int orderInCategory) { this(propertyNameToSet, defaultValueToSet, 0L, 0L, descriptionToSet, sinceVersionToSet, category, orderInCategory); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  456 */     void setValue(long value) { this.valueAsObject = new Long(value); }
/*      */ 
/*      */ 
/*      */     
/*  460 */     long getValueAsLong() { return ((Long)this.valueAsObject).longValue(); }
/*      */ 
/*      */     
/*      */     void initializeFrom(String extractedValue) throws SQLException {
/*  464 */       if (extractedValue != null) {
/*      */         
/*      */         try {
/*  467 */           long longValue = Double.valueOf(extractedValue).longValue();
/*      */           
/*  469 */           this.valueAsObject = new Long(longValue);
/*  470 */         } catch (NumberFormatException nfe) {
/*  471 */           throw SQLError.createSQLException("The connection property '" + getPropertyName() + "' only accepts long integer values. The value '" + extractedValue + "' can not be converted to a long integer.", "S1009", ConnectionPropertiesImpl.this.getExceptionInterceptor());
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  479 */         this.valueAsObject = this.defaultValue;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   class MemorySizeConnectionProperty
/*      */     extends IntegerConnectionProperty
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 7351065128998572656L;
/*      */     
/*      */     private String valueAsString;
/*      */ 
/*      */     
/*  494 */     MemorySizeConnectionProperty(String propertyNameToSet, int defaultValueToSet, int lowerBoundToSet, int upperBoundToSet, String descriptionToSet, String sinceVersionToSet, String category, int orderInCategory) { super(ConnectionPropertiesImpl.this, propertyNameToSet, defaultValueToSet, lowerBoundToSet, upperBoundToSet, descriptionToSet, sinceVersionToSet, category, orderInCategory); }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void initializeFrom(String extractedValue) throws SQLException {
/*  500 */       this.valueAsString = extractedValue;
/*      */       
/*  502 */       if (extractedValue != null) {
/*  503 */         if (extractedValue.endsWith("k") || extractedValue.endsWith("K") || extractedValue.endsWith("kb") || extractedValue.endsWith("Kb") || extractedValue.endsWith("kB")) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  508 */           this.multiplier = 1024;
/*  509 */           int indexOfK = StringUtils.indexOfIgnoreCase(extractedValue, "k");
/*      */           
/*  511 */           extractedValue = extractedValue.substring(0, indexOfK);
/*  512 */         } else if (extractedValue.endsWith("m") || extractedValue.endsWith("M") || extractedValue.endsWith("G") || extractedValue.endsWith("mb") || extractedValue.endsWith("Mb") || extractedValue.endsWith("mB")) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  518 */           this.multiplier = 1048576;
/*  519 */           int indexOfM = StringUtils.indexOfIgnoreCase(extractedValue, "m");
/*      */           
/*  521 */           extractedValue = extractedValue.substring(0, indexOfM);
/*  522 */         } else if (extractedValue.endsWith("g") || extractedValue.endsWith("G") || extractedValue.endsWith("gb") || extractedValue.endsWith("Gb") || extractedValue.endsWith("gB")) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  527 */           this.multiplier = 1073741824;
/*  528 */           int indexOfG = StringUtils.indexOfIgnoreCase(extractedValue, "g");
/*      */           
/*  530 */           extractedValue = extractedValue.substring(0, indexOfG);
/*      */         } 
/*      */       }
/*      */       
/*  534 */       super.initializeFrom(extractedValue);
/*      */     }
/*      */ 
/*      */     
/*  538 */     void setValue(String value) throws SQLException { initializeFrom(value); }
/*      */ 
/*      */ 
/*      */     
/*  542 */     String getValueAsString() throws SQLException { return this.valueAsString; }
/*      */   }
/*      */ 
/*      */   
/*      */   class StringConnectionProperty
/*      */     extends ConnectionProperty
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 5432127962785948272L;
/*      */ 
/*      */     
/*  553 */     StringConnectionProperty(ConnectionPropertiesImpl this$0, String propertyNameToSet, String defaultValueToSet, String descriptionToSet, String sinceVersionToSet, String category, int orderInCategory) { this(propertyNameToSet, defaultValueToSet, null, descriptionToSet, sinceVersionToSet, category, orderInCategory); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  571 */     StringConnectionProperty(String propertyNameToSet, String defaultValueToSet, String[] allowableValuesToSet, String descriptionToSet, String sinceVersionToSet, String category, int orderInCategory) { super(ConnectionPropertiesImpl.this, propertyNameToSet, defaultValueToSet, allowableValuesToSet, 0, 0, descriptionToSet, sinceVersionToSet, category, orderInCategory); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  577 */     String getValueAsString() throws SQLException { return (String)this.valueAsObject; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  584 */     boolean hasValueConstraints() { return (this.allowableValues != null && this.allowableValues.length > 0); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void initializeFrom(String extractedValue) throws SQLException {
/*  592 */       if (extractedValue != null) {
/*  593 */         validateStringValues(extractedValue);
/*      */         
/*  595 */         this.valueAsObject = extractedValue;
/*      */       } else {
/*  597 */         this.valueAsObject = this.defaultValue;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  605 */     boolean isRangeBased() { return false; }
/*      */ 
/*      */ 
/*      */     
/*  609 */     void setValue(String valueFlag) throws SQLException { this.valueAsObject = valueFlag; }
/*      */   }
/*      */ 
/*      */   
/*  613 */   private static final String CONNECTION_AND_AUTH_CATEGORY = Messages.getString("ConnectionProperties.categoryConnectionAuthentication");
/*      */   
/*  615 */   private static final String NETWORK_CATEGORY = Messages.getString("ConnectionProperties.categoryNetworking");
/*      */   
/*  617 */   private static final String DEBUGING_PROFILING_CATEGORY = Messages.getString("ConnectionProperties.categoryDebuggingProfiling");
/*      */   
/*  619 */   private static final String HA_CATEGORY = Messages.getString("ConnectionProperties.categorryHA");
/*      */   
/*  621 */   private static final String MISC_CATEGORY = Messages.getString("ConnectionProperties.categoryMisc");
/*      */   
/*  623 */   private static final String PERFORMANCE_CATEGORY = Messages.getString("ConnectionProperties.categoryPerformance");
/*      */   
/*  625 */   private static final String SECURITY_CATEGORY = Messages.getString("ConnectionProperties.categorySecurity");
/*      */   
/*  627 */   private static final String[] PROPERTY_CATEGORIES = { CONNECTION_AND_AUTH_CATEGORY, NETWORK_CATEGORY, HA_CATEGORY, SECURITY_CATEGORY, PERFORMANCE_CATEGORY, DEBUGING_PROFILING_CATEGORY, MISC_CATEGORY };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  632 */   private static final ArrayList PROPERTY_LIST = new ArrayList();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  637 */   private static final String STANDARD_LOGGER_NAME = com.mysql.jdbc.log.StandardLogger.class.getName();
/*      */   
/*      */   protected static final String ZERO_DATETIME_BEHAVIOR_CONVERT_TO_NULL = "convertToNull";
/*      */   
/*      */   protected static final String ZERO_DATETIME_BEHAVIOR_EXCEPTION = "exception";
/*      */   
/*      */   protected static final String ZERO_DATETIME_BEHAVIOR_ROUND = "round";
/*      */   
/*      */   static  {
/*      */     try {
/*  647 */       declaredFields = ConnectionPropertiesImpl.class.getDeclaredFields();
/*      */ 
/*      */       
/*  650 */       for (int i = 0; i < declaredFields.length; i++) {
/*  651 */         if (ConnectionProperty.class.isAssignableFrom(declaredFields[i].getType()))
/*      */         {
/*  653 */           PROPERTY_LIST.add(declaredFields[i]);
/*      */         }
/*      */       } 
/*  656 */     } catch (Exception ex) {
/*  657 */       RuntimeException rtEx = new RuntimeException();
/*  658 */       rtEx.initCause(ex);
/*      */       
/*  660 */       throw rtEx;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*  665 */   public ExceptionInterceptor getExceptionInterceptor() { return null; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  684 */   protected static DriverPropertyInfo[] exposeAsDriverPropertyInfo(Properties info, int slotsToReserve) throws SQLException { return (new ConnectionPropertiesImpl() {  }).exposeAsDriverPropertyInfoInternal(info, slotsToReserve); }
/*      */ 
/*      */ 
/*      */   
/*  688 */   private BooleanConnectionProperty allowLoadLocalInfile = new BooleanConnectionProperty("allowLoadLocalInfile", true, Messages.getString("ConnectionProperties.loadDataLocal"), "3.0.3", SECURITY_CATEGORY, 2147483647);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  694 */   private BooleanConnectionProperty allowMultiQueries = new BooleanConnectionProperty("allowMultiQueries", false, Messages.getString("ConnectionProperties.allowMultiQueries"), "3.1.1", SECURITY_CATEGORY, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  700 */   private BooleanConnectionProperty allowNanAndInf = new BooleanConnectionProperty("allowNanAndInf", false, Messages.getString("ConnectionProperties.allowNANandINF"), "3.1.5", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  706 */   private BooleanConnectionProperty allowUrlInLocalInfile = new BooleanConnectionProperty("allowUrlInLocalInfile", false, Messages.getString("ConnectionProperties.allowUrlInLoadLocal"), "3.1.4", SECURITY_CATEGORY, 2147483647);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  712 */   private BooleanConnectionProperty alwaysSendSetIsolation = new BooleanConnectionProperty("alwaysSendSetIsolation", true, Messages.getString("ConnectionProperties.alwaysSendSetIsolation"), "3.1.7", PERFORMANCE_CATEGORY, 2147483647);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  718 */   private BooleanConnectionProperty autoClosePStmtStreams = new BooleanConnectionProperty("autoClosePStmtStreams", false, Messages.getString("ConnectionProperties.autoClosePstmtStreams"), "3.1.12", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  726 */   private BooleanConnectionProperty autoDeserialize = new BooleanConnectionProperty("autoDeserialize", false, Messages.getString("ConnectionProperties.autoDeserialize"), "3.1.5", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  732 */   private BooleanConnectionProperty autoGenerateTestcaseScript = new BooleanConnectionProperty("autoGenerateTestcaseScript", false, Messages.getString("ConnectionProperties.autoGenerateTestcaseScript"), "3.1.9", DEBUGING_PROFILING_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean autoGenerateTestcaseScriptAsBoolean = false;
/*      */ 
/*      */   
/*  739 */   private BooleanConnectionProperty autoReconnect = new BooleanConnectionProperty("autoReconnect", false, Messages.getString("ConnectionProperties.autoReconnect"), "1.1", HA_CATEGORY, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  745 */   private BooleanConnectionProperty autoReconnectForPools = new BooleanConnectionProperty("autoReconnectForPools", false, Messages.getString("ConnectionProperties.autoReconnectForPools"), "3.1.3", HA_CATEGORY, true);
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean autoReconnectForPoolsAsBoolean = false;
/*      */ 
/*      */ 
/*      */   
/*  753 */   private MemorySizeConnectionProperty blobSendChunkSize = new MemorySizeConnectionProperty("blobSendChunkSize", 1048576, true, 2147483647, Messages.getString("ConnectionProperties.blobSendChunkSize"), "3.1.9", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  761 */   private BooleanConnectionProperty autoSlowLog = new BooleanConnectionProperty("autoSlowLog", true, Messages.getString("ConnectionProperties.autoSlowLog"), "5.1.4", DEBUGING_PROFILING_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  766 */   private BooleanConnectionProperty blobsAreStrings = new BooleanConnectionProperty("blobsAreStrings", false, "Should the driver always treat BLOBs as Strings - specifically to work around dubious metadata returned by the server for GROUP BY clauses?", "5.0.8", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  772 */   private BooleanConnectionProperty functionsNeverReturnBlobs = new BooleanConnectionProperty("functionsNeverReturnBlobs", false, "Should the driver always treat data from functions returning BLOBs as Strings - specifically to work around dubious metadata returned by the server for GROUP BY clauses?", "5.0.8", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  778 */   private BooleanConnectionProperty cacheCallableStatements = new BooleanConnectionProperty("cacheCallableStmts", false, Messages.getString("ConnectionProperties.cacheCallableStatements"), "3.1.2", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  783 */   private BooleanConnectionProperty cachePreparedStatements = new BooleanConnectionProperty("cachePrepStmts", false, Messages.getString("ConnectionProperties.cachePrepStmts"), "3.0.10", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  789 */   private BooleanConnectionProperty cacheResultSetMetadata = new BooleanConnectionProperty("cacheResultSetMetadata", false, Messages.getString("ConnectionProperties.cacheRSMetadata"), "3.1.1", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean cacheResultSetMetaDataAsBoolean;
/*      */ 
/*      */ 
/*      */   
/*  797 */   private BooleanConnectionProperty cacheServerConfiguration = new BooleanConnectionProperty("cacheServerConfiguration", false, Messages.getString("ConnectionProperties.cacheServerConfiguration"), "3.1.5", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  803 */   private IntegerConnectionProperty callableStatementCacheSize = new IntegerConnectionProperty("callableStmtCacheSize", 100, false, 2147483647, Messages.getString("ConnectionProperties.callableStmtCacheSize"), "3.1.2", PERFORMANCE_CATEGORY, 5);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  811 */   private BooleanConnectionProperty capitalizeTypeNames = new BooleanConnectionProperty("capitalizeTypeNames", true, Messages.getString("ConnectionProperties.capitalizeTypeNames"), "2.0.7", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  817 */   private StringConnectionProperty characterEncoding = new StringConnectionProperty("characterEncoding", null, Messages.getString("ConnectionProperties.characterEncoding"), "1.1g", MISC_CATEGORY, 5);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  823 */   private String characterEncodingAsString = null;
/*      */   
/*  825 */   private StringConnectionProperty characterSetResults = new StringConnectionProperty("characterSetResults", null, Messages.getString("ConnectionProperties.characterSetResults"), "3.0.13", MISC_CATEGORY, 6);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  830 */   private StringConnectionProperty clientInfoProvider = new StringConnectionProperty("clientInfoProvider", "com.mysql.jdbc.JDBC4CommentClientInfoProvider", Messages.getString("ConnectionProperties.clientInfoProvider"), "5.1.0", DEBUGING_PROFILING_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  836 */   private BooleanConnectionProperty clobberStreamingResults = new BooleanConnectionProperty("clobberStreamingResults", false, Messages.getString("ConnectionProperties.clobberStreamingResults"), "3.0.9", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  842 */   private StringConnectionProperty clobCharacterEncoding = new StringConnectionProperty("clobCharacterEncoding", null, Messages.getString("ConnectionProperties.clobCharacterEncoding"), "5.0.0", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  848 */   private BooleanConnectionProperty compensateOnDuplicateKeyUpdateCounts = new BooleanConnectionProperty("compensateOnDuplicateKeyUpdateCounts", false, Messages.getString("ConnectionProperties.compensateOnDuplicateKeyUpdateCounts"), "5.1.7", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  853 */   private StringConnectionProperty connectionCollation = new StringConnectionProperty("connectionCollation", null, Messages.getString("ConnectionProperties.connectionCollation"), "3.0.13", MISC_CATEGORY, 7);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  859 */   private StringConnectionProperty connectionLifecycleInterceptors = new StringConnectionProperty("connectionLifecycleInterceptors", null, Messages.getString("ConnectionProperties.connectionLifecycleInterceptors"), "5.1.4", CONNECTION_AND_AUTH_CATEGORY, 2147483647);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  865 */   private IntegerConnectionProperty connectTimeout = new IntegerConnectionProperty("connectTimeout", false, false, 2147483647, Messages.getString("ConnectionProperties.connectTimeout"), "3.0.1", CONNECTION_AND_AUTH_CATEGORY, 9);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  870 */   private BooleanConnectionProperty continueBatchOnError = new BooleanConnectionProperty("continueBatchOnError", true, Messages.getString("ConnectionProperties.continueBatchOnError"), "3.0.3", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  876 */   private BooleanConnectionProperty createDatabaseIfNotExist = new BooleanConnectionProperty("createDatabaseIfNotExist", false, Messages.getString("ConnectionProperties.createDatabaseIfNotExist"), "3.1.9", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  882 */   private IntegerConnectionProperty defaultFetchSize = new IntegerConnectionProperty("defaultFetchSize", false, Messages.getString("ConnectionProperties.defaultFetchSize"), "3.1.9", PERFORMANCE_CATEGORY, -2147483648);
/*      */   
/*  884 */   private BooleanConnectionProperty detectServerPreparedStmts = new BooleanConnectionProperty("useServerPrepStmts", false, Messages.getString("ConnectionProperties.useServerPrepStmts"), "3.1.0", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  890 */   private BooleanConnectionProperty dontTrackOpenResources = new BooleanConnectionProperty("dontTrackOpenResources", false, Messages.getString("ConnectionProperties.dontTrackOpenResources"), "3.1.7", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  896 */   private BooleanConnectionProperty dumpQueriesOnException = new BooleanConnectionProperty("dumpQueriesOnException", false, Messages.getString("ConnectionProperties.dumpQueriesOnException"), "3.1.3", DEBUGING_PROFILING_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  902 */   private BooleanConnectionProperty dynamicCalendars = new BooleanConnectionProperty("dynamicCalendars", false, Messages.getString("ConnectionProperties.dynamicCalendars"), "3.1.5", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  908 */   private BooleanConnectionProperty elideSetAutoCommits = new BooleanConnectionProperty("elideSetAutoCommits", false, Messages.getString("ConnectionProperties.eliseSetAutoCommit"), "3.1.3", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  914 */   private BooleanConnectionProperty emptyStringsConvertToZero = new BooleanConnectionProperty("emptyStringsConvertToZero", true, Messages.getString("ConnectionProperties.emptyStringsConvertToZero"), "3.1.8", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  919 */   private BooleanConnectionProperty emulateLocators = new BooleanConnectionProperty("emulateLocators", false, Messages.getString("ConnectionProperties.emulateLocators"), "3.1.0", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */   
/*  923 */   private BooleanConnectionProperty emulateUnsupportedPstmts = new BooleanConnectionProperty("emulateUnsupportedPstmts", true, Messages.getString("ConnectionProperties.emulateUnsupportedPstmts"), "3.1.7", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  929 */   private BooleanConnectionProperty enablePacketDebug = new BooleanConnectionProperty("enablePacketDebug", false, Messages.getString("ConnectionProperties.enablePacketDebug"), "3.1.3", DEBUGING_PROFILING_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  935 */   private BooleanConnectionProperty enableQueryTimeouts = new BooleanConnectionProperty("enableQueryTimeouts", true, Messages.getString("ConnectionProperties.enableQueryTimeouts"), "5.0.6", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  942 */   private BooleanConnectionProperty explainSlowQueries = new BooleanConnectionProperty("explainSlowQueries", false, Messages.getString("ConnectionProperties.explainSlowQueries"), "3.1.2", DEBUGING_PROFILING_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  948 */   private StringConnectionProperty exceptionInterceptors = new StringConnectionProperty("exceptionInterceptors", null, Messages.getString("ConnectionProperties.exceptionInterceptors"), "5.1.8", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  955 */   private BooleanConnectionProperty failOverReadOnly = new BooleanConnectionProperty("failOverReadOnly", true, Messages.getString("ConnectionProperties.failoverReadOnly"), "3.0.12", HA_CATEGORY, 2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  961 */   private BooleanConnectionProperty gatherPerformanceMetrics = new BooleanConnectionProperty("gatherPerfMetrics", false, Messages.getString("ConnectionProperties.gatherPerfMetrics"), "3.1.2", DEBUGING_PROFILING_CATEGORY, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  967 */   private BooleanConnectionProperty generateSimpleParameterMetadata = new BooleanConnectionProperty("generateSimpleParameterMetadata", false, Messages.getString("ConnectionProperties.generateSimpleParameterMetadata"), "5.0.5", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */   
/*      */   private boolean highAvailabilityAsBoolean = false;
/*      */   
/*  972 */   private BooleanConnectionProperty holdResultsOpenOverStatementClose = new BooleanConnectionProperty("holdResultsOpenOverStatementClose", false, Messages.getString("ConnectionProperties.holdRSOpenOverStmtClose"), "3.1.7", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  978 */   private BooleanConnectionProperty includeInnodbStatusInDeadlockExceptions = new BooleanConnectionProperty("includeInnodbStatusInDeadlockExceptions", false, "Include the output of \"SHOW ENGINE INNODB STATUS\" in exception messages when deadlock exceptions are detected?", "5.0.7", DEBUGING_PROFILING_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  984 */   private BooleanConnectionProperty ignoreNonTxTables = new BooleanConnectionProperty("ignoreNonTxTables", false, Messages.getString("ConnectionProperties.ignoreNonTxTables"), "3.0.9", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  990 */   private IntegerConnectionProperty initialTimeout = new IntegerConnectionProperty("initialTimeout", 2, true, 2147483647, Messages.getString("ConnectionProperties.initialTimeout"), "1.1", HA_CATEGORY, 5);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  995 */   private BooleanConnectionProperty isInteractiveClient = new BooleanConnectionProperty("interactiveClient", false, Messages.getString("ConnectionProperties.interactiveClient"), "3.1.0", CONNECTION_AND_AUTH_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1001 */   private BooleanConnectionProperty jdbcCompliantTruncation = new BooleanConnectionProperty("jdbcCompliantTruncation", true, Messages.getString("ConnectionProperties.jdbcCompliantTruncation"), "3.1.2", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1007 */   private boolean jdbcCompliantTruncationForReads = this.jdbcCompliantTruncation.getValueAsBoolean();
/*      */ 
/*      */   
/* 1010 */   protected MemorySizeConnectionProperty largeRowSizeThreshold = new MemorySizeConnectionProperty("largeRowSizeThreshold", 'ࠀ', false, 2147483647, Messages.getString("ConnectionProperties.largeRowSizeThreshold"), "5.1.1", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1015 */   private StringConnectionProperty loadBalanceStrategy = new StringConnectionProperty("loadBalanceStrategy", "random", null, Messages.getString("ConnectionProperties.loadBalanceStrategy"), "5.0.6", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1022 */   private IntegerConnectionProperty loadBalanceBlacklistTimeout = new IntegerConnectionProperty("loadBalanceBlacklistTimeout", false, false, 2147483647, Messages.getString("ConnectionProperties.loadBalanceBlacklistTimeout"), "5.1.0", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1028 */   private IntegerConnectionProperty loadBalancePingTimeout = new IntegerConnectionProperty("loadBalancePingTimeout", false, false, 2147483647, Messages.getString("ConnectionProperties.loadBalancePingTimeout"), "5.1.13", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1034 */   private BooleanConnectionProperty loadBalanceValidateConnectionOnSwapServer = new BooleanConnectionProperty("loadBalanceValidateConnectionOnSwapServer", false, Messages.getString("ConnectionProperties.loadBalanceValidateConnectionOnSwapServer"), "5.1.13", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1040 */   private StringConnectionProperty loadBalanceConnectionGroup = new StringConnectionProperty("loadBalanceConnectionGroup", null, Messages.getString("ConnectionProperties.loadBalanceConnectionGroup"), "5.1.13", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1046 */   private StringConnectionProperty loadBalanceExceptionChecker = new StringConnectionProperty("loadBalanceExceptionChecker", "com.mysql.jdbc.StandardLoadBalanceExceptionChecker", null, Messages.getString("ConnectionProperties.loadBalanceExceptionChecker"), "5.1.13", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1053 */   private StringConnectionProperty loadBalanceSQLStateFailover = new StringConnectionProperty("loadBalanceSQLStateFailover", null, Messages.getString("ConnectionProperties.loadBalanceSQLStateFailover"), "5.1.13", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1059 */   private StringConnectionProperty loadBalanceSQLExceptionSubclassFailover = new StringConnectionProperty("loadBalanceSQLExceptionSubclassFailover", null, Messages.getString("ConnectionProperties.loadBalanceSQLExceptionSubclassFailover"), "5.1.13", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1065 */   private BooleanConnectionProperty loadBalanceEnableJMX = new BooleanConnectionProperty("loadBalanceEnableJMX", false, Messages.getString("ConnectionProperties.loadBalanceEnableJMX"), "5.1.13", MISC_CATEGORY, 2147483647);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1071 */   private StringConnectionProperty loadBalanceAutoCommitStatementRegex = new StringConnectionProperty("loadBalanceAutoCommitStatementRegex", null, Messages.getString("ConnectionProperties.loadBalanceAutoCommitStatementRegex"), "5.1.15", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1077 */   private IntegerConnectionProperty loadBalanceAutoCommitStatementThreshold = new IntegerConnectionProperty("loadBalanceAutoCommitStatementThreshold", false, false, 2147483647, Messages.getString("ConnectionProperties.loadBalanceAutoCommitStatementThreshold"), "5.1.15", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1084 */   private StringConnectionProperty localSocketAddress = new StringConnectionProperty("localSocketAddress", null, Messages.getString("ConnectionProperties.localSocketAddress"), "5.0.5", CONNECTION_AND_AUTH_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */   
/* 1088 */   private MemorySizeConnectionProperty locatorFetchBufferSize = new MemorySizeConnectionProperty("locatorFetchBufferSize", 1048576, false, 2147483647, Messages.getString("ConnectionProperties.locatorFetchBufferSize"), "3.2.1", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1096 */   private StringConnectionProperty loggerClassName = new StringConnectionProperty("logger", STANDARD_LOGGER_NAME, Messages.getString("ConnectionProperties.logger", new Object[] { com.mysql.jdbc.log.Log.class.getName(), STANDARD_LOGGER_NAME }), "3.1.1", DEBUGING_PROFILING_CATEGORY, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1102 */   private BooleanConnectionProperty logSlowQueries = new BooleanConnectionProperty("logSlowQueries", false, Messages.getString("ConnectionProperties.logSlowQueries"), "3.1.2", DEBUGING_PROFILING_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1108 */   private BooleanConnectionProperty logXaCommands = new BooleanConnectionProperty("logXaCommands", false, Messages.getString("ConnectionProperties.logXaCommands"), "5.0.5", DEBUGING_PROFILING_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1114 */   private BooleanConnectionProperty maintainTimeStats = new BooleanConnectionProperty("maintainTimeStats", true, Messages.getString("ConnectionProperties.maintainTimeStats"), "3.1.9", PERFORMANCE_CATEGORY, 2147483647);
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean maintainTimeStatsAsBoolean = true;
/*      */ 
/*      */ 
/*      */   
/* 1122 */   private IntegerConnectionProperty maxQuerySizeToLog = new IntegerConnectionProperty("maxQuerySizeToLog", 'ࠀ', false, 2147483647, Messages.getString("ConnectionProperties.maxQuerySizeToLog"), "3.1.3", DEBUGING_PROFILING_CATEGORY, 4);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1130 */   private IntegerConnectionProperty maxReconnects = new IntegerConnectionProperty("maxReconnects", 3, true, 2147483647, Messages.getString("ConnectionProperties.maxReconnects"), "1.1", HA_CATEGORY, 4);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1138 */   private IntegerConnectionProperty retriesAllDown = new IntegerConnectionProperty("retriesAllDown", 120, false, 2147483647, Messages.getString("ConnectionProperties.retriesAllDown"), "5.1.6", HA_CATEGORY, 4);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1146 */   private IntegerConnectionProperty maxRows = new IntegerConnectionProperty("maxRows", -1, -1, 2147483647, Messages.getString("ConnectionProperties.maxRows"), Messages.getString("ConnectionProperties.allVersions"), MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1151 */   private int maxRowsAsInt = -1;
/*      */   
/* 1153 */   private IntegerConnectionProperty metadataCacheSize = new IntegerConnectionProperty("metadataCacheSize", 50, true, 2147483647, Messages.getString("ConnectionProperties.metadataCacheSize"), "3.1.1", PERFORMANCE_CATEGORY, 5);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1161 */   private IntegerConnectionProperty netTimeoutForStreamingResults = new IntegerConnectionProperty("netTimeoutForStreamingResults", 'ɘ', false, 2147483647, Messages.getString("ConnectionProperties.netTimeoutForStreamingResults"), "5.1.0", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1167 */   private BooleanConnectionProperty noAccessToProcedureBodies = new BooleanConnectionProperty("noAccessToProcedureBodies", false, "When determining procedure parameter types for CallableStatements, and the connected user  can't access procedure bodies through \"SHOW CREATE PROCEDURE\" or select on mysql.proc  should the driver instead create basic metadata (all parameters reported as IN VARCHARs, but allowing registerOutParameter() to be called on them anyway) instead  of throwing an exception?", "5.0.3", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1177 */   private BooleanConnectionProperty noDatetimeStringSync = new BooleanConnectionProperty("noDatetimeStringSync", false, Messages.getString("ConnectionProperties.noDatetimeStringSync"), "3.1.7", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1183 */   private BooleanConnectionProperty noTimezoneConversionForTimeType = new BooleanConnectionProperty("noTimezoneConversionForTimeType", false, Messages.getString("ConnectionProperties.noTzConversionForTimeType"), "5.0.0", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1189 */   private BooleanConnectionProperty nullCatalogMeansCurrent = new BooleanConnectionProperty("nullCatalogMeansCurrent", true, Messages.getString("ConnectionProperties.nullCatalogMeansCurrent"), "3.1.8", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1195 */   private BooleanConnectionProperty nullNamePatternMatchesAll = new BooleanConnectionProperty("nullNamePatternMatchesAll", true, Messages.getString("ConnectionProperties.nullNamePatternMatchesAll"), "3.1.8", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1201 */   private IntegerConnectionProperty packetDebugBufferSize = new IntegerConnectionProperty("packetDebugBufferSize", 20, false, 2147483647, Messages.getString("ConnectionProperties.packetDebugBufferSize"), "3.1.3", DEBUGING_PROFILING_CATEGORY, 7);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1209 */   private BooleanConnectionProperty padCharsWithSpace = new BooleanConnectionProperty("padCharsWithSpace", false, Messages.getString("ConnectionProperties.padCharsWithSpace"), "5.0.6", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1217 */   private BooleanConnectionProperty paranoid = new BooleanConnectionProperty("paranoid", false, Messages.getString("ConnectionProperties.paranoid"), "3.0.1", SECURITY_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1223 */   private BooleanConnectionProperty pedantic = new BooleanConnectionProperty("pedantic", false, Messages.getString("ConnectionProperties.pedantic"), "3.0.0", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */   
/* 1227 */   private BooleanConnectionProperty pinGlobalTxToPhysicalConnection = new BooleanConnectionProperty("pinGlobalTxToPhysicalConnection", false, Messages.getString("ConnectionProperties.pinGlobalTxToPhysicalConnection"), "5.0.1", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */   
/* 1231 */   private BooleanConnectionProperty populateInsertRowWithDefaultValues = new BooleanConnectionProperty("populateInsertRowWithDefaultValues", false, Messages.getString("ConnectionProperties.populateInsertRowWithDefaultValues"), "5.0.5", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1236 */   private IntegerConnectionProperty preparedStatementCacheSize = new IntegerConnectionProperty("prepStmtCacheSize", 25, false, 2147483647, Messages.getString("ConnectionProperties.prepStmtCacheSize"), "3.0.10", PERFORMANCE_CATEGORY, 10);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1241 */   private IntegerConnectionProperty preparedStatementCacheSqlLimit = new IntegerConnectionProperty("prepStmtCacheSqlLimit", 'Ā', true, 2147483647, Messages.getString("ConnectionProperties.prepStmtCacheSqlLimit"), "3.0.10", PERFORMANCE_CATEGORY, 11);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1249 */   private BooleanConnectionProperty processEscapeCodesForPrepStmts = new BooleanConnectionProperty("processEscapeCodesForPrepStmts", true, Messages.getString("ConnectionProperties.processEscapeCodesForPrepStmts"), "3.1.12", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1256 */   private StringConnectionProperty profilerEventHandler = new StringConnectionProperty("profilerEventHandler", "com.mysql.jdbc.profiler.LoggingProfilerEventHandler", Messages.getString("ConnectionProperties.profilerEventHandler"), "5.1.6", DEBUGING_PROFILING_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1262 */   private StringConnectionProperty profileSql = new StringConnectionProperty("profileSql", null, Messages.getString("ConnectionProperties.profileSqlDeprecated"), "2.0.14", DEBUGING_PROFILING_CATEGORY, 3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1268 */   private BooleanConnectionProperty profileSQL = new BooleanConnectionProperty("profileSQL", false, Messages.getString("ConnectionProperties.profileSQL"), "3.1.0", DEBUGING_PROFILING_CATEGORY, true);
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean profileSQLAsBoolean = false;
/*      */ 
/*      */ 
/*      */   
/* 1276 */   private StringConnectionProperty propertiesTransform = new StringConnectionProperty("propertiesTransform", null, Messages.getString("ConnectionProperties.connectionPropertiesTransform"), "3.1.4", CONNECTION_AND_AUTH_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1282 */   private IntegerConnectionProperty queriesBeforeRetryMaster = new IntegerConnectionProperty("queriesBeforeRetryMaster", 50, true, 2147483647, Messages.getString("ConnectionProperties.queriesBeforeRetryMaster"), "3.0.2", HA_CATEGORY, 7);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1290 */   private BooleanConnectionProperty queryTimeoutKillsConnection = new BooleanConnectionProperty("queryTimeoutKillsConnection", false, Messages.getString("ConnectionProperties.queryTimeoutKillsConnection"), "5.1.9", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */   
/* 1294 */   private BooleanConnectionProperty reconnectAtTxEnd = new BooleanConnectionProperty("reconnectAtTxEnd", false, Messages.getString("ConnectionProperties.reconnectAtTxEnd"), "3.0.10", HA_CATEGORY, 4);
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean reconnectTxAtEndAsBoolean = false;
/*      */ 
/*      */   
/* 1301 */   private BooleanConnectionProperty relaxAutoCommit = new BooleanConnectionProperty("relaxAutoCommit", false, Messages.getString("ConnectionProperties.relaxAutoCommit"), "2.0.13", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1307 */   private IntegerConnectionProperty reportMetricsIntervalMillis = new IntegerConnectionProperty("reportMetricsIntervalMillis", '田', false, 2147483647, Messages.getString("ConnectionProperties.reportMetricsIntervalMillis"), "3.1.2", DEBUGING_PROFILING_CATEGORY, 3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1315 */   private BooleanConnectionProperty requireSSL = new BooleanConnectionProperty("requireSSL", false, Messages.getString("ConnectionProperties.requireSSL"), "3.1.0", SECURITY_CATEGORY, 3);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1320 */   private StringConnectionProperty resourceId = new StringConnectionProperty("resourceId", null, Messages.getString("ConnectionProperties.resourceId"), "5.0.1", HA_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1327 */   private IntegerConnectionProperty resultSetSizeThreshold = new IntegerConnectionProperty("resultSetSizeThreshold", 100, Messages.getString("ConnectionProperties.resultSetSizeThreshold"), "5.0.5", DEBUGING_PROFILING_CATEGORY, -2147483648);
/*      */ 
/*      */   
/* 1330 */   private BooleanConnectionProperty retainStatementAfterResultSetClose = new BooleanConnectionProperty("retainStatementAfterResultSetClose", false, Messages.getString("ConnectionProperties.retainStatementAfterResultSetClose"), "3.1.11", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1336 */   private BooleanConnectionProperty rewriteBatchedStatements = new BooleanConnectionProperty("rewriteBatchedStatements", false, Messages.getString("ConnectionProperties.rewriteBatchedStatements"), "3.1.13", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1342 */   private BooleanConnectionProperty rollbackOnPooledClose = new BooleanConnectionProperty("rollbackOnPooledClose", true, Messages.getString("ConnectionProperties.rollbackOnPooledClose"), "3.0.15", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1348 */   private BooleanConnectionProperty roundRobinLoadBalance = new BooleanConnectionProperty("roundRobinLoadBalance", false, Messages.getString("ConnectionProperties.roundRobinLoadBalance"), "3.1.2", HA_CATEGORY, 5);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1354 */   private BooleanConnectionProperty runningCTS13 = new BooleanConnectionProperty("runningCTS13", false, Messages.getString("ConnectionProperties.runningCTS13"), "3.1.7", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1360 */   private IntegerConnectionProperty secondsBeforeRetryMaster = new IntegerConnectionProperty("secondsBeforeRetryMaster", 30, true, 2147483647, Messages.getString("ConnectionProperties.secondsBeforeRetryMaster"), "3.0.2", HA_CATEGORY, 8);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1368 */   private IntegerConnectionProperty selfDestructOnPingSecondsLifetime = new IntegerConnectionProperty("selfDestructOnPingSecondsLifetime", false, false, 2147483647, Messages.getString("ConnectionProperties.selfDestructOnPingSecondsLifetime"), "5.1.6", HA_CATEGORY, 2147483647);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1376 */   private IntegerConnectionProperty selfDestructOnPingMaxOperations = new IntegerConnectionProperty("selfDestructOnPingMaxOperations", false, false, 2147483647, Messages.getString("ConnectionProperties.selfDestructOnPingMaxOperations"), "5.1.6", HA_CATEGORY, 2147483647);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1384 */   private StringConnectionProperty serverTimezone = new StringConnectionProperty("serverTimezone", null, Messages.getString("ConnectionProperties.serverTimezone"), "3.0.2", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1390 */   private StringConnectionProperty sessionVariables = new StringConnectionProperty("sessionVariables", null, Messages.getString("ConnectionProperties.sessionVariables"), "3.1.8", MISC_CATEGORY, 2147483647);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1395 */   private IntegerConnectionProperty slowQueryThresholdMillis = new IntegerConnectionProperty("slowQueryThresholdMillis", 'ߐ', false, 2147483647, Messages.getString("ConnectionProperties.slowQueryThresholdMillis"), "3.1.2", DEBUGING_PROFILING_CATEGORY, 9);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1403 */   private LongConnectionProperty slowQueryThresholdNanos = new LongConnectionProperty("slowQueryThresholdNanos", 0L, Messages.getString("ConnectionProperties.slowQueryThresholdNanos"), "5.0.7", DEBUGING_PROFILING_CATEGORY, 10);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1411 */   private StringConnectionProperty socketFactoryClassName = new StringConnectionProperty("socketFactory", StandardSocketFactory.class.getName(), Messages.getString("ConnectionProperties.socketFactory"), "3.0.3", CONNECTION_AND_AUTH_CATEGORY, 4);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1417 */   private IntegerConnectionProperty socketTimeout = new IntegerConnectionProperty("socketTimeout", false, false, 2147483647, Messages.getString("ConnectionProperties.socketTimeout"), "3.0.1", CONNECTION_AND_AUTH_CATEGORY, 10);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1425 */   private StringConnectionProperty statementInterceptors = new StringConnectionProperty("statementInterceptors", null, Messages.getString("ConnectionProperties.statementInterceptors"), "5.1.1", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */   
/* 1428 */   private BooleanConnectionProperty strictFloatingPoint = new BooleanConnectionProperty("strictFloatingPoint", false, Messages.getString("ConnectionProperties.strictFloatingPoint"), "3.0.0", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1433 */   private BooleanConnectionProperty strictUpdates = new BooleanConnectionProperty("strictUpdates", true, Messages.getString("ConnectionProperties.strictUpdates"), "3.0.4", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1439 */   private BooleanConnectionProperty overrideSupportsIntegrityEnhancementFacility = new BooleanConnectionProperty("overrideSupportsIntegrityEnhancementFacility", false, Messages.getString("ConnectionProperties.overrideSupportsIEF"), "3.1.12", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1445 */   private BooleanConnectionProperty tcpNoDelay = new BooleanConnectionProperty("tcpNoDelay", Boolean.valueOf("true").booleanValue(), Messages.getString("ConnectionProperties.tcpNoDelay"), "5.0.7", NETWORK_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1451 */   private BooleanConnectionProperty tcpKeepAlive = new BooleanConnectionProperty("tcpKeepAlive", Boolean.valueOf("true").booleanValue(), Messages.getString("ConnectionProperties.tcpKeepAlive"), "5.0.7", NETWORK_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1457 */   private IntegerConnectionProperty tcpRcvBuf = new IntegerConnectionProperty("tcpRcvBuf", Integer.parseInt("0"), false, 2147483647, Messages.getString("ConnectionProperties.tcpSoRcvBuf"), "5.0.7", NETWORK_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1464 */   private IntegerConnectionProperty tcpSndBuf = new IntegerConnectionProperty("tcpSndBuf", Integer.parseInt("0"), false, 2147483647, Messages.getString("ConnectionProperties.tcpSoSndBuf"), "5.0.7", NETWORK_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1471 */   private IntegerConnectionProperty tcpTrafficClass = new IntegerConnectionProperty("tcpTrafficClass", Integer.parseInt("0"), false, 'ÿ', Messages.getString("ConnectionProperties.tcpTrafficClass"), "5.0.7", NETWORK_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1478 */   private BooleanConnectionProperty tinyInt1isBit = new BooleanConnectionProperty("tinyInt1isBit", true, Messages.getString("ConnectionProperties.tinyInt1isBit"), "3.0.16", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1484 */   private BooleanConnectionProperty traceProtocol = new BooleanConnectionProperty("traceProtocol", false, Messages.getString("ConnectionProperties.traceProtocol"), "3.1.2", DEBUGING_PROFILING_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1489 */   private BooleanConnectionProperty treatUtilDateAsTimestamp = new BooleanConnectionProperty("treatUtilDateAsTimestamp", true, Messages.getString("ConnectionProperties.treatUtilDateAsTimestamp"), "5.0.5", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1494 */   private BooleanConnectionProperty transformedBitIsBoolean = new BooleanConnectionProperty("transformedBitIsBoolean", false, Messages.getString("ConnectionProperties.transformedBitIsBoolean"), "3.1.9", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1500 */   private BooleanConnectionProperty useBlobToStoreUTF8OutsideBMP = new BooleanConnectionProperty("useBlobToStoreUTF8OutsideBMP", false, Messages.getString("ConnectionProperties.useBlobToStoreUTF8OutsideBMP"), "5.1.3", MISC_CATEGORY, '');
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1506 */   private StringConnectionProperty utf8OutsideBmpExcludedColumnNamePattern = new StringConnectionProperty("utf8OutsideBmpExcludedColumnNamePattern", null, Messages.getString("ConnectionProperties.utf8OutsideBmpExcludedColumnNamePattern"), "5.1.3", MISC_CATEGORY, '');
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1512 */   private StringConnectionProperty utf8OutsideBmpIncludedColumnNamePattern = new StringConnectionProperty("utf8OutsideBmpIncludedColumnNamePattern", null, Messages.getString("ConnectionProperties.utf8OutsideBmpIncludedColumnNamePattern"), "5.1.3", MISC_CATEGORY, '');
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1518 */   private BooleanConnectionProperty useCompression = new BooleanConnectionProperty("useCompression", false, Messages.getString("ConnectionProperties.useCompression"), "3.0.17", CONNECTION_AND_AUTH_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1524 */   private BooleanConnectionProperty useColumnNamesInFindColumn = new BooleanConnectionProperty("useColumnNamesInFindColumn", false, Messages.getString("ConnectionProperties.useColumnNamesInFindColumn"), "5.1.7", MISC_CATEGORY, 2147483647);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1530 */   private StringConnectionProperty useConfigs = new StringConnectionProperty("useConfigs", null, Messages.getString("ConnectionProperties.useConfigs"), "3.1.5", CONNECTION_AND_AUTH_CATEGORY, 2147483647);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1536 */   private BooleanConnectionProperty useCursorFetch = new BooleanConnectionProperty("useCursorFetch", false, Messages.getString("ConnectionProperties.useCursorFetch"), "5.0.0", PERFORMANCE_CATEGORY, 2147483647);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1542 */   private BooleanConnectionProperty useDynamicCharsetInfo = new BooleanConnectionProperty("useDynamicCharsetInfo", true, Messages.getString("ConnectionProperties.useDynamicCharsetInfo"), "5.0.6", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1548 */   private BooleanConnectionProperty useDirectRowUnpack = new BooleanConnectionProperty("useDirectRowUnpack", true, "Use newer result set row unpacking code that skips a copy from network buffers  to a MySQL packet instance and instead reads directly into the result set row data buffers.", "5.1.1", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1554 */   private BooleanConnectionProperty useFastIntParsing = new BooleanConnectionProperty("useFastIntParsing", true, Messages.getString("ConnectionProperties.useFastIntParsing"), "3.1.4", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1560 */   private BooleanConnectionProperty useFastDateParsing = new BooleanConnectionProperty("useFastDateParsing", true, Messages.getString("ConnectionProperties.useFastDateParsing"), "5.0.5", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1566 */   private BooleanConnectionProperty useHostsInPrivileges = new BooleanConnectionProperty("useHostsInPrivileges", true, Messages.getString("ConnectionProperties.useHostsInPrivileges"), "3.0.2", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1571 */   private BooleanConnectionProperty useInformationSchema = new BooleanConnectionProperty("useInformationSchema", false, Messages.getString("ConnectionProperties.useInformationSchema"), "5.0.0", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1576 */   private BooleanConnectionProperty useJDBCCompliantTimezoneShift = new BooleanConnectionProperty("useJDBCCompliantTimezoneShift", false, Messages.getString("ConnectionProperties.useJDBCCompliantTimezoneShift"), "5.0.0", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1583 */   private BooleanConnectionProperty useLocalSessionState = new BooleanConnectionProperty("useLocalSessionState", false, Messages.getString("ConnectionProperties.useLocalSessionState"), "3.1.7", PERFORMANCE_CATEGORY, 5);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1589 */   private BooleanConnectionProperty useLocalTransactionState = new BooleanConnectionProperty("useLocalTransactionState", false, Messages.getString("ConnectionProperties.useLocalTransactionState"), "5.1.7", PERFORMANCE_CATEGORY, 6);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1595 */   private BooleanConnectionProperty useLegacyDatetimeCode = new BooleanConnectionProperty("useLegacyDatetimeCode", true, Messages.getString("ConnectionProperties.useLegacyDatetimeCode"), "5.1.6", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1601 */   private BooleanConnectionProperty useNanosForElapsedTime = new BooleanConnectionProperty("useNanosForElapsedTime", false, Messages.getString("ConnectionProperties.useNanosForElapsedTime"), "5.0.7", DEBUGING_PROFILING_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1608 */   private BooleanConnectionProperty useOldAliasMetadataBehavior = new BooleanConnectionProperty("useOldAliasMetadataBehavior", false, Messages.getString("ConnectionProperties.useOldAliasMetadataBehavior"), "5.0.4", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1616 */   private BooleanConnectionProperty useOldUTF8Behavior = new BooleanConnectionProperty("useOldUTF8Behavior", false, Messages.getString("ConnectionProperties.useOldUtf8Behavior"), "3.1.6", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean useOldUTF8BehaviorAsBoolean = false;
/*      */ 
/*      */ 
/*      */   
/* 1624 */   private BooleanConnectionProperty useOnlyServerErrorMessages = new BooleanConnectionProperty("useOnlyServerErrorMessages", true, Messages.getString("ConnectionProperties.useOnlyServerErrorMessages"), "3.0.15", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1630 */   private BooleanConnectionProperty useReadAheadInput = new BooleanConnectionProperty("useReadAheadInput", true, Messages.getString("ConnectionProperties.useReadAheadInput"), "3.1.5", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1636 */   private BooleanConnectionProperty useSqlStateCodes = new BooleanConnectionProperty("useSqlStateCodes", true, Messages.getString("ConnectionProperties.useSqlStateCodes"), "3.1.3", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1642 */   private BooleanConnectionProperty useSSL = new BooleanConnectionProperty("useSSL", false, Messages.getString("ConnectionProperties.useSSL"), "3.0.2", SECURITY_CATEGORY, 2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1648 */   private BooleanConnectionProperty useSSPSCompatibleTimezoneShift = new BooleanConnectionProperty("useSSPSCompatibleTimezoneShift", false, Messages.getString("ConnectionProperties.useSSPSCompatibleTimezoneShift"), "5.0.5", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1654 */   private BooleanConnectionProperty useStreamLengthsInPrepStmts = new BooleanConnectionProperty("useStreamLengthsInPrepStmts", true, Messages.getString("ConnectionProperties.useStreamLengthsInPrepStmts"), "3.0.2", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1660 */   private BooleanConnectionProperty useTimezone = new BooleanConnectionProperty("useTimezone", false, Messages.getString("ConnectionProperties.useTimezone"), "3.0.2", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1666 */   private BooleanConnectionProperty useUltraDevWorkAround = new BooleanConnectionProperty("ultraDevHack", false, Messages.getString("ConnectionProperties.ultraDevHack"), "2.0.3", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1672 */   private BooleanConnectionProperty useUnbufferedInput = new BooleanConnectionProperty("useUnbufferedInput", true, Messages.getString("ConnectionProperties.useUnbufferedInput"), "3.0.11", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1677 */   private BooleanConnectionProperty useUnicode = new BooleanConnectionProperty("useUnicode", true, Messages.getString("ConnectionProperties.useUnicode"), "1.1g", MISC_CATEGORY, false);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean useUnicodeAsBoolean = true;
/*      */ 
/*      */ 
/*      */   
/* 1686 */   private BooleanConnectionProperty useUsageAdvisor = new BooleanConnectionProperty("useUsageAdvisor", false, Messages.getString("ConnectionProperties.useUsageAdvisor"), "3.1.1", DEBUGING_PROFILING_CATEGORY, 10);
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean useUsageAdvisorAsBoolean = false;
/*      */ 
/*      */ 
/*      */   
/* 1694 */   private BooleanConnectionProperty yearIsDateType = new BooleanConnectionProperty("yearIsDateType", true, Messages.getString("ConnectionProperties.yearIsDateType"), "3.1.9", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1700 */   private StringConnectionProperty zeroDateTimeBehavior = new StringConnectionProperty("zeroDateTimeBehavior", "exception", new String[] { "exception", "round", "convertToNull" }, Messages.getString("ConnectionProperties.zeroDateTimeBehavior", new Object[] { "exception", "round", "convertToNull" }), "3.1.4", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1710 */   private BooleanConnectionProperty useJvmCharsetConverters = new BooleanConnectionProperty("useJvmCharsetConverters", false, Messages.getString("ConnectionProperties.useJvmCharsetConverters"), "5.0.1", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */   
/* 1713 */   private BooleanConnectionProperty useGmtMillisForDatetimes = new BooleanConnectionProperty("useGmtMillisForDatetimes", false, Messages.getString("ConnectionProperties.useGmtMillisForDatetimes"), "3.1.12", MISC_CATEGORY, -2147483648);
/*      */   
/* 1715 */   private BooleanConnectionProperty dumpMetadataOnColumnNotFound = new BooleanConnectionProperty("dumpMetadataOnColumnNotFound", false, Messages.getString("ConnectionProperties.dumpMetadataOnColumnNotFound"), "3.1.13", DEBUGING_PROFILING_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */   
/* 1719 */   private StringConnectionProperty clientCertificateKeyStoreUrl = new StringConnectionProperty("clientCertificateKeyStoreUrl", null, Messages.getString("ConnectionProperties.clientCertificateKeyStoreUrl"), "5.1.0", SECURITY_CATEGORY, 5);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1724 */   private StringConnectionProperty trustCertificateKeyStoreUrl = new StringConnectionProperty("trustCertificateKeyStoreUrl", null, Messages.getString("ConnectionProperties.trustCertificateKeyStoreUrl"), "5.1.0", SECURITY_CATEGORY, 8);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1729 */   private StringConnectionProperty clientCertificateKeyStoreType = new StringConnectionProperty("clientCertificateKeyStoreType", "JKS", Messages.getString("ConnectionProperties.clientCertificateKeyStoreType"), "5.1.0", SECURITY_CATEGORY, 6);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1734 */   private StringConnectionProperty clientCertificateKeyStorePassword = new StringConnectionProperty("clientCertificateKeyStorePassword", null, Messages.getString("ConnectionProperties.clientCertificateKeyStorePassword"), "5.1.0", SECURITY_CATEGORY, 7);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1739 */   private StringConnectionProperty trustCertificateKeyStoreType = new StringConnectionProperty("trustCertificateKeyStoreType", "JKS", Messages.getString("ConnectionProperties.trustCertificateKeyStoreType"), "5.1.0", SECURITY_CATEGORY, 9);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1744 */   private StringConnectionProperty trustCertificateKeyStorePassword = new StringConnectionProperty("trustCertificateKeyStorePassword", null, Messages.getString("ConnectionProperties.trustCertificateKeyStorePassword"), "5.1.0", SECURITY_CATEGORY, 10);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1749 */   private BooleanConnectionProperty verifyServerCertificate = new BooleanConnectionProperty("verifyServerCertificate", true, Messages.getString("ConnectionProperties.verifyServerCertificate"), "5.1.6", SECURITY_CATEGORY, 4);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1755 */   private BooleanConnectionProperty useAffectedRows = new BooleanConnectionProperty("useAffectedRows", false, Messages.getString("ConnectionProperties.useAffectedRows"), "5.1.7", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1760 */   private StringConnectionProperty passwordCharacterEncoding = new StringConnectionProperty("passwordCharacterEncoding", null, Messages.getString("ConnectionProperties.passwordCharacterEncoding"), "5.1.7", SECURITY_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1765 */   private IntegerConnectionProperty maxAllowedPacket = new IntegerConnectionProperty("maxAllowedPacket", -1, Messages.getString("ConnectionProperties.maxAllowedPacket"), "5.1.8", NETWORK_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected DriverPropertyInfo[] exposeAsDriverPropertyInfoInternal(Properties info, int slotsToReserve) throws SQLException {
/* 1771 */     initializeProperties(info);
/*      */     
/* 1773 */     int numProperties = PROPERTY_LIST.size();
/*      */     
/* 1775 */     int listSize = numProperties + slotsToReserve;
/*      */     
/* 1777 */     DriverPropertyInfo[] driverProperties = new DriverPropertyInfo[listSize];
/*      */     
/* 1779 */     for (int i = slotsToReserve; i < listSize; i++) {
/* 1780 */       Field propertyField = (Field)PROPERTY_LIST.get(i - slotsToReserve);
/*      */ 
/*      */       
/*      */       try {
/* 1784 */         ConnectionProperty propToExpose = (ConnectionProperty)propertyField.get(this);
/*      */ 
/*      */         
/* 1787 */         if (info != null) {
/* 1788 */           propToExpose.initializeFrom(info);
/*      */         }
/*      */ 
/*      */         
/* 1792 */         driverProperties[i] = propToExpose.getAsDriverPropertyInfo();
/* 1793 */       } catch (IllegalAccessException iae) {
/* 1794 */         throw SQLError.createSQLException(Messages.getString("ConnectionProperties.InternalPropertiesFailure"), "S1000", getExceptionInterceptor());
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1799 */     return driverProperties;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Properties exposeAsProperties(Properties info) throws SQLException {
/* 1804 */     if (info == null) {
/* 1805 */       info = new Properties();
/*      */     }
/*      */     
/* 1808 */     int numPropertiesToSet = PROPERTY_LIST.size();
/*      */     
/* 1810 */     for (int i = 0; i < numPropertiesToSet; i++) {
/* 1811 */       Field propertyField = (Field)PROPERTY_LIST.get(i);
/*      */ 
/*      */       
/*      */       try {
/* 1815 */         ConnectionProperty propToGet = (ConnectionProperty)propertyField.get(this);
/*      */ 
/*      */         
/* 1818 */         Object propValue = propToGet.getValueAsObject();
/*      */         
/* 1820 */         if (propValue != null) {
/* 1821 */           info.setProperty(propToGet.getPropertyName(), propValue.toString());
/*      */         }
/*      */       }
/* 1824 */       catch (IllegalAccessException iae) {
/* 1825 */         throw SQLError.createSQLException("Internal properties failure", "S1000", getExceptionInterceptor());
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1830 */     return info;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String exposeAsXml() throws SQLException {
/* 1837 */     StringBuffer xmlBuf = new StringBuffer();
/* 1838 */     xmlBuf.append("<ConnectionProperties>");
/*      */     
/* 1840 */     int numPropertiesToSet = PROPERTY_LIST.size();
/*      */     
/* 1842 */     int numCategories = PROPERTY_CATEGORIES.length;
/*      */     
/* 1844 */     Map propertyListByCategory = new HashMap();
/*      */     
/* 1846 */     for (int i = 0; i < numCategories; i++) {
/* 1847 */       propertyListByCategory.put(PROPERTY_CATEGORIES[i], new Map[] { new TreeMap(), new TreeMap() });
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1857 */     StringConnectionProperty userProp = new StringConnectionProperty("user", null, Messages.getString("ConnectionProperties.Username"), Messages.getString("ConnectionProperties.allVersions"), CONNECTION_AND_AUTH_CATEGORY, -2147483647);
/*      */ 
/*      */ 
/*      */     
/* 1861 */     StringConnectionProperty passwordProp = new StringConnectionProperty("password", null, Messages.getString("ConnectionProperties.Password"), Messages.getString("ConnectionProperties.allVersions"), CONNECTION_AND_AUTH_CATEGORY, -2147483646);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1866 */     Map[] connectionSortMaps = (Map[])propertyListByCategory.get(CONNECTION_AND_AUTH_CATEGORY);
/*      */     
/* 1868 */     TreeMap userMap = new TreeMap();
/* 1869 */     userMap.put(userProp.getPropertyName(), userProp);
/*      */     
/* 1871 */     connectionSortMaps[0].put(new Integer(userProp.getOrder()), userMap);
/*      */     
/* 1873 */     TreeMap passwordMap = new TreeMap();
/* 1874 */     passwordMap.put(passwordProp.getPropertyName(), passwordProp);
/*      */     
/* 1876 */     connectionSortMaps[0].put(new Integer(passwordProp.getOrder()), passwordMap);
/*      */ 
/*      */     
/*      */     try {
/* 1880 */       for (i = 0; i < numPropertiesToSet; i++) {
/* 1881 */         Field propertyField = (Field)PROPERTY_LIST.get(i);
/*      */         
/* 1883 */         ConnectionProperty propToGet = (ConnectionProperty)propertyField.get(this);
/*      */         
/* 1885 */         Map[] sortMaps = (Map[])propertyListByCategory.get(propToGet.getCategoryName());
/*      */         
/* 1887 */         int orderInCategory = propToGet.getOrder();
/*      */         
/* 1889 */         if (orderInCategory == Integer.MIN_VALUE) {
/* 1890 */           sortMaps[1].put(propToGet.getPropertyName(), propToGet);
/*      */         } else {
/* 1892 */           Integer order = new Integer(orderInCategory);
/*      */           
/* 1894 */           Map orderMap = (Map)sortMaps[0].get(order);
/*      */           
/* 1896 */           if (orderMap == null) {
/* 1897 */             orderMap = new TreeMap();
/* 1898 */             sortMaps[0].put(order, orderMap);
/*      */           } 
/*      */           
/* 1901 */           orderMap.put(propToGet.getPropertyName(), propToGet);
/*      */         } 
/*      */       } 
/*      */       
/* 1905 */       for (int j = 0; j < numCategories; j++) {
/* 1906 */         Map[] sortMaps = (Map[])propertyListByCategory.get(PROPERTY_CATEGORIES[j]);
/*      */         
/* 1908 */         Iterator orderedIter = sortMaps[0].values().iterator();
/* 1909 */         Iterator alphaIter = sortMaps[1].values().iterator();
/*      */         
/* 1911 */         xmlBuf.append("\n <PropertyCategory name=\"");
/* 1912 */         xmlBuf.append(PROPERTY_CATEGORIES[j]);
/* 1913 */         xmlBuf.append("\">");
/*      */         
/* 1915 */         while (orderedIter.hasNext()) {
/* 1916 */           Iterator orderedAlphaIter = ((Map)orderedIter.next()).values().iterator();
/*      */           
/* 1918 */           while (orderedAlphaIter.hasNext()) {
/* 1919 */             ConnectionProperty propToGet = (ConnectionProperty)orderedAlphaIter.next();
/*      */ 
/*      */             
/* 1922 */             xmlBuf.append("\n  <Property name=\"");
/* 1923 */             xmlBuf.append(propToGet.getPropertyName());
/* 1924 */             xmlBuf.append("\" required=\"");
/* 1925 */             xmlBuf.append(propToGet.required ? "Yes" : "No");
/*      */             
/* 1927 */             xmlBuf.append("\" default=\"");
/*      */             
/* 1929 */             if (propToGet.getDefaultValue() != null) {
/* 1930 */               xmlBuf.append(propToGet.getDefaultValue());
/*      */             }
/*      */             
/* 1933 */             xmlBuf.append("\" sortOrder=\"");
/* 1934 */             xmlBuf.append(propToGet.getOrder());
/* 1935 */             xmlBuf.append("\" since=\"");
/* 1936 */             xmlBuf.append(propToGet.sinceVersion);
/* 1937 */             xmlBuf.append("\">\n");
/* 1938 */             xmlBuf.append("    ");
/* 1939 */             xmlBuf.append(propToGet.description);
/* 1940 */             xmlBuf.append("\n  </Property>");
/*      */           } 
/*      */         } 
/*      */         
/* 1944 */         while (alphaIter.hasNext()) {
/* 1945 */           ConnectionProperty propToGet = (ConnectionProperty)alphaIter.next();
/*      */ 
/*      */           
/* 1948 */           xmlBuf.append("\n  <Property name=\"");
/* 1949 */           xmlBuf.append(propToGet.getPropertyName());
/* 1950 */           xmlBuf.append("\" required=\"");
/* 1951 */           xmlBuf.append(propToGet.required ? "Yes" : "No");
/*      */           
/* 1953 */           xmlBuf.append("\" default=\"");
/*      */           
/* 1955 */           if (propToGet.getDefaultValue() != null) {
/* 1956 */             xmlBuf.append(propToGet.getDefaultValue());
/*      */           }
/*      */           
/* 1959 */           xmlBuf.append("\" sortOrder=\"alpha\" since=\"");
/* 1960 */           xmlBuf.append(propToGet.sinceVersion);
/* 1961 */           xmlBuf.append("\">\n");
/* 1962 */           xmlBuf.append("    ");
/* 1963 */           xmlBuf.append(propToGet.description);
/* 1964 */           xmlBuf.append("\n  </Property>");
/*      */         } 
/*      */         
/* 1967 */         xmlBuf.append("\n </PropertyCategory>");
/*      */       } 
/* 1969 */     } catch (IllegalAccessException iae) {
/* 1970 */       throw SQLError.createSQLException("Internal properties failure", "S1000", getExceptionInterceptor());
/*      */     } 
/*      */ 
/*      */     
/* 1974 */     xmlBuf.append("\n</ConnectionProperties>");
/*      */     
/* 1976 */     return xmlBuf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1983 */   public boolean getAllowLoadLocalInfile() { return this.allowLoadLocalInfile.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1990 */   public boolean getAllowMultiQueries() { return this.allowMultiQueries.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1997 */   public boolean getAllowNanAndInf() { return this.allowNanAndInf.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2004 */   public boolean getAllowUrlInLocalInfile() { return this.allowUrlInLocalInfile.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2011 */   public boolean getAlwaysSendSetIsolation() { return this.alwaysSendSetIsolation.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2018 */   public boolean getAutoDeserialize() { return this.autoDeserialize.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2025 */   public boolean getAutoGenerateTestcaseScript() { return this.autoGenerateTestcaseScriptAsBoolean; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2032 */   public boolean getAutoReconnectForPools() { return this.autoReconnectForPoolsAsBoolean; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2039 */   public int getBlobSendChunkSize() { return this.blobSendChunkSize.getValueAsInt(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2046 */   public boolean getCacheCallableStatements() { return this.cacheCallableStatements.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2053 */   public boolean getCachePreparedStatements() { return ((Boolean)this.cachePreparedStatements.getValueAsObject()).booleanValue(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2061 */   public boolean getCacheResultSetMetadata() { return this.cacheResultSetMetaDataAsBoolean; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2068 */   public boolean getCacheServerConfiguration() { return this.cacheServerConfiguration.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2075 */   public int getCallableStatementCacheSize() { return this.callableStatementCacheSize.getValueAsInt(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2082 */   public boolean getCapitalizeTypeNames() { return this.capitalizeTypeNames.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2089 */   public String getCharacterSetResults() throws SQLException { return this.characterSetResults.getValueAsString(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2096 */   public boolean getClobberStreamingResults() { return this.clobberStreamingResults.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2103 */   public String getClobCharacterEncoding() throws SQLException { return this.clobCharacterEncoding.getValueAsString(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2110 */   public String getConnectionCollation() throws SQLException { return this.connectionCollation.getValueAsString(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2117 */   public int getConnectTimeout() { return this.connectTimeout.getValueAsInt(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2124 */   public boolean getContinueBatchOnError() { return this.continueBatchOnError.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2131 */   public boolean getCreateDatabaseIfNotExist() { return this.createDatabaseIfNotExist.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2138 */   public int getDefaultFetchSize() { return this.defaultFetchSize.getValueAsInt(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2145 */   public boolean getDontTrackOpenResources() { return this.dontTrackOpenResources.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2152 */   public boolean getDumpQueriesOnException() { return this.dumpQueriesOnException.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2159 */   public boolean getDynamicCalendars() { return this.dynamicCalendars.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2166 */   public boolean getElideSetAutoCommits() { return this.elideSetAutoCommits.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2173 */   public boolean getEmptyStringsConvertToZero() { return this.emptyStringsConvertToZero.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2180 */   public boolean getEmulateLocators() { return this.emulateLocators.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2187 */   public boolean getEmulateUnsupportedPstmts() { return this.emulateUnsupportedPstmts.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2194 */   public boolean getEnablePacketDebug() { return this.enablePacketDebug.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2201 */   public String getEncoding() throws SQLException { return this.characterEncodingAsString; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2208 */   public boolean getExplainSlowQueries() { return this.explainSlowQueries.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2215 */   public boolean getFailOverReadOnly() { return this.failOverReadOnly.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2222 */   public boolean getGatherPerformanceMetrics() { return this.gatherPerformanceMetrics.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2231 */   protected boolean getHighAvailability() { return this.highAvailabilityAsBoolean; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2238 */   public boolean getHoldResultsOpenOverStatementClose() { return this.holdResultsOpenOverStatementClose.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2245 */   public boolean getIgnoreNonTxTables() { return this.ignoreNonTxTables.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2252 */   public int getInitialTimeout() { return this.initialTimeout.getValueAsInt(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2259 */   public boolean getInteractiveClient() { return this.isInteractiveClient.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2266 */   public boolean getIsInteractiveClient() { return this.isInteractiveClient.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2273 */   public boolean getJdbcCompliantTruncation() { return this.jdbcCompliantTruncation.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2280 */   public int getLocatorFetchBufferSize() { return this.locatorFetchBufferSize.getValueAsInt(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2287 */   public String getLogger() throws SQLException { return this.loggerClassName.getValueAsString(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2294 */   public String getLoggerClassName() throws SQLException { return this.loggerClassName.getValueAsString(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2301 */   public boolean getLogSlowQueries() { return this.logSlowQueries.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2308 */   public boolean getMaintainTimeStats() { return this.maintainTimeStatsAsBoolean; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2315 */   public int getMaxQuerySizeToLog() { return this.maxQuerySizeToLog.getValueAsInt(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2322 */   public int getMaxReconnects() { return this.maxReconnects.getValueAsInt(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2329 */   public int getMaxRows() { return this.maxRowsAsInt; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2336 */   public int getMetadataCacheSize() { return this.metadataCacheSize.getValueAsInt(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2343 */   public boolean getNoDatetimeStringSync() { return this.noDatetimeStringSync.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2350 */   public boolean getNullCatalogMeansCurrent() { return this.nullCatalogMeansCurrent.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2357 */   public boolean getNullNamePatternMatchesAll() { return this.nullNamePatternMatchesAll.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2364 */   public int getPacketDebugBufferSize() { return this.packetDebugBufferSize.getValueAsInt(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2371 */   public boolean getParanoid() { return this.paranoid.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2378 */   public boolean getPedantic() { return this.pedantic.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2385 */   public int getPreparedStatementCacheSize() { return ((Integer)this.preparedStatementCacheSize.getValueAsObject()).intValue(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2393 */   public int getPreparedStatementCacheSqlLimit() { return ((Integer)this.preparedStatementCacheSqlLimit.getValueAsObject()).intValue(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2401 */   public boolean getProfileSql() { return this.profileSQLAsBoolean; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2408 */   public boolean getProfileSQL() { return this.profileSQL.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2415 */   public String getPropertiesTransform() throws SQLException { return this.propertiesTransform.getValueAsString(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2422 */   public int getQueriesBeforeRetryMaster() { return this.queriesBeforeRetryMaster.getValueAsInt(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2429 */   public boolean getReconnectAtTxEnd() { return this.reconnectTxAtEndAsBoolean; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2436 */   public boolean getRelaxAutoCommit() { return this.relaxAutoCommit.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2443 */   public int getReportMetricsIntervalMillis() { return this.reportMetricsIntervalMillis.getValueAsInt(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2450 */   public boolean getRequireSSL() { return this.requireSSL.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */   
/* 2454 */   public boolean getRetainStatementAfterResultSetClose() { return this.retainStatementAfterResultSetClose.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2461 */   public boolean getRollbackOnPooledClose() { return this.rollbackOnPooledClose.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2468 */   public boolean getRoundRobinLoadBalance() { return this.roundRobinLoadBalance.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2475 */   public boolean getRunningCTS13() { return this.runningCTS13.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2482 */   public int getSecondsBeforeRetryMaster() { return this.secondsBeforeRetryMaster.getValueAsInt(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2489 */   public String getServerTimezone() throws SQLException { return this.serverTimezone.getValueAsString(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2496 */   public String getSessionVariables() throws SQLException { return this.sessionVariables.getValueAsString(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2503 */   public int getSlowQueryThresholdMillis() { return this.slowQueryThresholdMillis.getValueAsInt(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2510 */   public String getSocketFactoryClassName() throws SQLException { return this.socketFactoryClassName.getValueAsString(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2517 */   public int getSocketTimeout() { return this.socketTimeout.getValueAsInt(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2524 */   public boolean getStrictFloatingPoint() { return this.strictFloatingPoint.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2531 */   public boolean getStrictUpdates() { return this.strictUpdates.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2538 */   public boolean getTinyInt1isBit() { return this.tinyInt1isBit.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2545 */   public boolean getTraceProtocol() { return this.traceProtocol.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2552 */   public boolean getTransformedBitIsBoolean() { return this.transformedBitIsBoolean.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2559 */   public boolean getUseCompression() { return this.useCompression.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2566 */   public boolean getUseFastIntParsing() { return this.useFastIntParsing.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2573 */   public boolean getUseHostsInPrivileges() { return this.useHostsInPrivileges.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2580 */   public boolean getUseInformationSchema() { return this.useInformationSchema.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2587 */   public boolean getUseLocalSessionState() { return this.useLocalSessionState.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2594 */   public boolean getUseOldUTF8Behavior() { return this.useOldUTF8BehaviorAsBoolean; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2601 */   public boolean getUseOnlyServerErrorMessages() { return this.useOnlyServerErrorMessages.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2608 */   public boolean getUseReadAheadInput() { return this.useReadAheadInput.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2615 */   public boolean getUseServerPreparedStmts() { return this.detectServerPreparedStmts.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2622 */   public boolean getUseSqlStateCodes() { return this.useSqlStateCodes.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2629 */   public boolean getUseSSL() { return this.useSSL.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2636 */   public boolean getUseStreamLengthsInPrepStmts() { return this.useStreamLengthsInPrepStmts.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2643 */   public boolean getUseTimezone() { return this.useTimezone.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2650 */   public boolean getUseUltraDevWorkAround() { return this.useUltraDevWorkAround.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2657 */   public boolean getUseUnbufferedInput() { return this.useUnbufferedInput.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2664 */   public boolean getUseUnicode() { return this.useUnicodeAsBoolean; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2671 */   public boolean getUseUsageAdvisor() { return this.useUsageAdvisorAsBoolean; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2678 */   public boolean getYearIsDateType() { return this.yearIsDateType.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2685 */   public String getZeroDateTimeBehavior() throws SQLException { return this.zeroDateTimeBehavior.getValueAsString(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void initializeFromRef(Reference ref) throws SQLException {
/* 2699 */     int numPropertiesToSet = PROPERTY_LIST.size();
/*      */     
/* 2701 */     for (int i = 0; i < numPropertiesToSet; i++) {
/* 2702 */       Field propertyField = (Field)PROPERTY_LIST.get(i);
/*      */ 
/*      */       
/*      */       try {
/* 2706 */         ConnectionProperty propToSet = (ConnectionProperty)propertyField.get(this);
/*      */ 
/*      */         
/* 2709 */         if (ref != null) {
/* 2710 */           propToSet.initializeFrom(ref);
/*      */         }
/* 2712 */       } catch (IllegalAccessException iae) {
/* 2713 */         throw SQLError.createSQLException("Internal properties failure", "S1000", getExceptionInterceptor());
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2718 */     postInitialization();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void initializeProperties(Properties info) throws SQLException {
/* 2731 */     if (info != null) {
/*      */       
/* 2733 */       String profileSqlLc = info.getProperty("profileSql");
/*      */       
/* 2735 */       if (profileSqlLc != null) {
/* 2736 */         info.put("profileSQL", profileSqlLc);
/*      */       }
/*      */       
/* 2739 */       Properties infoCopy = (Properties)info.clone();
/*      */       
/* 2741 */       infoCopy.remove("HOST");
/* 2742 */       infoCopy.remove("user");
/* 2743 */       infoCopy.remove("password");
/* 2744 */       infoCopy.remove("DBNAME");
/* 2745 */       infoCopy.remove("PORT");
/* 2746 */       infoCopy.remove("profileSql");
/*      */       
/* 2748 */       int numPropertiesToSet = PROPERTY_LIST.size();
/*      */       
/* 2750 */       for (int i = 0; i < numPropertiesToSet; i++) {
/* 2751 */         Field propertyField = (Field)PROPERTY_LIST.get(i);
/*      */ 
/*      */         
/*      */         try {
/* 2755 */           ConnectionProperty propToSet = (ConnectionProperty)propertyField.get(this);
/*      */ 
/*      */           
/* 2758 */           propToSet.initializeFrom(infoCopy);
/* 2759 */         } catch (IllegalAccessException iae) {
/* 2760 */           throw SQLError.createSQLException(Messages.getString("ConnectionProperties.unableToInitDriverProperties") + iae.toString(), "S1000", getExceptionInterceptor());
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2767 */       postInitialization();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void postInitialization() {
/* 2774 */     if (this.profileSql.getValueAsObject() != null) {
/* 2775 */       this.profileSQL.initializeFrom(this.profileSql.getValueAsObject().toString());
/*      */     }
/*      */ 
/*      */     
/* 2779 */     this.reconnectTxAtEndAsBoolean = ((Boolean)this.reconnectAtTxEnd.getValueAsObject()).booleanValue();
/*      */ 
/*      */ 
/*      */     
/* 2783 */     if (getMaxRows() == 0)
/*      */     {
/*      */       
/* 2786 */       this.maxRows.setValueAsObject(Constants.integerValueOf(-1));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2792 */     String testEncoding = getEncoding();
/*      */     
/* 2794 */     if (testEncoding != null) {
/*      */       
/*      */       try {
/*      */         
/* 2798 */         String testString = "abc";
/* 2799 */         testString.getBytes(testEncoding);
/* 2800 */       } catch (UnsupportedEncodingException UE) {
/* 2801 */         throw SQLError.createSQLException(Messages.getString("ConnectionProperties.unsupportedCharacterEncoding", new Object[] { testEncoding }), "0S100", getExceptionInterceptor());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2810 */     if (((Boolean)this.cacheResultSetMetadata.getValueAsObject()).booleanValue()) {
/*      */       
/*      */       try {
/* 2813 */         Class.forName("java.util.LinkedHashMap");
/* 2814 */       } catch (ClassNotFoundException cnfe) {
/* 2815 */         this.cacheResultSetMetadata.setValue(false);
/*      */       } 
/*      */     }
/*      */     
/* 2819 */     this.cacheResultSetMetaDataAsBoolean = this.cacheResultSetMetadata.getValueAsBoolean();
/*      */     
/* 2821 */     this.useUnicodeAsBoolean = this.useUnicode.getValueAsBoolean();
/* 2822 */     this.characterEncodingAsString = (String)this.characterEncoding.getValueAsObject();
/*      */     
/* 2824 */     this.highAvailabilityAsBoolean = this.autoReconnect.getValueAsBoolean();
/* 2825 */     this.autoReconnectForPoolsAsBoolean = this.autoReconnectForPools.getValueAsBoolean();
/*      */     
/* 2827 */     this.maxRowsAsInt = ((Integer)this.maxRows.getValueAsObject()).intValue();
/*      */     
/* 2829 */     this.profileSQLAsBoolean = this.profileSQL.getValueAsBoolean();
/* 2830 */     this.useUsageAdvisorAsBoolean = this.useUsageAdvisor.getValueAsBoolean();
/*      */     
/* 2832 */     this.useOldUTF8BehaviorAsBoolean = this.useOldUTF8Behavior.getValueAsBoolean();
/*      */     
/* 2834 */     this.autoGenerateTestcaseScriptAsBoolean = this.autoGenerateTestcaseScript.getValueAsBoolean();
/*      */     
/* 2836 */     this.maintainTimeStatsAsBoolean = this.maintainTimeStats.getValueAsBoolean();
/*      */     
/* 2838 */     this.jdbcCompliantTruncationForReads = getJdbcCompliantTruncation();
/*      */     
/* 2840 */     if (getUseCursorFetch())
/*      */     {
/*      */       
/* 2843 */       setDetectServerPreparedStmts(true);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2851 */   public void setAllowLoadLocalInfile(boolean property) { this.allowLoadLocalInfile.setValue(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2858 */   public void setAllowMultiQueries(boolean property) { this.allowMultiQueries.setValue(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2865 */   public void setAllowNanAndInf(boolean flag) { this.allowNanAndInf.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2872 */   public void setAllowUrlInLocalInfile(boolean flag) { this.allowUrlInLocalInfile.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2879 */   public void setAlwaysSendSetIsolation(boolean flag) { this.alwaysSendSetIsolation.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2886 */   public void setAutoDeserialize(boolean flag) { this.autoDeserialize.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAutoGenerateTestcaseScript(boolean flag) {
/* 2893 */     this.autoGenerateTestcaseScript.setValue(flag);
/* 2894 */     this.autoGenerateTestcaseScriptAsBoolean = this.autoGenerateTestcaseScript.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2902 */   public void setAutoReconnect(boolean flag) { this.autoReconnect.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAutoReconnectForConnectionPools(boolean property) {
/* 2909 */     this.autoReconnectForPools.setValue(property);
/* 2910 */     this.autoReconnectForPoolsAsBoolean = this.autoReconnectForPools.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2918 */   public void setAutoReconnectForPools(boolean flag) { this.autoReconnectForPools.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2925 */   public void setBlobSendChunkSize(String value) throws SQLException { this.blobSendChunkSize.setValue(value); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2932 */   public void setCacheCallableStatements(boolean flag) { this.cacheCallableStatements.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2939 */   public void setCachePreparedStatements(boolean flag) { this.cachePreparedStatements.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCacheResultSetMetadata(boolean property) {
/* 2946 */     this.cacheResultSetMetadata.setValue(property);
/* 2947 */     this.cacheResultSetMetaDataAsBoolean = this.cacheResultSetMetadata.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2955 */   public void setCacheServerConfiguration(boolean flag) { this.cacheServerConfiguration.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2962 */   public void setCallableStatementCacheSize(int size) { this.callableStatementCacheSize.setValue(size); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2969 */   public void setCapitalizeDBMDTypes(boolean property) { this.capitalizeTypeNames.setValue(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2976 */   public void setCapitalizeTypeNames(boolean flag) { this.capitalizeTypeNames.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2983 */   public void setCharacterEncoding(String encoding) throws SQLException { this.characterEncoding.setValue(encoding); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2990 */   public void setCharacterSetResults(String characterSet) throws SQLException { this.characterSetResults.setValue(characterSet); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2997 */   public void setClobberStreamingResults(boolean flag) { this.clobberStreamingResults.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3004 */   public void setClobCharacterEncoding(String encoding) throws SQLException { this.clobCharacterEncoding.setValue(encoding); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3011 */   public void setConnectionCollation(String collation) throws SQLException { this.connectionCollation.setValue(collation); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3018 */   public void setConnectTimeout(int timeoutMs) { this.connectTimeout.setValue(timeoutMs); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3025 */   public void setContinueBatchOnError(boolean property) { this.continueBatchOnError.setValue(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3032 */   public void setCreateDatabaseIfNotExist(boolean flag) { this.createDatabaseIfNotExist.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3039 */   public void setDefaultFetchSize(int n) { this.defaultFetchSize.setValue(n); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3046 */   public void setDetectServerPreparedStmts(boolean property) { this.detectServerPreparedStmts.setValue(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3053 */   public void setDontTrackOpenResources(boolean flag) { this.dontTrackOpenResources.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3060 */   public void setDumpQueriesOnException(boolean flag) { this.dumpQueriesOnException.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3067 */   public void setDynamicCalendars(boolean flag) { this.dynamicCalendars.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3074 */   public void setElideSetAutoCommits(boolean flag) { this.elideSetAutoCommits.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3081 */   public void setEmptyStringsConvertToZero(boolean flag) { this.emptyStringsConvertToZero.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3088 */   public void setEmulateLocators(boolean property) { this.emulateLocators.setValue(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3095 */   public void setEmulateUnsupportedPstmts(boolean flag) { this.emulateUnsupportedPstmts.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3102 */   public void setEnablePacketDebug(boolean flag) { this.enablePacketDebug.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEncoding(String property) throws SQLException {
/* 3109 */     this.characterEncoding.setValue(property);
/* 3110 */     this.characterEncodingAsString = this.characterEncoding.getValueAsString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3118 */   public void setExplainSlowQueries(boolean flag) { this.explainSlowQueries.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3125 */   public void setFailOverReadOnly(boolean flag) { this.failOverReadOnly.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3132 */   public void setGatherPerformanceMetrics(boolean flag) { this.gatherPerformanceMetrics.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setHighAvailability(boolean property) {
/* 3141 */     this.autoReconnect.setValue(property);
/* 3142 */     this.highAvailabilityAsBoolean = this.autoReconnect.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3149 */   public void setHoldResultsOpenOverStatementClose(boolean flag) { this.holdResultsOpenOverStatementClose.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3156 */   public void setIgnoreNonTxTables(boolean property) { this.ignoreNonTxTables.setValue(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3163 */   public void setInitialTimeout(int property) { this.initialTimeout.setValue(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3170 */   public void setIsInteractiveClient(boolean property) { this.isInteractiveClient.setValue(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3177 */   public void setJdbcCompliantTruncation(boolean flag) { this.jdbcCompliantTruncation.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3184 */   public void setLocatorFetchBufferSize(String value) throws SQLException { this.locatorFetchBufferSize.setValue(value); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3191 */   public void setLogger(String property) throws SQLException { this.loggerClassName.setValueAsObject(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3198 */   public void setLoggerClassName(String className) throws SQLException { this.loggerClassName.setValue(className); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3205 */   public void setLogSlowQueries(boolean flag) { this.logSlowQueries.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaintainTimeStats(boolean flag) {
/* 3212 */     this.maintainTimeStats.setValue(flag);
/* 3213 */     this.maintainTimeStatsAsBoolean = this.maintainTimeStats.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3221 */   public void setMaxQuerySizeToLog(int sizeInBytes) { this.maxQuerySizeToLog.setValue(sizeInBytes); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3228 */   public void setMaxReconnects(int property) { this.maxReconnects.setValue(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaxRows(int property) {
/* 3235 */     this.maxRows.setValue(property);
/* 3236 */     this.maxRowsAsInt = this.maxRows.getValueAsInt();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3243 */   public void setMetadataCacheSize(int value) { this.metadataCacheSize.setValue(value); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3250 */   public void setNoDatetimeStringSync(boolean flag) { this.noDatetimeStringSync.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3257 */   public void setNullCatalogMeansCurrent(boolean value) { this.nullCatalogMeansCurrent.setValue(value); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3264 */   public void setNullNamePatternMatchesAll(boolean value) { this.nullNamePatternMatchesAll.setValue(value); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3271 */   public void setPacketDebugBufferSize(int size) { this.packetDebugBufferSize.setValue(size); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3278 */   public void setParanoid(boolean property) { this.paranoid.setValue(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3285 */   public void setPedantic(boolean property) { this.pedantic.setValue(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3292 */   public void setPreparedStatementCacheSize(int cacheSize) { this.preparedStatementCacheSize.setValue(cacheSize); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3299 */   public void setPreparedStatementCacheSqlLimit(int cacheSqlLimit) { this.preparedStatementCacheSqlLimit.setValue(cacheSqlLimit); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setProfileSql(boolean property) {
/* 3306 */     this.profileSQL.setValue(property);
/* 3307 */     this.profileSQLAsBoolean = this.profileSQL.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3314 */   public void setProfileSQL(boolean flag) { this.profileSQL.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3321 */   public void setPropertiesTransform(String value) throws SQLException { this.propertiesTransform.setValue(value); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3328 */   public void setQueriesBeforeRetryMaster(int property) { this.queriesBeforeRetryMaster.setValue(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setReconnectAtTxEnd(boolean property) {
/* 3335 */     this.reconnectAtTxEnd.setValue(property);
/* 3336 */     this.reconnectTxAtEndAsBoolean = this.reconnectAtTxEnd.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3344 */   public void setRelaxAutoCommit(boolean property) { this.relaxAutoCommit.setValue(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3351 */   public void setReportMetricsIntervalMillis(int millis) { this.reportMetricsIntervalMillis.setValue(millis); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3358 */   public void setRequireSSL(boolean property) { this.requireSSL.setValue(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3365 */   public void setRetainStatementAfterResultSetClose(boolean flag) { this.retainStatementAfterResultSetClose.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3372 */   public void setRollbackOnPooledClose(boolean flag) { this.rollbackOnPooledClose.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3379 */   public void setRoundRobinLoadBalance(boolean flag) { this.roundRobinLoadBalance.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3386 */   public void setRunningCTS13(boolean flag) { this.runningCTS13.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3393 */   public void setSecondsBeforeRetryMaster(int property) { this.secondsBeforeRetryMaster.setValue(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3400 */   public void setServerTimezone(String property) throws SQLException { this.serverTimezone.setValue(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3407 */   public void setSessionVariables(String variables) throws SQLException { this.sessionVariables.setValue(variables); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3414 */   public void setSlowQueryThresholdMillis(int millis) { this.slowQueryThresholdMillis.setValue(millis); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3421 */   public void setSocketFactoryClassName(String property) throws SQLException { this.socketFactoryClassName.setValue(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3428 */   public void setSocketTimeout(int property) { this.socketTimeout.setValue(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3435 */   public void setStrictFloatingPoint(boolean property) { this.strictFloatingPoint.setValue(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3442 */   public void setStrictUpdates(boolean property) { this.strictUpdates.setValue(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3449 */   public void setTinyInt1isBit(boolean flag) { this.tinyInt1isBit.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3456 */   public void setTraceProtocol(boolean flag) { this.traceProtocol.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3463 */   public void setTransformedBitIsBoolean(boolean flag) { this.transformedBitIsBoolean.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3470 */   public void setUseCompression(boolean property) { this.useCompression.setValue(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3477 */   public void setUseFastIntParsing(boolean flag) { this.useFastIntParsing.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3484 */   public void setUseHostsInPrivileges(boolean property) { this.useHostsInPrivileges.setValue(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3491 */   public void setUseInformationSchema(boolean flag) { this.useInformationSchema.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3498 */   public void setUseLocalSessionState(boolean flag) { this.useLocalSessionState.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseOldUTF8Behavior(boolean flag) {
/* 3505 */     this.useOldUTF8Behavior.setValue(flag);
/* 3506 */     this.useOldUTF8BehaviorAsBoolean = this.useOldUTF8Behavior.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3514 */   public void setUseOnlyServerErrorMessages(boolean flag) { this.useOnlyServerErrorMessages.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3521 */   public void setUseReadAheadInput(boolean flag) { this.useReadAheadInput.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3528 */   public void setUseServerPreparedStmts(boolean flag) { this.detectServerPreparedStmts.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3535 */   public void setUseSqlStateCodes(boolean flag) { this.useSqlStateCodes.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3542 */   public void setUseSSL(boolean property) { this.useSSL.setValue(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3549 */   public void setUseStreamLengthsInPrepStmts(boolean property) { this.useStreamLengthsInPrepStmts.setValue(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3556 */   public void setUseTimezone(boolean property) { this.useTimezone.setValue(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3563 */   public void setUseUltraDevWorkAround(boolean property) { this.useUltraDevWorkAround.setValue(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3570 */   public void setUseUnbufferedInput(boolean flag) { this.useUnbufferedInput.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseUnicode(boolean flag) {
/* 3577 */     this.useUnicode.setValue(flag);
/* 3578 */     this.useUnicodeAsBoolean = this.useUnicode.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseUsageAdvisor(boolean useUsageAdvisorFlag) {
/* 3585 */     this.useUsageAdvisor.setValue(useUsageAdvisorFlag);
/* 3586 */     this.useUsageAdvisorAsBoolean = this.useUsageAdvisor.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3594 */   public void setYearIsDateType(boolean flag) { this.yearIsDateType.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3601 */   public void setZeroDateTimeBehavior(String behavior) throws SQLException { this.zeroDateTimeBehavior.setValue(behavior); }
/*      */ 
/*      */   
/*      */   protected void storeToRef(Reference ref) throws SQLException {
/* 3605 */     int numPropertiesToSet = PROPERTY_LIST.size();
/*      */     
/* 3607 */     for (int i = 0; i < numPropertiesToSet; i++) {
/* 3608 */       Field propertyField = (Field)PROPERTY_LIST.get(i);
/*      */ 
/*      */       
/*      */       try {
/* 3612 */         ConnectionProperty propToStore = (ConnectionProperty)propertyField.get(this);
/*      */ 
/*      */         
/* 3615 */         if (ref != null) {
/* 3616 */           propToStore.storeTo(ref);
/*      */         }
/* 3618 */       } catch (IllegalAccessException iae) {
/* 3619 */         throw SQLError.createSQLException(Messages.getString("ConnectionProperties.errorNotExpected"), getExceptionInterceptor());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3628 */   public boolean useUnbufferedInput() { return this.useUnbufferedInput.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3635 */   public boolean getUseCursorFetch() { return this.useCursorFetch.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3642 */   public void setUseCursorFetch(boolean flag) { this.useCursorFetch.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3649 */   public boolean getOverrideSupportsIntegrityEnhancementFacility() { return this.overrideSupportsIntegrityEnhancementFacility.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3656 */   public void setOverrideSupportsIntegrityEnhancementFacility(boolean flag) { this.overrideSupportsIntegrityEnhancementFacility.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3663 */   public boolean getNoTimezoneConversionForTimeType() { return this.noTimezoneConversionForTimeType.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3670 */   public void setNoTimezoneConversionForTimeType(boolean flag) { this.noTimezoneConversionForTimeType.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3677 */   public boolean getUseJDBCCompliantTimezoneShift() { return this.useJDBCCompliantTimezoneShift.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3684 */   public void setUseJDBCCompliantTimezoneShift(boolean flag) { this.useJDBCCompliantTimezoneShift.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3691 */   public boolean getAutoClosePStmtStreams() { return this.autoClosePStmtStreams.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3698 */   public void setAutoClosePStmtStreams(boolean flag) { this.autoClosePStmtStreams.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3705 */   public boolean getProcessEscapeCodesForPrepStmts() { return this.processEscapeCodesForPrepStmts.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3712 */   public void setProcessEscapeCodesForPrepStmts(boolean flag) { this.processEscapeCodesForPrepStmts.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3719 */   public boolean getUseGmtMillisForDatetimes() { return this.useGmtMillisForDatetimes.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3726 */   public void setUseGmtMillisForDatetimes(boolean flag) { this.useGmtMillisForDatetimes.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3733 */   public boolean getDumpMetadataOnColumnNotFound() { return this.dumpMetadataOnColumnNotFound.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3740 */   public void setDumpMetadataOnColumnNotFound(boolean flag) { this.dumpMetadataOnColumnNotFound.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3747 */   public String getResourceId() throws SQLException { return this.resourceId.getValueAsString(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3754 */   public void setResourceId(String resourceId) throws SQLException { this.resourceId.setValue(resourceId); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3761 */   public boolean getRewriteBatchedStatements() { return this.rewriteBatchedStatements.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3768 */   public void setRewriteBatchedStatements(boolean flag) { this.rewriteBatchedStatements.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3775 */   public boolean getJdbcCompliantTruncationForReads() { return this.jdbcCompliantTruncationForReads; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3783 */   public void setJdbcCompliantTruncationForReads(boolean jdbcCompliantTruncationForReads) { this.jdbcCompliantTruncationForReads = jdbcCompliantTruncationForReads; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3790 */   public boolean getUseJvmCharsetConverters() { return this.useJvmCharsetConverters.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3797 */   public void setUseJvmCharsetConverters(boolean flag) { this.useJvmCharsetConverters.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3804 */   public boolean getPinGlobalTxToPhysicalConnection() { return this.pinGlobalTxToPhysicalConnection.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3811 */   public void setPinGlobalTxToPhysicalConnection(boolean flag) { this.pinGlobalTxToPhysicalConnection.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3823 */   public void setGatherPerfMetrics(boolean flag) { setGatherPerformanceMetrics(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3830 */   public boolean getGatherPerfMetrics() { return getGatherPerformanceMetrics(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3837 */   public void setUltraDevHack(boolean flag) { setUseUltraDevWorkAround(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3844 */   public boolean getUltraDevHack() { return getUseUltraDevWorkAround(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3851 */   public void setInteractiveClient(boolean property) { setIsInteractiveClient(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3858 */   public void setSocketFactory(String name) throws SQLException { setSocketFactoryClassName(name); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3865 */   public String getSocketFactory() throws SQLException { return getSocketFactoryClassName(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3872 */   public void setUseServerPrepStmts(boolean flag) { setUseServerPreparedStmts(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3879 */   public boolean getUseServerPrepStmts() { return getUseServerPreparedStmts(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3886 */   public void setCacheCallableStmts(boolean flag) { setCacheCallableStatements(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3893 */   public boolean getCacheCallableStmts() { return getCacheCallableStatements(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3900 */   public void setCachePrepStmts(boolean flag) { setCachePreparedStatements(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3907 */   public boolean getCachePrepStmts() { return getCachePreparedStatements(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3914 */   public void setCallableStmtCacheSize(int cacheSize) { setCallableStatementCacheSize(cacheSize); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3921 */   public int getCallableStmtCacheSize() { return getCallableStatementCacheSize(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3928 */   public void setPrepStmtCacheSize(int cacheSize) { setPreparedStatementCacheSize(cacheSize); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3935 */   public int getPrepStmtCacheSize() { return getPreparedStatementCacheSize(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3942 */   public void setPrepStmtCacheSqlLimit(int sqlLimit) { setPreparedStatementCacheSqlLimit(sqlLimit); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3949 */   public int getPrepStmtCacheSqlLimit() { return getPreparedStatementCacheSqlLimit(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3956 */   public boolean getNoAccessToProcedureBodies() { return this.noAccessToProcedureBodies.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3963 */   public void setNoAccessToProcedureBodies(boolean flag) { this.noAccessToProcedureBodies.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3970 */   public boolean getUseOldAliasMetadataBehavior() { return this.useOldAliasMetadataBehavior.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3977 */   public void setUseOldAliasMetadataBehavior(boolean flag) { this.useOldAliasMetadataBehavior.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3984 */   public String getClientCertificateKeyStorePassword() throws SQLException { return this.clientCertificateKeyStorePassword.getValueAsString(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3992 */   public void setClientCertificateKeyStorePassword(String value) throws SQLException { this.clientCertificateKeyStorePassword.setValue(value); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3999 */   public String getClientCertificateKeyStoreType() throws SQLException { return this.clientCertificateKeyStoreType.getValueAsString(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4007 */   public void setClientCertificateKeyStoreType(String value) throws SQLException { this.clientCertificateKeyStoreType.setValue(value); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4014 */   public String getClientCertificateKeyStoreUrl() throws SQLException { return this.clientCertificateKeyStoreUrl.getValueAsString(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4022 */   public void setClientCertificateKeyStoreUrl(String value) throws SQLException { this.clientCertificateKeyStoreUrl.setValue(value); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4029 */   public String getTrustCertificateKeyStorePassword() throws SQLException { return this.trustCertificateKeyStorePassword.getValueAsString(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4037 */   public void setTrustCertificateKeyStorePassword(String value) throws SQLException { this.trustCertificateKeyStorePassword.setValue(value); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4044 */   public String getTrustCertificateKeyStoreType() throws SQLException { return this.trustCertificateKeyStoreType.getValueAsString(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4052 */   public void setTrustCertificateKeyStoreType(String value) throws SQLException { this.trustCertificateKeyStoreType.setValue(value); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4059 */   public String getTrustCertificateKeyStoreUrl() throws SQLException { return this.trustCertificateKeyStoreUrl.getValueAsString(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4067 */   public void setTrustCertificateKeyStoreUrl(String value) throws SQLException { this.trustCertificateKeyStoreUrl.setValue(value); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4074 */   public boolean getUseSSPSCompatibleTimezoneShift() { return this.useSSPSCompatibleTimezoneShift.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4081 */   public void setUseSSPSCompatibleTimezoneShift(boolean flag) { this.useSSPSCompatibleTimezoneShift.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4088 */   public boolean getTreatUtilDateAsTimestamp() { return this.treatUtilDateAsTimestamp.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4095 */   public void setTreatUtilDateAsTimestamp(boolean flag) { this.treatUtilDateAsTimestamp.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4102 */   public boolean getUseFastDateParsing() { return this.useFastDateParsing.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4109 */   public void setUseFastDateParsing(boolean flag) { this.useFastDateParsing.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4116 */   public String getLocalSocketAddress() throws SQLException { return this.localSocketAddress.getValueAsString(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4123 */   public void setLocalSocketAddress(String address) throws SQLException { this.localSocketAddress.setValue(address); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4130 */   public void setUseConfigs(String configs) throws SQLException { this.useConfigs.setValue(configs); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4137 */   public String getUseConfigs() throws SQLException { return this.useConfigs.getValueAsString(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4145 */   public boolean getGenerateSimpleParameterMetadata() { return this.generateSimpleParameterMetadata.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4152 */   public void setGenerateSimpleParameterMetadata(boolean flag) { this.generateSimpleParameterMetadata.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4159 */   public boolean getLogXaCommands() { return this.logXaCommands.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4166 */   public void setLogXaCommands(boolean flag) { this.logXaCommands.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4173 */   public int getResultSetSizeThreshold() { return this.resultSetSizeThreshold.getValueAsInt(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4180 */   public void setResultSetSizeThreshold(int threshold) { this.resultSetSizeThreshold.setValue(threshold); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4187 */   public int getNetTimeoutForStreamingResults() { return this.netTimeoutForStreamingResults.getValueAsInt(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4194 */   public void setNetTimeoutForStreamingResults(int value) { this.netTimeoutForStreamingResults.setValue(value); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4201 */   public boolean getEnableQueryTimeouts() { return this.enableQueryTimeouts.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4208 */   public void setEnableQueryTimeouts(boolean flag) { this.enableQueryTimeouts.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4215 */   public boolean getPadCharsWithSpace() { return this.padCharsWithSpace.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4222 */   public void setPadCharsWithSpace(boolean flag) { this.padCharsWithSpace.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4229 */   public boolean getUseDynamicCharsetInfo() { return this.useDynamicCharsetInfo.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4236 */   public void setUseDynamicCharsetInfo(boolean flag) { this.useDynamicCharsetInfo.setValue(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4243 */   public String getClientInfoProvider() throws SQLException { return this.clientInfoProvider.getValueAsString(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4250 */   public void setClientInfoProvider(String classname) throws SQLException { this.clientInfoProvider.setValue(classname); }
/*      */ 
/*      */ 
/*      */   
/* 4254 */   public boolean getPopulateInsertRowWithDefaultValues() { return this.populateInsertRowWithDefaultValues.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */   
/* 4258 */   public void setPopulateInsertRowWithDefaultValues(boolean flag) { this.populateInsertRowWithDefaultValues.setValue(flag); }
/*      */ 
/*      */ 
/*      */   
/* 4262 */   public String getLoadBalanceStrategy() throws SQLException { return this.loadBalanceStrategy.getValueAsString(); }
/*      */ 
/*      */ 
/*      */   
/* 4266 */   public void setLoadBalanceStrategy(String strategy) throws SQLException { this.loadBalanceStrategy.setValue(strategy); }
/*      */ 
/*      */ 
/*      */   
/* 4270 */   public boolean getTcpNoDelay() { return this.tcpNoDelay.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */   
/* 4274 */   public void setTcpNoDelay(boolean flag) { this.tcpNoDelay.setValue(flag); }
/*      */ 
/*      */ 
/*      */   
/* 4278 */   public boolean getTcpKeepAlive() { return this.tcpKeepAlive.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */   
/* 4282 */   public void setTcpKeepAlive(boolean flag) { this.tcpKeepAlive.setValue(flag); }
/*      */ 
/*      */ 
/*      */   
/* 4286 */   public int getTcpRcvBuf() { return this.tcpRcvBuf.getValueAsInt(); }
/*      */ 
/*      */ 
/*      */   
/* 4290 */   public void setTcpRcvBuf(int bufSize) { this.tcpRcvBuf.setValue(bufSize); }
/*      */ 
/*      */ 
/*      */   
/* 4294 */   public int getTcpSndBuf() { return this.tcpSndBuf.getValueAsInt(); }
/*      */ 
/*      */ 
/*      */   
/* 4298 */   public void setTcpSndBuf(int bufSize) { this.tcpSndBuf.setValue(bufSize); }
/*      */ 
/*      */ 
/*      */   
/* 4302 */   public int getTcpTrafficClass() { return this.tcpTrafficClass.getValueAsInt(); }
/*      */ 
/*      */ 
/*      */   
/* 4306 */   public void setTcpTrafficClass(int classFlags) { this.tcpTrafficClass.setValue(classFlags); }
/*      */ 
/*      */ 
/*      */   
/* 4310 */   public boolean getUseNanosForElapsedTime() { return this.useNanosForElapsedTime.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */   
/* 4314 */   public void setUseNanosForElapsedTime(boolean flag) { this.useNanosForElapsedTime.setValue(flag); }
/*      */ 
/*      */ 
/*      */   
/* 4318 */   public long getSlowQueryThresholdNanos() { return this.slowQueryThresholdNanos.getValueAsLong(); }
/*      */ 
/*      */ 
/*      */   
/* 4322 */   public void setSlowQueryThresholdNanos(long nanos) { this.slowQueryThresholdNanos.setValue(nanos); }
/*      */ 
/*      */ 
/*      */   
/* 4326 */   public String getStatementInterceptors() throws SQLException { return this.statementInterceptors.getValueAsString(); }
/*      */ 
/*      */ 
/*      */   
/* 4330 */   public void setStatementInterceptors(String value) throws SQLException { this.statementInterceptors.setValue(value); }
/*      */ 
/*      */ 
/*      */   
/* 4334 */   public boolean getUseDirectRowUnpack() { return this.useDirectRowUnpack.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */   
/* 4338 */   public void setUseDirectRowUnpack(boolean flag) { this.useDirectRowUnpack.setValue(flag); }
/*      */ 
/*      */ 
/*      */   
/* 4342 */   public String getLargeRowSizeThreshold() throws SQLException { return this.largeRowSizeThreshold.getValueAsString(); }
/*      */ 
/*      */   
/*      */   public void setLargeRowSizeThreshold(String value) throws SQLException {
/*      */     try {
/* 4347 */       this.largeRowSizeThreshold.setValue(value);
/* 4348 */     } catch (SQLException sqlEx) {
/* 4349 */       RuntimeException ex = new RuntimeException(sqlEx.getMessage());
/* 4350 */       ex.initCause(sqlEx);
/*      */       
/* 4352 */       throw ex;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/* 4357 */   public boolean getUseBlobToStoreUTF8OutsideBMP() { return this.useBlobToStoreUTF8OutsideBMP.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */   
/* 4361 */   public void setUseBlobToStoreUTF8OutsideBMP(boolean flag) { this.useBlobToStoreUTF8OutsideBMP.setValue(flag); }
/*      */ 
/*      */ 
/*      */   
/* 4365 */   public String getUtf8OutsideBmpExcludedColumnNamePattern() throws SQLException { return this.utf8OutsideBmpExcludedColumnNamePattern.getValueAsString(); }
/*      */ 
/*      */ 
/*      */   
/* 4369 */   public void setUtf8OutsideBmpExcludedColumnNamePattern(String regexPattern) throws SQLException { this.utf8OutsideBmpExcludedColumnNamePattern.setValue(regexPattern); }
/*      */ 
/*      */ 
/*      */   
/* 4373 */   public String getUtf8OutsideBmpIncludedColumnNamePattern() throws SQLException { return this.utf8OutsideBmpIncludedColumnNamePattern.getValueAsString(); }
/*      */ 
/*      */ 
/*      */   
/* 4377 */   public void setUtf8OutsideBmpIncludedColumnNamePattern(String regexPattern) throws SQLException { this.utf8OutsideBmpIncludedColumnNamePattern.setValue(regexPattern); }
/*      */ 
/*      */ 
/*      */   
/* 4381 */   public boolean getIncludeInnodbStatusInDeadlockExceptions() { return this.includeInnodbStatusInDeadlockExceptions.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */   
/* 4385 */   public void setIncludeInnodbStatusInDeadlockExceptions(boolean flag) { this.includeInnodbStatusInDeadlockExceptions.setValue(flag); }
/*      */ 
/*      */ 
/*      */   
/* 4389 */   public boolean getBlobsAreStrings() { return this.blobsAreStrings.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */   
/* 4393 */   public void setBlobsAreStrings(boolean flag) { this.blobsAreStrings.setValue(flag); }
/*      */ 
/*      */ 
/*      */   
/* 4397 */   public boolean getFunctionsNeverReturnBlobs() { return this.functionsNeverReturnBlobs.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */   
/* 4401 */   public void setFunctionsNeverReturnBlobs(boolean flag) { this.functionsNeverReturnBlobs.setValue(flag); }
/*      */ 
/*      */ 
/*      */   
/* 4405 */   public boolean getAutoSlowLog() { return this.autoSlowLog.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */   
/* 4409 */   public void setAutoSlowLog(boolean flag) { this.autoSlowLog.setValue(flag); }
/*      */ 
/*      */ 
/*      */   
/* 4413 */   public String getConnectionLifecycleInterceptors() throws SQLException { return this.connectionLifecycleInterceptors.getValueAsString(); }
/*      */ 
/*      */ 
/*      */   
/* 4417 */   public void setConnectionLifecycleInterceptors(String interceptors) throws SQLException { this.connectionLifecycleInterceptors.setValue(interceptors); }
/*      */ 
/*      */ 
/*      */   
/* 4421 */   public String getProfilerEventHandler() throws SQLException { return this.profilerEventHandler.getValueAsString(); }
/*      */ 
/*      */ 
/*      */   
/* 4425 */   public void setProfilerEventHandler(String handler) throws SQLException { this.profilerEventHandler.setValue(handler); }
/*      */ 
/*      */ 
/*      */   
/* 4429 */   public boolean getVerifyServerCertificate() { return this.verifyServerCertificate.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */   
/* 4433 */   public void setVerifyServerCertificate(boolean flag) { this.verifyServerCertificate.setValue(flag); }
/*      */ 
/*      */ 
/*      */   
/* 4437 */   public boolean getUseLegacyDatetimeCode() { return this.useLegacyDatetimeCode.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */   
/* 4441 */   public void setUseLegacyDatetimeCode(boolean flag) { this.useLegacyDatetimeCode.setValue(flag); }
/*      */ 
/*      */ 
/*      */   
/* 4445 */   public int getSelfDestructOnPingSecondsLifetime() { return this.selfDestructOnPingSecondsLifetime.getValueAsInt(); }
/*      */ 
/*      */ 
/*      */   
/* 4449 */   public void setSelfDestructOnPingSecondsLifetime(int seconds) { this.selfDestructOnPingSecondsLifetime.setValue(seconds); }
/*      */ 
/*      */ 
/*      */   
/* 4453 */   public int getSelfDestructOnPingMaxOperations() { return this.selfDestructOnPingMaxOperations.getValueAsInt(); }
/*      */ 
/*      */ 
/*      */   
/* 4457 */   public void setSelfDestructOnPingMaxOperations(int maxOperations) { this.selfDestructOnPingMaxOperations.setValue(maxOperations); }
/*      */ 
/*      */ 
/*      */   
/* 4461 */   public boolean getUseColumnNamesInFindColumn() { return this.useColumnNamesInFindColumn.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */   
/* 4465 */   public void setUseColumnNamesInFindColumn(boolean flag) { this.useColumnNamesInFindColumn.setValue(flag); }
/*      */ 
/*      */ 
/*      */   
/* 4469 */   public boolean getUseLocalTransactionState() { return this.useLocalTransactionState.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */   
/* 4473 */   public void setUseLocalTransactionState(boolean flag) { this.useLocalTransactionState.setValue(flag); }
/*      */ 
/*      */ 
/*      */   
/* 4477 */   public boolean getCompensateOnDuplicateKeyUpdateCounts() { return this.compensateOnDuplicateKeyUpdateCounts.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */   
/* 4481 */   public void setCompensateOnDuplicateKeyUpdateCounts(boolean flag) { this.compensateOnDuplicateKeyUpdateCounts.setValue(flag); }
/*      */ 
/*      */ 
/*      */   
/* 4485 */   public int getLoadBalanceBlacklistTimeout() { return this.loadBalanceBlacklistTimeout.getValueAsInt(); }
/*      */ 
/*      */ 
/*      */   
/* 4489 */   public void setLoadBalanceBlacklistTimeout(int loadBalanceBlacklistTimeout) { this.loadBalanceBlacklistTimeout.setValue(loadBalanceBlacklistTimeout); }
/*      */ 
/*      */ 
/*      */   
/* 4493 */   public int getLoadBalancePingTimeout() { return this.loadBalancePingTimeout.getValueAsInt(); }
/*      */ 
/*      */ 
/*      */   
/* 4497 */   public void setLoadBalancePingTimeout(int loadBalancePingTimeout) { this.loadBalancePingTimeout.setValue(loadBalancePingTimeout); }
/*      */ 
/*      */ 
/*      */   
/* 4501 */   public void setRetriesAllDown(int retriesAllDown) { this.retriesAllDown.setValue(retriesAllDown); }
/*      */ 
/*      */ 
/*      */   
/* 4505 */   public int getRetriesAllDown() { return this.retriesAllDown.getValueAsInt(); }
/*      */ 
/*      */ 
/*      */   
/* 4509 */   public void setUseAffectedRows(boolean flag) { this.useAffectedRows.setValue(flag); }
/*      */ 
/*      */ 
/*      */   
/* 4513 */   public boolean getUseAffectedRows() { return this.useAffectedRows.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */   
/* 4517 */   public void setPasswordCharacterEncoding(String characterSet) throws SQLException { this.passwordCharacterEncoding.setValue(characterSet); }
/*      */ 
/*      */ 
/*      */   
/* 4521 */   public String getPasswordCharacterEncoding() throws SQLException { return this.passwordCharacterEncoding.getValueAsString(); }
/*      */ 
/*      */ 
/*      */   
/* 4525 */   public void setExceptionInterceptors(String exceptionInterceptors) throws SQLException { this.exceptionInterceptors.setValue(exceptionInterceptors); }
/*      */ 
/*      */ 
/*      */   
/* 4529 */   public String getExceptionInterceptors() throws SQLException { return this.exceptionInterceptors.getValueAsString(); }
/*      */ 
/*      */ 
/*      */   
/* 4533 */   public void setMaxAllowedPacket(int max) { this.maxAllowedPacket.setValue(max); }
/*      */ 
/*      */ 
/*      */   
/* 4537 */   public int getMaxAllowedPacket() { return this.maxAllowedPacket.getValueAsInt(); }
/*      */ 
/*      */ 
/*      */   
/* 4541 */   public boolean getQueryTimeoutKillsConnection() { return this.queryTimeoutKillsConnection.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */   
/* 4545 */   public void setQueryTimeoutKillsConnection(boolean queryTimeoutKillsConnection) { this.queryTimeoutKillsConnection.setValue(queryTimeoutKillsConnection); }
/*      */ 
/*      */ 
/*      */   
/* 4549 */   public boolean getLoadBalanceValidateConnectionOnSwapServer() { return this.loadBalanceValidateConnectionOnSwapServer.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4554 */   public void setLoadBalanceValidateConnectionOnSwapServer(boolean loadBalanceValidateConnectionOnSwapServer) { this.loadBalanceValidateConnectionOnSwapServer.setValue(loadBalanceValidateConnectionOnSwapServer); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4559 */   public String getLoadBalanceConnectionGroup() throws SQLException { return this.loadBalanceConnectionGroup.getValueAsString(); }
/*      */ 
/*      */ 
/*      */   
/* 4563 */   public void setLoadBalanceConnectionGroup(String loadBalanceConnectionGroup) throws SQLException { this.loadBalanceConnectionGroup.setValue(loadBalanceConnectionGroup); }
/*      */ 
/*      */ 
/*      */   
/* 4567 */   public String getLoadBalanceExceptionChecker() throws SQLException { return this.loadBalanceExceptionChecker.getValueAsString(); }
/*      */ 
/*      */ 
/*      */   
/* 4571 */   public void setLoadBalanceExceptionChecker(String loadBalanceExceptionChecker) throws SQLException { this.loadBalanceExceptionChecker.setValue(loadBalanceExceptionChecker); }
/*      */ 
/*      */ 
/*      */   
/* 4575 */   public String getLoadBalanceSQLStateFailover() throws SQLException { return this.loadBalanceSQLStateFailover.getValueAsString(); }
/*      */ 
/*      */ 
/*      */   
/* 4579 */   public void setLoadBalanceSQLStateFailover(String loadBalanceSQLStateFailover) throws SQLException { this.loadBalanceSQLStateFailover.setValue(loadBalanceSQLStateFailover); }
/*      */ 
/*      */ 
/*      */   
/* 4583 */   public String getLoadBalanceSQLExceptionSubclassFailover() throws SQLException { return this.loadBalanceSQLExceptionSubclassFailover.getValueAsString(); }
/*      */ 
/*      */ 
/*      */   
/* 4587 */   public void setLoadBalanceSQLExceptionSubclassFailover(String loadBalanceSQLExceptionSubclassFailover) throws SQLException { this.loadBalanceSQLExceptionSubclassFailover.setValue(loadBalanceSQLExceptionSubclassFailover); }
/*      */ 
/*      */ 
/*      */   
/* 4591 */   public boolean getLoadBalanceEnableJMX() { return this.loadBalanceEnableJMX.getValueAsBoolean(); }
/*      */ 
/*      */ 
/*      */   
/* 4595 */   public void setLoadBalanceEnableJMX(boolean loadBalanceEnableJMX) { this.loadBalanceEnableJMX.setValue(loadBalanceEnableJMX); }
/*      */ 
/*      */ 
/*      */   
/* 4599 */   public void setLoadBalanceAutoCommitStatementThreshold(int loadBalanceAutoCommitStatementThreshold) { this.loadBalanceAutoCommitStatementThreshold.setValue(loadBalanceAutoCommitStatementThreshold); }
/*      */ 
/*      */ 
/*      */   
/* 4603 */   public int getLoadBalanceAutoCommitStatementThreshold() { return this.loadBalanceAutoCommitStatementThreshold.getValueAsInt(); }
/*      */ 
/*      */ 
/*      */   
/* 4607 */   public void setLoadBalanceAutoCommitStatementRegex(String loadBalanceAutoCommitStatementRegex) throws SQLException { this.loadBalanceAutoCommitStatementRegex.setValue(loadBalanceAutoCommitStatementRegex); }
/*      */ 
/*      */ 
/*      */   
/* 4611 */   public String getLoadBalanceAutoCommitStatementRegex() throws SQLException { return this.loadBalanceAutoCommitStatementRegex.getValueAsString(); }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\ConnectionPropertiesImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */