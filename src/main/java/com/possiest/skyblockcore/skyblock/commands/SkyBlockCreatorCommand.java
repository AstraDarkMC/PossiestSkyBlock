package com.possiest.skyblockcore.skyblock.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class SkyBlockCreatorCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID playerUUID = player.getUniqueId();
            String worldName = playerUUID.toString();
            File worldFolder = new File(worldName);

            if (!worldFolder.exists()) {
                // if the folder does not exist create a copy of the default world and use that one
                System.out.println("World folder for player " + player.getName() + " does not exist. Creating...");
                try {
                    File defaultWorld = new File("island_default");
                    if (defaultWorld.exists()) {
                        Files.copy(defaultWorld.toPath(), worldFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    } else {
                        System.out.println("Default world (island_default) not found. World not created.");
                        return true;
                    }
                } catch (IOException e) {
                    System.out.println("Error copying default world: " + e.getMessage());
                }
            }

            World world = Bukkit.getWorld(worldName);
            if (world == null) {
                world = Bukkit.createWorld(new WorldCreator(worldName));
            }

            // Teleport player to the spawn point of the world
            Location spawn = world.getSpawnLocation();
                spawn.setYaw(90);
            player.teleport(spawn);
            }

        return true;
    }
}