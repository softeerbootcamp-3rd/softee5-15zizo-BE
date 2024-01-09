package com.zizo.carteeng.members.dto;

import com.zizo.carteeng.members.model.Gender;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PostSignUpReqDto {

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

    @NotNull(message = "성별을 입력해주세요.(MALE 혹은 FEMALE)")
    private Gender gender;

    @NotNull(message = "나이를 입력해주세요.")
    @Min(value = 20, message = "20세 미만입니다.")
    private Integer age;

    @NotNull(message = "정보를 입력해주세요.")
    private String info;

    @NotNull(message = "동행 여부를 입력해주세요.")
    private Boolean hasCompany;

    private String companyInfo;

    @NotNull
    private Boolean hasCar;

    @NotNull
    private LatLng location;
}
