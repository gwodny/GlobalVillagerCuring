package me.gwodny;

import me.gwodny.commands.GVCCommands;
import me.gwodny.events.GVCEvents;
import org.bukkit.plugin.java.JavaPlugin;

public final class globalvillagercuring extends JavaPlugin {

    @Override
    public void onEnable() {

        GVCCommands commands = new GVCCommands();

        getServer().getPluginManager().registerEvents(new GVCEvents(), this);

        getCommand("setBestReputation").setExecutor(commands);
        getCommand("reputationQuery").setExecutor(commands);

        getServer().getConsoleSender().sendMessage("[GlobalVillagerCuring] Global Villager Curing Enabled");
    }

    @Override
    public void onDisable() {

        getServer().getConsoleSender().sendMessage("[GlobalVillagerCuring] Global Villager Curing Disabled");

    }
}
