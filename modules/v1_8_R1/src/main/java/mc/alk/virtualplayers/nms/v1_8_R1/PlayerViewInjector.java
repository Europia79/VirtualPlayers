package mc.alk.virtualplayers.nms.v1_8_R1;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_8_R1.CraftServer;

/**
 * 
 * @author Nikolai
 */
public class PlayerViewInjector implements Injector {

    @Override
    public void inject(VirtualPlayer vp) {
        Server server = Bukkit.getServer();
        CraftServer cserver = (CraftServer) server;
        cserver.getClass();
    }

}
