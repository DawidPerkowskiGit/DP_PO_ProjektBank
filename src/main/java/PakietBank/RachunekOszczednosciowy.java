package PakietBank;

import java.io.Serializable;
import java.util.ArrayList;

public class RachunekOszczednosciowy extends RachunekZwykly implements Serializable {
    private final double kosztPrzelewu = 1.50; // koszt wykonania przelewu dla oszczedniosciowego typu rachunku

    // Domyslnie stworzony rachunek oszczednosciowy ma nazwe "Rachunek oszczednosciowy"
    RachunekOszczednosciowy() {
        this.label = "Rachunek oszczednosciowy";
    }

    // Stworzenie rachunku o wskazanej nazwie
    RachunekOszczednosciowy(String nazwa) {
        this.label = nazwa;
    }
    public double getKosztPrzelewu() {
        return this.kosztPrzelewu;
    }

    // Prowizja od wyplaty gotowki jest zalezna od wyplacanej kwoty
    public double jakaPriwzjaOdWyplatyGotowki(double wartoscTransakcji) {
        if (wartoscTransakcji < 20)
            return 1.50;
        if (wartoscTransakcji < 100)
            return 5.00;
        if (wartoscTransakcji < 1000)
            return 20.00;
        if (wartoscTransakcji > 1000) {
            return wartoscTransakcji*0.20;
         }
    return 0;
    }

    // Wypisanie informacji o nazwie rachunku i jego saldzie
    public String toString() {
        return this.label + " | " + this.sprawdzSaldo();
    }

    // wplata gotowki i wpisanie jej do historii operacji
    public void wplacGotowke(double iloscGotowki) {
        if (iloscGotowki > 0) {
            this.saldo += iloscGotowki;
            historiaOperacji.add("  Wplata " + iloscGotowki + " | " + this.sprawdzSaldo());
        }
        else
            System.out.println("Nie mozna wykonac operacji, kwota mniejsza od 0");
    }

    // wyplata gotowki i wpisanie jej do hsitorii operacji
    public void wyplacGotowke(double iloscGotowki) {
        double prowizja = jakaPriwzjaOdWyplatyGotowki(iloscGotowki);
        if (this.saldo < (iloscGotowki + prowizja) || iloscGotowki < 0) {
            System.out.println("Nie mozna wyplacic gotowki, nie wystarczajaca ilosc srodkow lub niepoprawna nieprawidlowa wartosc");
        }
        else {
            this.saldo -= iloscGotowki + prowizja;
            historiaOperacji.add("  Wyplata " + iloscGotowki + " Prowizja " + prowizja + " | " + this.sprawdzSaldo());

        }
    }
    // Wypisanie historii operacji
    public ArrayList<String> pokazHistorieOperacjiNaRachunku() {
        System.out.print(this.label + " : \n");
        return historiaOperacji;
    }

    // Wysalnie pieniedzy z rachunku w ramach przelewu, zapisanie tej operacji do historii transkacji
    public void przelewWychodzacy(double ilePieniedzyPrzelac) {
        this.saldo -= ilePieniedzyPrzelac + this.getKosztPrzelewu();
        this.historiaOperacji.add("  Zlecono przelew na kwote " + ilePieniedzyPrzelac + " zlotych. Koszt przelewu "+ this.getKosztPrzelewu() + " | " + this.sprawdzSaldo() );
    }

    // Otrzymanie pieniedzy z przelewu, zapisanie tej operacji do historii transkacji
    public void przelewPrzychodzacy(double ilePieniedzyOtrzymano) {
        this.saldo += ilePieniedzyOtrzymano;
        this.historiaOperacji.add("  Przelew przychodzacy na kwote " + ilePieniedzyOtrzymano + " zlotych | " + this.sprawdzSaldo());
    }
}
