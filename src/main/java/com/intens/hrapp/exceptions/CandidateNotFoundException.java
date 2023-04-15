package com.intens.hrapp.exceptions;

public class CandidateNotFoundException extends RuntimeException{
    public CandidateNotFoundException(){
        super("Candidate Not Found");
    }
}
