package com.mloteria.apis;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigAPI {
    private JavaPlugin plugin;
    private String name;
    private File file;
    private FileConfiguration config;

    public String getName() {
        return this.name;
    }

    public ConfigAPI setPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
        return this;
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    public ConfigAPI(String name, JavaPlugin plugin) {
        this.plugin = plugin;
        if (plugin == null) {
            this.plugin = JavaPlugin.getProvidingPlugin(this.getClass());
        }

        this.name = name;
        this.reloadConfig();
    }

    public ConfigAPI(String name) {
        this(name, (JavaPlugin)null);
    }

    public ConfigAPI saveConfig() {
        try {
            this.config.save(this.file);
        } catch (IOException var2) {
            var2.printStackTrace();
        }

        return this;
    }

    public String message(String path) {
        return !this.contains(path) ? null : ChatColor.translateAlternateColorCodes('&', this.getConfig().getString(path));
    }

    public ConfigAPI saveDefaultConfig() {
        if (this.exists()) {
            return this;
        } else {
            this.plugin.saveResource(this.name, false);
            return this;
        }
    }

    public ConfigAPI saveDefault() {
        this.config.options().copyDefaults(true);
        this.saveConfig();
        return this;
    }

    public ConfigAPI reloadConfig() {
        JavaPlugin var10003 = this.plugin;
        this.file = new File(JavaPlugin.getPlugin(this.plugin.getClass()).getDataFolder(), this.getName());
        this.config = YamlConfiguration.loadConfiguration(this.file);
        return this;
    }

    public static String toChatMessage(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String toConfigMessage(String text) {
        return text.replace("§", "&");
    }

    public boolean delete() {
        return this.file.delete();
    }

    public boolean exists() {
        return this.file.exists();
    }

    public void add(String path, Object value) {
        this.config.addDefault(path, value);
    }

    public boolean contains(String path) {
        return this.config.contains(path);
    }

    public ConfigAPI create(String path) {
        this.config.createSection(path);
        return this;
    }

    public Object get(String path) {
        return this.config.get(path);
    }

    public boolean getBoolean(String path) {
        return this.config.getBoolean(path);
    }

    public ConfigurationSection getSection(String path) {
        return this.config.getConfigurationSection(path);
    }

    public double getDouble(String path) {
        return this.config.getDouble(path);
    }

    public int getInt(String path) {
        return this.config.getInt(path);
    }

    public List<Integer> getIntegerList(String path) {
        return this.config.getIntegerList(path);
    }

    public ItemStack getItemStack(String path) {
        return this.config.getItemStack(path);
    }

    public Set<String> getKeys(boolean deep) {
        return this.config.getKeys(deep);
    }

    public List<?> getList(String path) {
        return this.config.getList(path);
    }

    public long getLong(String path) {
        return this.config.getLong(path);
    }

    public List<Long> getLongList(String path) {
        return this.config.getLongList(path);
    }

    public List<Map<?, ?>> getMapList(String path) {
        return this.config.getMapList(path);
    }

    public String getString(String path) {
        return this.config.getString(path).replace("&", "§");
    }

    public List<String> getStringList(String path) {
        return this.config.getStringList(path);
    }

    public Map<String, Object> getValues(boolean deep) {
        return this.config.getValues(deep);
    }

    public void set(String path, Object value) {
        this.config.set(path, value);
    }
}
