package impl;

import java.util.List;
import java.util.Objects;

import api.user.UserComputeAPI;
import grpc.user.UserComputeRequest;
import grpc.user.UserComputeResponse;
import grpc.user.UserComputeServiceGrpc;
import io.grpc.stub.StreamObserver;

public class UserComputeServiceImpl extends UserComputeServiceGrpc.UserComputeServiceImplBase {

	private final UserComputeAPI delegate;

	public UserComputeServiceImpl(UserComputeAPI delegate) {
		this.delegate = Objects.requireNonNull(delegate, "delegate must not be null");
	}

	@Override
	public void computeFactors(UserComputeRequest request,
			StreamObserver<UserComputeResponse> responseObserver) {

		String inputPath = request.getInputPath();
		String outputPath = request.getOutputPath();
		String delimiter = request.getDelimiter();

		UserComputeResponse.Builder builder = UserComputeResponse.newBuilder();

		try {
			List<Integer> factors = delegate.computeFactors(inputPath, outputPath, delimiter);

			if (factors == null || factors.isEmpty()) {
				builder.setSuccess(false)
				.setMessage("No factors computed (empty input or invalid data).");
			} else {
				builder.setSuccess(true)
				.setMessage("Successfully computed factors for last input: " + factors);
			}

		} catch (IllegalArgumentException ex) {
			builder.setSuccess(false)
			.setMessage("Invalid request: " + ex.getMessage());
		} catch (Exception ex) {
			builder.setSuccess(false)
			.setMessage("Internal error while computing factors.");
		}

		responseObserver.onNext(builder.build());
		responseObserver.onCompleted();
	}
}
