package net.coddev.mod.manager;

import net.coddev.mod.Main;
import net.coddev.mod.listeners.InventoryClick;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class EventsManager {
    public void registers(){
        PluginManager pn = Bukkit.getPluginManager();
        pn.registerEvents(new InventoryClick(), Main.getInstance());
    }
}
