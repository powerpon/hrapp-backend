package com.intens.hrapp.exceptions;

public class SkillNotFoundException extends RuntimeException{
    public SkillNotFoundException(){
        super("Skill Not Found");
    }
}
