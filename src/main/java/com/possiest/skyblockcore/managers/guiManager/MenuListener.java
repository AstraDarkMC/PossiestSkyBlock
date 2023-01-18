package com.possiest.skyblockcore.managers.guiManager;

import com.possiest.skyblockcore.SkyBlockCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;

public class MenuListener implements Listener {
    private final SkyBlockCore plugin;

    public MenuListener(SkyBlockCore plugin) {
        this.plugin = plugin;
    }

    public void onMenuClick(InventoryClickEvent e) {
        InventoryHolder holder = e.getInventory().getHolder();
        if (holder instanceof Menu) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) {
                return;
            }
            if (e.getClickedInventory().getItem(e.getSlot()) == null) {
                return;
            }
            if (e.getClickedInventory().getHolder() instanceof Player) {
                return;
            }
            Menu menu = (Menu) holder;
            if (e.getCurrentItem().isSimilar(menu.getNext())) {
                menu.nextPage();
                return;
            }
            if (e.getCurrentItem().isSimilar(menu.getPrev())) {
                menu.prevPage();
                return;
            }
            menu.handleMenu(e);
        }
    }

    @EventHandler
    public void onMenuClose(InventoryCloseEvent e) {
        InventoryHolder holder = e.getInventory().getHolder();
        if (holder instanceof Menu) {
            Menu menu = (Menu) holder;
            menu.flushRunningTasks();
        }
    }
}