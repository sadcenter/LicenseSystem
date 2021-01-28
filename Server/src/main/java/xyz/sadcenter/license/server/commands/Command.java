package xyz.sadcenter.license.server.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sadcenter on 28.01.2021
 * @project LicenseSystem
 */

public abstract class Command {

    private final String command;
    private final List<String> aliases;

    public Command(String command, List<String> aliases) {
        this.command = command;
        this.aliases = aliases;
    }

    public Command(String command) {
        this.command = command;
        this.aliases = new ArrayList<>();
    }

    public abstract void executeCommand(String... args);

    public final String getCommand() {
        return command;
    }

    public List<String> getAliases() {
        return aliases;
    }

}
