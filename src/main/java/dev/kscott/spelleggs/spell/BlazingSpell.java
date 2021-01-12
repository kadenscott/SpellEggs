package dev.kscott.spelleggs.spell;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Random;

public class BlazingSpell extends Spell {

    private final int localX = 7;
    private final int localY = 3;
    private final int localZ = 7;

    private final @NonNull Random random;

    public BlazingSpell() {
        super("blazing");
        this.random = new Random();
    }

    @Override
    public void onActivation(@NonNull Location location, @NonNull Player player) {
        final @NonNull World world = location.getWorld();

        final int minX = (int) location.getX() - localX;
        final int maxX = (int) location.getX() + localX;

        final int minY = (int) location.getY() - localY;
        final int maxY = (int) location.getY() + localY;

        final int minZ = (int) location.getZ() - localZ;
        final int maxZ = (int) location.getZ() + localZ;

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    final boolean ignite = random.nextBoolean();

                    if (!ignite) {
                        return;
                    }

                    final @NonNull Block block = world.getBlockAt(x, y, z);

                    final @NonNull Block blockBelow = world.getBlockAt(x, y - 1, z);

                    if (block.getType().isAir() && !blockBelow.getType().isAir()) {
                        block.setType(Material.FIRE);
                    }
                }
            }
        }
    }
}
