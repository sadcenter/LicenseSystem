package xyz.sadcenter.license.client;

import xyz.sadcenter.license.client.callback.LicenseCallback;

/**
 * @author sadcenter on 09.08.2020
 * @project LicenseSystem
 */

public final class Client {

    public static void connect(String host, int port, String token, LicenseCallback licenseCallback) {
        new ClientServer().setup(host, port, token, licenseCallback);
    }

    public static void main(String[] args) {
        connect("127.0.0.1", 2138, "b449abcc-6172-11eb-ae93-0242ac130002", new LicenseCallback() {
            @Override
            public void correct(String token) {
                System.out.println("correct " + token);
            }

            @Override
            public void incorrect(String token) {
                System.out.println("incorrect " + token);
            }

            @Override
            public void disconnected(Throwable e) {
                System.out.println("cant connect!");
            }
        });
    }

}