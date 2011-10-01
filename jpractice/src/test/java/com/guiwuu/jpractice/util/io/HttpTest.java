package com.guiwuu.jpractice.util.io;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.junit.Test;

public class HttpTest {

    @Test
    public void testConsoleInput() throws Exception {
        Thread.currentThread().setName("console-io-test");
        Scanner s = new Scanner(System.in);
        s.next();
    }

    @Test
    public void testBIOSocketConnection() throws Exception {
        Socket socket = new Socket("localhost", 9090);
        //socket.setSoTimeout(100000);
        InputStream is = socket.getInputStream();
        byte[] b = new byte[8192];
        int i = 0;
        while (true) {
            Thread.currentThread().setName("http-test" + i++);
            is.read(b);
        }
    }

    @Test
    public void testNIOChannelConnection() throws Exception {
        SocketChannel sc = SocketChannel.open(new InetSocketAddress("localhost", 9090));
        sc.configureBlocking(false);
        ByteBuffer b = ByteBuffer.allocate(8192);
        int i = 0;
        while (true) {
            Thread.currentThread().setName("nio-test" + i++);
            sc.read(b);
        }
    }

    @Test
    public void testNettyClient() throws Exception {
        ChannelFactory factory =
                new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool());

        ClientBootstrap bootstrap = new ClientBootstrap(factory);
        bootstrap.getPipeline().addLast("noop", new SimpleChannelHandler());
        bootstrap.connect(new InetSocketAddress("localhost", 9090));
    }
}
