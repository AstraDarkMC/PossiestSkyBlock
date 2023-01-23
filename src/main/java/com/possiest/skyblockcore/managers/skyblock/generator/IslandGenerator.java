package com.possiest.skyblockcore.managers.skyblock.generator;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class IslandGenerator {

    public static void createWorldFromLevelDat(String newWorldName, UUID playerUUID, File levelDat) {
        // Get the world container
        File worldContainer = Bukkit.getServer().getWorldContainer();

        // Create a new folder for the new world
        File newWorldFolder = new File(worldContainer, newWorldName);
        newWorldFolder.mkdir();

        // Copy the level.dat file to the new world folder
        try {
            FileUtils.copyFileToDirectory(levelDat, newWorldFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create the new world
        WorldCreator creator = new WorldCreator(newWorldName);
        World world = creator.createWorld();

        // Set the spawn location for the player
        world.setSpawnLocation(0, world.getHighestBlockYAt(0, 0), 0);

        // Set the spawn location for the player
        Player player = Bukkit.getPlayer(playerUUID);
        if (player != null) {
            player.teleport(world.getSpawnLocation());
        }
    }

}