package com.javabase.base.execl;

public class UserBean {

	private int test_id;

	private String test_name;

	private String test_pass;

	private String test_sex;

	private int test_age;

	private String test_phone;

	private String test_address;

	// constructor
	public UserBean() {
		super();
	}

	public UserBean(String test_name, String test_pass, String test_sex,
			int test_age, String test_phone, String test_address) {
		super();
		this.test_name = test_name;
		this.test_pass = test_pass;
		this.test_sex = test_sex;
		this.test_age = test_age;
		this.test_phone = test_phone;
		this.test_address = test_address;
	}

	public UserBean(int test_id, String test_name, String test_pass,
			String test_sex, int test_age, String test_phone,
			String test_address) {
		super();
		this.test_id = test_id;
		this.test_name = test_name;
		this.test_pass = test_pass;
		this.test_sex = test_sex;
		this.test_age = test_age;
		this.test_phone = test_phone;
		this.test_address = test_address;
	}

	// getter and setter
	public int getTest_id() {
		return test_id;
	}

	public void setTest_id(int test_id) {
		this.test_id = test_id;
	}

	public String getTest_name() {
		return test_name;
	}

	public void setTest_name(String test_name) {
		this.test_name = test_name;
	}

	public String getTest_pass() {
		return test_pass;
	}

	public void setTest_pass(String test_pass) {
		this.test_pass = test_pass;
	}

	public String getTest_sex() {
		return test_sex;
	}

	public void setTest_sex(String test_sex) {
		this.test_sex = test_sex;
	}

	public int getTest_age() {
		return test_age;
	}

	public void setTest_age(int test_age) {
		this.test_age = test_age;
	}

	public String getTest_phone() {
		return test_phone;
	}

	public void setTest_phone(String test_phone) {
		this.test_phone = test_phone;
	}

	public String getTest_address() {
		return test_address;
	}

	public void setTest_address(String test_address) {
		this.test_address = test_address;
	}

}
