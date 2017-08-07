package cc.isotopestudio.privilege;
/*
 * Created by Mars Tan on 8/7/2017.
 * Copyright ISOTOPE Studio
 */

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class CheckEquipTask extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            ItemStack[] armorContents = player.getInventory().getArmorContents();
            boolean change = false;
            for (int i = 0; i < armorContents.length; i++) {
                ItemStack item = armorContents[i];
                if (item == null || item.getType() == Material.AIR) continue;
                if (!PlayerListener.checkItem(item, player)) {
                    change = true;
                    HashMap<Integer, ItemStack> map = player.getInventory().addItem(item);
                    map.values().forEach(
                            tempItem -> player.getLocation().getWorld().dropItem(player.getLocation(), tempItem));
                    armorContents[i] = null;
                }
            }
            if (change) {
                player.getInventory().setArmorContents(armorContents);
                player.updateInventory();
                player.sendMessage(S.toPrefixRed("你不能装备此物品"));
            }
        }
    }
}
