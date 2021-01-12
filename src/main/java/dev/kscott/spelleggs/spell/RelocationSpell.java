package dev.kscott.spelleggs.spell;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;
import java.util.Random;

public class RelocationSpell extends Spell {

    private final int localX = 7;
    private final int localZ = 7;

    final @NonNull Random random;

    public RelocationSpell() {
        super("relocation");
        random = new Random();
    }

    @Override
    public void onActivation(@NonNull Location location, @NonNull Player player) {
        final int teleportationHeight = random.nextInt(20) + 15;
        final int localY = random.nextInt(15) + 5;

        final @NonNull World world = location.getWorld();

        final @NonNull Collection<Entity> entities = location.getNearbyEntities(localX, localY, localZ);


        final int minX = location.getBlockX() - localX;
        final int maxX = location.getBlockX() + localX;

        final int minY = location.getBlockY() - localY;
        final int maxY = location.getBlockY() + localY;

        final int minZ = location.getBlockZ() - localZ;
        final int maxZ = location.getBlockZ() + localZ;

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {

                    boolean teleport = new Random().nextInt(10) >= 3;

                    if (teleport) {
                        final @NonNull Block block = world.getBlockAt(x, y, z);

                        final @NonNull BlockData blockData = block.getBlockData().clone();

                        block.setType(Material.AIR);

                        world.getBlockAt(x, y + teleportationHeight, z).setBlockData(blockData);
                    }
                }
            }
        }

        for (final Entity entity : entities) {
            final @NonNull Location entityLocation = entity.getLocation();

            entityLocation.add(0, teleportationHeight, 0);

            entity.teleportAsync(entityLocation);
        }
    }
}
