package com.svetlio.salon.model;

public class ServiceFactory {
	
	private static ServiceFactory instance;
	
	static { 
		instance = new ServiceFactory();
	}
	
	private ServiceFactory(){}
	
	public static ServiceFactory getServiceFactory(){
		return instance;
	}
	
	public Service createServiceInstance(int serviceTypeChoice){
		switch(serviceTypeChoice){
		case 1: return new ManHairCut();
		
		case 2: return new WomanHairCut();
		
		default: return null;
		}
	}
	
	public Service createServiceInstance(String serviceTypeChoice){
		if(serviceTypeChoice.equalsIgnoreCase("man Hair Cut")){
			return new ManHairCut();
		}else if(serviceTypeChoice.equalsIgnoreCase("woman hair cut")){
			return new WomanHairCut();
		}else return null;
	}

}
