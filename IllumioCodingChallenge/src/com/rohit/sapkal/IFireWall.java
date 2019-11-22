package com.rohit.sapkal;

public interface IFireWall {
	/**
	 * acceptPacket decides whether to accept the new packet or not.
	 * @param dir
	 * @param prot
	 * @param port
	 * @param ip
	 * @return
	 */
	public boolean acceptPacket(String dir,String prot,int port,String ip);
	
}
