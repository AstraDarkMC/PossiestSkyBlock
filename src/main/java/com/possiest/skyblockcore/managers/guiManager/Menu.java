package com.possiest.skyblockcore.managers.guiManager;

import com.cryptomorin.xseries.XMaterial;
import me.term.core.Core;
import me.term.core.helpers.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class Menu implements InventoryHolder {
    protected Player player;

    protected Inventory inventory;

    protected Map<Integer, ItemStack> items = new HashMap<>();

    protected int currentPage = 0;

    protected boolean enablePagination = false;

    protected Consumer<Menu> onPageChange;

    protected List<BukkitTask> runningTasks = new ArrayList<>();

    public Menu(Player player) {
        this.player = player;
    }

    public abstract String getMenuName();

    public abstract int getSlots();

    public abstract void handleMenu(InventoryClickEvent InventoryClickEvent);

    public abstract void setMenuItems();

    public void setButton(int page, int slot, ItemStack item) {
        if (slot < 0 || slot > getSlots())
            return;

        setButton((page * getSlots()) + slot, item);
    }

    public void setButton(int slot, ItemStack item) {
        items.put(slot, item);
    }

    public void addButton(ItemStack item) {
        if (getHighestFilledSlot() == 0 && getButton(0) == null) {
            setButton(0, item);
            return;
        }
        setButton(getHighestFilledSlot() + 1, item);
    }

    public ItemStack getButton(int slot) {
        if (slot < 0 || slot > getHighestFilledSlot())
            return null;

        return items.get(slot);
    }

    public ItemStack getButton(int page, int slot) {
        if (slot < 0 || slot > getSlots())
            return null;

        return getButton((page * getSlots()) + slot);
    }

    public Integer getRealNum(int page, int slot) {
        if (slot < 0 || slot > getSlots())
            return null;

        return (page * getSlots()) + slot;
    }

    public void open() {
        setMenuItems();
        this.inventory = inventory();
        this.player.openInventory(inventory);
    }

    public void open(int page) {
        currentPage = page;
        setMenuItems();
        this.inventory = inventory();
        this.player.openInventory(inventory);
    }

    public void reopen() {
        this.inventory = inventory();
        this.player.openInventory(inventory);
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public int getHighestFilledSlot() {
        int slot = 0;

        for (int nextSlot : items.keySet()) {
            if (items.get(nextSlot) != null && nextSlot > slot) {
                slot = nextSlot;
            }
        }
        return slot;
    }

    private int getMaxPage() {
        return (int) Math.ceil(((double) getHighestFilledSlot() + 1) / ((double) getSlots()));
    }

    private Inventory inventory() {
        boolean needsPagination = getMaxPage() > 1 && this.enablePagination;

        String name = getMenuName();
        if (currentPage > 0) {
            name = name + " (Page " + (currentPage + 1) + "/" + getMaxPage() + ")";
        }

        Inventory inv = Bukkit.createInventory(this, getSlots() + (needsPagination ? 9 : 0), name);

        for (int key = currentPage * getSlots(); key < (currentPage + 1) * getSlots(); key++) {
            if (key > getHighestFilledSlot()) break;

            if (items.containsKey(key)) {
                inv.setItem(key - (currentPage * getSlots()), items.get(key));
            }
        }

        if (needsPagination) {
            if (currentPage < getMaxPage() - 1) {
                inv.setItem((getSlots() + 9) - 1, getNext());
            }
            if (currentPage > 0) {
                inv.setItem((getSlots() + 9) - 9, getPrev());
            }
        }

        return inv;
    }

    public void flushRunningTasks() {
        this.runningTasks.forEach(BukkitTask::cancel);
    }

    public ItemStack getNext() {
        LanguageManager.LanguageUtils langUtils = Core.getInstance().getLanguageManger().getPlayer(this.player);
        ItemStack item = XMaterial.ARROW.parseItem();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Core.color(langUtils.getString("GUI.Page.Next.Name")));
        List<String> lore = langUtils.getList("GUI.Page.Next.Lore");
        lore.replaceAll(l -> l.replace("%num%", String.valueOf(currentPage + 2)));
        meta.setLore(Core.lore(lore));
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getPrev() {
        LanguageManager.LanguageUtils langUtils = Core.getInstance().getLanguageManger().getPlayer(this.player);
        ItemStack item = XMaterial.ARROW.parseItem();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Core.color(langUtils.getString("GUI.Page.Prev.Name")));
        List<String> lore = langUtils.getList("GUI.Page.Prev.Lore");
        lore.replaceAll(l -> l.replace("%num%", String.valueOf(currentPage)));
        meta.setLore(Core.lore(lore));
        item.setItemMeta(meta);
        return item;
    }

    public void nextPage() {
        if (currentPage < getMaxPage() - 1) {
            currentPage++;
            reopen();
            if (this.onPageChange != null) this.onPageChange.accept(this);
        }
    }

    public void prevPage() {
        if (currentPage > 0) {
            currentPage--;
            reopen();
            if (this.onPageChange != null) this.onPageChange.accept(this);
        }
    }

    public void setCurrentPage(int page) {
        this.currentPage = page;
        if (this.onPageChange != null) this.onPageChange.accept(this);
    }

    public Consumer<Menu> getOnPageChange() {
        return onPageChange;
    }

    public void setOnPageChange(Consumer<Menu> onPageChange) {
        this.onPageChange = onPageChange;
    }
}
