package com.possiest.skyblockcore;

import com.possiest.skyblockcore.dungeon.brekableBlocks.BreakableBlocks;
import com.possiest.skyblockcore.helper.ReflectionHelper;
import com.possiest.skyblockcore.skyblockBoards.BaseScoreBoard;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class SkyBlockCore extends JavaPlugin {
    private static SkyBlockCore skyBlockCore;
    private ReflectionHelper reflectionHelper;

    @Override
    public void onEnable() {
        skyBlockCore = this;
        startMessage();
        registerCommands();
        registerEvents();
        registerConfig();
        registerRecipes();
        registerWorlds();
        registerQuests();
        registerGUIs();
        scoreboards();
    }

    @Override
    public void onDisable() {endMessage();}

    private void registerCommands() {
        //getCommand("ssb").setExecutor(new (this));
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new BreakableBlocks(), this);
    }

    private void registerConfig() {

    }

    private void registerRecipes() {

    }

    private void registerWorlds() {

    }

    private void registerQuests(){

    }

    private void registerGUIs(){

    }

    private void scoreboards(){
        Bukkit.getPluginManager().registerEvents(new BaseScoreBoard(), this);
    }

    public ReflectionHelper getReflectionHelper() {
        return reflectionHelper;
    }

    private void startMessage() {
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage(ChatColor.of("#45b2ed")+"SkyBlockCore has been enabled!");
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage(ChatColor.of("#45b2ed")+"");
    }

    private void endMessage() {
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage(ChatColor.of("#ed4566")+"SkyBlockCore has been disabled!");
        Bukkit.getConsoleSender().sendMessage("");
    }

    public static Plugin getInstance() {
        return skyBlockCore;
    }
}
