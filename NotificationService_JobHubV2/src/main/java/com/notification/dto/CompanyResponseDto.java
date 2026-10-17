package com.notification.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyResponseDto {

    private  Long companyId;
    private String companyName;
    private Long ownerId;
    private String logoUrl;
}