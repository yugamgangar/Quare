package com.tyitproject.quare;

public class DiseaseSearchData {
    int aboutImage;
    String diseaseName, symptoms, medicines, precautions;

    public DiseaseSearchData(int aboutImage, String diseaseName, String symptoms, String medicines, String precautions) {
        this.aboutImage = aboutImage;
        this.diseaseName = diseaseName;
        this.symptoms = symptoms;
        this.medicines = medicines;
        this.precautions = precautions;
    }

    public int getAboutImage() {
        return aboutImage;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public String getMedicines() {
        return medicines;
    }

    public String getPrecautions() {
        return precautions;
    }

}
