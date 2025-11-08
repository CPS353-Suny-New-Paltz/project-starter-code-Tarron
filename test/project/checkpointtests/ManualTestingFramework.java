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

        StorageComputeAPI storage = new StorageComputeImpl();
        ComputeEngineAPI  engine  = new ComputeEngineImpl();
        UserComputeAPI    user    = new UserComputeImpl(storage, engine);

        List<Integer> inputs = storage.readInput(INPUT);

        List<Integer> onePerInput = new ArrayList<>();
        for (int n : inputs) {
            List<Integer> f = engine.factors(n);
            onePerInput.add(f.size());   
        }
        
        String line = onePerInput.stream()
                .map(String::valueOf)
                .collect(java.util.stream.Collectors.joining(","));
        try {
			java.nio.file.Files.writeString(java.nio.file.Path.of(OUTPUT), line);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
