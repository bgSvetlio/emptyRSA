package com.svetlio.salon.model;

public class ManHairCut implements Service{

	public static final int durationInMinutes = 30;
	public static final long durationInMiliseconds = durationInMinutes*60000;
	
	public int getDurationInMinutes(){
		return durationInMinutes;
	}
	
	public long getDurationInMiliseconds(){
		return durationInMiliseconds;
	}
	
	@Override
	public String toString(){
		return "man hair cut";
	}
}
