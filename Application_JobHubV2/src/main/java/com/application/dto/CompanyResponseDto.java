package com.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyResponseDto {

    private  Long companyId;
    private String companyName;
    @JsonProperty("logoUrl")
    private  String companyLogo;
    private Long ownerId;
}
