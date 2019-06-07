package net.coddev.mod.commands;


import net.coddev.mod.Main;
import net.coddev.mod.manager.PlayerManager;
import net.coddev.mod.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage("Seul un joueur peut executer cette commande !");
            return false;
        }

        Player player = (Player) sender;

        if(label.equalsIgnoreCase("mod")){
            if(!player.hasPermission("moderation.mod")){
                player.sendMessage("§cVous n'avez pas la permission d'éxecuter cette commande !");
                return false;
            }

            if(Main.getInstance().moderateurs.contains(player.getUniqueId())){
                PlayerManager pm = PlayerManager.getFromPlayer(player);

                Main.getInstance().moderateurs.remove(player.getUniqueId());
                player.getInventory().clear();
                player.sendMessage("§cVous n'êtes maintenant plus en mode modération");
                pm.giveInventory();
                pm.destroy();
                return false;
            }

            PlayerManager pm = new PlayerManager(player);
            pm.init();

            Main.getInstance().moderateurs.add(player.getUniqueId());
            player.sendMessage("§aVous êtes à présent en mode modération");
            pm.saveInventory();

            ItemBuilder invSee = new ItemBuilder(Material.PAPER).setName("§eVoir l'inventaire").setLore("§7Clique droit sur un joueur" );
            ItemBuilder reports = new ItemBuilder(Material.BOOK).setName("§cSIGNALEMENT").setLore("§6Clique droit");
            ItemBuilder freeze = new ItemBuilder(Material.PACKED_ICE).setName("§bFreeze").setLore("Clique droit");
            ItemBuilder kbTester = new ItemBuilder(Material.STICK).setName("§cKB TEST").setLore("Clique gauche");
            ItemBuilder killer = new ItemBuilder(Material.BLAZE_ROD).setName("§Kill Le Joueur").setLore("Clique droit");
            ItemBuilder tpRandom = new ItemBuilder(Material.ARROW).setName("§7TP random").setLore("Clique droit");
            player.getInventory().setItem(0, invSee.toItemStack());
            player.getInventory().setItem(1, reports.toItemStack());
            player.getInventory().setItem(2, freeze.toItemStack());
            player.getInventory().setItem(3, kbTester.toItemStack());
            player.getInventory().setItem(4, killer.toItemStack());
            player.getInventory().setItem(5, tpRandom.toItemStack());
        }

        if(label.equalsIgnoreCase("report")){
            if(args.length != 1){
                player.sendMessage("§cVeuillez saisir le pseudo d'un joueur !");
                return false;
            }

            String targetName = args[0];

            if(Bukkit.getPlayer(targetName) == null){
                player.sendMessage("§cCe joueur n'est pas connecté ou n'existe pas !");
                return false;
            }

            Player target = Bukkit.getPlayer(targetName);

            Inventory inv = Bukkit.createInventory(null, 18, "§bReport: §c" + target.getName());

            inv.setItem(0, new ItemBuilder(Material.BEDROCK).setName("§cXRAY").toItemStack());
            inv.setItem(1, new ItemBuilder(Material.BED).setName("§bFLY").toItemStack());

            player.openInventory(inv);
        }

        return false;
    }
}
