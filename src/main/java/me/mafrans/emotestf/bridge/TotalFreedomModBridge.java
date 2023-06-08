package me.mafrans.emotestf.bridge;

import java.util.List;
import me.mafrans.emotestf.EmotesTF;
import me.totalfreedom.totalfreedommod.TotalFreedomMod;
import me.totalfreedom.totalfreedommod.admin.AdminList;
import me.totalfreedom.totalfreedommod.command.Command_vanish;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class TotalFreedomModBridge {
   private TotalFreedomMod tfm;
   private boolean enabled;

   public TotalFreedomModBridge(EmotesTF plugin) {
      Server server = plugin.getServer();
      Plugin[] pluginList = server.getPluginManager().getPlugins();
      this.enable();
      boolean hasTFM = false;

      for(Plugin pl : pluginList) {
         if (hasTFM) {
            break;
         }

         if (pl.isEnabled()) {
            try {
               this.tfm = (TotalFreedomMod)pl;
               hasTFM = true;
            } catch (NoClassDefFoundError | ClassCastException var10) {
               hasTFM = false;
            }
         }
      }

      if (!hasTFM) {
         plugin.getLogger().warning("Could not enable TotalFreedomMod bridge, as the plugin is not enabled!");
         this.disable();
      }
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public TotalFreedomMod getTFM() {
      return this.tfm;
   }

   public AdminList getAdminList() {
      return this.tfm != null ? this.tfm.al : null;
   }

   public List<Player> getVanishedPlayers() {
      return this.tfm != null ? Command_vanish.VANISHED : null;
   }

   public void enable() {
      this.enabled = true;
   }

   public void disable() {
      this.enabled = false;
   }
}
