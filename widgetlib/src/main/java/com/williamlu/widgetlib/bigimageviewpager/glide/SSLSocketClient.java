package com.williamlu.widgetlib.bigimageviewpager.glide;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

/**
 * @author 工藤
 * @email gougou@16fan.com
 * cc.shinichi.library.glide.sunfusheng.progress
 * create at 2018/11/2  15:55
 * description:
 */
public class SSLSocketClient {

    public static SSLSocketFactory getSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static TrustManager[] getTrustManager() {
        return new TrustManager[] {
            new X509TrustManager() {
                @Override public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }

                @Override public void checkServerTrusted(X509Certificate[] chain, String authType) {
                }

                @Override public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[] {};
                }
            }
        };
    }

    public static HostnameVerifier getHostnameVerifier() {
        return new HostnameVerifier() {
            @Override public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };
    }
}
