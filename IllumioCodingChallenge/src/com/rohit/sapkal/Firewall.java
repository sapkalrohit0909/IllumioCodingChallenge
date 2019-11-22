package com.rohit.sapkal;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;

public class Firewall implements IFireWall{
	Map<Integer, String>direction;
	Map<Integer, String>protocol;
	Map<Integer, List<Integer>>port;
	Map<Integer, List<String>>ipAddress;
	
	/**
	 * Constructor takes file path as an argument
	 * @param filePath
	 */
	public Firewall(String filePath) {
		direction=new TreeMap<Integer, String>();
		protocol=new TreeMap<Integer, String>();
		port=new TreeMap<Integer, List<Integer>>();
		ipAddress=new TreeMap<Integer, List<String>>();
		readFile(filePath);			
	}
	
	/**
	 * readFile reads the rules csv and adds corresponding rules to Firewall
	 * @param filePath
	 */
	public void readFile(String filePath) {
		BufferedReader br = null;
        String line = "";
        String cvsSplitBy=",";
        try { 	
            br = new BufferedReader(new FileReader(filePath));
            int count=0;
            while ((line = br.readLine()) != null) {
            	String[] rules = line.split(cvsSplitBy);
            	addDirection(count,rules[0]);
            	addProtocol(count,rules[1]);
            	addPorts(count,rules[2]);
            	addIp(count,rules[3]);
            	count++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

	}
	
	
	/**
	 * addDirection adds new rule of Firewall direction 
	 * @param count
	 * @param direction
	 */
	public void addDirection(int count,String direction) {
		this.direction.put(count,direction);
	}
	
	/**
	 * addProtocol adds new rule of Firewall protocol
	 * @param count
	 * @param protocol
	 */
	public void addProtocol(int count,String protocol) {
		this.protocol.put(count,protocol);
	}
	
	/**
	 * addPorts adds new rule of Firewall ports
	 * @param count
	 * @param portsString
	 */
	public void addPorts(int count,String portsString) {
		List<Integer>portsList=new ArrayList<>();
    	if(portsString.contains("-")) {
    		String []ports=portsString.split("-");
    		portsList.add(Integer.parseInt(ports[0]));
    		portsList.add(Integer.parseInt(ports[1]));
    	}else {
    		portsList.add(Integer.parseInt(portsString));
    	}
    	this.port.put(count,portsList);
	}
	
	/**
	 * addIp adds new rule of Firewall Ip
	 * @param count
	 * @param IpString
	 */
	public void addIp(int count,String IpString) {
		List<String>ip=new ArrayList<String>();
    	if(IpString.contains("-")) {
    		String[]newIps=IpString.split("-");
    		ip.add(newIps[0]);
    		ip.add(newIps[1]);
    	}else{
    		ip.add(IpString);
    	}
    	this.ipAddress.put(count,ip);
	}
	
	/**
	 * isDirectionValid checks whether the new direction is valid or not
	 * @param dir
	 * @param index
	 * @return
	 */
	public boolean isDirectionValid(String dir,int index) {
		if(this.direction.get(index).equals(dir)) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * isProtocolValid checks whether the new protocol is valid or not
	 * @param prot
	 * @param index
	 * @return
	 */
	public boolean isProtocolValid(String prot,int index) {
		if(this.protocol.get(index).equals(prot)) {
			return true;
		}else {
			return false;
		}
	}
	
	
	/**
	 * isPortValid checks whether the new port is valid or not.
	 * @param portNumber
	 * @param index
	 * @return
	 */
	public boolean isPortValid(int portNumber,int index) {
		if(this.port.get(index).size()==2) {
			int min=this.port.get(index).get(0);
			int max=this.port.get(index).get(1);
			if(portNumber>=min && portNumber<=max){
				return true;
			}else {
				return false;
			}
		}else {
			if(this.port.get(index).get(0)==portNumber) {
				return true;
			}else {
				return false;
			}
		}
	}
	
	
	/**
	 * isIpValid checks whether the new Ip is valid or not.
	 * @param ip
	 * @param index
	 * @return
	 */
	public boolean isIpValid(String ip,int index) {
		if(this.ipAddress.get(index).size()==2) {
			long ipMin=ipToLong(this.ipAddress.get(index).get(0));
			long ipMax=ipToLong(this.ipAddress.get(index).get(1));
			long testIp=ipToLong(ip);
			return testIp >= ipMin && testIp <= ipMax;
		}else {
			return ip.equals(this.ipAddress.get(index).get(0));
		}
		
	}
	
	/**
	 * It converts given ip to a long formed by octates of the IP
	 * @param ip
	 * @return
	 */
	public long ipToLong(String ip) {
		String stOctets[]=ip.split("\\.");
		Integer intOctate[]=new Integer[stOctets.length];
		byte[] octets= new byte[4];;
		for(int i=0;i<stOctets.length;i++) {
			intOctate[i]=Integer.parseInt(stOctets[i]);
			octets[i] = intOctate[i].byteValue();
		}
        long result = 0;
        for (byte octet : octets) {
            result <<= 8;
            result |= octet & 0xff;
        }
        return result;
    }
	
	
	/**
	 * acceptPacket checks whether to accept packet or not.
	 */
	@Override
	public boolean acceptPacket(String dir, String prot, int portNumber, String ip) {
		for(int i:direction.keySet()) {
			boolean isDirectionValid=isDirectionValid(dir,i);
			if(!isDirectionValid) {
				continue;
			}
			boolean isprotocolValid=isProtocolValid(prot,i);
			if(!isprotocolValid) {
				continue;
			}
			boolean isportValid=isPortValid(portNumber,i);
			if(!isportValid) {
				continue;
			}
			boolean isipAddressValid=isIpValid(ip,i);
			if(!isipAddressValid) {
				continue;
			}
			if(isDirectionValid && isprotocolValid && isportValid && isipAddressValid) {
				return true;
			};
		}
		return false;
	}
}

