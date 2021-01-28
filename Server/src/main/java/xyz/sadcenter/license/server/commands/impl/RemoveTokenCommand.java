package xyz.sadcenter.license.server.commands.impl;

import xyz.sadcenter.license.server.Server;
import xyz.sadcenter.license.server.commands.Command;
import xyz.sadcenter.license.server.configuration.Configuration;
import xyz.sadcenter.license.server.util.LicenseLogger;

import java.util.Set;
import java.util.UUID;

/**
 * @author sadcenter on 28.01.2021
 * @project LicenseSystem
 */

public final class RemoveTokenCommand extends Command {

    public RemoveTokenCommand() {
        super("remove");
    }

    @Override
    public void executeCommand(String... args) {
        UUID uuid;
        try {
            uuid = UUID.fromString(args[0]);
        } catch (Exception e) {
            LicenseLogger.logError("Wrong license format!");
            return;
        }

        String uuidString = uuid.toString();
        Configuration configuration = Server.getServer().getConfiguration();
        Set<String> authUsers = configuration.getStorage().getAuthUsers();

        if (!authUsers.contains(uuidString)) {
            LicenseLogger.logError("This license dont exists!");
            return;
        }

        authUsers
                .remove(uuidString);
        configuration
                .save();

        LicenseLogger.logInfo("Removed " + uuidString + " from license!");
    }

}
