package codes.meruhz.nametag.api.data;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Nametag {

    @NotNull ChatColor getColor();

    @NotNull BaseComponent @Nullable [] getPrefix();

    @NotNull BaseComponent @Nullable [] getSuffix();

    boolean isVisible();

    boolean isCollidable();

    static @NotNull Nametag of(final @NotNull ChatColor color, final @NotNull BaseComponent @Nullable [] prefix, final @NotNull BaseComponent @Nullable [] suffix) {
        return Nametag.of(color, prefix, suffix, true, true);
    }

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
