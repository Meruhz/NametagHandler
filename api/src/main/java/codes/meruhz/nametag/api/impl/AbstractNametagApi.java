package codes.meruhz.nametag.api.impl;

import codes.meruhz.nametag.api.NametagApi;
import codes.meruhz.nametag.api.data.Nametag;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
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

public abstract class AbstractNametagApi implements NametagApi {

    private final @NotNull Map<@NotNull UUID, @NotNull Nametag> nametags;

    private @NotNull Nametag defaultNametag;

    protected AbstractNametagApi() {
        this(Nametag.of(ChatColor.YELLOW, TextComponent.fromLegacyText("§a§lVIP "), null));
    }

    protected AbstractNametagApi(@NotNull Nametag defaultNametag) {
        this.defaultNametag = defaultNametag;
        this.nametags = new HashMap<>();
    }

    protected @NotNull Map<@NotNull UUID, @NotNull Nametag> getNametagsMap() {
        return this.nametags;
    }

    @Override
    public @NotNull Collection<Nametag> getNametags() {
        return this.getNametagsMap().values();
    }

    @Override
    public @NotNull Nametag getNametag(@NotNull UUID user) {
        return this.getNametagsMap().computeIfAbsent(user, key -> this.getDefaultNametag());
    }

    @Override
    public boolean hasNametag(@NotNull UUID user) {
        return !this.getNametag(user).equals(this.getDefaultNametag());
    }

    @Override
    public final void setNametag(@NotNull UUID user, @Nullable Nametag nametag) {
        if(nametag == null && this.hasNametag(user)) {
            nametag = this.getDefaultNametag();
        }

        assert nametag != null;
        this.setNametag(user, nametag.getColor(), nametag.getPrefix(), nametag.getSuffix(), nametag.isVisible(), nametag.isCollidable());
        this.getNametagsMap().put(user, nametag);
    }

    @ApiStatus.OverrideOnly
    protected abstract void setNametag(@NotNull UUID user, @NotNull ChatColor color, @NotNull BaseComponent @Nullable [] prefix, @NotNull BaseComponent @Nullable [] suffix, boolean visible, boolean collidable);

    @Override
    public @NotNull Nametag getDefaultNametag() {
        return this.defaultNametag;
    }

    @Override
    public void setDefaultNametag(@NotNull Nametag nametag) {
        this.defaultNametag = nametag;
    }

    @Override
    public void update(@NotNull Plugin plugin) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {

            for(Player target : Bukkit.getOnlinePlayers()) {
                this.setNametag(target.getUniqueId(), this.getNametag(target.getUniqueId()));
            }

        }, 20L);
    }
}
