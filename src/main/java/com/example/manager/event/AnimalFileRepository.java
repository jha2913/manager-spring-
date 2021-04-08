package com.example.manager.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.manager.manager.Found;

@Repository
public interface AnimalFileRepository

		extends JpaRepository<AnimalFile, Long> {
	public Page<AnimalFile> findByLostfoundId(long lostfoundId, Pageable pageable);

}
