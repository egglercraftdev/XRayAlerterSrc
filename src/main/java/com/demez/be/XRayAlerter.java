package com.demez.untitled;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class XRayAlerter extends JavaPlugin implements Listener {

    private final Map<UUID, Integer> diamondMined = new HashMap<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("XrayAlertPlugin has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("XrayAlertPlugin has been disabled.");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        if (block.getType() == Material.DIAMOND_ORE || block.getType() == Material.DEEPSLATE_DIAMOND_ORE) {
            int amount = 1;
            // Belirli bir eşya düşüp düşmediğini kontrol et
            if (event.isDropItems()) {
                for (ItemStack item : block.getDrops(player.getInventory().getItemInMainHand())) {
                    if (item.getType() == Material.DIAMOND) {
                        amount = item.getAmount();
                        break;
                    }
                }
            }
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.isOp()) {
                    p.sendMessage(ChatColor.GOLD + "XRay: " + ChatColor.RED + player.getName() + " has found " + amount + " diamonds!");
                }
            }
        }
    }
}
