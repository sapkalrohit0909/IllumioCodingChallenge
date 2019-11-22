package com.rohit.sapkal;


public class Main {
	public static void main(String[] args) {
		String csvFile = "/Users/rohitsapkal/Desktop/Interviews/CompaniesRounds/Illumio/CodingChallenge/src/sapkal/rohit/rules.csv";
		Firewall fireWall=new Firewall(csvFile);
		boolean result=fireWall.acceptPacket("outbound","tcp",1000,"192.168.10.11");
		System.out.print(result);
	}
}