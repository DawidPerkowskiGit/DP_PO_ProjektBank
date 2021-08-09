package PakietBank;

import java.io.Serializable;
import java.util.ArrayList;

abstract class Rachunek implements Serializable {
    double saldo; // stan konta
    double kosztPrzelewu = 0.00; // koszt zlecenia przelewu
    String label; // nazwa rachunku

    Rachunek() {}

    abstract ArrayList<String> pokazHistorieOperacjiNaRachunku(); // Wyswietlanie historii transkacji
    abstract void wyplacGotowke(double iloscGotowki); // Wyplata srodkow, zmiejszenie wartosci saldo
    abstract void wplacGotowke(double iloscGotowki); // Wplata gotowki, zwiekszenie wartosci saldo
    abstract void przelewWychodzacy(double iloscGotowki); // Przelew - wyslanie gotowki z tego konta
    abstract void przelewPrzychodzacy(double iloscGotowki); // Przelew - wplyniecie srodkow na te konto

    // Pobranie nazwy konta
    public String getLabel() {
        return this.label;
    }

    // Zmiana nazwy istniejaego rachunku
    public void setLabel(String nazwa) {
        this.label = nazwa;
    }
    public String toString() {
        return "Rachunek bankowy";
    }

    // Pobranie wartosci mowiecej o prowizji przelewu wykonanego z tego rachunku
    public double getKosztPrzelewu() {
        return this.kosztPrzelewu;
    }
    // Pobranie wartosci salko
    public double getSaldo() {
        return this.saldo;
    }

    // Wyswietlenie stanu salda
    public String sprawdzSaldo () {
        return "Saldo : " + this.saldo;
    }
}
