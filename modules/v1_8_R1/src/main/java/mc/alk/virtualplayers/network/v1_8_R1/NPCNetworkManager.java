package mc.alk.virtualplayers.network.v1_8_R1;

import java.lang.reflect.Field;

import net.minecraft.server.v1_8_R1.EnumProtocolDirection;
import net.minecraft.server.v1_8_R1.NetworkManager;
import lombok.*;

@Getter
public class NPCNetworkManager extends NetworkManager {

    public NPCNetworkManager() {
        super(EnumProtocolDirection.CLIENTBOUND); //MCP = isClientSide ---- SRG=field_150747_h
        Field channel = makeField(NetworkManager.class, "i"); //MCP = channel ---- SRG=field_150746_k
        Field address = makeField(NetworkManager.class, "j"); //MCP = address ---- SRG=field_77527_e

        setField(channel, this, new NullChannel());
        setField(address, this, new NullSocketAddress());

    }

    public static Field makeField(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void setField(Field field, Object objToSet, Object value) {
        field.setAccessible(true);
        try {
            field.set(objToSet, value);
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

}
