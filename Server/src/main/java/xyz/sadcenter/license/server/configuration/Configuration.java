package xyz.sadcenter.license.server.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.SneakyThrows;

import java.io.*;

/**
 * @author sadcenter on 24.11.2020
 * @project LicenseSystem
 */

public final class Configuration {

    private final ConfigurationStorage storage;
    private final File file;
    private final Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    @SneakyThrows
    public Configuration(File file)  {
        this.file = file;
        if (!file.exists()) {
            file.createNewFile();
            storage = new ConfigurationStorage();
            save();
        } else {
            storage = gson.fromJson(new BufferedReader(new FileReader(file)), ConfigurationStorage.class);
        }
    }

    public void save() {
        try (FileWriter fileWriter = new FileWriter(file)) {
            gson.toJson(storage, fileWriter);
            fileWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ConfigurationStorage getStorage() {
        return storage;
    }
}
