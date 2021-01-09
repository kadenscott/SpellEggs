package dev.kscott.spelleggs.listeners;

import com.destroystokyo.paper.Namespaced;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class EggInteractListener implements Listener {

    private final @NonNull JavaPlugin plugin;

    private final @NonNull NamespacedKey eggIdKey;

    private final @NonNull NamespacedKey playerKey;

    public EggInteractListener(final @NonNull JavaPlugin plugin) {
        this.plugin = plugin;
        this.eggIdKey = new NamespacedKey(plugin, "egg_id");
        this.playerKey = new NamespacedKey(plugin, "player_id");
    }

    @EventHandler
    public void onEggInteract(PlayerInteractEvent event) {
        final @Nullable ItemStack itemStack = event.getItem();


        if (itemStack == null) {
            return;
        }

        final @Nullable ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta == null) {
            return;
        }

        if (!itemMeta.getPersistentDataContainer().has(eggIdKey, PersistentDataType.STRING)) {
            return;
        }

        event.setCancelled(true);

        // consume egg
        int count = itemStack.getAmount();

        if (count == 1) {
            itemStack.setAmount(0);
        } else {
            itemStack.setAmount(itemStack.getAmount() - 1);
        }

        final @NonNull String eggId = itemMeta.getPersistentDataContainer().get(eggIdKey, PersistentDataType.STRING);

        final @NonNull Player player = event.getPlayer();

        // Create projectile
        final @NonNull Egg egg = player.launchProjectile(Egg.class);

        egg.getPersistentDataContainer().set(eggIdKey, PersistentDataType.STRING, eggId);
        egg.getPersistentDataContainer().set(playerKey, PersistentDataType.STRING, player.getName());
    }



}
