package com.zizo.carteeng.matches.domain;

import com.zizo.carteeng.members.model.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public Match(Member walker, Member driver) {
        this.walker = walker;
        this.driver = driver;
    }

}
