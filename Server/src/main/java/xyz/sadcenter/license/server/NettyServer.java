package xyz.sadcenter.license.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import lombok.SneakyThrows;
import xyz.sadcenter.license.server.commands.Command;
import xyz.sadcenter.license.server.commands.impl.AddTokenCommand;
import xyz.sadcenter.license.server.commands.impl.RemoveTokenCommand;
import xyz.sadcenter.license.server.configuration.Configuration;
import xyz.sadcenter.license.server.events.PacketEvent;
import xyz.sadcenter.license.server.util.LicenseLogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author sadcenter on 09.08.2020
 * @project LicenseSystem
 */

public final class NettyServer {

    @Getter
    private final Configuration configuration;
    @Getter
    private final Set<Command> commands = new HashSet<>();
    @Getter
    private final BufferedWriter bufferedWriter;

    @SneakyThrows
    public NettyServer() {
        configuration = new Configuration(new File("config.json"));
        this.bufferedWriter = new BufferedWriter(new FileWriter("logs.log", true));
        registerCommands(
                new AddTokenCommand(),
                new RemoveTokenCommand()
        );

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(this::startCommandService);
        executorService.execute(this::startServer);
    }

    private void registerCommand(Command command) {
        this.commands.add(command);
    }

    private void registerCommands(Command... commands) {
        this.commands.addAll(Arrays.asList(commands));
    }

    private void startCommandService() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String[] args = scanner.nextLine().split(" ");
            String cmdName = args[0];

            if (cmdName.isEmpty()) {
                LicenseLogger.logError("Please write a command, not empty text!");
                continue;
            }

            Command command = commands.stream()
                    .filter(cmd -> cmd.getCommand().equalsIgnoreCase(cmdName) || cmd.getAliases().contains(cmdName))
                    .findFirst()
                    .orElse(null);

            if (command == null) {
                LicenseLogger.logError("Command not found!");
                continue;
            }

            List<String> outputList = new ArrayList<>(Arrays.asList(args));
            outputList.remove(0);
            String[] outputArgs = outputList.toArray(new String[0]);

            try {
                command.executeCommand(outputArgs);
            } catch (Exception exception) {
                LicenseLogger.logError("Unexpected error while execution command. " + exception.toString());
                for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
                    LicenseLogger.logError("   " + stackTraceElement.toString());
                }
            }
        }
    }

    @SneakyThrows
    private void startServer() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(group);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.localAddress(new InetSocketAddress(configuration.getStorage().getHost(), configuration.getStorage().getPort()));
            LicenseLogger.logInfo("Server bind on " + configuration.getStorage().getHost() + ":" + configuration.getStorage().getPort());
            final PacketEvent packetReceiveEvent = new PacketEvent();
            serverBootstrap.childHandler(new ChannelInitializer<Channel>() {
                protected void initChannel(Channel channel) {
                    channel.pipeline().addLast(packetReceiveEvent);
                }

            });
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}
