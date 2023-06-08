package me.mafrans.emotestf;

import java.io.File;
import java.io.IOException;
import me.mafrans.emotestf.bridge.TotalFreedomModBridge;
import me.mafrans.emotestf.commands.CommandLoader;
import me.mafrans.emotestf.commands.Command_emotes;
import me.mafrans.emotestf.commands.Command_notifyme;
import me.mafrans.emotestf.listeners.PlayerListener;
import me.mafrans.emotestf.util.EmoteLoader;
import org.bukkit.plugin.java.JavaPlugin;

public class EmotesTF extends JavaPlugin {
   public static EmotesTF plugin;
   public CooldownTask cooldownTask;
   public TotalFreedomModBridge TFMBridge;

   public void onEnable() {
      plugin = this;
      plugin.getServer().getPluginManager().registerEvents(new PlayerListener(), plugin);
      this.saveDefaultConfig();
      plugin.getCommand("notifyme").setExecutor(new Command_notifyme());
      plugin.getCommand("emotes").setExecutor(new Command_emotes());
      this.TFMBridge = new TotalFreedomModBridge(plugin);
      this.cooldownTask = new CooldownTask();
      this.cooldownTask.runTaskTimerAsynchronously(plugin, 0L, 20L);

      try {
         EmoteLoader.registerDefaultEmote("greet");
         EmoteLoader.registerDefaultEmote("highfive");
         EmoteLoader.registerDefaultEmote("dance");
         EmoteLoader.registerDefaultEmote("hug");
         EmoteLoader.registerDefaultEmote("smile");
         EmoteLoader.registerDefaultEmote("thumbsup");
         EmoteLoader.registerOtherDefault("emotes", "_Emote Reference Sheet_.txt", "emotes");
         EmoteLoader.saveOtherDefaults(this.getDataFolder());
         EmoteLoader.saveDefaultEmotes(new File(this.getDataFolder() + "/emotes"));
         EmoteLoader.loadEmotes(new File(this.getDataFolder() + "/emotes"));
         CommandLoader.loadAllCommands();
      } catch (NoSuchFieldException | IllegalAccessException | IOException var2) {
         var2.printStackTrace();
      }
   }

   public void onDisable() {
      this.cooldownTask.cancel();
   }
}
