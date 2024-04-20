package com.Fjc.instantglassbreak;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class InstantGlassBreak extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        registerEnchantment(new EnchantmentWrapper(101) {
            @Override
            public String getName() {
                return "InstantGlassBreak";
            }

            @Override
            public int getMaxLevel() {
                return 1;
            }

            @Override
            public int getStartLevel() {
                return 1;
            }

            @Override
            public EnchantmentTarget getItemTarget() {
                return EnchantmentTarget.TOOL;
            }

            @Override
            public boolean canEnchantItem(ItemStack item) {
                return item.getType() == Material.NETHERITE_PICKAXE;
            }

            @Override
            public boolean conflictsWith(Enchantment other) {
                return false;
            }

            @Override
            public boolean canEnchantItem(Material item) {
                return item == Material.NETHERITE_PICKAXE;
            }
        });
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.containsEnchantment(Enchantment.getByKey(EnchantmentWrapper.getKey(101)))) {
            if (event.getBlock().getType() == Material.GLASS) {
                event.setCancelled(true);
                event.getBlock().breakNaturally(item);
            }
        }
    }

    private void registerEnchantment(Enchantment enchantment) {
        try {
            Enchantment.registerEnchantment(enchantment);
        } catch (IllegalArgumentException ignored) {
            // Enchantment is already registered, do nothing
        }
    }
}
