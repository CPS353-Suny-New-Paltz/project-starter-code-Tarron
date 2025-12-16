package impl;

import java.io.IOException;

import api.process.StorageComputeAPI;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class StorageComputeServer {

    private final Server server;

    public StorageComputeServer(int port, StorageComputeAPI storageApi) {
        this.server = ServerBuilder
                .forPort(port)
                .addService(new StorageComputeServiceImpl(storageApi))
                .build();
    }

    public void start() throws IOException {
        server.start();
        System.out.println("StorageCompute gRPC server started on port " + server.getPort());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down StorageCompute gRPC server...");
            StorageComputeServer.this.stop();
            System.out.println("StorageCompute server shut down.");
        }));
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 50052;

        StorageComputeAPI storage = new StorageComputeImpl();

        StorageComputeServer server = new StorageComputeServer(port, storage);
        server.start();
        server.blockUntilShutdown();
    }
    
    public int getPort() {
        return server.getPort();
    }
}
