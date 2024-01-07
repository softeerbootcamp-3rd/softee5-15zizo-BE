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
public class Match {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "match_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    private Member walker;

    @OneToOne(fetch = LAZY)
    private Member driver;

    @CreatedDate
    private LocalDateTime createdAt;

}
