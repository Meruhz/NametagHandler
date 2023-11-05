package codes.meruhz.nametag.api.data;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This interface represents a player nametag.
 * A nametag can have a color, prefix, suffix, visibility and collision settings.
 */
public interface Nametag {

    /**
     * Get the color of the player's name.
     *
     * @return The color of the player's name, represented by a ChatColor.
     */
    @NotNull ChatColor getColor();

    /**
     * Get the prefix of the nametag, which is an optional array of BaseComponents.
     *
     * @return An array of BaseComponents representing the nametag's prefix, or null if no prefix is set.
     */
    @NotNull BaseComponent @Nullable [] getPrefix();

    /**
     * Get the suffix of the nametag, which is an optional array of BaseComponents.
     *
     * @return An array of BaseComponents representing the nametag's suffix, or null if no suffix is set.
     */
    @NotNull BaseComponent @Nullable [] getSuffix();

    /**
     * Check if the nametag is visible.
     *
     * @return true if the nametag is visible; false otherwise.
     */
    boolean isVisible();

    /**
     * Check if the nametag is collidable.
     *
     * @return true if the nametag is collidable; false otherwise.
     */
    boolean isCollidable();

    /**
     * Create a new Nametag with the specified color, prefix, and suffix. By default, the nametag is visible and collidable.
     *
     * @param color  The color of the player's name, represented by a ChatColor.
     * @param prefix An array of BaseComponents representing the nametag's prefix, or null if no prefix is set.
     * @param suffix An array of BaseComponents representing the nametag's suffix, or null if no suffix is set.
     * @return A new Nametag instance with the specified attributes.
     */
    static @NotNull Nametag of(final @NotNull ChatColor color, final @NotNull BaseComponent @Nullable [] prefix, final @NotNull BaseComponent @Nullable [] suffix) {
        return Nametag.of(color, prefix, suffix, true, true);
    }

    /**
     * Create a new Nametag with the specified color, prefix, suffix, visibility, and collision settings.
     *
     * @param color  The color of the player's name, represented by a ChatColor.
     * @param prefix     An array of BaseComponents representing the nametag's prefix, or null if no prefix is set.
     * @param suffix     An array of BaseComponents representing the nametag's suffix, or null if no suffix is set.
     * @param visible    true if the nametag is visible; false otherwise.
     * @param collidable true if the nametag is collidable; false otherwise.
     * @return A new Nametag instance with the specified attributes.
     */
    static @NotNull Nametag of(final @NotNull ChatColor color, final @NotNull BaseComponent @Nullable [] prefix, final @NotNull BaseComponent @Nullable [] suffix, final boolean visible, final boolean collidable) {
        return new Nametag() {

            @Override
            public @NotNull ChatColor getColor() {
                return color;
            }

            @Override
            public @NotNull BaseComponent @Nullable [] getPrefix() {
                return prefix;
            }

            @Override
            public @NotNull BaseComponent @Nullable [] getSuffix() {
                return suffix;
            }

            @Override
            public boolean isVisible() {
                return visible;
            }

            @Override
            public boolean isCollidable() {
                return collidable;
            }
        };
    }
}
