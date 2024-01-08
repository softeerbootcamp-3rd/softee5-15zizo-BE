package com.zizo.carteeng.members;

import com.zizo.carteeng.members.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByHasCar(Boolean hasCar);
}
