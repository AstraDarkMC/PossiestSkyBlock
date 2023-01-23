package com.possiest.skyblockcore.skyblockBoards;

import com.possiest.skyblockcore.SkyBlockCore;
import com.possiest.skyblockcore.managers.scoreboards.engine.PossiestBoard;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class BaseScoreBoard implements Listener {

    private final Map<UUID, PossiestBoard> boards = new HashMap<>();

    public BaseScoreBoard() {
        Bukkit.getServer().getScheduler().runTaskTimer(SkyBlockCore.getInstance(), () -> {
            for (PossiestBoard board : this.boards.values()) {
                updateBoard(board);
            }
        }, 0, 20);
    }

    @EventHandler

    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        PossiestBoard lobbysb = new PossiestBoard(player);

        lobbysb.updateTitle(ChatColor.of("#0F7AD9")+""+ChatColor.BOLD + "ᴘᴏssɪᴇsᴛ");

        this.boards.put(player.getUniqueId(), lobbysb);


    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        PossiestBoard lobbysb = this.boards.remove(player.getUniqueId());

        if (lobbysb != null) {
            lobbysb.delete();
        }

    }



    //TODO do a scoreboard
    private void updateBoard(PossiestBoard lobbysb) {
        lobbysb.updateLines(
                "",
                ChatColor.of("#0F7AD9")+""+ChatColor.BOLD + "ɪsʟᴀɴᴅ sᴛᴀᴛs:",
                ChatColor.of("#ffffff")+"» "+ChatColor.of("#00a8ff")+"ɪsʟᴀɴᴅ ʟᴇᴠᴇʟ: " + ChatColor.of("#c4cdd6"),
                ChatColor.of("#ffffff")+"» "+ChatColor.of("#00a8ff")+"ɪsʟᴀɴᴅ ʀᴏʟᴇ: " + ChatColor.of("#c4cdd6"),
                ChatColor.of("#ffffff")+"» "+ChatColor.of("#00a8ff")+"ɪsʟᴀɴᴅ ʟᴏᴀᴅᴇᴅ: " + ChatColor.of("#c4cdd6"),
                "",
                ChatColor.of("#ffffff")+"» "+ChatColor.of("#00a8ff")+"ɪsʟᴀɴᴅ ᴍᴇᴍʙᴇʀs: " + ChatColor.of("#c4cdd6"),
                ChatColor.of("#ffffff")+"» "+ChatColor.of("#00a8ff")+"ɪsʟᴀɴᴅ ᴠᴏᴛᴇs: " + ChatColor.of("#c4cdd6"),
                "",
                ChatColor.of("#00a8ff")+""+ChatColor.BOLD + "ᴘʟᴀʏ.ᴘᴏssɪᴇsᴛ.ᴄᴏᴍ"
        );


    }
}
