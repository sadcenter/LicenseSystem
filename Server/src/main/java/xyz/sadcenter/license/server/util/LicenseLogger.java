package xyz.sadcenter.license.server.util;

import lombok.SneakyThrows;
import xyz.sadcenter.license.server.Server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author sadcenter on 28.01.2021
 * @project LicenseSystem
 */

public final class LicenseLogger {

    private static final SimpleDateFormat simpleDateFormat =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @SneakyThrows
    public static void logError(String message) {
        String string = "[" + simpleDateFormat.format(new Date()) + "] [ERROR] - " + message;
        System.out.println(string);

        BufferedWriter fileWriter = Server.getServer().getBufferedWriter();
        fileWriter.append(string).append("\n");
        fileWriter.flush();
    }

    @SneakyThrows
    public static void logInfo(String message) {
        String string = "[" + simpleDateFormat.format(new Date()) + "] [INFO] - " + message;
        System.out.println(string);

        BufferedWriter fileWriter = Server.getServer().getBufferedWriter();
        fileWriter.append(string).append("\n");
        fileWriter.flush();
    }

}
