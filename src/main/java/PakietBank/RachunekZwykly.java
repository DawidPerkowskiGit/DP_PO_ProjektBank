package PakietBank;

import java.io.Serializable;
import java.util.ArrayList;

public class RachunekZwykly extends Rachunek implements Serializable {

    final double kosztPrzelewu = 0.00; // koszt wykonania przelewu z tego rachunku
    public ArrayList<String> historiaOperacji = new ArrayList<>(); // Lista przechowujaca historie operacji dla tego rachunku

    // Domyslnie kazde nowy zwykly rachunek bedzie mial nazwe "Zwykly rachunek"
    RachunekZwykly() {
        this.label = "Zwykly rachunek";
    }

    // Tworzenie nowego rachunku ale z podaniem wlasnej nazwy
    RachunekZwykly(String nazwa) {
        this.label = nazwa;
    }
    // Pobranie wartosci prowizji od wykonania przlewu dla tego typu rachunku
    public double getKosztPrzelewu() {
        return this.kosztPrzelewu;
    }

    // Sprawdzenie stanu rachunku
    public String sprawdzSaldo() {
        return "Saldo : " + this.saldo;
    }

    // Wplacenie gotowki na rachunek i wpisanie tej operacji do listy przechowujacej historie transakcji
    public void wplacGotowke(double iloscGotowki) {
        if (iloscGotowki > 0) {
            this.saldo += iloscGotowki;
            historiaOperacji.add("  Wplata " + iloscGotowki + " | " + this.sprawdzSaldo());
        }
        else
            System.out.println("Nie mozna wykonac operacji, kwota mniejsza od 0");
    }

    // Wplacenie gotowki z rachunku i wpisanie tej operacji do listy przechowujacej historie transakcji
    public void wyplacGotowke(double iloscGotowki) {
        if (this.saldo < iloscGotowki || iloscGotowki < 0) {
            System.out.println("Nie mozna wyplacic gotowki, nie wystarczajaca ilosc srodkow lub podana nieprawidlowa wartosc");
        }
        else {
            this.saldo -= iloscGotowki;
            historiaOperacji.add("  Wyplata " + iloscGotowki + " | " + this.sprawdzSaldo());
        }
    }

    // Wypisanie nazwy rachunku i jego salda
    public String toString() {
        return this.label + " | " + this.sprawdzSaldo();
    }

    // Wypisanie historii transkacji tego rachunku
    public ArrayList<String> pokazHistorieOperacjiNaRachunku() {
        System.out.print(this.label + " : \n");
        return historiaOperacji;
    }

    // Wykonanie przelewu z rachunku i wpisanie tej operacji do historii transkacji
    public void przelewWychodzacy(double ilePieniedzyPrzelac) {
        this.saldo -= ilePieniedzyPrzelac + this.getKosztPrzelewu();
        this.historiaOperacji.add("  Zlecono przelew na kwote " + ilePieniedzyPrzelac + " zlotych. Koszt przelewu "+ this.kosztPrzelewu + " | " + this.sprawdzSaldo() );
    }

    // Otrzymanie przelewu i wpisanie tej operacji do historii transkacji
    public void przelewPrzychodzacy(double ilePieniedzyOtrzymano) {
        this.saldo += ilePieniedzyOtrzymano;
        this.historiaOperacji.add("  Przelew przychodzacy na kwote " + ilePieniedzyOtrzymano + " zlotych | " + this.sprawdzSaldo());
    }

}
