package tests;

import api.user.UserComputeAPI;
import impl.UserComputeImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class TestUserComputeAPI {

	@Test
	void smoke() {
		new UserComputeImpl();
		
		//Creating our fake object
		UserComputeAPI userAPI = new UserComputeImpl();
		
		//If method is called with these args, return list [1, 2, 3, 4]
		when(userAPI.computeFactors("input.txt", "output.txt", ",")).thenReturn(List.of(1, 2, 3, 4));
		
		//Calls mock method with matching args, returns list [1, 2, 3, 4]
		List<Integer> result = userAPI.computeFactors("input.txt", "output.txt", ",");
		
		//Confrims matching result
		assertEquals(List.of(1, 2, 3, 6), result);
		
		//Makes sure mock method was called with  this exact arg
		verify(userAPI).computeFactors("input.txt", "output.txt", ",");
	}
}
 
 