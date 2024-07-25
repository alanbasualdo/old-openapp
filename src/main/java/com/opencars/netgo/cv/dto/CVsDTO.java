package com.opencars.netgo.cv.dto;

import com.opencars.netgo.users.entity.User;

public class CVsDTO {

    private User colaborator;
    private boolean checkTitle;
    private boolean checkCertification;
    private boolean checkExperience;
    private boolean checkHobbies;

    public CVsDTO() {
    }

    public CVsDTO(User colaborator, boolean checkTitle, boolean checkCertification, boolean checkExperience, boolean checkHobbies) {
        this.colaborator = colaborator;
        this.checkTitle = checkTitle;
        this.checkCertification = checkCertification;
        this.checkExperience = checkExperience;
        this.checkHobbies = checkHobbies;
    }

    public User getColaborator() {
        return colaborator;
    }

    public void setColaborator(User colaborator) {
        this.colaborator = colaborator;
    }

    public boolean isCheckTitle() {
        return checkTitle;
    }

    public void setCheckTitle(boolean checkTitle) {
        this.checkTitle = checkTitle;
    }

    public boolean isCheckCertification() {
        return checkCertification;
    }

    public void setCheckCertification(boolean checkCertification) {
        this.checkCertification = checkCertification;
    }

    public boolean isCheckExperience() {
        return checkExperience;
    }

    public void setCheckExperience(boolean checkExperience) {
        this.checkExperience = checkExperience;
    }

    public boolean isCheckHobbies() {
        return checkHobbies;
    }

    public void setCheckHobbies(boolean checkHobbies) {
        this.checkHobbies = checkHobbies;
    }
}
