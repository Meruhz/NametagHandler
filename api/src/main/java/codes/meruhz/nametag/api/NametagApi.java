package codes.meruhz.nametag.api;

import codes.meruhz.nametag.api.data.Nametag;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;

public interface NametagApi {

    @NotNull Collection<Nametag> getNametags();

    @NotNull Nametag getNametag(@NotNull UUID user);

    boolean hasNametag(@NotNull UUID user);

    void setNametag(@NotNull UUID user, @Nullable Nametag nametag);

    @NotNull Nametag getDefaultNametag();

    void setDefaultNametag(@NotNull Nametag nametag);

    void update(@NotNull Plugin plugin);
}