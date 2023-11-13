package tv.culturesport.sirrendergui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import tv.culturesport.sirrendergui.ApplicationConstants;

public class Utils {

	public static String getLowIpAddress() throws SocketException {
		String SirRenderIpAddress = System.getenv(ApplicationConstants.ENV_SIRRENDER_IP_ADDRESS);
		if (SirRenderIpAddress == null) {
			SirRenderIpAddress = "LOW";
		}
		//MasterMain.log.debug("SirRenderIpAddress="+SirRenderIpAddress);
		
		List<String> addrList = new ArrayList<String>();
		Enumeration<NetworkInterface> enumNI = NetworkInterface.getNetworkInterfaces();
		while (enumNI.hasMoreElements()) {
			NetworkInterface ifc = enumNI.nextElement();
			if (ifc.isUp()) {
				Enumeration<InetAddress> enumAdds = ifc.getInetAddresses();
				while (enumAdds.hasMoreElements()) {
					InetAddress addr = enumAdds.nextElement();
					if (addr.getHostAddress().startsWith("10.0.") && SirRenderIpAddress.equalsIgnoreCase("LOW")) {
						addrList.add(addr.getHostAddress());
					} else if (addr.getHostAddress().startsWith("192.168.") && SirRenderIpAddress.equalsIgnoreCase("HIGH")) {
						addrList.add(addr.getHostAddress());
					}
					//MasterMain.log.debug("IpAddress: " + addr.getHostAddress());
				}
			}
		}
		if (addrList.isEmpty()) {
			if (SirRenderIpAddress.equalsIgnoreCase("LOW")) {
				SirRenderIpAddress = "HIGH";
			} else {
				SirRenderIpAddress = "LOW";
			}
			enumNI = NetworkInterface.getNetworkInterfaces();
			while (enumNI.hasMoreElements()) {
				NetworkInterface ifc = enumNI.nextElement();
				if (ifc.isUp()) {
					Enumeration<InetAddress> enumAdds = ifc.getInetAddresses();
					while (enumAdds.hasMoreElements()) {
						InetAddress addr = enumAdds.nextElement();
						if (addr.getHostAddress().startsWith("10.0.") && SirRenderIpAddress.equalsIgnoreCase("LOW")) {
							addrList.add(addr.getHostAddress());
						} else if (addr.getHostAddress().startsWith("192.168.") && SirRenderIpAddress.equalsIgnoreCase("HIGH")) {
							addrList.add(addr.getHostAddress());
						}
						//MasterMain.log.debug("IpAddress: " + addr.getHostAddress());
					}
				}
			}
			Collections.sort(addrList);
			return addrList.get(0);
		} else {
			Collections.sort(addrList);
			return addrList.get(0);
		}
	}
	
    public static String readMasterServerFile(String fileName) {
		try {
			FileReader reader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(reader);

			String line = bufferedReader.readLine();

			reader.close();
    		return line;
		} catch (FileNotFoundException e) {
			;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static void writeMasterServerFile(String fileName, String serverName, String ipAddress) {
		String output = serverName + "|" + ipAddress;
		try {
			FileWriter writer = new FileWriter(fileName);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);

			bufferedWriter.write(output);
			bufferedWriter.close();			//FileWriter writer = new FileWriter(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String timestampToString(java.sql.Timestamp dateTime){
	    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    return f.format(dateTime);
	}
	
	public static String getCurrentTimeStampForLog() {
	    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
	}	
	
	public static void log(String logLevel, String message) {
		MainApp.log.debug(getCurrentTimeStampForLog() + ": " + message);
	}
	
	public static java.sql.Timestamp getCurrentTimeStamp() {
		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime()/1000);
	}	
	
	public static java.sql.Timestamp getCurrentTime() {
		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());
	}	
	
	// Date Now for "DatePicker"
	/*
    public static final LocalDate NOW_LOCAL_DATE (){
        String date = new SimpleDateFormat("dd-MM-  yyyy").format(Calendar.getInstance().getTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date , formatter);
        return localDate;
    }
    */	
	
}
