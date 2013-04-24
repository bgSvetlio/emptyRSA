package com.svetlio.salon.model;

public class WomanHairCut implements Service{
	
	public static final int durationInMinutes = 45;
	public static final long durationInMiliseconds = durationInMinutes*60000;
	
	public int getDurationInMinutes(){
		return durationInMinutes;
	}
	
	public long getDurationInMiliseconds(){
		return durationInMiliseconds;
	}
	
	@Override
	public String toString(){
		return "woman hair cut";
	}

}
