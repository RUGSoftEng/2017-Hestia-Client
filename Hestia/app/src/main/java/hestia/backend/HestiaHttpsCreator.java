package hestia.backend;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class HestiaHttpsCreator {

    public SSLSocketFactory getFactory(){
        X509TrustManager trustManager;
        SSLSocketFactory sslSocketFactory;
        try {
            trustManager = trustManagerForCertificates(trustedCertificatesInputStream());
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        return sslSocketFactory;
    }

    /**
     * Returns an input stream containing one or more certificate PEM files. This implementation just
     * embeds the PEM files in Java strings; most applications will instead read this from a resource
     * file that gets bundled with the application.
     */
    private InputStream trustedCertificatesInputStream() {
        String hestiaRsaCertificationAuthority = ""
                + "-----BEGIN CERTIFICATE-----\n"
                + "MIIC1zCCAb8CCB0FwpZGACgAMA0GCSqGSIb3DQEBCwUAMDQxHDAaBgNVBAMME1Vu\n"
                + "dHJ1c3RlZCBBdXRob3JpdHkxFDASBgNVBAoMC1NlbGYtU2lnbmVkMB4XDTE3MDYw\n"
                + "NDExNDkxMloXDTE4MDYwNDExNDkxMlowKDEKMAgGA1UEAwwBKjEaMBgGA1UECgwR\n"
                + "RHVtbXkgQ2VydGlmaWNhdGUwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIB\n"
                + "AQDn2zV4xNehxvVtiUTnuE4shEDdumDSvXaTbQQF0Bviv4qkTPwQpMozcRkgJV8X\n"
                + "JoNKQ7jpe/QYANSfyWZloNxoyi5MG9gs9Z4Up7OLYLzCiOu+n+G5PV8L0KiIGJoG\n"
                + "9qRAaabfc5LzQa0Z90GtYL86w51YGxUCD7UYaSE6gY/9IGpEXrGjhJ3R41uVxUOm\n"
                + "/FTO2lBmnuJ7UvqwoHrRbcTQy6nUMIJb68ZJR7dPe1kYIA8kdm6kQFe1d+PJOwPi\n"
                + "Q0Y073tITFlOF45EKM/7eLj9hMeYhAbqp9Zn6XPb4z64d8+34Cer9KOjTzRfoxyD\n"
                + "HEt2DEY6RL7REYW5SY9DvBP3AgMBAAEwDQYJKoZIhvcNAQELBQADggEBAJqdfvWc\n"
                + "KQ/3pOWjI75HrEnylouKu5NWlgHG80B6LvPbLF6FDGJy/Rs9kdiWu2cJTKLXNNPa\n"
                + "bw867kjw7AnqrEY34xu5+ywtPs4JYct8Iq2C2uYhCa65UTmeegZHxVI+ZI93c6lb\n"
                + "uL47tRa1OvBLsh58Ozt1cPFu9NzFh4/764tPDbRTpkTGKXmuUARsdU1OC5YVU+N\n"
                + "83ayJdiLhoU4r2/6epKbjhYvCouTz4Scjjuz4TAhCDDE/vKYlAN4x95mVgmfBWnN\n"
                + "a4m0RqIjEPe0d982cNxX/y7JQ9dluBgpDD/HNZ/TKu8bPQu91qojkK8MYVvYjpQ\n"
                + "pq0zmasmmrF+Dew=\n"
                + "-----END CERTIFICATE-----\n";
        return new ByteArrayInputStream(Charset.forName("UTF-8").encode(hestiaRsaCertificationAuthority).array());

    }

    /**
     * Returns a trust manager that trusts {@code certificates} and none other. HTTPS services whose
     * certificates have not been signed by these certificates will fail with a {@code
     * SSLHandshakeException}.
     * <p>
     * <p>This can be used to replace the host platform's built-in trusted certificates with a custom
     * set. This is useful in development where certificate authority-trusted certificates aren't
     * available. Or in production, to avoid reliance on third-party certificate authorities.
     * <p>
     * <p>
     * <h3>Warning: Customizing Trusted Certificates is Dangerous!</h3>
     * <p>
     * <p>Relying on your own trusted certificates limits your server team's ability to update their
     * TLS certificates. By installing a specific set of trusted certificates, you take on additional
     * operational complexity and limit your ability to migrate between certificate authorities. Do
     * not use custom trusted certificates in production without the blessing of your server's TLS
     * administrator.
     */
    private X509TrustManager trustManagerForCertificates(InputStream in)
            throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        // Put the certificates a key store.
        char[] password = "password".toCharArray(); // Any password will work.
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }

        // Use it to build an X509 trust manager.
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream in = null; // By convention, 'null' creates an empty key store.
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
