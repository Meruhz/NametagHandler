package codes.meruhz.nametag.api.impl;

import codes.meruhz.nametag.api.NametagApi;
import codes.meruhz.nametag.api.data.Nametag;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * This abstract class serves as the base implementation of the NametagApi interface and provides methods
 * for managing nametags associated with users in a virtual environment.
 */
public abstract class AbstractNametagApi implements NametagApi {

    private final @NotNull Map<@NotNull UUID, @NotNull Nametag> nametags;

    private @NotNull Nametag defaultNametag;

    /**
     * Constructs an AbstractNametagApi with the default nametag set to a blank nametag with the color ChatColor.RESET.
     */
    protected AbstractNametagApi() {
        this(Nametag.of(ChatColor.RESET, null, null));
    }

    /**
     * Constructs an AbstractNametagApi with a specified default nametag.
     *
     * @param defaultNametag The default nametag to use for users without a specific nametag set.
     */
    protected AbstractNametagApi(@NotNull Nametag defaultNametag) {
        this.defaultNametag = defaultNametag;
        this.nametags = new HashMap<>();
    }

    /**
     * Get the map of user UUIDs to their associated nametags.
     *
     * @return A map of user UUIDs to their corresponding nametags.
     */
    protected @NotNull Map<@NotNull UUID, @NotNull Nametag> getNametagsMap() {
        return this.nametags;
    }

    /**
     * Get a collection of all active nametags.
     *
     * @return A collection of all active nametags.
     */
    @Override
    public @NotNull Collection<Nametag> getNametags() {
        return this.getNametagsMap().values();
    }

    /**
     * Get the nametag associated with a specific user.
     *
     * @param user The UUID of the user.
     * @return The nametag associated with the user or the default nametag if not set.
     */
    @Override
    public @NotNull Nametag getNametag(@NotNull UUID user) {
        return this.getNametagsMap().computeIfAbsent(user, key -> this.getDefaultNametag());
    }

    /**
     * Check if a user has a custom nametag set.
     *
     * @param user The UUID of the user.
     * @return true if the user has a custom nametag; false if using the default nametag.
     */
    @Override
    public boolean hasNametag(@NotNull UUID user) {
        return !this.getNametag(user).equals(this.getDefaultNametag());
    }

    /**
     * Set the nametag for a specific user.
     *
     * @param user The UUID of the user.
     * @param nametag The nametag to set or the default nametag if null.
     */
    @Override
    public final void setNametag(@NotNull UUID user, @Nullable Nametag nametag) {
        if (nametag == null && this.hasNametag(user)) {
            nametag = this.getDefaultNametag();
        }

        assert nametag != null;
        this.setNametag(user, nametag.getColor(), nametag.getPrefix(), nametag.getSuffix(), nametag.isVisible(), nametag.isCollidable());
        this.getNametagsMap().put(user, nametag);
    }

    /**
     * Set the specific nametag attributes for a user.
     *
     * @param user The UUID of the user.
     * @param color The color of the nametag.
     * @param prefix The prefix for the nametag.
     * @param suffix The suffix for the nametag.
     * @param visible true if the nametag is visible; false if hidden.
     * @param collidable true if the nametag is collidable; false if not collidable.
     */
    @ApiStatus.OverrideOnly
    protected abstract void setNametag(@NotNull UUID user, @NotNull ChatColor color, @NotNull BaseComponent @Nullable [] prefix, @NotNull BaseComponent @Nullable [] suffix, boolean visible, boolean collidable);

    /**
     * Get the default nametag.
     *
     * @return The default nametag.
     */
    @Override
    public @NotNull Nametag getDefaultNametag() {
        return this.defaultNametag;
    }

    /**
     * Set the default nametag.
     *
     * @param nametag The default nametag to set.
     */
    @Override
    public void setDefaultNametag(@NotNull Nametag nametag) {
        this.defaultNametag = nametag;
    }

    /**
     * Update nametags for all online players, typically called periodically.
     *
     * @param plugin The plugin instance that schedules the update.
     */
    @Override
    public void update(@NotNull Plugin plugin) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {

            for (Player target : Bukkit.getOnlinePlayers()) {
                this.setNametag(target.getUniqueId(), this.getNametag(target.getUniqueId()));
            }

        }, 20L);
    }
}
