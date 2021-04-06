package com.example.manager.manager;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.manager.event.AdoptionRequest;
import com.example.manager.event.LostAndFound;

// 필수는 아니지만 실무적으로는 거의 사용하는 클래스
// 이 클래스의 주로 하는 작업 : 데이터변환, 트랜잭션 처리, 외부 서비스 또는 시스템 연동
@Service
public class ManagerService {

	private AdoptionRepository adoptionRepo;
	private LostRepository lostRepo;
	private FoundRepository foundRepo;
	private RabbitTemplate rabbit;

	@Autowired
	public ManagerService(AdoptionRepository adoptionRepo, LostRepository lostRepo, FoundRepository foundRepo,
			RabbitTemplate rabbit) {
		this.rabbit = rabbit;
		this.adoptionRepo = adoptionRepo;
		this.lostRepo = lostRepo;
		this.foundRepo = foundRepo;
	}

	// 만든 rabbitmq 이름
	@RabbitListener(queues = "adoption.request")
	public void receiveOrder(AdoptionRequest adoptionRequest) {
		System.out.println(adoptionRequest); // 받은거확인

		Adoption myAdoption = Adoption.builder().adoptionId(adoptionRequest.getId())
				.requestNo(adoptionRequest.getRequestNo()).animalId(adoptionRequest.getAnimalId())
				.noticeNo(adoptionRequest.getNoticeNo()).name(adoptionRequest.getName())
				.mobile(adoptionRequest.getMobile()).email(adoptionRequest.getEmail())
				.gender(adoptionRequest.getGender()).address(adoptionRequest.getAddress()).job(adoptionRequest.getJob())
				.familyMember(adoptionRequest.getFamilyMember()).familyAgreed(adoptionRequest.getFamilyAgreed())
				.petAtHome(adoptionRequest.getPetAtHome()).petDetails(adoptionRequest.getPetDetails())
				.houseType(adoptionRequest.getHouseType()).status(adoptionRequest.getStatus())
				.reason(adoptionRequest.getReason()).animalImg(adoptionRequest.getAnimalImg())
				// sales_order 참조하고 있는
				// purchase_order의 id
				.build();

		System.out.println(myAdoption);
		adoptionRepo.save(myAdoption);
	}

	@RabbitListener(queues = "lostandfound.request")
	public void receiveOrder2(LostAndFound lostAndFound) {
		System.out.println(lostAndFound); // 받은거확인

		Lost myLost = Lost.builder().lostId(lostAndFound.getId()).status(lostAndFound.getStatus())
				.name(lostAndFound.getName()).area(lostAndFound.getArea()).color(lostAndFound.getColor())
				.gender(lostAndFound.getGender()).number(lostAndFound.getNumber()).date(lostAndFound.getDate())
				.content(lostAndFound.getContent()).type(lostAndFound.getType()).state(lostAndFound.getState())
				.files(lostAndFound.getFiles())
				// sales_order 참조하고 있는
				// purchase_order의 id
				.build();

		System.out.println(myLost);
		lostRepo.save(myLost);
	}

	public void sendOrder(Adoption adoption) {
		rabbit.convertAndSend("amq.topic", "adoption", adoption);
		System.out.println(adoption);
	}

	public void sendOrder2(Lost lost) {
		rabbit.convertAndSend("manager.animal.status", lost);
	}

}
