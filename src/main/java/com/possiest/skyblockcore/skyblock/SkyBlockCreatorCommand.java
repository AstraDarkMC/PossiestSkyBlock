package com.possiest.skyblockcore.skyblock;

import com.possiest.skyblockcore.managers.skyblock.generator.VoidGen;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class SkyBlockCreatorCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player.");
            return true;
        }

        File serverWorldFolder = Bukkit.getServer().getWorldContainer();
        File worldToCopy = new File(serverWorldFolder, "island_default");
        if(worldToCopy.exists()){
            File newWorld = new File(serverWorldFolder, player.getUniqueId().toString());
            if(newWorld.exists()){
                World existingWorld = Bukkit.getWorld(player.getUniqueId().toString());
                player.teleport(existingWorld.getSpawnLocation());
                player.sendMessage("Teleported to existing world: " + existingWorld.getName());
            }else{
                try {
                    FileUtils.copyDirectory(worldToCopy, newWorld);
                    WorldCreator creator = new WorldCreator(player.getUniqueId().toString());
                    World newWorldLoaded = Bukkit.createWorld(creator);
                    player.teleport(newWorldLoaded.getSpawnLocation());
                    player.sendMessage("Successfully copied the world and teleported to " + newWorldLoaded.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else{
            player.sendMessage("island_default not found");
        }
        return true;
    }
}