package dev.kscott.spelleggs.spell;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;

public class HealSpell extends Spell {

    public HealSpell() {
        super("heal");
    }

    @Override
    public void onActivation(@NonNull Location location, @NonNull Player player) {
        final @NonNull Collection<Player> players =  location.getNearbyPlayers(10);

        for (final @NonNull Player nearbyPlayer : players) {
            final double health = nearbyPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            nearbyPlayer.setHealth(health);
        }
    }
}
