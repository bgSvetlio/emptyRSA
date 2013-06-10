package com.svetlio.salon.model;

public class ServiceFactory {
	
	/*
	 * Tuk moje bi e uda4no tova factory da e sigleton.
	 * I zashto sa ti dva metoda koito pravat edno i su6to izberi si koi pattern shte
	 * polzvash s 4islo ili string. Hubav princip e kogato imash factory i 
	 * metodite vinagi vru6tat instanciq na nov obekt. Da se kazvat 'createXXX'.
	 */
	
	private static ServiceFactory instance;
	
	static { 
		instance = new ServiceFactory();
	}
	
	private ServiceFactory(){
		
	}
	
	public static ServiceFactory getServiceFactory(){
		return instance;
	}
	
	public Service createServiceInstance(int serviceTypeChoice){
		switch(serviceTypeChoice){
		case 1: return new ManHairCut();
		
		case 2: return new WomanHairCut();
		
		/*
		 * Tuk po default moje bi e po dobre da se hvurli IllegalArgumentExcepetion
		 * vmesto 4oveka da se 4udi kade v programata mu e izbil NullPointerException.
		 */
		
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
