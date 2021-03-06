package com.Projekat.Dostava.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Lokacija implements Serializable {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long idLokacija;

    @Column
    private double geografska_sirina;

    @Column
    private double geografska_duzina;

    @Column
    private String adresa;

    public Lokacija(){}

    public Lokacija(double geografska_sirina,double geografska_duzina,String adresa){
        this.geografska_sirina = geografska_sirina;
        this.geografska_duzina = geografska_duzina;
        this.adresa = adresa;
    }

    public double getGeografska_sirina() {
        return geografska_sirina;
    }

    public void setGeografska_sirina(double geografska_sirina) {
        geografska_sirina = geografska_sirina;
    }

    public double getGeografska_duzina() {
        return geografska_duzina;
    }

    public void setGeografska_duzina(double geografska_duzina) {
        geografska_duzina = geografska_duzina;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        adresa = adresa;
    }
}
