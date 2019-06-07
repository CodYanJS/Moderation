package net.coddev.mod;

import net.coddev.mod.commands.Commands;
import net.coddev.mod.manager.EventsManager;
import net.coddev.mod.manager.PlayerManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin {

    private static Main instance;

    public ArrayList<UUID> moderateurs = new ArrayList<>();
    public HashMap<UUID, PlayerManager> players = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;

        new EventsManager().registers();

        getCommand("mod").setExecutor(new Commands());
        getCommand("report").setExecutor(new Commands());
    }

    public static Main getInstance() {
        return instance;
    }
}