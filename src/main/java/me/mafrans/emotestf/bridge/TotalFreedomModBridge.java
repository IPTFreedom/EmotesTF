package me.mafrans.emotestf.bridge;

import java.util.List;
import me.mafrans.emotestf.EmotesTF;
import me.totalfreedom.totalfreedommod.admin.AdminList;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;

public class TotalFreedomModBridge {
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


   public List<String> getVanishedPlayers() {
      return this.enabled ? AdminList.vanished : null;
   }

   public void enable() {
      this.enabled = true;
   }

   public void disable() {
      this.enabled = false;
   }
}
