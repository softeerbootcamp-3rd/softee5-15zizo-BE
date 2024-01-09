package com.zizo.carteeng.members.dto;

import com.zizo.carteeng.members.model.Gender;
import com.zizo.carteeng.members.model.Member;
import com.zizo.carteeng.members.model.MemberStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MeResDto {

    private  Long id;

    private String nickname;

    private Gender gender;

    private Integer age;

    private String info;

    private Boolean hasCompany;

    private String companyInfo;

    private Boolean hasCar;

    private LatLng location;

    private MemberStatus status;

    private Long partnerId;

    @Builder
    private MeResDto(Long id, String nickname, Gender gender, Integer age, String info, Boolean hasCompany, String companyInfo, Boolean hasCar, LatLng location, MemberStatus status, Long partnerId) {
        this.id = id;
        this.nickname = nickname;
        this.gender = gender;
        this.age = age;
        this.info = info;
        this.hasCompany = hasCompany;
        this.companyInfo = companyInfo;
        this.hasCar = hasCar;
        this.location = location;
        this.status = status;
        this.partnerId = partnerId;
    }

    static public MeResDto of(Member member) {
        return MeResDto.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .gender(member.getGender())
                .age(member.getAge())
                .info(member.getInfo())
                .hasCompany(member.getHasCompany())
                .companyInfo(member.getCompanyInfo())
                .hasCar(member.getHasCar())
                .location(LatLng.fromPoint(member.getLocation()))
                .status(member.getStatus())
                .partnerId(member.getPartner() == null ? null : member.getPartner().getId())
                .build();
    }
}
