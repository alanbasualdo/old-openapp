package com.opencars.netgo.news.general.dto;

import com.opencars.netgo.dms.calidad.entity.ReportRegister;
import com.opencars.netgo.news.goodbyes.dto.GoodbyesSummary;
import com.opencars.netgo.news.letters.dto.LettersSummary;
import com.opencars.netgo.news.maximuns.entity.Maximuns;
import com.opencars.netgo.news.recognitions.dto.RecognitionsList;
import com.opencars.netgo.news.roundtrips.dto.RoundtripsSummary;
import com.opencars.netgo.news.searchs.entity.Searchs;
import com.opencars.netgo.news.welcomes.entity.Welcome;
import com.opencars.netgo.users.dto.Birthday;

import java.util.List;

public class Summary {

    List<GoodbyesSummary> listGoodbyes;
    List<LettersSummary> listLetters;
    List<Maximuns> listMaximuns;
    List<RecognitionsList> listRecognitions;
    List<RoundtripsSummary> listRoundtrips;
    List<Welcome> listWelcomes;

    List<Birthday> listBirthdays;

    List<ReportRegister> listCalidadReport;

    List<ReportRegister> listVentasReport;

    List<Searchs> listSearchs;

    public Summary() {
    }

    public Summary(List<GoodbyesSummary> listGoodbyes, List<LettersSummary> listLetters, List<Maximuns> listMaximuns, List<RecognitionsList> listRecognitions, List<RoundtripsSummary> listRoundtrips, List<Welcome> listWelcomes, List<Birthday> listBirthdays,  List<ReportRegister> listCalidadReport, List<ReportRegister> listVentasReport, List<Searchs> listSearchs) {
        this.listGoodbyes = listGoodbyes;
        this.listLetters = listLetters;
        this.listMaximuns = listMaximuns;
        this.listRecognitions = listRecognitions;
        this.listRoundtrips = listRoundtrips;
        this.listWelcomes = listWelcomes;
        this.listBirthdays = listBirthdays;
        this.listCalidadReport = listCalidadReport;
        this.listVentasReport = listVentasReport;
        this.listSearchs = listSearchs;
    }

    public List<GoodbyesSummary> getListGoodbyes() {
        return listGoodbyes;
    }

    public void setListGoodbyes(List<GoodbyesSummary> listGoodbyes) {
        this.listGoodbyes = listGoodbyes;
    }

    public List<LettersSummary> getListLetters() {
        return listLetters;
    }

    public void setListLetters(List<LettersSummary> listLetters) {
        this.listLetters = listLetters;
    }

    public List<Maximuns> getListMaximuns() {
        return listMaximuns;
    }

    public void setListMaximuns(List<Maximuns> listMaximuns) {
        this.listMaximuns = listMaximuns;
    }

    public List<RecognitionsList> getListRecognitions() {
        return listRecognitions;
    }

    public void setListRecognitions(List<RecognitionsList> listRecognitions) {
        this.listRecognitions = listRecognitions;
    }

    public List<RoundtripsSummary> getListRoundtrips() {
        return listRoundtrips;
    }

    public void setListRoundtrips(List<RoundtripsSummary> listRoundtrips) {
        this.listRoundtrips = listRoundtrips;
    }

    public List<Welcome> getListWelcomes() {
        return listWelcomes;
    }

    public void setListWelcomes(List<Welcome> listWelcomes) {
        this.listWelcomes = listWelcomes;
    }

    public List<Birthday> getListBirthdays() {
        return listBirthdays;
    }

    public void setListBirthdays(List<Birthday> listBirthdays) {
        this.listBirthdays = listBirthdays;
    }

    public List<ReportRegister> getListCalidadReport() {
        return listCalidadReport;
    }

    public void setListCalidadReport(List<ReportRegister> listCalidadReport) {
        this.listCalidadReport = listCalidadReport;
    }

    public List<ReportRegister> getListVentasReport() {
        return listVentasReport;
    }

    public void setListVentasReport(List<ReportRegister> listVentasReport) {
        this.listVentasReport = listVentasReport;
    }

    public List<Searchs> getListSearchs() {
        return listSearchs;
    }

    public void setListSearchs(List<Searchs> listSearchs) {
        this.listSearchs = listSearchs;
    }
}
