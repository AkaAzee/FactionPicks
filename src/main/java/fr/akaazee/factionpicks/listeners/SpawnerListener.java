package fr.akaazee.factionpicks.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class SpawnerListener implements Listener {

    private int configCMD;

    public SpawnerListener(FileConfiguration config) {
        this.configCMD = config.getConfigurationSection("CMD").getInt("SpawnerPick");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){

        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (player.getInventory().getItemInMainHand().hasItemMeta()){
            if (player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData()) {
                int CMD = player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData();

                if (block.getType() == Material.SPAWNER && CMD == this.configCMD) {
                    event.setDropItems(false);

                    BlockState state = block.getState();
                    if (state instanceof CreatureSpawner) {
                        CreatureSpawner spawner = (CreatureSpawner) state;
                        ItemStack drop = new ItemStack(Material.SPAWNER);
                        BlockStateMeta bsm = (BlockStateMeta) drop.getItemMeta();
                        bsm.setBlockState(spawner);
                        bsm.setDisplayName("§f§b" + toTitleCase(spawner.getSpawnedType().toString().toLowerCase().replace('_', ' ')) + " Spawner");
                        drop.setItemMeta(bsm);


                        event.getBlock().getWorld().dropItemNaturally(block.getLocation(), drop);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPrepareItemEnchant(PrepareItemEnchantEvent event){

        EnchantingInventory enchantingInventory = (EnchantingInventory) event.getInventory();

        if(enchantingInventory.getItem().getItemMeta().getCustomModelData() == this.configCMD){
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onInventoryClick (InventoryClickEvent event){
        Inventory inv = event.getClickedInventory();
        ItemStack item = event.getCurrentItem();
        if(item != null)
            if (item.hasItemMeta()) {
                if (item.getItemMeta().hasCustomModelData()) {
                    if (inv.getType().equals(InventoryType.ANVIL) && item.getItemMeta().getCustomModelData() == this.configCMD && event.getSlot() == 2) {
                        event.getWhoClicked().sendMessage("§4You can't repair or enchant this item");
                        event.setCancelled(true);
                    }
                }
            }
    }

    public static String toTitleCase(String givenString) {
        String[] arr = givenString.split(" ");
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < arr.length; i++) {
            sb.append(Character.toUpperCase(arr[i].charAt(0)))
                    .append(arr[i].substring(1)).append(" ");
        }
        return sb.toString().trim();
    }

    @EventHandler
    public void PlaceSpawnerEvent(BlockPlaceEvent event) {
        ItemStack itemInHand = event.getItemInHand();
        Block block = event.getBlockPlaced();
        if (itemInHand.getType() == Material.SPAWNER) {
            BlockStateMeta blockStateMeta = (BlockStateMeta) itemInHand.getItemMeta();
            CreatureSpawner creatureSpawner = (CreatureSpawner) blockStateMeta.getBlockState();
            CreatureSpawner blockCreatureSpawner = (CreatureSpawner) block.getState();
            blockCreatureSpawner.setSpawnedType(creatureSpawner.getSpawnedType());
            blockCreatureSpawner.update();
        }
    }
}





