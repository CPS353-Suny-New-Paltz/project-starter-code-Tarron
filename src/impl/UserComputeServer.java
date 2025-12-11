package impl;

import java.io.IOException;

import api.compute.ComputeEngineAPI;
import api.process.StorageComputeAPI;
import api.user.UserComputeAPI;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class UserComputeServer {
	
	 private final Server server;
	 
	 public UserComputeServer(int port, UserComputeAPI userComputeApi) {
	        this.server = ServerBuilder
	                .forPort(port)
	                .addService(new UserComputeServiceImpl(userComputeApi))
	                .build();
	    }
	 
	 public void start() throws IOException {
	        server.start();
	        System.out.println("UserCompute gRPC server started on port " + server.getPort());

	        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
	            System.out.println("Shutting down gRPC server...");
	            UserComputeServer.this.stop();
	            System.out.println("Server shut down.");
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
	        int port = 50051;

	        StorageComputeAPI storage = new StorageComputeImpl();
	        ComputeEngineAPI engine = new ComputeEngineImpl();
	        UserComputeAPI userApi = new UserComputeImpl(storage, engine);

	        UserComputeServer server = new UserComputeServer(port, userApi);
	        server.start();
	        server.blockUntilShutdown();
	    }
	 
	 
}
