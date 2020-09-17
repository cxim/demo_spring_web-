package com.test.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
public class MyProduct {
	@Id
	@Column(name = "title121")
	private String productTitle;
}