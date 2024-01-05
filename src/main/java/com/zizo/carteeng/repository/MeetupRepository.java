package com.zizo.carteeng.repository;

import com.zizo.carteeng.domain.Meetup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetupRepository extends JpaRepository<Meetup, Long> {

}
