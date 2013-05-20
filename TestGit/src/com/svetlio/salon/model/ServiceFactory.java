package com.svetlio.salon.model;

public class ServiceFactory {
	
	public static Service getServiceInstance(int serviceTypeChoice){
		switch(serviceTypeChoice){
		case 1: return new ManHairCut();
		
		case 2: return new WomanHairCut();
		
		default: return null;
		}
	}
	
	public static Service getServiceInstance(String serviceTypeChoice){
		if(serviceTypeChoice.equalsIgnoreCase("man Hair Cut")){
			return new ManHairCut();
		}else if(serviceTypeChoice.equalsIgnoreCase("woman hair cut")){
			return new WomanHairCut();
		}else return null;
	}

}
