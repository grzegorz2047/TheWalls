package pl.grzegorz2047.thewalls.api.file;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;

/**
 * @author Grzegorz
 */
public class YmlFileHandler {
    private File file;
    private FileConfiguration config;
    private String filename;

    private Plugin plugin;

    private YmlFileHandler() {

    }

    public YmlFileHandler(Plugin plugin, String path, String name) {
        this.plugin = plugin;

        this.filename = name;
        this.file = new File(path, this.filename);
        System.out.println(path + " sciezka dla " + name);
        this.config = new YamlConfiguration();
        if (!file.exists()) {//Copy from plugin.jar
            System.out.println("Plik " + this.filename + " nie istnieje!");
            try {
                copyFile();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.println("Nie ma pliku " + this.filename + "- Tworzenie...");
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void load() {
        try {
            config.load(file); //Wczytuje plik z cala konfiguracj§
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            config.save(file);//Zapisuje konfiguracje do pliku

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyFile() throws Exception {
        this.file.getParentFile().mkdirs();
        if (plugin.getResource(this.filename) != null) {
            kopiujPlik(plugin.getResource(this.filename), this.file);
        } else {
            this.file.createNewFile();
            System.out.println("Plik " + this.filename + " nie jest dolaczony do pluginu. Zostanie utworzony przy zapisie danych");
        }

    }


    private void kopiujPlik(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
