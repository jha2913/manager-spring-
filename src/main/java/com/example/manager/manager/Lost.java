package com.example.manager.manager;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Lost {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String status;
	private String name;
	private String area;
	private String color;
	private String gender;
	private String number;
	private String date;
	private String content;
	private String listAnimalFile;
	private String type;
	private String state;

}
