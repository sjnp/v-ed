package com.ved.backend.response;

import java.util.ArrayList;

import com.ved.backend.model.PreviewModel;

public class PreviewResponse {
 
    private ArrayList<PreviewModel> art;
    private ArrayList<PreviewModel> bussiness;
    private ArrayList<PreviewModel> academic;
    private ArrayList<PreviewModel> design;
    private ArrayList<PreviewModel> programming;
    private ArrayList<PreviewModel> myCourse;

    public PreviewResponse() {}

    public ArrayList<PreviewModel> getArt() {
        return art;
    }

    public void setArt(ArrayList<PreviewModel> art) {
        this.art = art;
    }

    public ArrayList<PreviewModel> getBussiness() {
        return bussiness;
    }

    public void setBussiness(ArrayList<PreviewModel> bussiness) {
        this.bussiness = bussiness;
    }

    public ArrayList<PreviewModel> getAcademic() {
        return academic;
    }

    public void setAcademic(ArrayList<PreviewModel> academic) {
        this.academic = academic;
    }

    public ArrayList<PreviewModel> getDesign() {
        return design;
    }

    public void setDesign(ArrayList<PreviewModel> design) {
        this.design = design;
    }

    public ArrayList<PreviewModel> getProgramming() {
        return programming;
    }

    public void setProgramming(ArrayList<PreviewModel> programming) {
        this.programming = programming;
    }

    public ArrayList<PreviewModel> getMyCourse() {
        return myCourse;
    }

    public void setMyCourse(ArrayList<PreviewModel> myCourse) {
        this.myCourse = myCourse;
    }

}