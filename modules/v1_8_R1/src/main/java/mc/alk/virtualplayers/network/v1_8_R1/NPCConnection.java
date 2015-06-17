package mc.alk.virtualplayers.network.v1_8_R1;

import mc.alk.virtualplayers.nms.v1_8_R1.VirtualPlayer;

import net.minecraft.server.v1_8_R1.EntityPlayer;
import net.minecraft.server.v1_8_R1.MinecraftServer;
import net.minecraft.server.v1_8_R1.Packet;
import net.minecraft.server.v1_8_R1.PlayerConnection;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R1.CraftServer;

import lombok.*;

@Getter
public class NPCConnection extends PlayerConnection {

	public NPCConnection(EntityPlayer npc) {
		super(getHandle(Bukkit.getServer()), new NPCNetworkManager(), npc);
                // npc.joining = true;
	}
	@Override
	public void sendPacket(Packet packet) {
		//Don't send packets to an npc
	}
        
        public static MinecraftServer getHandle(org.bukkit.Server bukkitServer) {
    	if (bukkitServer instanceof CraftServer) {
    		return ((CraftServer)bukkitServer).getServer();
    	} else {
    		return null;
    	}
    }
}
