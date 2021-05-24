package com.github.bartimaeusnek.MyMilightRemote;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.SneakyThrows;
import lombok.Synchronized;
import lombok.var;

public class NetworkingThread implements Runnable {

    private static final ExecutorService pool = Executors.newSingleThreadExecutor();

    private final String ip;
    private final int port;
    private final int command;
    private final int payloads;

    private NetworkingThread(String ip, int port, int command, int payloads) {
        this.ip = ip;
        this.port = port;
        this.command = command;
        this.payloads = payloads;
    }

    public static void request(String ip, int port, BasicCommands command, ColorPayloads payloads) {
        new NetworkingThread(ip, port, command.getCommand(), payloads.getPayload()).submit();
    }

    public static void request(String ip, int port, int command, int payloads) {
        new NetworkingThread(ip, port, command, payloads).submit();
    }

    private void submit() {
        pool.submit(this);
    }

    @Override
    @Synchronized
    @SneakyThrows
    public void run() {
        var socket = new DatagramSocket();
        socket.connect(new InetSocketAddress(ip, port));
        socket.send(new DatagramPacket(new byte[]{(byte) command, (byte) payloads}, 2));
        socket.disconnect();
        socket.close();
        socket = null;
    }
}
