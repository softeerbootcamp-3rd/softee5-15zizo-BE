package com.zizo.carteeng.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Meetup {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "meetup_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    private Member member1;

    @OneToOne(fetch = LAZY)
    private Member member2;

    @CreatedDate
    private LocalDateTime createdAt;

}
