package net.coddev.mod.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getCurrentItem() == null) return;

        Player player = (Player) e.getWhoClicked();

        switch(e.getCurrentItem().getType()){

            case BEDROCK:
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§cXRAY")){
                    e.setCancelled(true);
                    player.closeInventory();
                    sendToMods(e.getCurrentItem().getItemMeta().getDisplayName(), e.getInventory().getName().substring(12));
                    player.sendMessage("§aVous avez bien signalé ce joueur !");
                }
                break;

            case BED:
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§bFLY")){
                    e.setCancelled(true);
                    player.closeInventory();
                    sendToMods(e.getCurrentItem().getItemMeta().getDisplayName(), e.getInventory().getName().substring(12));
                    player.sendMessage("§aVous avez bien signalé ce joueur !");
                }
                break;

            default: break;
        }
    }

    private void sendToMods(String reason, String targetName) {
        for(Player players : Bukkit.getOnlinePlayers()){
            if(players.hasPermission("mod.receive")){
                players.sendMessage("§bLe joueur §a" + targetName + " §ba été signalé pour : " + reason);
            }
        }
    }
}