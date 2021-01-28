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
}
