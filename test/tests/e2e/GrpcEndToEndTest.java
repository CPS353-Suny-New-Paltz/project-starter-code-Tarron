package tests.e2e;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import api.compute.ComputeEngineAPI;
import api.process.StorageComputeAPI;
import api.user.UserComputeAPI;
import grpc.user.UserComputeRequest;
import grpc.user.UserComputeResponse;
import grpc.user.UserComputeServiceGrpc;
import impl.ComputeEngineImplFast;
import impl.NetworkStorageComputeAPI;
import impl.StorageComputeImpl;
import impl.StorageComputeServer;
import impl.UserComputeImpl;
import impl.UserComputeServer;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcEndToEndTest {

    private StorageComputeServer storageServer;
    private UserComputeServer userServer;
    private ManagedChannel channel;

    @AfterEach
    void cleanup() throws Exception {
        if (channel != null) {
            channel.shutdownNow();
        }
        if (userServer != null) {
            userServer.stop();
        }
        if (storageServer != null) {
            storageServer.stop();
        }
    }

    @Test
    void grpc_computeFactors_end_to_end_writes_expected_output_file() throws Exception {
        //temp input/output files
        Path input = Files.createTempFile("grpc-e2e-input", ".txt");
        Path output = Files.createTempFile("grpc-e2e-output", ".txt");

        //Input numbers
        Files.writeString(input, "48 12 7");

        //Start StorageCompute server on ephemeral port
        StorageComputeAPI storageImpl = new StorageComputeImpl();
        storageServer = new StorageComputeServer(0, storageImpl); // 0 => pick free port
        storageServer.start();
        int storagePort = storageServer.getPort();

        //Start UserCompute server on ephemeral port, pointing at storage server
        StorageComputeAPI storageClient = new NetworkStorageComputeAPI("localhost", storagePort);

        //Use fast engine
        ComputeEngineAPI engine = new ComputeEngineImplFast();
        UserComputeAPI userApi = new UserComputeImpl(storageClient, engine);

        userServer = new UserComputeServer(0, userApi);
        userServer.start();
        int userPort = userServer.getPort();

        //Create real gRPC client to UserCompute server
        channel = ManagedChannelBuilder
                .forAddress("localhost", userPort)
                .usePlaintext()
                .build();

        UserComputeServiceGrpc.UserComputeServiceBlockingStub stub =
                UserComputeServiceGrpc.newBlockingStub(channel);

        //Call gRPC
        UserComputeRequest req = UserComputeRequest.newBuilder()
                .setInputPath(input.toString())
                .setOutputPath(output.toString())
                .setDelimiter(",")
                .build();

        UserComputeResponse resp = stub.computeFactors(req);

        //Response + output file content
        assertTrue(resp.getSuccess(), "Expected gRPC response success=true, but got: " + resp.getMessage());

        String fileOut = Files.readString(output).trim();

        //Because UserComputeImpl currently outputs the largest factor for each input, output equals input values.
        //Input: 48,12,7 => Output: "48,12,7"
        assertEquals("48,12,7", fileOut);
    }
}
