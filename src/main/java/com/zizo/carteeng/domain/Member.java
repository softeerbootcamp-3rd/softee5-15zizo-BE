package com.zizo.carteeng.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String info;

    private Boolean hasCompany;

    private String companyInfo;

    private Boolean hasCar;

    private Point location;

}
