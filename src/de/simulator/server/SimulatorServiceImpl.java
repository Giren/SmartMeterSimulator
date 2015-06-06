package de.simulator.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.simulator.client.SimulatorService;
import de.simulator.shared.Device;

@SuppressWarnings("serial")
public class SimulatorServiceImpl extends RemoteServiceServlet implements
		SimulatorService {

	private final HashMap<String, Device> devices = new HashMap<String, Device>();

	public SimulatorServiceImpl() {
		loadTestData();
	}

	private void loadTestData() {
		ArrayList<Integer> element1 = new ArrayList<Integer>();
		element1.add(100);
		element1.add(123);
		element1.add(125);
		element1.add(120);
		element1.add(150);
		element1.add(100);
		element1.add(123);
		element1.add(125);
		element1.add(120);
		element1.add(150);
		element1.add(100);
		element1.add(123);
		element1.add(125);
		element1.add(120);
		element1.add(150);
		element1.add(100);
		element1.add(123);
		element1.add(125);
		element1.add(120);
		element1.add(150);
		element1.add(100);
		element1.add(123);
		element1.add(125);
		element1.add(120);
		element1.add(150);
		element1.add(100);
		element1.add(123);
		element1.add(125);
		element1.add(120);
		element1.add(150);
		ArrayList<Integer> element2 = new ArrayList<Integer>();
		element2.add(90);
		element2.add(125);
		element2.add(150);
		element2.add(80);
		element2.add(100);
		element2.add(90);
		element2.add(125);
		element2.add(150);
		element2.add(80);
		element2.add(100);
		element2.add(90);
		element2.add(125);
		element2.add(150);
		element2.add(80);
		element2.add(100);
		ArrayList<Integer> element3 = new ArrayList<Integer>();
		element3.add(95);
		element3.add(103);
		element3.add(125);
		element3.add(140);
		element3.add(110);
		element3.add(95);
		element3.add(103);
		element3.add(125);
		element3.add(140);
		element3.add(110);
		element3.add(95);
		element3.add(103);
		element3.add(125);
		element3.add(140);
		element3.add(110);
		
		final Device test1 = new Device("0", "cat1", "WMF",
				"Mixer 400W", "Info1", element1);
		final Device test2 = new Device("1", "cat2", "Grundig",
				"Kühlschrank", "Info2", element2);
		final Device test3 = new Device("2", "cat3", "WMF",
				"Küchenmaschine", "Info3", element3);

		devices.put(test1.getID(), test1);
		devices.put(test2.getID(), test2);
		devices.put(test3.getID(), test3);
	}

	public Device getDevice(String id) {
		return devices.get(id);
	}

	public ArrayList<Device> getDeviceList() {
		ArrayList<Device> deviceList = new ArrayList<Device>();

		Iterator<String> it = devices.keySet().iterator();
		while (it.hasNext()) {
			Device device = devices.get(it.next());
			deviceList.add(device);
		}
	
		return deviceList;
	}
}


