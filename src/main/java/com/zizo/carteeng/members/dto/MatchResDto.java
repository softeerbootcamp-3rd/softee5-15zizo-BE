package com.zizo.carteeng.members.dto;

import com.zizo.carteeng.members.model.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MatchResDto {

    private Long walkerId;
    private Long driverId;
    private LocalDateTime createdAt;

    @Builder
    public MatchResDto(Member walker, Member driver, LocalDateTime createdAt) {
        this.walkerId = walker.getId();
        this.driverId = driver.getId();
        this.createdAt = createdAt;
    }

}
