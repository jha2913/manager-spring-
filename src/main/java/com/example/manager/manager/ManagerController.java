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

@RestController
public class ManagerController {

	private AdoptionRepository adoptionRepo;
	private LostAndFoundRepository lostAndFoundRepo;
	@Autowired
	private ApiConfiguration apiConfig;

	@Autowired

	public ManagerController(AdoptionRepository adoptionRepo, LostAndFoundRepository lostAndFoundrepo) {
		this.adoptionRepo = adoptionRepo;
		this.lostAndFoundRepo = lostAndFoundrepo;

	}

	// 목록 조회

	@RequestMapping(value = "/adoptions", method = RequestMethod.GET)
	public List<Adoption> getAdoptions(HttpServletRequest req) {

		List<Adoption> list = adoptionRepo.findAll(Sort.by("id").descending());

		return list;
	}

	@RequestMapping(value = "/lostAndFounds", method = RequestMethod.GET)
	public List<LostAndFound> getLostAndFounds(HttpServletRequest req) {

		List<LostAndFound> list = lostAndFoundRepo.findAll(Sort.by("id").descending());

		return list;
	}

	// 페이징

	@RequestMapping(value = "/adoptions/paging", method = RequestMethod.GET)
	public Page<Adoption> getAdoptionsPaging(@RequestParam("page") int page) {
		// 전체 목록 조회, 페이징
		return adoptionRepo.findAll(PageRequest.of(page, 1));
	}

	@RequestMapping(value = "/lostAndFounds/paging", method = RequestMethod.GET)
	public Page<LostAndFound> getLostAndFoundsPaging(@RequestParam("page") int page) {
		// 전체 목록 조회, 페이징
		return lostAndFoundRepo.findAll(PageRequest.of(page, 1));
	}

	// 1건 수정

	@RequestMapping(value = "/adoptions/{id}", method = RequestMethod.PUT)

	public Adoption modifyAdoption

	(@PathVariable("id") long id, @RequestBody Adoption adoptions, HttpServletResponse res) {

		Adoption adoption = adoptionRepo.findById(id).orElse(null);

		if (adoption == null) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		// 2. 수정할 필드(컬럼)만 수정한다.

		adoption.setStatus("완료");

		// {"content":"수정한 내용"}
		// 모든 필드(컬럼)을 업데이트함
		// 3. save(update)를 한다.

		adoptionRepo.save(adoption);

		return adoption;
	}

	@RequestMapping(value = "/lostAndFounds/{id}", method = RequestMethod.PUT)

	public LostAndFound modifyLostAndFound

	(@PathVariable("id") long id, @RequestBody LostAndFound lostAndFounds, HttpServletResponse res) {

		LostAndFound lostAndFound = lostAndFoundRepo.findById(id).orElse(null);

		if (lostAndFound == null) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		lostAndFound.setStatus("완료");

		lostAndFoundRepo.save(lostAndFound);

		return lostAndFound;
	}

}
