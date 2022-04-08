package com.eldenwyre.ender_dragon_protection;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class Ender_dragon_protection extends JavaPlugin implements Listener {

    //Config File
    FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        // Plugin startup logic
        //Load Config
        config.options().copyDefaults(true);
        saveConfig();

        //Enable Listeners
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        saveConfig();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onDragonDamage(EntityDamageEvent event) {
        if (!config.getBoolean("enableDragonDamage"))
            if (event.getEntity() instanceof EnderDragon) {
                event.setCancelled(true);
            }

    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("toggleDragonDamage")) {
            //If command issued by player, deny due to permissions
            if (sender instanceof Player) {
                {
                    sender.sendMessage("Command can only be issued in server console");
                    return true;
                }
            }
            //Sender is Console
            else{
                boolean status = !config.getBoolean("enableDragonDamage");
                config.set("enableDragonDamage", status);
                saveConfig();

                //Report current status
                if (status)
                    sender.sendMessage("Ender dragon damage is now enabled");
                else
                    sender.sendMessage("Ender dragon damage is now disabled");
            }
        }
        return false;
    }
}

