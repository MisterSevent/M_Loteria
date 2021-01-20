package com.mloteria;

import com.mloteria.apis.ConfigAPI;
import com.mloteria.commands.LoteriaCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import sun.security.krb5.Config;

public class Lottery extends JavaPlugin {

    private static Lottery instance;
    private static ConfigAPI mensagens;


    @Override
    public void onEnable() {
        instance = this;
        loadConfigurantions();
        saveDefaultConfig();
        loadCommands();


    }


    @Override
    public void onDisable() {

    }

    boolean loadConfigurantions() {
        this.mensagens = new ConfigAPI("mensagens.yml");
        boolean continueFlux = true;
        ConfigAPI[] var2 = new ConfigAPI[]{this.mensagens};
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            ConfigAPI configAPI = var2[var4];
            if (!configAPI.exists()) {
                continueFlux = false;
                configAPI.saveDefaultConfig();
            }
        }
        if (!continueFlux) {
            this.getLogger().info("Plugin desligado para configuração, reiniciei novamente.");
            return true;
        } else {
            return false;
        }
    }

    public void loadCommands() {
        getCommand("loteria").setExecutor(new LoteriaCommand());
    }
    public static Lottery getInstance() {
        return instance;
    }

    public static ConfigAPI getMensagens() {
        return mensagens;

    }
}