package fr.akaazee.factionpicks.listeners;

import com.massivecraft.factions.*;
import com.massivecraft.factions.zcore.fperms.Access;
import com.massivecraft.factions.zcore.fperms.PermissableAction;
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

    private final int configCMD;
    private BlockFace blockFace;

    public RangeListener(FileConfiguration config) {
        this.configCMD = config.getConfigurationSection("CMD").getInt("RangePick");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(event.isCancelled())return;
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (!event.isCancelled()) {
            if (player.getInventory().getItemInMainHand().hasItemMeta()) {
                if (player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData()) {
                    int CMD = player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData();

                    if (CMD == this.configCMD) {
                        World world = player.getWorld();
                        ItemStack item = player.getInventory().getItemInMainHand();
                        switch (this.blockFace) {
                            case UP:
                            case DOWN:
                                breakBlock(-1, 0, 1, block, player, item);
                                breakBlock(0, 0, 1, block, player, item);
                                breakBlock(1, 0, 1, block, player, item);
                                breakBlock(-1, 0, 0, block, player, item);
                                breakBlock(1, 0, 0, block, player, item);
                                breakBlock(-1, 0, -1, block, player, item);
                                breakBlock(0, 0, -1, block, player, item);
                                breakBlock(1, 0, -1, block, player, item);
                                break;

                            case NORTH:
                            case SOUTH:
                                breakBlock(-1, 1, 0, block, player, item);
                                breakBlock(0, 1, 0, block, player, item);
                                breakBlock(1, 1, 0, block, player, item);
                                breakBlock(-1, 0, 0, block, player, item);
                                breakBlock(1, 0, 0, block, player, item);
                                breakBlock(-1, -1, 0, block, player, item);
                                breakBlock(0, -1, 0, block, player, item);
                                breakBlock(1, -1, 0, block, player, item);
                                break;

                            case EAST:
                            case WEST:
                                breakBlock(0, 1, 1, block, player, item);
                                breakBlock(0, 1, 0, block, player, item);
                                breakBlock(0, 1, -1, block, player, item);
                                breakBlock(0, 0, 1, block, player, item);
                                breakBlock(0, 0, -1, block, player, item);
                                breakBlock(0, -1, 1, block, player, item);
                                breakBlock(0, -1, 0, block, player, item);
                                breakBlock(0, -1, -1, block, player, item);
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

    public void breakBlock(int x, int y, int z, Block block, Player player, ItemStack item){
        Block nBlock = player.getWorld().getBlockAt(block.getX() + x, block.getY() + y, block.getZ() + z);
        FLocation fLocation = new FLocation(nBlock.getLocation());
        FPlayer fPlayer = FPlayers.getInstance().getById(player.getUniqueId().toString());
        Faction faction = Board.getInstance().getFactionAt(fLocation);
        if(faction.getAccess(fPlayer, PermissableAction.DESTROY).equals(Access.ALLOW)){
            nBlock.breakNaturally(item);
        } else if (faction.isWilderness()) {
            nBlock.breakNaturally(item);
        }
    }
}
