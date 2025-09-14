package com.turkcell.LibraryAppDb.dto.customer.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CreateCustomerRequest {
	@NotBlank
	private String name;
	@NotBlank(message = "mobileNumber is required")
	@Pattern(regexp = "^(?:\\+90|90|0)?5\\d{9}$", message = "Geçersiz Türkiye GSM numarası (örn: +9053XXXXXXX)")
	private String phone;
	@Email
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
