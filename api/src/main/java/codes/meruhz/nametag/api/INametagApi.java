package codes.meruhz.nametag.api;

import codes.meruhz.nametag.api.data.Nametag;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;

public interface INametagApi {

    /**
     * Get a collection of all active nametags.
     *
     * @return A collection of Nametag instances.
     */
    @NotNull Collection<@NotNull Nametag> getNametags();

    /**
     * Get the nametag associated with a specific user.
     *
     * @param user The UUID of the user.
     * @return The Nametag associated with the user.
     */
    @NotNull Nametag getNametag(@NotNull UUID user);

    /**
     * Check if a user has a custom nametag set.
     *
     * @param user The UUID of the user.
     * @return true if the user has a custom nametag; false if using the default nametag.
     */
    boolean hasNametag(@NotNull UUID user);

    /**
     * Set the nametag for a specific user.
     *
     * @param user     The UUID of the user.
     * @param nametag  The nametag to set or the default nametag if null.
     */
    void setNametag(@NotNull UUID user, @Nullable Nametag nametag);

    /**
     * Get the default nametag.
     *
     * @return The default Nametag used when a user doesn't have a custom nametag.
     */
    @NotNull Nametag getDefaultNametag();

    /**
     * Set the default nametag.
     *
     * @param nametag The default Nametag to set for users without a custom nametag.
     */
    void setDefaultNametag(@NotNull Nametag nametag);

    /**
     * Update nametags for all online players, typically called periodically.
     *
     * @param plugin The plugin instance that schedules the update.
     */
    void update(@NotNull Plugin plugin);
}