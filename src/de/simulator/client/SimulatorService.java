package de.simulator.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.simulator.shared.Device;


@RemoteServiceRelativePath( "sim")
public interface SimulatorService extends RemoteService {
	Device getDevice( String id);
	ArrayList<Device> getDeviceList();
}
