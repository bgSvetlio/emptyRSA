package com.svetlio.salon.model;

import java.util.Calendar;

public class Reservation {

	private Calendar calendar;
	private Customer customer;
	private Service service;
	
	public Reservation(Customer customer, Service service, Calendar calendar){
		this.customer = customer;
		this.service = service;
		this.calendar = calendar;
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}
}
