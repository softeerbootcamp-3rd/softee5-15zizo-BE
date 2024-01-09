package com.zizo.carteeng.members.dto;

import com.zizo.carteeng.members.model.Gender;
import com.zizo.carteeng.members.model.Member;
import com.zizo.carteeng.members.model.MemberStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResDto {

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

    @Builder
    private MemberResDto(Long id, String nickname, Gender gender, Integer age, String info, Boolean hasCompany, String companyInfo, Boolean hasCar, LatLng location, MemberStatus status) {
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
    }

    static public MemberResDto of(Member member) {
        return MemberResDto.builder()
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
                .build();
    }
}
