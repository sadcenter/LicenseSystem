package xyz.sadcenter.license.server;

/**
 * @author sadcenter on 09.08.2020
 * @project LicenseSystem
 */

public class Server {

    private static NettyServer server;

    public static void main(String[] args) {
        server = new NettyServer();
    }

    public static NettyServer getServer() {
        return server;
    }
}
