package com.opencars.netgo.news.general.dto;

public class NewsCounts {

    private int countWelcomes;
    private int countRoundtrips;
    private int countLetters;
    private int countRecognitions;
    private int countBirthdays;
    private int countGoodbyes;

    private int countSearchsActives;

    public NewsCounts() {
    }

    public NewsCounts(int countWelcomes, int countRoundtrips, int countLetters, int countRecognitions, int countBirthdays, int countGoodbyes, int countSearchsActives) {
        this.countWelcomes = countWelcomes;
        this.countRoundtrips = countRoundtrips;
        this.countLetters = countLetters;
        this.countRecognitions = countRecognitions;
        this.countBirthdays = countBirthdays;
        this.countGoodbyes = countGoodbyes;
        this.countSearchsActives = countSearchsActives;
    }

    public int getCountWelcomes() {
        return countWelcomes;
    }

    public void setCountWelcomes(int countWelcomes) {
        this.countWelcomes = countWelcomes;
    }

    public int getCountRoundtrips() {
        return countRoundtrips;
    }

    public void setCountRoundtrips(int countRoundtrips) {
        this.countRoundtrips = countRoundtrips;
    }

    public int getCountLetters() {
        return countLetters;
    }

    public void setCountLetters(int countLetters) {
        this.countLetters = countLetters;
    }

    public int getCountRecognitions() {
        return countRecognitions;
    }

    public void setCountRecognitions(int countRecognitions) {
        this.countRecognitions = countRecognitions;
    }

    public int getCountBirthdays() {
        return countBirthdays;
    }

    public void setCountBirthdays(int countBirthdays) {
        this.countBirthdays = countBirthdays;
    }

    public int getCountGoodbyes() {
        return countGoodbyes;
    }

    public void setCountGoodbyes(int countGoodbyes) {
        this.countGoodbyes = countGoodbyes;
    }

    public int getCountSearchsActives() {
        return countSearchsActives;
    }

    public void setCountSearchsActives(int countSearchsActives) {
        this.countSearchsActives = countSearchsActives;
    }
}
