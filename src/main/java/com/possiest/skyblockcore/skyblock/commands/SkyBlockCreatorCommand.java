package com.possiest.skyblockcore.skyblock.commands;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
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
                File defaultWorld = new File("island_default");
                File playerWorld = new File(Bukkit.getServer().getWorldContainer().getAbsolutePath() + "/" + worldName);
                if(!playerWorld.exists()) {
                    if(defaultWorld.exists()) {
                        try {
                            FileUtils.copyDirectory(defaultWorld, playerWorld);
                            System.out.println("World folder for player " + player.getName() + " created.");
                        } catch (IOException e) {
                            System.out.println("Error copying default world: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Default world (island_default) not found. World not created.");
                        return true;
                    }
                }
                World world = Bukkit.getWorld(worldName);
                if (world == null) {
                    world = Bukkit.createWorld(new WorldCreator(worldName));
                    world.getWorldBorder().setSize(50);
                    world.getWorldBorder().setCenter(0, 0);
                }
                Location spawn = world.getSpawnLocation();
                player.teleport(spawn);
                player.sendMessage("You have been teleported to your island.");
        }return true;
    }
}