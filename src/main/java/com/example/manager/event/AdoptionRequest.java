package com.example.manager.event;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdoptionRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int adoptionId;
	private String requestNo;
	private long animalId;
	private String noticeNo;
	private String name;
	private String mobile;
	private String email;
	private String gender;
	private String address;
	private String job;
	private String familyMember;
	private String familyAgreed;
	private String petAtHome;
	private String petDetails;
	private String houseType;
	private String reason;
	private String status;
	private String animalImg;

}
