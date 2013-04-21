package NotDefault;

import java.util.Scanner;

public class ServiceForThisDay {
	 int[] womanHairCutHour = {9,10,11,12,13,14,15,16,17};
	 double[] manHairCutHour ={9,9.3,10,10.3,11,11.3,12,12.3,13,13.3,14,14.3,15,15.3,16,16.3,17,17.3};
	private int appW;
	private double appM;
	private int service;

	public ServiceForThisDay(int service){
	
		this.service=service;
		
	}
	
	public void chooseHourForService(){
        Scanner input = new Scanner(System.in);
		
		switch(service){
		case 1: for(int i: womanHairCutHour){
			if(i!=0){
				System.out.print(i + "   ");
			}
		}
		
		System.out.println("Choose hour");
		appW=input.nextInt();
		for(int i=0;i<womanHairCutHour.length;i++){
			if(womanHairCutHour[i]==appW){
				womanHairCutHour[i]=0;
				break;
			}
		}
		break;
		
		case 2: for(double i: manHairCutHour){
			if(i!=0){
				System.out.print(i+ "0   ");
			}
		}
		System.out.println("Choose hour");
		appM=input.nextDouble();
		for(int i=0;i<manHairCutHour.length;i++){
			if(manHairCutHour[i]==appM){
				manHairCutHour[i]=0;
				for(int j=0;j<womanHairCutHour.length;j++){
					if(womanHairCutHour[j]==(int)appM){
						womanHairCutHour[j]=0;
						break;
					}
				}
				break;
			}
		}
		}
	}
	
	public void setService(int service){
		this.service = service;
	}
	
	public int getAppW(){
		return this.appW;
	}
	
	public double getAppM(){
		return this.appM;
	}

}
