package com.example.manager.manager;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// 필수는 아니지만 실무적으로는 거의 사용하는 클래스
// 이 클래스의 주로 하는 작업 : 데이터변환, 트랜잭션 처리, 외부 서비스 또는 시스템 연동
@Service
public class ManagerService {

	private AdoptionRepository adoptionRepo;
	private LostRepository lostRepo;
	private FoundRepository foundRepo;

	@Autowired
	public ManagerService(AdoptionRepository adoptionRepo, LostRepository lostRepo, FoundRepository foundRepo) {

		this.adoptionRepo = adoptionRepo;
		this.lostRepo = lostRepo;
		this.foundRepo = foundRepo;
	}

}
