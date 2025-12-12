package impl;

import java.util.List;
import java.util.Objects;

import api.process.StorageComputeAPI;
import grpc.storage.StorageComputeServiceGrpc;
import grpc.storage.StorageReadRequest;
import grpc.storage.StorageReadResponse;
import grpc.storage.StorageWriteRequest;
import grpc.storage.StorageWriteResponse;
import io.grpc.stub.StreamObserver;

public class StorageComputeServiceImpl extends StorageComputeServiceGrpc.StorageComputeServiceImplBase {

    private final StorageComputeAPI delegate;

    public StorageComputeServiceImpl(StorageComputeAPI delegate) {
        this.delegate = Objects.requireNonNull(delegate, "delegate must not be null");
    }

    @Override
    public void readInput(StorageReadRequest request,
                          StreamObserver<StorageReadResponse> responseObserver) {

        String inputPath = request.getInputPath();
        StorageReadResponse.Builder builder = StorageReadResponse.newBuilder();

        try {
            List<Integer> numbers = delegate.readInput(inputPath);
            builder.addAllNumbers(numbers)
                   .setSuccess(true)
                   .setMessage("Successfully read " + numbers.size() + " numbers from " + inputPath);
        } catch (IllegalArgumentException ex) {
            builder.setSuccess(false)
                   .setMessage("Invalid read request: " + ex.getMessage());
        } catch (Exception ex) {
            builder.setSuccess(false)
                   .setMessage("Internal error while reading input.");
        }

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void writeOutput(StorageWriteRequest request,
                            StreamObserver<StorageWriteResponse> responseObserver) {

        List<Integer> numbers = request.getNumbersList();
        String delimiter = request.getDelimiter();

        StorageWriteResponse.Builder builder = StorageWriteResponse.newBuilder();

        try {
            int count = delegate.writeOutput(numbers, delimiter);
            builder.setSuccess(true)
                   .setCount(count)
                   .setMessage("Successfully wrote " + count + " numbers.");
        } catch (IllegalArgumentException ex) {
            builder.setSuccess(false)
                   .setCount(0)
                   .setMessage("Invalid write request: " + ex.getMessage());
        } catch (Exception ex) {
            builder.setSuccess(false)
                   .setCount(0)
                   .setMessage("Internal error while writing output.");
        }

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}