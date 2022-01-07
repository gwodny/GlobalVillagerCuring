package me.gwodny.commands;

import com.destroystokyo.paper.entity.villager.Reputation;
import com.destroystokyo.paper.entity.villager.ReputationType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.util.List;

public class GVCCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player senderPlayer)) return false;

        if (cmd.getName().equalsIgnoreCase("setBestReputation")){
            OfflinePlayer[] players = Bukkit.getOfflinePlayers();

            for (World world : Bukkit.getWorlds()) {
                for (Entity entity : world.getEntities()) {
                    if (entity.getType() == EntityType.VILLAGER) {
                        Villager villager = (Villager) entity;

                        int bestMajor = 0;
                        int bestMinor = 0;

                        for (OfflinePlayer player : players) {
                            int major = villager.getReputation(player.getUniqueId()).getReputation(ReputationType.MAJOR_POSITIVE);
                            int minor = villager.getReputation(player.getUniqueId()).getReputation(ReputationType.MINOR_POSITIVE);

                            if (major > bestMajor) {
                                bestMajor = major;
                            }
                            if (minor > bestMinor) {
                                bestMinor = minor;
                            }
                        }


                        for (OfflinePlayer player : players) {
                            Reputation reputation = villager.getReputation(player.getUniqueId());

                            reputation.setReputation(ReputationType.MAJOR_POSITIVE, bestMajor);
                            reputation.setReputation(ReputationType.MINOR_POSITIVE, bestMinor);

                            villager.setReputation(player.getUniqueId(), reputation);
                        }

                    }
                }
            }
        }

        else if (cmd.getName().equalsIgnoreCase("reputationQuery")){

            Villager villager = null;

            List<Entity> nearby = senderPlayer.getNearbyEntities(5, 5, 5);

            for (Entity object : nearby){
                if (object.getType() == EntityType.VILLAGER){

                    if (villager == null)
                        villager = (Villager) object;

                    else if (object.getLocation().distanceSquared(senderPlayer.getLocation())
                            < villager.getLocation().distanceSquared(senderPlayer.getLocation())){
                        villager = (Villager) object;
                    }
                }
            }

            if (villager == null){
                sender.sendMessage("No villagers in range");
                return false;
            }
            else {

                villager.setGlowing(true);

                Reputation reputation = villager.getReputation(senderPlayer.getUniqueId());

                if (reputation != null) {
                    sender.sendMessage("MAJOR_NEGATIVE" + " = " + ChatColor.RED + reputation.getReputation(ReputationType.MAJOR_NEGATIVE));
                    sender.sendMessage("MINOR_NEGATIVE" + " = " + ChatColor.YELLOW + reputation.getReputation(ReputationType.MINOR_NEGATIVE));
                    sender.sendMessage("MINOR_POSITIVE" + " = " + ChatColor.GREEN + reputation.getReputation(ReputationType.MINOR_POSITIVE));
                    sender.sendMessage("MAJOR_POSITIVE" + " = " + ChatColor.DARK_GREEN + reputation.getReputation(ReputationType.MAJOR_POSITIVE));
                    sender.sendMessage("TRADING" + " = " + reputation.getReputation(ReputationType.TRADING));
                }

                Villager finalVillager = villager;
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                finalVillager.setGlowing(false);
                            }
                        },
                        5000
                );
            }
        }

        return true;
    }

}