package ru.shop.security.configs;

import javax.net.ssl.*;
import java.security.*;
import java.security.cert.X509Certificate;

/**
 * Given from here to turn SSL checking off:
 * https://stackoverflow.com/questions/23504819/how-to-disable-ssl-certificate-checking-with-spring-resttemplate
 */
public final class TestSslUtil {
	
	private static final TrustManager[] UNQUESTIONING_TRUST_MANAGER = new TrustManager[]{
			new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}
				
				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}
				
				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}
			}
	};
	
	public static void turnOffSslChecking() throws NoSuchAlgorithmException, KeyManagementException {
		// Install the all-trusting trust manager
		final SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, UNQUESTIONING_TRUST_MANAGER, null);
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		//No name matching localhost found; nested exception is javax.net.ssl.SSLHandshakeException
		//In Java 8 you can skip server name checking with the following code:
		HttpsURLConnection.setDefaultHostnameVerifier ((hostname, session) -> true);
	}
	
	public static void turnOnSslChecking() throws KeyManagementException, NoSuchAlgorithmException {
		// Return it to the initial state (discovered by reflection, now hardcoded)
		SSLContext.getInstance("SSL").init(null, null, null);
	}
	
	private void SSLUtil() {
		throw new UnsupportedOperationException("Do not instantiate libraries.");
	}
}
