package xyz.sadcenter.license.client.callback;

/**
 * @author sadcenter on 09.08.2020
 * @project LicenseSystem
 */

public interface LicenseCallback {

    void correct();

    void incorrect();

    void disconnected(Throwable e);
}
