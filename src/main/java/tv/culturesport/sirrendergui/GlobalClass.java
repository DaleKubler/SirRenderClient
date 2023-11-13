package tv.culturesport.sirrendergui;

/*
 * Copyright (c) 2016, Dale Kubler. All rights reserved.
 *
 */

public class GlobalClass {
	//public static String initialDirectory = "U:/";
	//public static String initialDirectory = "V:/";
	public static String initialDirectory = ApplicationConstants.DEFAULT_SIRRENDER_LAN_DRIVE + ":/";
	public static String serverMasterIpAddress = "";
	public static java.sql.Timestamp lastPollTime;
	public static int portNumber;
	private static boolean h2ServerMode = true;
	private static boolean masterServer = false;
	
	public static String getInitialDirectory() {
		return initialDirectory;
	}
	public static void setInitialDirectory(String initialDirectory) {
		GlobalClass.initialDirectory = initialDirectory;
	}
	public static String getServerMasterIpAddress() {
		return serverMasterIpAddress;
	}
	public static void setServerMasterIpAddress(String serverMasterIpAddress) {
		GlobalClass.serverMasterIpAddress = serverMasterIpAddress;
	}
	public static java.sql.Timestamp getLastPollTime() {
		return lastPollTime;
	}
	public static void setLastPollTime(java.sql.Timestamp lastPollTime) {
		GlobalClass.lastPollTime = lastPollTime;
	}
	public static int getPortNumber() {
		return portNumber;
	}
	public static void setPortNumber(int portNumber) {
		GlobalClass.portNumber = portNumber;
	}
	public static boolean isH2ServerMode() {
		return h2ServerMode;
	}
	public static void setH2ServerMode(boolean h2ServerMode) {
		GlobalClass.h2ServerMode = h2ServerMode;
	}
	public static boolean isMasterServer() {
		return masterServer;
	}
	public static void setMasterServer(boolean masterServer) {
		GlobalClass.masterServer = masterServer;
	}
	
}
