package com.zizo.carteeng.members.dto;

import com.zizo.carteeng.members.model.Gender;
import com.zizo.carteeng.members.model.Member;
import com.zizo.carteeng.members.model.MemberStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostSignUpResDto  {

    private String nickname;

    private Gender gender;

    private String info;

    private Boolean hasCompany;

    private String companyInfo;

    private Boolean hasCar;

    private LatLng location;

    private MemberStatus status;

    @Builder
    private PostSignUpResDto(String nickname, Gender gender, String info, Boolean hasCompany, String companyInfo, Boolean hasCar, LatLng location, MemberStatus status) {
        this.nickname = nickname;
        this.gender = gender;
        this.info = info;
        this.hasCompany = hasCompany;
        this.companyInfo = companyInfo;
        this.hasCar = hasCar;
        this.location = location;
        this.status = status;
    }

    static public PostSignUpResDto of(Member member) {
        return PostSignUpResDto.builder()
                .nickname(member.getNickname())
                .gender(member.getGender())
                .info(member.getInfo())
                .hasCompany(member.getHasCompany())
                .companyInfo(member.getCompanyInfo())
                .hasCar(member.getHasCar())
                .location(LatLng.fromPoint(member.getLocation()))
                .status(member.getStatus())
                .build();
    }
}
