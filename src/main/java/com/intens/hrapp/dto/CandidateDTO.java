package com.intens.hrapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;


@NoArgsConstructor
@Setter
@Getter
public class CandidateDTO {

    private String name;
    private Date dateOfBirth;
    private String contactNumber;
    private String email;

}
