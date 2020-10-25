/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.security.KeyManagementException;
/*     */ import java.security.KeyStore;
/*     */ import java.security.KeyStoreException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.UnrecoverableKeyException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.sql.SQLException;
/*     */ import javax.net.ssl.KeyManagerFactory;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLSocket;
/*     */ import javax.net.ssl.SSLSocketFactory;
/*     */ import javax.net.ssl.TrustManagerFactory;
/*     */ import javax.net.ssl.X509TrustManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExportControlled
/*     */ {
/*     */   private static final String SQL_STATE_BAD_SSL_PARAMS = "08000";
/*     */   
/*  62 */   protected static boolean enabled() { return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void transformSocketToSSLSocket(MysqlIO mysqlIO) throws SQLException {
/*  80 */     SSLSocketFactory sslFact = getSSLSocketFactoryDefaultOrConfigured(mysqlIO);
/*     */     
/*     */     try {
/*  83 */       mysqlIO.mysqlConnection = sslFact.createSocket(mysqlIO.mysqlConnection, mysqlIO.host, mysqlIO.port, true);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  88 */       ((SSLSocket)mysqlIO.mysqlConnection).setEnabledProtocols(new String[] { "TLSv1" });
/*     */       
/*  90 */       ((SSLSocket)mysqlIO.mysqlConnection).startHandshake();
/*     */ 
/*     */       
/*  93 */       if (mysqlIO.connection.getUseUnbufferedInput()) {
/*  94 */         mysqlIO.mysqlInput = mysqlIO.mysqlConnection.getInputStream();
/*     */       } else {
/*  96 */         mysqlIO.mysqlInput = new BufferedInputStream(mysqlIO.mysqlConnection.getInputStream(), '䀀');
/*     */       } 
/*     */ 
/*     */       
/* 100 */       mysqlIO.mysqlOutput = new BufferedOutputStream(mysqlIO.mysqlConnection.getOutputStream(), '䀀');
/*     */ 
/*     */       
/* 103 */       mysqlIO.mysqlOutput.flush();
/* 104 */     } catch (IOException ioEx) {
/* 105 */       throw SQLError.createCommunicationsException(mysqlIO.connection, mysqlIO.getLastPacketSentTimeMs(), mysqlIO.getLastPacketReceivedTimeMs(), ioEx, mysqlIO.getExceptionInterceptor());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static SSLSocketFactory getSSLSocketFactoryDefaultOrConfigured(MysqlIO mysqlIO) throws SQLException {
/* 116 */     String clientCertificateKeyStoreUrl = mysqlIO.connection.getClientCertificateKeyStoreUrl();
/*     */     
/* 118 */     String trustCertificateKeyStoreUrl = mysqlIO.connection.getTrustCertificateKeyStoreUrl();
/*     */     
/* 120 */     String clientCertificateKeyStoreType = mysqlIO.connection.getClientCertificateKeyStoreType();
/*     */     
/* 122 */     String clientCertificateKeyStorePassword = mysqlIO.connection.getClientCertificateKeyStorePassword();
/*     */     
/* 124 */     String trustCertificateKeyStoreType = mysqlIO.connection.getTrustCertificateKeyStoreType();
/*     */     
/* 126 */     String trustCertificateKeyStorePassword = mysqlIO.connection.getTrustCertificateKeyStorePassword();
/*     */ 
/*     */     
/* 129 */     if (StringUtils.isNullOrEmpty(clientCertificateKeyStoreUrl) && StringUtils.isNullOrEmpty(trustCertificateKeyStoreUrl))
/*     */     {
/* 131 */       if (mysqlIO.connection.getVerifyServerCertificate()) {
/* 132 */         return (SSLSocketFactory)SSLSocketFactory.getDefault();
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 137 */     TrustManagerFactory tmf = null;
/* 138 */     KeyManagerFactory kmf = null;
/*     */     
/*     */     try {
/* 141 */       tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
/*     */       
/* 143 */       kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
/*     */     }
/* 145 */     catch (NoSuchAlgorithmException nsae) {
/* 146 */       throw SQLError.createSQLException("Default algorithm definitions for TrustManager and/or KeyManager are invalid.  Check java security properties file.", "08000", 0, false, mysqlIO.getExceptionInterceptor());
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 152 */     if (!StringUtils.isNullOrEmpty(clientCertificateKeyStoreUrl)) {
/*     */       try {
/* 154 */         if (!StringUtils.isNullOrEmpty(clientCertificateKeyStoreType)) {
/* 155 */           KeyStore clientKeyStore = KeyStore.getInstance(clientCertificateKeyStoreType);
/*     */           
/* 157 */           URL ksURL = new URL(clientCertificateKeyStoreUrl);
/* 158 */           char[] password = (clientCertificateKeyStorePassword == null) ? new char[0] : clientCertificateKeyStorePassword.toCharArray();
/*     */           
/* 160 */           clientKeyStore.load(ksURL.openStream(), password);
/* 161 */           kmf.init(clientKeyStore, password);
/*     */         } 
/* 163 */       } catch (UnrecoverableKeyException uke) {
/* 164 */         throw SQLError.createSQLException("Could not recover keys from client keystore.  Check password?", "08000", 0, false, mysqlIO.getExceptionInterceptor());
/*     */ 
/*     */       
/*     */       }
/* 168 */       catch (NoSuchAlgorithmException nsae) {
/* 169 */         throw SQLError.createSQLException("Unsupported keystore algorithm [" + nsae.getMessage() + "]", "08000", 0, false, mysqlIO.getExceptionInterceptor());
/*     */       
/*     */       }
/* 172 */       catch (KeyStoreException kse) {
/* 173 */         throw SQLError.createSQLException("Could not create KeyStore instance [" + kse.getMessage() + "]", "08000", 0, false, mysqlIO.getExceptionInterceptor());
/*     */       
/*     */       }
/* 176 */       catch (CertificateException nsae) {
/* 177 */         throw SQLError.createSQLException("Could not load client" + clientCertificateKeyStoreType + " keystore from " + clientCertificateKeyStoreUrl, mysqlIO.getExceptionInterceptor());
/*     */       
/*     */       }
/* 180 */       catch (MalformedURLException mue) {
/* 181 */         throw SQLError.createSQLException(clientCertificateKeyStoreUrl + " does not appear to be a valid URL.", "08000", 0, false, mysqlIO.getExceptionInterceptor());
/*     */       
/*     */       }
/* 184 */       catch (IOException ioe) {
/* 185 */         SQLException sqlEx = SQLError.createSQLException("Cannot open " + clientCertificateKeyStoreUrl + " [" + ioe.getMessage() + "]", "08000", 0, false, mysqlIO.getExceptionInterceptor());
/*     */ 
/*     */         
/* 188 */         sqlEx.initCause(ioe);
/*     */         
/* 190 */         throw sqlEx;
/*     */       } 
/*     */     }
/*     */     
/* 194 */     if (!StringUtils.isNullOrEmpty(trustCertificateKeyStoreUrl)) {
/*     */       
/*     */       try {
/* 197 */         if (!StringUtils.isNullOrEmpty(trustCertificateKeyStoreType)) {
/* 198 */           KeyStore trustKeyStore = KeyStore.getInstance(trustCertificateKeyStoreType);
/*     */           
/* 200 */           URL ksURL = new URL(trustCertificateKeyStoreUrl);
/*     */           
/* 202 */           char[] password = (trustCertificateKeyStorePassword == null) ? new char[0] : trustCertificateKeyStorePassword.toCharArray();
/*     */           
/* 204 */           trustKeyStore.load(ksURL.openStream(), password);
/* 205 */           tmf.init(trustKeyStore);
/*     */         } 
/* 207 */       } catch (NoSuchAlgorithmException nsae) {
/* 208 */         throw SQLError.createSQLException("Unsupported keystore algorithm [" + nsae.getMessage() + "]", "08000", 0, false, mysqlIO.getExceptionInterceptor());
/*     */       
/*     */       }
/* 211 */       catch (KeyStoreException kse) {
/* 212 */         throw SQLError.createSQLException("Could not create KeyStore instance [" + kse.getMessage() + "]", "08000", 0, false, mysqlIO.getExceptionInterceptor());
/*     */       
/*     */       }
/* 215 */       catch (CertificateException nsae) {
/* 216 */         throw SQLError.createSQLException("Could not load trust" + trustCertificateKeyStoreType + " keystore from " + trustCertificateKeyStoreUrl, "08000", 0, false, mysqlIO.getExceptionInterceptor());
/*     */       
/*     */       }
/* 219 */       catch (MalformedURLException mue) {
/* 220 */         throw SQLError.createSQLException(trustCertificateKeyStoreUrl + " does not appear to be a valid URL.", "08000", 0, false, mysqlIO.getExceptionInterceptor());
/*     */       
/*     */       }
/* 223 */       catch (IOException ioe) {
/* 224 */         SQLException sqlEx = SQLError.createSQLException("Cannot open " + trustCertificateKeyStoreUrl + " [" + ioe.getMessage() + "]", "08000", 0, false, mysqlIO.getExceptionInterceptor());
/*     */ 
/*     */ 
/*     */         
/* 228 */         sqlEx.initCause(ioe);
/*     */         
/* 230 */         throw sqlEx;
/*     */       } 
/*     */     }
/*     */     
/* 234 */     SSLContext sslContext = null;
/*     */     
/*     */     try {
/* 237 */       sslContext = SSLContext.getInstance("TLS");
/* 238 */       new X509TrustManager[1][0] = new X509TrustManager() {
/*     */           public void checkClientTrusted(X509Certificate[] chain, String authType) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void checkServerTrusted(X509Certificate[] chain, String authType) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public X509Certificate[] getAcceptedIssuers() {
/* 252 */             return null;
/*     */           }
/*     */         };
/*     */       sslContext.init(StringUtils.isNullOrEmpty(clientCertificateKeyStoreUrl) ? null : kmf.getKeyManagers(), mysqlIO.connection.getVerifyServerCertificate() ? tmf.getTrustManagers() : new X509TrustManager[1], null);
/* 256 */       return sslContext.getSocketFactory();
/* 257 */     } catch (NoSuchAlgorithmException nsae) {
/* 258 */       throw SQLError.createSQLException("TLS is not a valid SSL protocol.", "08000", 0, false, mysqlIO.getExceptionInterceptor());
/*     */     
/*     */     }
/* 261 */     catch (KeyManagementException kme) {
/* 262 */       throw SQLError.createSQLException("KeyManagementException: " + kme.getMessage(), "08000", 0, false, mysqlIO.getExceptionInterceptor());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\ExportControlled.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */