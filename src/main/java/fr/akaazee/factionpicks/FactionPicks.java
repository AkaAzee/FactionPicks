package fr.akaazee.factionpicks;

import fr.akaazee.factionpicks.listeners.RangeListener;
import fr.akaazee.factionpicks.listeners.SpawnerListener;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
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
        getServer().getPluginManager().registerEvents(new RangeListener(getConfig()), this);


        ItemStack superPick = new ItemStack(Material.NETHERITE_PICKAXE, 1);
        ItemMeta superMeta = superPick.getItemMeta();
        superMeta.setDisplayName("§f§bPioche MultiBlock");
        superMeta.setCustomModelData(getConfig().getConfigurationSection("CMD").getInt("Pioche MultiBlock"));
        Damageable superDamageMeta = (Damageable) superMeta;
        superPick.setItemMeta(superMeta);

        ShapedRecipe superPickRecipe = new ShapedRecipe(new NamespacedKey(this, "superPick"), superPick);

        superPickRecipe.shape("X%X", " * ", " * ");
        superPickRecipe.setIngredient('%', Material.NETHER_STAR);
        superPickRecipe.setIngredient('*', Material.DIAMOND_BLOCK);
        superPickRecipe.setIngredient('X', Material.OBSIDIAN);

        getServer().addRecipe(superPickRecipe);



        ItemStack sPick = new ItemStack(Material.NETHERITE_PICKAXE, 1);
        ItemMeta meta = sPick.getItemMeta();
        meta.setDisplayName("§f§bPioche à Spawner");
        meta.setCustomModelData(getConfig().getConfigurationSection("CMD").getInt("Pioche à Spawner"));
        meta.setLore(List.of(new String[]{"Cette pioche n'a que 3 utilisations !"}));
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
