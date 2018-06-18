package de.ironjan.mensaupb.model;

import com.google.gson.annotations.SerializedName;

public class NutritionalInfo {
    @SerializedName("kj")
    private double kj;

    private double kcal;
    private double fat;
    private double carbohydrates;
    private double protein;

    public NutritionalInfo() {
    }

    public double getKj() {
        return this.kj;
    }

    public void setKj(double kj) {
        this.kj = kj;
    }

    public double getKcal() {
        return this.kcal;
    }

    public void setKcal(double kcal) {
        this.kcal = kcal;
    }

    public double getFat() {
        return this.fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarbohydrates() {
        return this.carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public double getProtein() {
        return this.protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }
}
