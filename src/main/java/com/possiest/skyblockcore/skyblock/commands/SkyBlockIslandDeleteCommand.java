package com.possiest.skyblockcore.skyblock.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.UUID;

public class SkyBlockIslandDeleteCommand implements CommandExecutor {



    @Override
    public boolean onCommand( CommandSender sender,  Command command,  String s,  String[] strings) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        }

        Player player = (Player) sender;
        UUID worldName = player.getUniqueId();
        World world = Bukkit.getWorld(worldName);

        if (world == null) {
            player.sendMessage("The world '" + worldName + "' does not exist.");
            return false;
        }

        File worldFolder = world.getWorldFolder();
        File worldUUID = new File(worldFolder, "uid.dat");
        if (worldUUID.exists()){
            worldUUID.delete();
        }
        if (worldFolder.delete()) {
            player.sendMessage("The world '" + worldName + "' has been deleted.");
            return true;
        } else {
            player.sendMessage("An error occurred while trying to delete the world '" + worldName + "'.");
            return false;
        }
    }
}
