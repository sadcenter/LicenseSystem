package xyz.sadcenter.license.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.SneakyThrows;
import xyz.sadcenter.license.client.callback.LicenseCallback;
import xyz.sadcenter.license.client.events.PacketEvent;

import java.net.InetSocketAddress;

/**
 * @author sadcenter on 09.08.2020
 * @project LicenseSystem
 */

public final class ClientServer {

    @SneakyThrows
    void setup(final String host, final int port, final String token, final LicenseCallback callback) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap clientBootstrap = new Bootstrap();
            clientBootstrap.group(group);
            clientBootstrap.channel(NioSocketChannel.class);
            clientBootstrap.remoteAddress(new InetSocketAddress(host, port));
            clientBootstrap.handler(new ChannelInitializer<Channel>() {
                protected void initChannel(Channel channel) {
                    channel.pipeline().addLast(new PacketEvent(token, callback));
                }

            });
            ChannelFuture channelFuture = clientBootstrap.connect().sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}
