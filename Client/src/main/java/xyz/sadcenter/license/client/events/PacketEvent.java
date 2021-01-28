package xyz.sadcenter.license.client.events;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import xyz.sadcenter.license.client.callback.LicenseCallback;

/**
 * @author sadcenter on 09.08.2020
 * @project untitled2
 */

public final class PacketEvent extends SimpleChannelInboundHandler {

    private final LicenseCallback callback;
    private final String user;

    public PacketEvent(String authUser, LicenseCallback licenseCallback) {
        this.user = authUser;
        this.callback = licenseCallback;
    }

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer(user, CharsetUtil.UTF_8));
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        cause.printStackTrace();
        channelHandlerContext.close();
        callback.disconnected(cause);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) {
        ByteBuf inBuffer = (ByteBuf) o;

        if (inBuffer.readBoolean()) {
            callback.correct();
        } else {
            callback.incorrect();
        }
    }
}