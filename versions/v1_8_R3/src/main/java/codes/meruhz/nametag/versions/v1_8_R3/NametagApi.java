package codes.meruhz.nametag.versions.v1_8_R3;

import codes.meruhz.nametag.api.impl.AbstractNametagApi;
import codes.meruhz.nametag.api.utils.ComponentUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class NametagApi extends AbstractNametagApi {

    @Override
    protected void setNametag(@NotNull UUID user, @NotNull ChatColor color, @NotNull BaseComponent @Nullable [] prefix, @NotNull BaseComponent @Nullable [] suffix, boolean visible, boolean collidable) {
        @Nullable Player player = Bukkit.getPlayer(user);

        if(player == null) {
            throw new NullPointerException("User '" + user + "' is not online");
        }

        @NotNull Scoreboard scoreboard = new Scoreboard();
        @Nullable ScoreboardTeam team = scoreboard.getTeam(player.getName());
        team = team == null ? scoreboard.createTeam(player.getName()) : team;

        team.a(EnumChatFormat.valueOf(color.name()));
        team.setPrefix(prefix == null ? "" : ComponentUtils.getText(prefix));
        team.setSuffix(suffix == null ? "" : ComponentUtils.getText(suffix));
        team.setNameTagVisibility(visible ? ScoreboardTeamBase.EnumNameTagVisibility.ALWAYS : ScoreboardTeamBase.EnumNameTagVisibility.NEVER);
        team.b(ScoreboardTeamBase.EnumNameTagVisibility.NEVER);

        if(!scoreboard.addPlayerToTeam(player.getName(), team.getName())) {
            throw new RuntimeException("An error occurred while add player '" + player.getName() + "' to team '" + team.getName() + "'");
        }

        @NotNull PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam(team, 0);
        Bukkit.getOnlinePlayers().forEach(target -> ((CraftPlayer) target).getHandle().playerConnection.sendPacket(packet));
    }
}