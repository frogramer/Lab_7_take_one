package org.example.Commands;

import org.example.IO.Server;

import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.ForkJoinPool;

public class CommandProcessor {
    private static final ForkJoinPool pool = ForkJoinPool.commonPool();

    public void process(DefaultCommand command, DatagramChannel datagramChannel, SocketAddress clientAddress) {
        pool.execute(() -> {
            String response = command.Execute(datagramChannel, clientAddress);
            new SaveCommand().Execute(datagramChannel, clientAddress);
            Server.sendResponse(response, clientAddress, datagramChannel);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
        });
    }
}