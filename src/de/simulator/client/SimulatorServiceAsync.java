package de.simulator.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.simulator.shared.Device;

public interface SimulatorServiceAsync {
	void getDevice( String id, AsyncCallback<Device> callback);
	void getDeviceList( AsyncCallback<ArrayList<Device>> callback);
}
