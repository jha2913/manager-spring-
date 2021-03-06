package com.example.manager.manager;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.example.manager.event.AnimalFile;

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
	private long lostId;
	private String name;
	private String area;
	private String animalFile;
	private String color;
	private String gender;
	private String number;
	private String date;
	private String content;
	private String listAnimalFile;
	private String type;
	private String state;
	private String status;
	private String dataUrl;

	@OneToMany
	@JoinColumn(name = "lostfoundId")
	private List<AnimalFile> files;

}
