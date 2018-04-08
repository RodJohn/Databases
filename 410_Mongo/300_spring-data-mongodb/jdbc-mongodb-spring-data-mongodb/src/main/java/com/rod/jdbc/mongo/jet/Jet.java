
package com.rod.jdbc.mongo.jet;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document()
public class Jet {

	@Id
	private String id;
	private String name;
	private Integer speed;
	private String country;
	private Date createdDateDate;
	
	
	private List<String> color;
	private List<Passenger> passengers;
	

	public Jet() {
		super();
	}

	public Jet(String name, Integer speed) {
		super();
		this.name = name;
		this.speed = speed;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public List<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public List<String> getColor() {
		return color;
	}

	public void setColor(List<String> color) {
		this.color = color;
	}

	public Date getCreatedDateDate() {
		return createdDateDate;
	}

	public void setCreatedDateDate(Date createdDateDate) {
		this.createdDateDate = createdDateDate;
	}


	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Jet [id=" + id + ", name=" + name + ", speed=" + speed + ", country=" + country + ", color=" + color
				+ "]";
	}

}
