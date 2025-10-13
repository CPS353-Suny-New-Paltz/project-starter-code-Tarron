package impl;

import java.util.List;

import api.compute.ComputeEngineAPI;
import api.process.StorageComputeAPI;
import api.user.UserComputeAPI;

public class UserComputeImpl implements UserComputeAPI {
	StorageComputeAPI storage;
	ComputeEngineAPI engine;
	
	public UserComputeImpl(StorageComputeAPI storage, ComputeEngineAPI engine) {
	    this.storage = storage;
	    this.engine = engine;
	}
	
	@Override
	public List<Integer> computeFactors(String inputSource, String outputSource, String delimiter) {
		// TODO Auto-generated method stub
		return null;
	}

}
