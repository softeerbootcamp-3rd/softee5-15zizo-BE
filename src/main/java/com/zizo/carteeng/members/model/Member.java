package com.zizo.carteeng.members.model;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    public static final String KEY_COLUMN = "member_id";

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = KEY_COLUMN)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private String info;

    @Column(nullable = false)
    private Boolean hasCompany = false;

    private String companyInfo;

    private Boolean hasCar;

    @Column(nullable = false, columnDefinition = "geometry(Point, 4326)")
    private Point location;

    @Enumerated(EnumType.STRING)
    private MemberStatus status = MemberStatus.AVAILABLE;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id")
    private Member partner;

    @OneToOne(mappedBy = "partner")
    private Member partnerOf;

    public void updateMemberStatus(MemberStatus status) {
        this.status = status;
    }
    public void updateMemberPartner(Member partner) {
        this.partner = partner; // TODO: partnerOf, partner.updateMemberPartner(this)
    }

    @Builder
    public Member(Long id, String nickname, Gender gender, String info, Boolean hasCompany, String companyInfo, Boolean hasCar, Point location, MemberStatus status, Member partner, Member partnerOf) {
        this.id = id;
        this.nickname = nickname;
        this.gender = gender;
        this.info = info;
        this.hasCompany = hasCompany;
        this.companyInfo = companyInfo;
        this.hasCar = hasCar;
        this.location = location;
        this.status = status;
        this.partner = partner;
        this.partnerOf = partnerOf;
    }
}