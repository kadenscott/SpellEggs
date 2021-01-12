package dev.kscott.spelleggs;

import cloud.commandframework.Command;
import cloud.commandframework.CommandTree;
import cloud.commandframework.Description;
import cloud.commandframework.arguments.CommandArgument;
import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.execution.AsynchronousCommandExecutionCoordinator;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.paper.PaperCommandManager;
import dev.kscott.spelleggs.egg.Egg;
import dev.kscott.spelleggs.egg.EggRegistry;
import dev.kscott.spelleggs.listeners.EggHitListener;
import dev.kscott.spelleggs.listeners.EggInteractListener;
import dev.kscott.spelleggs.menu.MenuManager;
import dev.kscott.spelleggs.spell.SpellRegistry;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.function.Function;

public final class SpellEggsPlugin extends JavaPlugin {

    /**
     * PaperCommandManager instance
     */
    private @MonotonicNonNull PaperCommandManager<CommandSender> manager;

    /**
     * BukkitAudiences instance
     */
    private @MonotonicNonNull BukkitAudiences bukkitAudiences;

    private final @NonNull MenuManager menuManager;

    private final @NonNull EggRegistry eggRegistry;

    private final @NonNull SpellRegistry spellRegistry;

    public SpellEggsPlugin() {
        this.spellRegistry = new SpellRegistry();
        this.eggRegistry = new EggRegistry(this, this.spellRegistry);
        this.menuManager = new MenuManager();
    }

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new EggInteractListener(this), this);
        this.getServer().getPluginManager().registerEvents(new EggHitListener(this, this.eggRegistry), this);

        this.bukkitAudiences = BukkitAudiences.create(this);

        // Cloud stuff
        final @NonNull Function<CommandTree<CommandSender>, CommandExecutionCoordinator<CommandSender>> executionCoordinatorFunction =
                AsynchronousCommandExecutionCoordinator.<CommandSender>newBuilder().build();
        final @NonNull Function<CommandSender, CommandSender> mapperFunction = Function.identity();

        try {
            this.manager = new PaperCommandManager<>(
                    this,
                    executionCoordinatorFunction,
                    mapperFunction,
                    mapperFunction
            );
        } catch (final Exception e) {
            this.getLogger().severe("Failed to initialize cloud");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        constructCommand();
    }

    private void constructCommand() {
        final Command.Builder<CommandSender> builder = this.manager.commandBuilder("spelleggs");

        // give command
        this.manager.command(builder.literal("give")
                .argument(
                        StringArgument.of("egg"),
                        Description.of("The egg type")
                )
                .handler(ctx -> {
                    final @NonNull CommandSender sender = ctx.getSender();

                    if (!(sender instanceof Player)) {
                        return;
                    }

                    final @NonNull Player player = (Player) sender;

                    final @NonNull String eggId = ctx.get("egg");
                    final @Nullable Egg egg = this.eggRegistry.getEgg(eggId);

                    if (egg == null) {
                        sender.sendMessage("Invalid egg id");
                        return;
                    }

                    final @NonNull ItemStack itemStack = egg.getItemStack();

                    player.getInventory().addItem(itemStack);
                })
        );

        this.manager.command(builder.literal("gui")
                .handler(ctx -> {
                    final @NonNull CommandSender sender = ctx.getSender();

                    if (!(sender instanceof Player)) {
                        return;
                    }

                    final @NonNull Player player = (Player) sender;

                    this.menuManager.openMenu(player);
                })
        );

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
