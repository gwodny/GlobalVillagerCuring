package me.gwodny.events;

import com.destroystokyo.paper.entity.villager.Reputation;
import com.destroystokyo.paper.entity.villager.ReputationType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTransformEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class GVCEvents implements Listener {

    @EventHandler
    public static void onEntityTransform(EntityTransformEvent event){

        if (event.getTransformReason() == EntityTransformEvent.TransformReason.CURED) {
            OfflinePlayer[] players = Bukkit.getOfflinePlayers();

            ZombieVillager zom = (ZombieVillager) event.getEntity();
            Villager villager = (Villager) event.getTransformedEntity();

            OfflinePlayer curer = zom.getConversionPlayer();

            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            for (OfflinePlayer player : players){
                                if (player != curer) {
                                    Reputation reputation = villager.getReputation(player.getUniqueId());
                                    reputation.setReputation(ReputationType.MINOR_POSITIVE, reputation.getReputation(ReputationType.MINOR_POSITIVE) + 25);
                                    reputation.setReputation(ReputationType.MAJOR_POSITIVE, reputation.getReputation(ReputationType.MAJOR_POSITIVE) + 20);
                                    if (reputation.getReputation(ReputationType.MAJOR_POSITIVE) > 100) {
                                        reputation.setReputation(ReputationType.MAJOR_POSITIVE, 100);
                                    }
                                    villager.setReputation(player.getUniqueId(), reputation);
                                }
                            }
                        }
                    },
                    1000
            );
        }
    }

    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event){
        Player joinedPlayer = event.getPlayer();
        if (!joinedPlayer.hasPlayedBefore()){
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

                        Reputation reputation = villager.getReputation(joinedPlayer.getUniqueId());

                        reputation.setReputation(ReputationType.MAJOR_POSITIVE, bestMajor);
                        reputation.setReputation(ReputationType.MINOR_POSITIVE, bestMinor);

                        villager.setReputation(joinedPlayer.getUniqueId(), reputation);

                    }
                }
            }



        }
    }

}