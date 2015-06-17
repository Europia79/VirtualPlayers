package mc.alk.virtualplayers.nms.v1_8_R1;

import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.server.v1_8_R1.DedicatedPlayerList;
import net.minecraft.server.v1_8_R1.EntityPlayer;
import net.minecraft.server.v1_8_R1.EnumProtocolDirection;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.PlayerList;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_8_R1.CraftServer;

/**
 * 
 * @author Nikolai
 */
public class PlayerListInjector implements Injector {

    @Override
    public void inject(VirtualPlayer vp) {
        Server server = Bukkit.getServer();
        CraftServer cserver = (CraftServer) server;
        try {
            Field f1 = cserver.getClass().getDeclaredField("playerList");
            f1.setAccessible(true);
            DedicatedPlayerList playerList = (DedicatedPlayerList) f1.get(cserver);
            Field f2 = playerList.getClass().getSuperclass().getDeclaredField("players");
            f2.setAccessible(true);
            List players = (List) f2.get(playerList);
            NetworkManager networkManager = new NetworkManager(EnumProtocolDirection.SERVERBOUND);
            EntityPlayer entityPlayer = vp.getHandle();
            playerList.a(networkManager, entityPlayer);
            players.add(vp.getHandle());
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(PlayerListInjector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(PlayerListInjector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(PlayerListInjector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(PlayerListInjector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
