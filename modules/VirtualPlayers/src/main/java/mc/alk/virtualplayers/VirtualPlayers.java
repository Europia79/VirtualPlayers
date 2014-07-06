package mc.alk.virtualplayers;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public class VirtualPlayers extends JavaPlugin implements Listener
{
    
    @Override
    public void onEnable()
    {
        // Bukkit.getPluginManager().registerEvents(this, this);
        // getCommand("vdc").setExecutor(new PlayerExecutor(this));
        // getCommand("virtualplayers").setExecutor(new VPExecutor(this));
        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable()
    {
        // mc.alk.virtualplayers.nms.{version}.VirtualPlayer.deleteVirtualPlayers();
        try {
            getNmsClass("VirtualPlayer").getDeclaredMethod("deleteVirtualPlayers", new Class[]{}).invoke(null);
        } catch (Exception ex) {
            Logger.getLogger(VirtualPlayers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setPlayerMessages(boolean show){
        // mc.alk.virtualplayers.nms.{version}.VirtualPlayer.setGlobalMessages(show);
        try {
            getNmsClass("VirtualPlayer").getDeclaredMethod("setGlobalMessages", boolean.class).invoke(null, show);
        } catch (Exception ex) {
            Logger.getLogger(VirtualPlayers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setEventMessages(boolean show){
        // mc.alk.virtualplayers.nms.{version}.VPBaseExecutor.setShowEventMessages(show);
        try {
            getNmsClass("VPBaseExecutor").getDeclaredMethod("setShowEventMessages", boolean.class).invoke(null, show);
        } catch (Exception ex) {
            Logger.getLogger(VirtualPlayers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Class<?> getNmsClass(String clazz) throws Exception {
        String mcVersion;
        try {
            mcVersion = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        } catch (ArrayIndexOutOfBoundsException ex) {
            mcVersion = "vpre";
        }
        return Class.forName("mc.alk.virtualplayers.nms." + mcVersion + "." + clazz);
    }
    
    private void registerListeners() {
        // mc.alk.virtualplayers.nms.{version}.PlayerListener
        try {
            getServer().getPluginManager().registerEvents(
                    (Listener) getNmsClass("PlayerListener").getConstructor().newInstance(), this);
        } catch (Exception ex) {
            Logger.getLogger(VirtualPlayers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void registerCommands() {
        // getCommand("vdc").setExecutor(new PlayerExecutor(this));
        // getCommand("virtualplayers").setExecutor(new VPExecutor(this));
        try {
            getCommand("vdc").setExecutor(
                    (CommandExecutor) getNmsClass("PlayerExecutor")
                    .getConstructor(new Class[]{Plugin.class}).newInstance(this));
            getCommand("virtualplayers").setExecutor(
                    (CommandExecutor) getNmsClass("VPExecutor")
                    .getConstructor(new Class[]{Plugin.class}).newInstance(this));
        } catch (Exception ex) {
            Logger.getLogger(VirtualPlayers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
