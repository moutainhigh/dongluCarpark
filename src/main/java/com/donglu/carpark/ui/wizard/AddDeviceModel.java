package com.donglu.carpark.ui.wizard;

import java.util.ArrayList;
import java.util.List;

import com.dongluhitec.card.domain.db.singlecarpark.SingleCarparkCarpark;
import com.dongluhitec.card.domain.db.singlecarpark.SingleCarparkDevice;


public class AddDeviceModel extends SingleCarparkDevice{
	private List<SingleCarparkCarpark> list=new ArrayList<SingleCarparkCarpark>();
	private String serialAddress="DOM1";
	private String tcpAddress="192.168.1.1:10001";
	
	public AddDeviceModel(){
		SingleCarparkCarpark s=new SingleCarparkCarpark();
		s.setName("停车场1");
		list.add(s);
	}
	public List<SingleCarparkCarpark> getList() {
		return list;
	}

	public void setList(List<SingleCarparkCarpark> list) {
		this.list = list;
		if (pcs != null)
			pcs.firePropertyChange("list", null, null);
	}
	public SingleCarparkDevice getDevice(){
		SingleCarparkDevice device=new SingleCarparkDevice();
		device.setAddress(getAddress());
		device.setCarpark(getCarpark());
		device.setId(getId());
		device.setIdentifire(getIdentifire());
		device.setInType(getInType());
		device.setIp(getIp());
		device.setLinkAddress(getLinkAddress());
		device.setName(getName());
		device.setRoadType(getRoadType());
		device.setType(getType());
		return device;
	}
	public String getSerialAddress() {
		return serialAddress;
	}
	public void setSerialAddress(String serialAddress) {
		this.serialAddress = serialAddress;
		if (pcs != null)
			pcs.firePropertyChange("serialAddress", null, null);
	}
	public String getTcpAddress() {
		return tcpAddress;
	}
	public void setTcpAddress(String tcpAddress) {
		this.tcpAddress = tcpAddress;
		if (pcs != null)
			pcs.firePropertyChange("tcpAddress", null, null);
	}
	public void setDevice(SingleCarparkDevice device) {
		setAddress(device.getAddress());
		setCarpark(device.getCarpark());
		setId(device.getId());
		setIdentifire(device.getIdentifire());
		setInType(device.getInType());
		setIp(device.getIp());
		setLinkAddress(device.getLinkAddress());
		setName(device.getName());
		setRoadType(device.getRoadType());
		setType(device.getType());
		String type2 = device.getType();
		if (type2.equals("tcp")) {
			setTcpAddress(device.getLinkAddress());
		}else{
			setSerialAddress(device.getLinkAddress());
		}
	}
	
}