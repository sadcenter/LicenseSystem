package xyz.sadcenter.license.server.events;

/**
 * @author sadcenter on 09.08.2020
 * @project LicenseSystem
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import xyz.sadcenter.license.server.Server;
import xyz.sadcenter.license.server.util.LicenseLogger;

import java.net.InetSocketAddress;

/**
 * @author sadcenter on 09.08.2020
 * @project untitled2
 */
@ChannelHandler.Sharable
public final class PacketEvent extends ChannelInboundHandlerAdapter {

    private final ByteBuf VERIFIED_BUFFER = Unpooled.buffer().writeBoolean(true);
    private final ByteBuf NOT_VERIFIED_BUFFER = Unpooled.buffer().writeBoolean(false);

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object message) {
        ByteBuf byteBuf = (ByteBuf) message;

        String received = byteBuf.toString(CharsetUtil.UTF_8);
        InetSocketAddress inet = (InetSocketAddress) channelHandlerContext.channel().remoteAddress();

        if (received == null || received.length() > 300) {
            LicenseLogger.logError("Error while client " + inet.getAddress() + " try to connect (" + received + ")");
            channelHandlerContext.writeAndFlush(NOT_VERIFIED_BUFFER.slice());
            channelHandlerContext.disconnect();
            return;
        }

        LicenseLogger.logInfo("New user connected from " + inet.getAddress() + ":" + inet.getPort());
        LicenseLogger.logInfo(inet.getAddress() + ":" + inet.getPort() + " -> Token " + received);
        if (Server.getServer().getConfiguration().getStorage().getAuthUsers().contains(received)) {
            channelHandlerContext.writeAndFlush(VERIFIED_BUFFER.slice());
            LicenseLogger.logInfo(inet.getAddress() + ":" + inet.getPort() + " -> Logged in!");
        } else {
            channelHandlerContext.writeAndFlush(NOT_VERIFIED_BUFFER.slice());
            LicenseLogger.logInfo(inet.getAddress() + ":" + inet.getPort() + " -> Cant find user!");
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) {
        throwable.printStackTrace();
        channelHandlerContext.close();
    }
}
