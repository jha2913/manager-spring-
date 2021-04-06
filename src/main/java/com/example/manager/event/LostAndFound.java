package com.example.manager.event;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//ORM(Object Relational Mapping)
//프로그래밍 객체 - 데이터베이스 객체(테이블)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
// Entity(SE) - 데이터를 처리하는(저장하는) 객체
// object(SE) - entity(데이터), controller(제어), boundary(경계)
//테이블명: feed
public class LostAndFound {

	// @Id: PK(primary key)
	// @GeneratedValue: 값 생성 방식
	// strategy = GenerationType.IDENTITY: auto_increment
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	// 관리자 승인 and 거절
	private String status;

	@Column(columnDefinition = "TEXT")
	// 사용자이름
	private String name;
	// 지역
	private String area;
	// 털색
	private String color;
	// 성별
	private String gender;
	// 연락처
	private String number;
	// 분실 and 보호 상태
	private String state;
	// 날짜
	private String date;
	// 특징
	private String content;
	// 축종(종류)
	private String type;

	@OneToMany
	@JoinColumn(name = "lostfoundId")
	private List<AnimalFile> files;
}