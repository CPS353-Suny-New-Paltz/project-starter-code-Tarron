package impl;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import grpc.user.UserComputeRequest;
import grpc.user.UserComputeResponse;
import grpc.user.UserComputeServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;


public class UserComputeClient {

	public static void main(String[] args) {
			//Connect to the gRPC server
			ManagedChannel channel = ManagedChannelBuilder
					.forAddress("localhost", 50051)
					.usePlaintext()
					.build();

			UserComputeServiceGrpc.UserComputeServiceBlockingStub stub =
					UserComputeServiceGrpc.newBlockingStub(channel);

			Scanner scanner = new Scanner(System.in);

			try {
				System.out.println("=== User Compute Client ===");
				System.out.println("Choose input mode:");
				System.out.println("  1) Use existing input file");
				System.out.println("  2) Type in a list of numbers");
				System.out.print("Enter choice (1 or 2): ");

				String choiceLine = scanner.nextLine().trim();
				int choice;
				try {
					choice = Integer.parseInt(choiceLine);
				} catch (NumberFormatException ex) {
					choice = 1;
				}

				String inputPath;

				if (choice == 2) {
					//Typed-in list of numbers mode
					System.out.println("\nEnter numbers to factor, separated by spaces or commas:");
					System.out.print("> ");
					String numbersLine = scanner.nextLine().trim();

					//Write the numbers to a temporary input file, one per line,
					//so that StorageComputeImpl can read it.
					inputPath = "typedInput.txt";

					try (PrintWriter writer = new PrintWriter(new FileWriter(inputPath))) {
						//Split on whitespace or commas
						String[] tokens = numbersLine.split("[,\\s]+");
						for (String token : tokens) {
							if (!token.isEmpty()) {
								writer.println(token);
							}
						}
					} catch (IOException e) {
						System.err.println("Failed to write typed input to file: " + e.getMessage());
						channel.shutdownNow();
						scanner.close();
						return;
					}

					System.out.println("Typed numbers written to " + inputPath);

				} else {
					// --- Existing input file mode ---
					System.out.print("\nEnter input file path (e.g., input.txt): ");
					inputPath = scanner.nextLine().trim();
					if (inputPath.isEmpty()) {
						inputPath = "input.txt";
					}
				}

				//Ask for output file
				System.out.print("Enter output file path (e.g., output.txt): ");
				String outputPath = scanner.nextLine().trim();
				if (outputPath.isEmpty()) {
					outputPath = "output.txt";
				}

				//Optional delimiter
				System.out.print("Enter delimiter for output file (empty for default): ");
				String delimiter = scanner.nextLine();
				if (delimiter == null) {
					delimiter = "";
				}

				//Build the gRPC request
				UserComputeRequest request = UserComputeRequest.newBuilder()
						.setInputPath(inputPath)
						.setOutputPath(outputPath)
						.setDelimiter(delimiter)
						.build();

				//Call the remote computeFactors() via Network API 1
				UserComputeResponse response = stub.computeFactors(request);

				//Display basic status
				System.out.println("\n=== Server Response ===");
				System.out.println("Success: " + response.getSuccess());
				System.out.println("Message: " + response.getMessage());

			} catch (Exception e) {
				System.err.println("Error while contacting compute server: " + e.getMessage());
			} finally {
				scanner.close();
				channel.shutdownNow();
			}
	}
}
