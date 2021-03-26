package xyz.sadcenter.license.client;

import io.netty.channel.Channel;
import xyz.sadcenter.license.client.callback.LicenseCallback;

/**
 * @author sadcenter on 09.08.2020
 * @project LicenseSystem
 */

public final class Client {

    public static Channel connect(String host, int port, String token, LicenseCallback licenseCallback) {
        ClientServer clientServer = new ClientServer();
        clientServer.setup(host, port, token, licenseCallback);
        return clientServer.getChannel();

    }
}
