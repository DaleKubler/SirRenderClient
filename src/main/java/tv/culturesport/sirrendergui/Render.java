package tv.culturesport.sirrendergui;

/*
 * Copyright (c) 2017, Dale Kubler. All rights reserved.
 *
 */

public class Render {
	
	private String ipAddress;
	private String fileName;
	private String portNumberStr;
	private int portNumber;
	private String overrideOutputDir;
	private String overrideRenderDevice;

	public Render(String ipAddress, String fileName, String overrideOutputDir, String overrideRenderDevice) {
		setIpAddress(ipAddress);
		setFileName(fileName);
		setOverrideOutputDir(overrideOutputDir);
		setOverrideRenderDevice(overrideRenderDevice);
		// TODO Auto-generated constructor stub
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPortNumberStr() {
		return portNumberStr;
	}

	public void setPortNumberStr(String portNumberStr) {
		this.portNumberStr = portNumberStr;
	}

	public int getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}

	public String getOverrideOutputDir() {
		return overrideOutputDir;
	}

	public void setOverrideOutputDir(String overrideOutputDir) {
		this.overrideOutputDir = overrideOutputDir;
	}

	public String getErrorCount() {
		return overrideOutputDir;
	}

	public String getOverrideRenderDevice() {
		return overrideRenderDevice;
	}

	public void setOverrideRenderDevice(String overrideRenderDevice) {
		this.overrideRenderDevice = overrideRenderDevice;
	}

}