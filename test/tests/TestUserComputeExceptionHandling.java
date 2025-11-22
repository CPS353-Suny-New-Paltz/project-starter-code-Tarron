package tests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import api.compute.ComputeEngineAPI;
import api.process.StorageComputeAPI;
import api.user.UserComputeAPI;
import impl.UserComputeImpl;

import java.util.List;

public class TestUserComputeExceptionHandling {
	@Test
	void computeFactors_returns_error_on_storage_failure() {
		StorageComputeAPI storage = mock(StorageComputeAPI.class);
		ComputeEngineAPI engine = mock(ComputeEngineAPI.class);

		when(storage.readInput("bad.txt")).thenThrow(new RuntimeException("67!"));

		UserComputeAPI user = new UserComputeImpl(storage, engine);

		List<Integer> result = user.computeFactors("bad.txt", "out.txt", ",");
		
        assertTrue(result.isEmpty(), "Failure, UserComputeImpl should return an empty list instead of throwing");
	}
}
