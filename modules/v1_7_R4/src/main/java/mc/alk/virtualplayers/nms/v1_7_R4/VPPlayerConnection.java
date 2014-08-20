package mc.alk.virtualplayers.nms.v1_7_R4;

import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.NetworkManager;
import net.minecraft.server.v1_7_R4.PacketListener;
import net.minecraft.server.v1_7_R4.PlayerConnection;
import net.minecraft.util.io.netty.buffer.ByteBufAllocator;
import net.minecraft.util.io.netty.channel.*;
import net.minecraft.util.io.netty.util.Attribute;
import net.minecraft.util.io.netty.util.AttributeKey;
import net.minecraft.util.io.netty.util.concurrent.EventExecutorGroup;

import java.lang.reflect.Field;
import java.net.SocketAddress;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*
 * This provides compatibility for HoloAPI.
 *
 * HoloAPI looks for the following:
 * - The EntityPlayer's handle must have a non-null PlayerConnection (field: playerConnection)
 * - The PlayerConnection must have a non-null NetworkManager (set in the PlayerConnection constructor)
 * - The NetworkManager must have:
 *   - In older versions, any declared field with type Channel
 *   - In newer versions, the Channel field in the base NetworkManager class must be set
 * - The Channel must:
 *   - In older versions, return true for `isActive()`
 */
public class VPPlayerConnection extends PlayerConnection {

    // 'wraps' the EntityPlayer with a PlayerConnection
    // PlayerConnection's constructor will set the player's `playerConnection` field
    public static void wrap(EntityPlayer player) {
        new VPPlayerConnection(player);
    }

    private VPPlayerConnection(EntityPlayer player) {
        super(null, FAKE_NETWORK_MANAGER, player);
    }


    // ----- Stub implementations

    private static final NetworkManager FAKE_NETWORK_MANAGER = new NetworkManager(false) {
        private Channel a = FAKE_CHANNEL; // Older versions of HoloAPI look for any field containing a Channel

        {
            // Newer versions search the NetworkManager class to find a Channel field.
            try {
                for (Field field : super.getClass().getDeclaredFields()) {
                    if (Channel.class.isAssignableFrom(field.getType())) {
                        field.setAccessible(true);
                        field.set(this, a);
                        break;
                    }
                }
            } catch (ReflectiveOperationException ignored) {}
        }

        @Override
        public void a(PacketListener listener) {} // Called by PlayerConnection
    };

    private static final Channel FAKE_CHANNEL = new Channel() {

        // These are the only two that matter.
        @Override
        public ChannelPipeline pipeline() {
            return FAKE_PIPELINE;
        }

        @Override
        public boolean isActive() {
            return true;
        }


        // The rest are junk ):
        @Override
        public EventLoop eventLoop() {
            return null;
        }

        @Override
        public Channel parent() {
            return null;
        }

        @Override
        public ChannelConfig config() {
            return null;
        }

        @Override
        public boolean isOpen() {
            return false;
        }

        @Override
        public boolean isRegistered() {
            return false;
        }

        @Override
        public ChannelMetadata metadata() {
            return null;
        }

        @Override
        public SocketAddress localAddress() {
            return null;
        }

        @Override
        public SocketAddress remoteAddress() {
            return null;
        }

        @Override
        public ChannelFuture closeFuture() {
            return null;
        }

        @Override
        public boolean isWritable() {
            return false;
        }

        @Override
        public Channel flush() {
            return null;
        }

        @Override
        public Channel read() {
            return null;
        }

        @Override
        public Unsafe unsafe() {
            return null;
        }

        @Override
        public <T> Attribute<T> attr(AttributeKey<T> tAttributeKey) {
            return null;
        }

        @Override
        public ChannelFuture bind(SocketAddress socketAddress) {
            return null;
        }

        @Override
        public ChannelFuture connect(SocketAddress socketAddress) {
            return null;
        }

        @Override
        public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2) {
            return null;
        }

        @Override
        public ChannelFuture disconnect() {
            return null;
        }

