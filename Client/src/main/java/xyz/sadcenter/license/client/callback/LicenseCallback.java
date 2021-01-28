package xyz.sadcenter.license.client.callback;

/**
 * @author sadcenter on 09.08.2020
 * @project LicenseSystem
 */

public interface LicenseCallback {

    void correct(String token);

    void incorrect(String token);

    void disconnected(Throwable e);
}
