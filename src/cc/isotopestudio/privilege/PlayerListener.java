package cc.isotopestudio.privilege;
/*
 * Created by Mars Tan on 8/7/2017.
 * Copyright ISOTOPE Studio
 */

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import static cc.isotopestudio.privilege.Privilege.classlore;
import static cc.isotopestudio.privilege.Privilege.levellore;

public class PlayerListener implements Listener {

    static boolean checkItem(ItemStack item, Player player) {
        if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
            for (String line : item.getItemMeta().getLore()) {
                line = ChatColor.stripColor(line);
                if (line.startsWith(levellore)) {
                    try {
                        int level = Integer.parseInt(line.replaceAll(levellore, ""));
                        if (level > player.getLevel()) {
                            return false;
                        }
                    } catch (NumberFormatException ignored) {
                    }
                }
                if (line.startsWith(classlore)) {
                    String className = line.replaceAll(classlore, "").replaceAll(" ", "");
                    if (!player.hasPermission("class." + Privilege.classNameMap.get(className))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        ItemStack item = e.getItem();
        if (item == null || item.getType() == Material.AIR) {
            return;
        }
        Player player = e.getPlayer();
        if (!checkItem(item, player)) {
            e.setCancelled(true);
            int oldPos = -1;
            for (int i = 0; i < 9; i++) {
                if (item.equals(player.getInventory().getContents()[i])) {
                    oldPos = i;
                    break;
                }
            }
            if (oldPos > 0)
                for (int i = 9; i < player.getInventory().getContents().length; i++) {
                    if (player.getInventory().getContents()[i] == null) {
                        player.getInventory().setItem(i, item);
                        player.getInventory().setItem(oldPos,null);
                        player.updateInventory();
                        player.sendMessage(S.toPrefixRed("你无法使用这个物品"));
                        return;
                    }
                }
        }
    }
}
