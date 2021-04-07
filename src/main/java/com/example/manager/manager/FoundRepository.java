package com.example.manager.manager;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoundRepository

		extends JpaRepository<Found, Long> {
	public Page<Found> findByStateContaining(String state, Pageable pageable);

}