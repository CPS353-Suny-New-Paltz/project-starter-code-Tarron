package tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import impl.NetworkStorageComputeAPI;

public class TestNetworkStorageComputeAPI {

    @Test
    public void canConstructNetworkStorageComputeAPI() {
        NetworkStorageComputeAPI api = new NetworkStorageComputeAPI("localhost", 50052);
        assertNotNull(api);
    }
}
