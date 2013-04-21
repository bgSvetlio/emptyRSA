package NotDefault;

import java.util.Scanner;


/*
 *  Също клас, който разбира от всичко видове услуги и има много логика в него. Какво ще стане ако се добави женска специална услуга като маникюр, педикюр,
 *  ами детско подстригване, бръснене и т.н. Този клас ще стане огромен и неконтролируем и съответно отново невъзможен за поддръжка и безболезнено разширение.
 */

public class ServiceForThisDay {
	
	// Тук не съм съгласен - някаде в условието казано ли е, че не мога да да поръчам постригване в 14:25 ? Но може пак да е наша грешка ще го обсъдим.
	// Също така ми е позволено да избера в една дата от 9 часа мъжко и дамско постригване едновременно,
	// а идеята мисля че е да не се засичат (уж фризъорката е една), но може пак да е наша вината, че няма пояснение, но все пак трябваше да го обсъдим поне по мейла
	// Дори да има assupmtion че са две фризъорки не личи от имплементацията
	
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
