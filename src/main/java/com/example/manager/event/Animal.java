package com.example.manager.event;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Animal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String kindCd;
	private String type;
	private String kind;
	private String sexCd;
	private String age;
	private String popfile;
	private String weight;
	private String noticeNo;
	private String careNm;
	private String careTel;
	private String chargeNm;
	private String officetel;
	private String specialMark;
	private String orgNm;
	private String sido;
	private String gugun;
	private String happenPlace;
	private long happenDt;
	private long noticeSdt;
	private long noticeEdt;
	private String processState;

	@OneToMany
	private List<AdoptionRequest> registeredRequest;

}
