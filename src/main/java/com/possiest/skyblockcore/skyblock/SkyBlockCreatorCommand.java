package com.possiest.skyblockcore.skyblock;

import com.possiest.skyblockcore.managers.skyblock.generator.VoidGen;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SkyBlockCreatorCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player.");
            return true;
        }
        Player player = (Player) sender;
        UUID playerUUID = player.getUniqueId();
        String worldName = playerUUID.toString();
        World isworld = Bukkit.getWorld(worldName);
        Material blockType = Material.STONE; //default value
        if(args.length > 0) {
            blockType = Material.getMaterial(args[0]);
        }
        // check if the world already exists
        if(Bukkit.getWorld(playerUUID.toString()) != null) {
            worldName = String.valueOf(Bukkit.getWorld(playerUUID.toString()));
            player.teleport(isworld.getSpawnLocation());
            player.sendMessage("Teleporting to SkyBlock world...");
            return true;
        } else {
            // Create new void world
            WorldCreator creator = new WorldCreator(worldName);
            creator.generator(new VoidGen());
            World newWorld = creator.createWorld();
            player.teleport(newWorld.getSpawnLocation());
            //set block on spawn
            Block block = newWorld.getSpawnLocation().getBlock();
            block.setType(blockType);
            player.sendMessage("Generating SkyBlock world...");
        }
        return true;
    }
}
