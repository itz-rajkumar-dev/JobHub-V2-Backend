package com.job.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponseDto {

    private Long companyId;
    private String companyName;
    private Long ownerId;
    private  String logoUrl;


}
