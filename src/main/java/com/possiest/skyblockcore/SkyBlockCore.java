package com.possiest.skyblockcore;

import com.possiest.skyblockcore.dungeon.brekableBlocks.BreakableBlocks;
import com.possiest.skyblockcore.helper.ReflectionHelper;
import com.possiest.skyblockcore.managers.skyblock.WorldManager;
import com.possiest.skyblockcore.skyblock.SkyBlockCreatorCommand;
import com.possiest.skyblockcore.skyblockBoards.BaseScoreBoard;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class SkyBlockCore extends JavaPlugin {
    private static SkyBlockCore skyBlockCore;
    private ReflectionHelper reflectionHelper;
    private final YamlConfiguration config = new YamlConfiguration();


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
        skyblocksystem();
        registerConfig();
        schematicfolder();
    }

    @Override
    public void onDisable() {endMessage();}

    private void registerCommands() {
        //getCommand("ssb").setExecutor(new (this));
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new BreakableBlocks(), this);
    }


    private void registerRecipes() {

    }

    private void registerWorlds() {

    }

    private void registerQuests(){

    }

    private void skyblocksystem(){
        //Commands
        getCommand("is").setExecutor(new SkyBlockCreatorCommand());
        //Events
        Bukkit.getPluginManager().registerEvents(new WorldManager(), this);
        //Config

    }

    private void registerGUIs(){

    }

    private void schematicfolder(){
        File schematicFolder = new File(getDataFolder(), "schematics");
        if (!schematicFolder.exists()) {
            schematicFolder.mkdir();
        }
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

    private void registerConfig() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }



}
