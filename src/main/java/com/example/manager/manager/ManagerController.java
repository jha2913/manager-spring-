package com.example.manager.manager;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.manager.configuration.ApiConfiguration;
import com.example.manager.event.AnimalFile;
import com.example.manager.event.AnimalFileRepository;

@RestController
public class ManagerController {

	private AdoptionRepository adoptionRepo;
	private LostRepository lostRepo;
	private FoundRepository foundRepo;
	private AnimalFileRepository animalFileRepo;
	@Autowired
	private ApiConfiguration apiConfig;

	private ManagerService service;

	@Autowired

	public ManagerController(AdoptionRepository adoptionRepo, LostRepository lostRepo, FoundRepository foundRepo,
			AnimalFileRepository animalFileRepo, ManagerService service) {
		this.adoptionRepo = adoptionRepo;
		this.service = service;
		this.lostRepo = lostRepo;
		this.foundRepo = foundRepo;
		this.animalFileRepo = animalFileRepo;

	}

	// 목록 조회

	@RequestMapping(value = "/adoptions/{id}", method = RequestMethod.GET)

	public Adoption getAdoption

	(@PathVariable("id") long id, HttpServletResponse res) {

		Adoption adoption = adoptionRepo.findById(id).orElse(null);

		if (adoption == null) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		return adoption;
	}

	@RequestMapping(value = "/losts/{id}", method = RequestMethod.GET)

	public Lost getLost

	(@PathVariable("id") long id, HttpServletResponse res) {

		Lost lost = lostRepo.findById(id).orElse(null);

		if (lost == null) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		return lost;
	}

	@RequestMapping(value = "/founds/{id}", method = RequestMethod.GET)

	public Found getFound

	(@PathVariable("id") long id, HttpServletResponse res) {

		Found found = foundRepo.findById(id).orElse(null);

		if (found == null) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		return found;
	}

	// 페이징

	@RequestMapping(value = "/adoptions/paging", method = RequestMethod.GET)
	public Page<Adoption> getAdoptionsPaging(@RequestParam("page") int page) {
		// 전체 목록 조회, 페이징
		return adoptionRepo.findAll(PageRequest.of(page, 1));
	}

//	@RequestMapping(value = "/losts/paging", method = RequestMethod.GET)
//	public Page<Lost> getLostsPaging(@RequestParam("state") String state, @RequestParam("page") int page) {
//		// 전체 목록 조회, 페이징
//
//		List<Lost> list = lostRepo.findAll(Sort.by("id").descending());
//		for (Lost animal : list) {
//			for (AnimalFile file : animal.getFiles()) {
//				file.setDataUrl(apiConfig.getBasePath() + "/animalFile/" + file.getId());
//			}
//		}
//		return lostRepo.findByStateContaining(state, PageRequest.of(page, 1));
//	}
	@RequestMapping(value = "/losts/paging", method = RequestMethod.GET)
	public Page<Lost> getLostsPaging(@RequestParam("state") String state, @RequestParam("page") int page) {
		// 전체 목록 조회, 페이징
		return lostRepo.findByStateContaining(state, PageRequest.of(page, 1));
	}

	@RequestMapping(value = "/losts/pagings", method = RequestMethod.GET)
	public Page<AnimalFile> getpic(@RequestParam("page") int page) {
		// 전체 목록 조회, 페이징
		return animalFileRepo.findAll(PageRequest.of(page, 1));
	}

//	@RequestMapping(value = "/founds/paging", method = RequestMethod.GET)
//	public Page<Found> getFoundsPaging(@RequestParam("state") String state, @RequestParam("page") int page) {
//		// 전체 목록 조회, 페이징
//		return foundRepo.findByStateContaining(state, PageRequest.of(page, 1));
//	}

	// 1건 상태 수정

	@RequestMapping(value = "/adoptions/{id}", method = RequestMethod.PATCH)

	public Adoption modifyAdoption

	(@PathVariable("id") long id, @RequestBody Adoption adoptions, HttpServletResponse res) {

		Adoption adoption = adoptionRepo.findById(id).orElse(null);
		System.out.println(adoption);

		if (adoption == null) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		adoptionRepo.save(adoptions);

		service.sendOrder(adoptions);

		return adoption;
	}

	// {"content":"수정한 내용"}
	// 모든 필드(컬럼)을 업데이트함
	// 3. save(update)를 한다.

	@RequestMapping(value = "/losts/{id}", method = RequestMethod.PATCH)

	public Lost modifyLost

	(@PathVariable("id") long id, @RequestBody Lost losts, HttpServletResponse res) {

		Lost lost = lostRepo.findById(id).orElse(null);

		if (lost == null) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		lostRepo.save(losts);

		service.sendOrder2(losts);

		return lost;
	}

	@RequestMapping(value = "/founds/{id}", method = RequestMethod.PATCH)

	public Found modifyFound

	(@PathVariable("id") long id, @RequestBody Found founds, HttpServletResponse res) {

		Found found = foundRepo.findById(id).orElse(null);

		if (found == null) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		foundRepo.save(founds);

		service.sendOrder3(founds);

		return found;
	}

}
