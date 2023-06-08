package me.mafrans.emotestf.bridge;

import me.mafrans.emotestf.EmotesTF;
import me.totalfreedom.totalfreedommod.admin.AdminList;
import org.bukkit.Server;

public final class TotalFreedomModBridge {
    private final boolean enabled;

    public TotalFreedomModBridge(final EmotesTF plugin) {
        final Server server = plugin.getServer();
        this.enabled = server.getPluginManager().isPluginEnabled("TotalFreedomMod");
    }

    public boolean isVanished(final String player) {
        return this.enabled && AdminList.vanished.contains(player);
    }
}
