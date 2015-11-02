/*
 * Group_14C
 * Arun Sai Sangawar Vijay: 800890154
 * Karishma Borole: 800862169  */


package com.example.befit;

public class UserInfo {
	int height_ft, height_in, height_cm, weight_kg, weight_lbs, age_int, hr,
			min, bmi, totalcal;
	double active, factor;

	public double getFactor() {
		return factor;
	}

	public void setFactor(double factor) {
		this.factor = factor;
	}

	public double getActive() {
		return active;
	}

	public void setActive(Double active) {
		this.active = active;
	}

	public int getTotalcal() {
		return totalcal;
	}

	public void setTotalcal(int totalcal) {
		this.totalcal = totalcal;
	}

	public UserInfo() {
		super();
		this.height_ft = 0;
		this.height_in = 0;
		this.height_cm = 0;
		this.weight_kg = 0;
		this.weight_lbs = 0;
		this.age_int = 0;
		this.hr = 0;
		this.min = 0;
		this.bmi = 0;
		this.totalcal = 0;

	}

	String sex;

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getHr() {
		return hr;
	}

	public void setHr(String hr) {
		this.hr = Integer.parseInt(hr);
	}

	public int getBmi() {
		return bmi;
	}

	public void setBmi(int bmi) {
		this.bmi = bmi;
	}

	public int getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = Integer.parseInt(min);
	}

	public int getHeight_ft() {
		return height_ft;
	}

	public void setHeight_ft(String height_ft) {
		this.height_ft = Integer.parseInt(height_ft);
	}

	public int getHeight_in() {
		return height_in;
	}

	public void setHeight_in(String height_in) {
		this.height_in = Integer.parseInt(height_in);
	}

	public int getHeight_cm() {
		return height_cm;
	}

	public void setHeight_cm(String height_cm) {
		this.height_cm = Integer.parseInt(height_cm);
	}

	public int getWeight_kg() {
		return weight_kg;
	}

	public void setWeight_kg(String weight_kg) {
		this.weight_kg = Integer.parseInt(weight_kg);
	}

	public int getWeight_lbs() {
		return weight_lbs;
	}

	public void setWeight_lbs(String weight_lbs) {
		this.weight_lbs = Integer.parseInt(weight_lbs);
	}

	public int getAge_int() {
		return age_int;
	}

	public void setAge_int(String age) {
		this.age_int = Integer.parseInt(age);
	}

	@Override
	public String toString() {
		return "UserInfo [height_ft=" + height_ft + ", height_in=" + height_in
				+ ", height_cm=" + height_cm + ", weight_kg=" + weight_kg
				+ ", weight_lbs=" + weight_lbs + ", age_int=" + age_int
				+ ", hr=" + hr + ", min=" + min + ", bmi=" + bmi
				+ ", totalcal=" + totalcal + ", active=" + active + ", factor="
				+ factor + ", sex=" + sex + "]";
	}

	

}
