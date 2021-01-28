package xyz.sadcenter.license.server.configuration;

import com.google.common.collect.Sets;

import java.util.Set;
import java.util.UUID;

/**
 * @author sadcenter on 24.11.2020
 * @project LicenseSystem
 */

public final class ConfigurationStorage {

    private final Set<String> authUsers;
    private final String host;
    private final int port;

    public ConfigurationStorage() {
        this.authUsers = Sets.newHashSet(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString());
        this.host = "127.0.0.1";
        this.port = 2138;
    }

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    public Set<String> getAuthUsers() {
        return authUsers;
    }
}
