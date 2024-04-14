package fr.akaazee.factionpicks;

import fr.akaazee.factionpicks.listeners.SpawnerListener;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.damage.DamageEffect;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class FactionPicks extends JavaPlugin {

    @Override
    public void onEnable() {

        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new SpawnerListener(getConfig()), this);




        ItemStack sPick = new ItemStack(Material.NETHERITE_PICKAXE, 1);
        ItemMeta meta = sPick.getItemMeta();
        meta.setDisplayName("§f§bSpawner Breaker");
        meta.setCustomModelData(getConfig().getConfigurationSection("CMD").getInt("SpawnerPick"));
        meta.setLore(List.of(new String[]{"This pickaxe only has 3 uses !"}));
        Damageable damageMeta = (Damageable) meta;
        damageMeta.setDamage(sPick.getType().getMaxDurability()-3);
        sPick.setItemMeta(meta);

        ShapedRecipe sPickRecipe = new ShapedRecipe(new NamespacedKey(this, "spawnerPick"), sPick);

        sPickRecipe.shape("%%%", " * ", " * ");
        sPickRecipe.setIngredient('%', Material.NETHER_STAR);
        sPickRecipe.setIngredient('*', Material.DIAMOND_BLOCK);

        getServer().addRecipe(sPickRecipe);
    }

    @Override
    public void onDisable() {
        getServer().clearRecipes();
    }
}
