package PakietBank;

import java.io.Serializable;
import java.util.ArrayList;

public class Klient implements Serializable {
    private String Imie, Nazwisko; // Imie i nazwisko klienta

    public ArrayList<Rachunek> listaRachunkow = new ArrayList<>(); // Lista przechowujaca rachunki

    Klient() {} // Domyslnie stworzony klient nie ma imienia, nazwiska, kont

    // Stworzenie klienta o podanym iemiu i nazwisku, domyslnie kazdy klient otrzymuje zwykly rachunek
    Klient(String Imie, String Nazwisko) {
        this.Imie = Imie;
        this.Nazwisko = Nazwisko;
        this.listaRachunkow.add(new RachunekZwykly());
    }

    public String getImie() {
        return Imie;
    } // Pobranie Imienia

    public String getNazwisko() {
        return Nazwisko;
    } // Pobranie nazwiska

    public ArrayList<Rachunek> getListaRachunkow() {
        return this.listaRachunkow;
    } // Pobranie listy rachunkow

    // Wypisanie listy rachunkow, linia po linii wraz z indeksem, ktorym nalezy sie poslugiwac
    public void printListaRachunkow() {
        for (int i = 0 ; i < this.listaRachunkow.size() ; i++)
            System.out.println(i+1 + ". " + this.listaRachunkow.get(i));
    }

    // Podstawowe dane klienta
    public String daneKlienta() {
        return Imie + " " + Nazwisko + " " + getListaRachunkow();
    }

    // Wydrukowanie dowolnej ArrayList linia po linii. Z listy rachunkow przekazywany jest jeden rachunek, ktorego zawartosc jest drukowana linia po linii
    public void drukujHistorieRachunku(ArrayList<String> input) {
        for (String s : input)
            System.out.println(s);
    }

    // Ta metoda wysyla pojedynczo wszystkie rachunki z listy listaRachunkow i wywoluje funkcje drukujHistorieRachunku aby wydrukowac jego zawartosc
    public void historiaOperacjiKlienta() {
        for (int i = 0 ; i < listaRachunkow.size() ; i++) {
            drukujHistorieRachunku(listaRachunkow.get(i).pokazHistorieOperacjiNaRachunku());
        }
    }

    // Stworzenia zwyklego rachunku z domyslna nazwa oraz dodanie go do listy rachunkow tego klienta
    public void dodajRachunekZwykly() {
        listaRachunkow.add(new RachunekZwykly());
    }

    // Stworzenia zwylego rachunku z wskazana nazwa oraz dodanie go do listy rachunkow tego klienta
    public void dodajRachunekZwykly(String nazwaRachunku) {
        listaRachunkow.add(new RachunekZwykly(nazwaRachunku));

    }

    // Stworzenia rachunku oszczednosciowego z domyslna nazwa oraz dodanie go do listy rachunkow tego klienta
    public void dodajRachunekOszczednosciowy() {
        listaRachunkow.add(new RachunekOszczednosciowy());
    }

    // Stworzenia rachunku oszczednosciowego z wskazana nazwa oraz dodanie go do listy rachunkow tego klienta
    public void dodajRachunekOszczednosciowy(String nazwa) {
        listaRachunkow.add(new RachunekOszczednosciowy(nazwa));
    }

    // Usuniecie wskazanego rachunku, parametrem jest indeks na liscie
    public void usunRachunek(int ktoryNaLiscie) {
        this.listaRachunkow.remove(ktoryNaLiscie);
    }
}
