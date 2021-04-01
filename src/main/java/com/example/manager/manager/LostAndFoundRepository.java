package com.example.manager.manager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LostAndFoundRepository

		extends JpaRepository<LostAndFound, Long> {

}
