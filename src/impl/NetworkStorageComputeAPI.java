package impl;

import java.util.List;

import api.process.StorageComputeAPI;
import grpc.storage.StorageComputeServiceGrpc;
import grpc.storage.StorageReadRequest;
import grpc.storage.StorageReadResponse;
import grpc.storage.StorageWriteRequest;
import grpc.storage.StorageWriteResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class NetworkStorageComputeAPI implements StorageComputeAPI {

	private final ManagedChannel channel;
	private final StorageComputeServiceGrpc.StorageComputeServiceBlockingStub stub;

	public NetworkStorageComputeAPI(String host, int port) {
		this.channel = ManagedChannelBuilder
				.forAddress(host, port)
				.usePlaintext()
				.build();
		this.stub = StorageComputeServiceGrpc.newBlockingStub(channel);
	}

	@Override
	public List<Integer> readInput(String inputSource) {
		StorageReadRequest request = StorageReadRequest.newBuilder()
				.setInputPath(inputSource)
				.build();

		StorageReadResponse response = stub.readInput(request);

		if (!response.getSuccess()) {
			throw new IllegalStateException("Storage read failed: " + response.getMessage());
		}

		return response.getNumbersList();
	}

	@Override
	public void writeOutput(List<Integer> numbers, String outputSource, String delimiter) {
        StorageWriteRequest request = StorageWriteRequest.newBuilder()
                .addAllNumbers(numbers)
                .setOutputPath(outputSource)
                .setDelimiter(delimiter == null ? "" : delimiter)
                .build();

		StorageWriteResponse response = stub.writeOutput(request);

		if (!response.getSuccess()) {
			throw new IllegalStateException("Storage write failed: " + response.getMessage());
		}
	}

	public void shutdown() {
		channel.shutdownNow();
	}
}

