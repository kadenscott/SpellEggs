package dev.kscott.spelleggs.listeners;

import dev.kscott.spelleggs.egg.EggRegistry;
import dev.kscott.spelleggs.spell.Spell;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class EggHitListener implements Listener {

    private final @NonNull JavaPlugin plugin;

    private final @NonNull NamespacedKey eggIdKey;

    private final @NonNull NamespacedKey playerKey;

    private final @NonNull EggRegistry eggRegistry;

    public EggHitListener(
            final @NonNull JavaPlugin plugin,
            final @NonNull EggRegistry eggRegistry
    ) {
        this.plugin = plugin;
        this.eggRegistry = eggRegistry;
        this.eggIdKey = new NamespacedKey(plugin, "egg_id");
        this.playerKey = new NamespacedKey(plugin, "player_id");
    }

    @EventHandler
    public void onEggHit(final @NonNull ProjectileHitEvent event) {
        final @NonNull Entity entity = event.getEntity();

        if (!(entity instanceof Egg)) {
            return;
        }

        if (!entity.getPersistentDataContainer().has(eggIdKey, PersistentDataType.STRING)) {
            return;
        }

        final @NonNull String eggId = entity.getPersistentDataContainer().get(eggIdKey, PersistentDataType.STRING);

        if (!entity.getPersistentDataContainer().has(playerKey, PersistentDataType.STRING)) {
            return;
        }

        final @NonNull String playerId = entity.getPersistentDataContainer().get(playerKey, PersistentDataType.STRING);

        final @Nullable Player player = Bukkit.getPlayer(playerId);

        if (player == null) {
            return;
        }

        final dev.kscott.spelleggs.egg.Egg egg = eggRegistry.getEgg(eggId);

        if (egg == null) {
            return;
        }

        final @NonNull Spell spell = egg.getSpell();

        spell.onActivation(event.getEntity().getLocation(), player);

    }

}
