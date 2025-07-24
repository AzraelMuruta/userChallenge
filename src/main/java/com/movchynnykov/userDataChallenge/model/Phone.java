package com.movchynnykov.userDataChallenge.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Phone")
public class Phone {
	
	@Id
	private String uuid;
	private long number;
	private int citycode;
	private String countrycode;
	
}
