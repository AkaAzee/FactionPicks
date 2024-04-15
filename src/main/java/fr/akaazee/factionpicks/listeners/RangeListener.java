package fr.akaazee.factionpicks.listeners;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class RangeListener implements Listener {

    private int configCMD;
    private BlockFace blockFace;

    public RangeListener(FileConfiguration config) {
        this.configCMD = config.getConfigurationSection("CMD").getInt("RangePick");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if(!event.isCancelled()) {
            if (player.getInventory().getItemInMainHand().hasItemMeta()) {
                if (player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData()) {
                    int CMD = player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData();

                    if (CMD == this.configCMD) {
                        World world = player.getWorld();
                        ItemStack item = player.getInventory().getItemInMainHand();
                        switch (this.blockFace) {
                            case UP:
                            case DOWN:
                                world.getBlockAt(block.getX()-1, block.getY(), block.getZ()+1).breakNaturally(item);
                                world.getBlockAt(block.getX(), block.getY(), block.getZ()+1).breakNaturally(item);
                                world.getBlockAt(block.getX()+1, block.getY(), block.getZ()+1).breakNaturally(item);
                                world.getBlockAt(block.getX()-1, block.getY(), block.getZ()).breakNaturally(item);
                                world.getBlockAt(block.getX()+1, block.getY(), block.getZ()).breakNaturally(item);
                                world.getBlockAt(block.getX()-1, block.getY(), block.getZ()-1).breakNaturally(item);
                                world.getBlockAt(block.getX(), block.getY(), block.getZ()-1).breakNaturally(item);
                                world.getBlockAt(block.getX()+1, block.getY(), block.getZ()-1).breakNaturally(item);
                                break;

                            case NORTH:
                            case SOUTH:
                                world.getBlockAt(block.getX()-1, block.getY()+1, block.getZ()).breakNaturally(item);
                                world.getBlockAt(block.getX(), block.getY()+1, block.getZ()).breakNaturally(item);
                                world.getBlockAt(block.getX()+1, block.getY() +1, block.getZ()).breakNaturally(item);
                                world.getBlockAt(block.getX()-1, block.getY(), block.getZ()).breakNaturally(item);
                                world.getBlockAt(block.getX()+1, block.getY(), block.getZ()).breakNaturally(item);
                                world.getBlockAt(block.getX()-1, block.getY() - 1, block.getZ()).breakNaturally(item);
                                world.getBlockAt(block.getX(), block.getY() - 1, block.getZ()).breakNaturally(item);
                                world.getBlockAt(block.getX()+1, block.getY() - 1, block.getZ()).breakNaturally(item);
                                break;

                            case EAST:
                            case WEST:
                                world.getBlockAt(block.getX(), block.getY()+1, block.getZ()+1).breakNaturally(item);
                                world.getBlockAt(block.getX(), block.getY()+1, block.getZ()).breakNaturally(item);
                                world.getBlockAt(block.getX(), block.getY()+1, block.getZ()-1).breakNaturally(item);
                                world.getBlockAt(block.getX(), block.getY(), block.getZ()+1).breakNaturally(item);
                                world.getBlockAt(block.getX(), block.getY(), block.getZ()-1).breakNaturally(item);
                                world.getBlockAt(block.getX(), block.getY()-1, block.getZ()+1).breakNaturally(item);
                                world.getBlockAt(block.getX(), block.getY() - 1, block.getZ()).breakNaturally(item);
                                world.getBlockAt(block.getX(), block.getY() - 1, block.getZ()-1).breakNaturally(item);
                                break;
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        this.blockFace = event.getBlockFace();
    }

}
