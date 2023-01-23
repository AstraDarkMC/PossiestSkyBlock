package com.possiest.skyblockcore;

import me.term.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spawn implements CommandExecutor {

    @Override
    public boolean onCommand( CommandSender commandSender,  Command command,  String s,  String[] strings) {

        Player player = (Player) commandSender;
        //get world named world and set player location to spawn
        World world = Bukkit.getWorld("world");

        player.teleport(world.getSpawnLocation());

        player.sendMessage("You have been teleported to spawn");
        return true;
    }
}
