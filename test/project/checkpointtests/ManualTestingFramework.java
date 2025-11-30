package project.checkpointtests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import api.compute.ComputeEngineAPI;
import api.process.StorageComputeAPI;
import api.user.UserComputeAPI;
import impl.StorageComputeImpl;
import impl.UserComputeImpl;
import impl.ComputeEngineImpl;

public class ManualTestingFramework {

	public static final String INPUT = "manualTestInput.txt";
	public static final String OUTPUT = "manualTestOutput.txt";

	public static void main(String[] args) {
		try {
			StorageComputeAPI storage = new StorageComputeImpl();
			ComputeEngineAPI  engine  = new ComputeEngineImpl();
			UserComputeAPI    user    = new UserComputeImpl(storage, engine);

			user.computeFactors(INPUT, OUTPUT, ",");

            System.out.println("Manual test completed successfully.");
		} catch (Exception e) {
			System.err.println("Manual test failed: " + e.getMessage());
		}
	}
}