        @Override
        public ChannelFuture close() {
            return null;
        }

        @Override
        public ChannelFuture deregister() {
            return null;
        }

        @Override
        public ChannelFuture bind(SocketAddress socketAddress, ChannelPromise channelPromise) {
            return null;
        }

        @Override
        public ChannelFuture connect(SocketAddress socketAddress, ChannelPromise channelPromise) {
            return null;
        }

        @Override
        public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
            return null;
        }

        @Override
        public ChannelFuture disconnect(ChannelPromise channelPromise) {
            return null;
        }

        @Override
        public ChannelFuture close(ChannelPromise channelPromise) {
            return null;
        }

        @Override
        public ChannelFuture deregister(ChannelPromise channelPromise) {
            return null;
        }

        @Override
        public ChannelFuture write(Object o) {
            return null;
        }

        @Override
        public ChannelFuture write(Object o, ChannelPromise channelPromise) {
            return null;
        }

        @Override
        public ChannelFuture writeAndFlush(Object o, ChannelPromise channelPromise) {
            return null;
        }

        @Override
        public ChannelFuture writeAndFlush(Object o) {
            return null;
        }

        @Override
        public ByteBufAllocator alloc() {
            return null;
        }

        @Override
        public ChannelPromise newPromise() {
            return null;
        }

        @Override
        public ChannelProgressivePromise newProgressivePromise() {
            return null;
        }

        @Override
        public ChannelFuture newSucceededFuture() {
            return null;
        }

        @Override
        public ChannelFuture newFailedFuture(Throwable throwable) {
            return null;
        }

        @Override
        public ChannelPromise voidPromise() {
            return null;
        }

        @Override
        public int compareTo(Channel o) {
            return 0;
        }
    };

    private static final ChannelPipeline FAKE_PIPELINE = new ChannelPipeline() {

        // These two are the only called by HoloAPI -- the return values do not matter
        @Override
        public ChannelPipeline addBefore(String s, String s2, ChannelHandler channelHandler) {
            return null;
        }

        @Override
        public ChannelPipeline remove(ChannelHandler channelHandler) {
            return null;
        }


        // The rest of these don't matter.
        @Override
        public ChannelPipeline addFirst(String s, ChannelHandler channelHandler) {
            return null;
        }

        @Override
        public ChannelPipeline addFirst(EventExecutorGroup eventExecutors, String s, ChannelHandler channelHandler) {
            return null;
        }

        @Override
        public ChannelPipeline addLast(String s, ChannelHandler channelHandler) {
            return null;
        }

        @Override
        public ChannelPipeline addLast(EventExecutorGroup eventExecutors, String s, ChannelHandler channelHandler) {
            return null;
        }

        @Override
        public ChannelPipeline addBefore(EventExecutorGroup eventExecutors, String s, String s2, ChannelHandler channelHandler) {
            return null;
        }

        @Override
        public ChannelPipeline addAfter(String s, String s2, ChannelHandler channelHandler) {
            return null;
        }

        @Override
        public ChannelPipeline addAfter(EventExecutorGroup eventExecutors, String s, String s2, ChannelHandler channelHandler) {
            return null;
        }

        @Override
        public ChannelPipeline addFirst(ChannelHandler... channelHandlers) {
            return null;
        }

        @Override
        public ChannelPipeline addFirst(EventExecutorGroup eventExecutors, ChannelHandler... channelHandlers) {
            return null;
        }

        @Override
        public ChannelPipeline addLast(ChannelHandler... channelHandlers) {
            return null;
        }

        @Override
        public ChannelPipeline addLast(EventExecutorGroup eventExecutors, ChannelHandler... channelHandlers) {
            return null;
        }

        @Override
        public ChannelHandler remove(String s) {
            return null;
        }

        @Override
        public <T extends ChannelHandler> T remove(Class<T> tClass) {
            return null;
        }

        @Override
        public ChannelHandler removeFirst() {
            return null;
        }

        @Override
        public ChannelHandler removeLast() {
            return null;
        }

        @Override
        public ChannelPipeline replace(ChannelHandler channelHandler, String s, ChannelHandler channelHandler2) {
            return null;
        }

        @Override
        public ChannelHandler replace(String s, String s2, ChannelHandler channelHandler) {
            return null;
        }

        @Override
        public <T extends ChannelHandler> T replace(Class<T> tClass, String s, ChannelHandler channelHandler) {
            return null;
        }

        @Override
        public ChannelHandler first() {
            return null;
        }

        @Override
        public ChannelHandlerContext firstContext() {
            return null;
        }

        @Override
        public ChannelHandler last() {
            return null;
        }

        @Override
        public ChannelHandlerContext lastContext() {
            return null;
        }

        @Override
        public ChannelHandler get(String s) {
            return null;
        }

        @Override
        public <T extends ChannelHandler> T get(Class<T> tClass) {
            return null;
        }

        @Override
        public ChannelHandlerContext context(ChannelHandler channelHandler) {
            return null;
        }

        @Override
        public ChannelHandlerContext context(String s) {
            return null;
        }

        @Override
        public ChannelHandlerContext context(Class<? extends ChannelHandler> aClass) {
            return null;
        }

        @Override
        public Channel channel() {
            return null;
        }

        @Override
        public List<String> names() {
            return null;
        }

        @Override
        public Map<String, ChannelHandler> toMap() {
            return null;
        }

        @Override
        public ChannelPipeline fireChannelRegistered() {
            return null;
        }

        @Override
        public ChannelPipeline fireChannelUnregistered() {
            return null;
        }

        @Override
        public ChannelPipeline fireChannelActive() {
            return null;
        }

        @Override
        public ChannelPipeline fireChannelInactive() {
            return null;
        }

        @Override
        public ChannelPipeline fireExceptionCaught(Throwable throwable) {
            return null;
        }

        @Override
        public ChannelPipeline fireUserEventTriggered(Object o) {
            return null;
        }

        @Override
        public ChannelPipeline fireChannelRead(Object o) {
            return null;
        }

        @Override
        public ChannelPipeline fireChannelReadComplete() {
            return null;
        }

        @Override
        public ChannelPipeline fireChannelWritabilityChanged() {
            return null;
        }

        @Override
        public ChannelPipeline flush() {
            return null;
        }

        @Override
        public ChannelPipeline read() {
            return null;
        }

        @Override
        public ChannelFuture bind(SocketAddress socketAddress) {
            return null;
        }

        @Override
        public ChannelFuture connect(SocketAddress socketAddress) {
            return null;
        }

        @Override
        public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2) {
            return null;
        }

        @Override
        public ChannelFuture disconnect() {
            return null;
        }

        @Override
        public ChannelFuture close() {
            return null;
        }

        @Override
        public ChannelFuture deregister() {
            return null;
        }

        @Override
        public ChannelFuture bind(SocketAddress socketAddress, ChannelPromise channelPromise) {
            return null;
        }

        @Override
        public ChannelFuture connect(SocketAddress socketAddress, ChannelPromise channelPromise) {
            return null;
        }

        @Override
        public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
            return null;
        }

        @Override
        public ChannelFuture disconnect(ChannelPromise channelPromise) {
            return null;
        }

        @Override
        public ChannelFuture close(ChannelPromise channelPromise) {
            return null;
        }

        @Override
        public ChannelFuture deregister(ChannelPromise channelPromise) {
            return null;
        }

        @Override
        public ChannelFuture write(Object o) {
            return null;
        }

        @Override
        public ChannelFuture write(Object o, ChannelPromise channelPromise) {
            return null;
        }

        @Override
        public ChannelFuture writeAndFlush(Object o, ChannelPromise channelPromise) {
            return null;
        }

        @Override
        public ChannelFuture writeAndFlush(Object o) {
            return null;
        }

        @Override
        public Iterator<Map.Entry<String, ChannelHandler>> iterator() {
            return null;
        }
    };
}
