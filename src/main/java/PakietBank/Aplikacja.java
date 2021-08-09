package PakietBank;


import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Aplikacja implements Serializable{

    // Metoda ta wypisuje informacje o kliencie i wywyoluje metode z Klasy Klient, ktora wypisuje historie transkacji ze wszystkich kont tego klienta
    static void historiaOperacji (Klient ktoregoKlienta) {
        System.out.println("Historia operacji na koncie Pana/Pani " + ktoregoKlienta.getImie() + " " + ktoregoKlienta.getNazwisko());
        ktoregoKlienta.historiaOperacjiKlienta();
    }

    // Usuniecie klienta z listy
    static void usunKlienta(ArrayList<Klient> inputList, int numerKlienta) {
        inputList.remove(numerKlienta);
    }

    // Metoda ta najpierw sprawdza czy dane sa poprawne a nasptenie wywyoluje metoda ktora wykona przelew
    static void sprawdzDaneWykonajPrzelew (Klient zlecajacy, int rachunekZlecajacego, Klient odbiorca, int rachunekOdbiorcy, double wartoscPrzlelewu) {
        if (zlecajacy.listaRachunkow.get(rachunekZlecajacego).getSaldo() < wartoscPrzlelewu + zlecajacy.listaRachunkow.get(rachunekZlecajacego).getKosztPrzelewu()) {
            System.out.println("Nie można zlecic przelewu, brak srodkow na rachunku");
        }
        else if (wartoscPrzlelewu < 0)
            System.out.println("Zlecona kwota przelewu nie moze byc mniejsza od 0");
        else {
            wykonajPrzelew(zlecajacy, rachunekZlecajacego, odbiorca, rachunekOdbiorcy, wartoscPrzlelewu);
        }
    }

    // Metoda wykonujaca przelew, odjecie pieniedzy z salda jednego rachunku, dodanie ich na inny rachunek
    static void wykonajPrzelew(Klient zlecajacy, int rachunekZlecajacego, Klient odbiorca, int rachunekOdbiorcy, double wartoscPrzlelewu) {
        zlecajacy.listaRachunkow.get(rachunekZlecajacego).przelewWychodzacy(wartoscPrzlelewu);
        odbiorca.listaRachunkow.get(rachunekOdbiorcy).przelewPrzychodzacy(wartoscPrzlelewu);
    }

    // Dodanie klienta o wskazanym imieniu i nazwisku
    static void dodajKlienta(String Imie, String Nazwisko, ArrayList<Klient> listaKlientow) {
        Klient tempKlient = new Klient(Imie, Nazwisko);
        listaKlientow.add(tempKlient);
    }

    // Metoda ta pobiera od uzytkownika kwote pieniedzy
    static double podajKwote() {
        Scanner tempScanner = new Scanner(System.in);
        double tempKwota;
        do {
            tempKwota = tempScanner.nextDouble();
            if (tempKwota < 0)
                System.out.println("Wprowadz kwote wieksza od 0");
        } while (tempKwota < 0);
        return tempKwota;
    }

    // Metoda pobiera z listy indeks konta, na ktorym uzytkownik chce pracowac
    static int podajIndeksKonta(ArrayList<Klient> lista, int ktoryKlient) {
        Scanner tempScanner = new Scanner(System.in);
        boolean error = false;
        int tempKonto;
        do {
            tempKonto = tempScanner.nextInt();
            if (tempKonto < 0 || lista.get(ktoryKlient).listaRachunkow.size() < tempKonto) {
                System.out.println("\nKonto nie isntenieje, podaj wartosc ponownie ponownie\n");
                drukujRachunki(lista, ktoryKlient);
                error = true;
            }
            else
                error = false;
        } while (tempKonto < 0 || error == true);
        return tempKonto-1;
    }

    // Metoda pobiera z listy indeks klienta, na ktorym uzytkownik chce pracowac
    static int podajIndeksKlienta(ArrayList<Klient> lista) {
        Scanner tempScanner = new Scanner(System.in);
        boolean error = false;
        int tempNumerKlienta;
        do {
            tempNumerKlienta = tempScanner.nextInt();
            if (tempNumerKlienta < 0 || lista.size() < tempNumerKlienta) {
                error = true;
                System.out.println("\nKlient nie istnieje, podaj wartosc ponownie\n");;
                drukujKlientow(lista);
            }
            else
                error = false;
        } while (error == true);
        return tempNumerKlienta-1;
    }

    // metoda wypisuje liste klientow, wraz z ich indeksami
    static void drukujKlientow(ArrayList<Klient> listaKlientow) {
        System.out.println("Lista wszystkich klientow : ");
        for (int i = 0 ; i < listaKlientow.size() ; i++)
            System.out.println( i+1 + ". " + listaKlientow.get(i).getImie() + " " + listaKlientow.get(i).getNazwisko());
    }

    // Metoda wypisuje liste rachunkow danego klienta wraz z ich indeksami
    static void drukujRachunki(ArrayList<Klient> listaKlientow, int wyborKlienta) {
        System.out.println("Lista rachunkow Pana/Pani " + listaKlientow.get(wyborKlienta).getImie() + " " + listaKlientow.get(wyborKlienta).getNazwisko());
        listaKlientow.get(wyborKlienta).printListaRachunkow();
    }

    // Metoda zapisuje do pliku klientow ich rachunki oraz hsitorie operacji
    static void zapiszDaneDoPliku(ArrayList<Klient> inputArrayList) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("klienci.dat"))) {
            out.writeObject(inputArrayList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // metoda wczytuje klientow, rachunki oraz historie operacji
    static ArrayList<Klient> wczytajDaneZPliku() throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("klienci.dat"))) {
            return (ArrayList<Klient>) in.readObject();
        }
    }



    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Scanner liczby = new Scanner(System.in);
        Scanner string = new Scanner(System.in);
        ArrayList<Klient> listaKlientow = new ArrayList<>();
        // Zaladowanie przykladowych danych - dwoch klientow, kilku rachunkow, wplat, wyplat i jeden przelew

            Klient klient1 = new Klient("Jan", "Kowalski");
            listaKlientow.add(klient1);

            klient1.listaRachunkow.get(0).wplacGotowke(9000);
            klient1.listaRachunkow.get(0).wyplacGotowke(8000);

            klient1.dodajRachunekOszczednosciowy();
            klient1.listaRachunkow.get(1).wplacGotowke(10000);
            klient1.listaRachunkow.get(1).wyplacGotowke(5000);
            klient1.listaRachunkow.get(1).wyplacGotowke(3000);

            Klient klient2 = new Klient("Janusz", "Nowak");
            listaKlientow.add(klient2);
            klient2.dodajRachunekOszczednosciowy();
            klient2.dodajRachunekZwykly();
            klient2.listaRachunkow.get(0).wplacGotowke(100000);
            klient2.listaRachunkow.get(1).wplacGotowke(70000);

            sprawdzDaneWykonajPrzelew(klient2, 1, klient1, 0, 50000);
            // Koniec ladowania danych

        int switchMenu;
        do {
            System.out.println();
            System.out.println("Menu narzedzia wspomagajacego prace banku\n1 - Lista klientow\n2 - Dodaj/usun klienta\n3 - Lista rachunkow\n" +
                    "4 - Dodaj/Usun rachunek\n5 - Historia transkacji\n6 - Wplac pieniadze\n7 - Wyplac pieniadze\n8 - Wykonaj przelew\n9 - Wyszukiwarka" +
                    "\n10 - Zapisz/wczytaj z pliku\n11 - Wyjscie");

            switchMenu = liczby.nextInt();

            switch (switchMenu) {
                case 1 : { // Wyswietlenie listy klientow
                    drukujKlientow(listaKlientow);
                    break;

                }
                case 2 : { // Dodawanie/usuwanie kleintow
                    int choice;
                    System.out.println("1 - Dodaj klienta\n2 - Usun klienta");
                    choice = liczby.nextInt();
                    if (choice == 1) {
                        System.out.println("Podaj Imie i Nazwisko klienta ktorego chcesz dodac");
                        String tempImie, tempNazwisko;
                        tempImie = string.nextLine();
                        tempNazwisko = string.nextLine();
                        dodajKlienta(tempImie, tempNazwisko, listaKlientow);
                    }
                    else if (choice == 2) {
                        System.out.println("Ktorego klienta usunac");
                        drukujKlientow(listaKlientow);
                        int tempKlient = podajIndeksKlienta(listaKlientow);
                        usunKlienta(listaKlientow, tempKlient);
                    }
                    else
                        System.out.println("Wybrano bledna opcje");

                    break;
                }

                case 3 : { // Wypisanie listy rachunkow wybranego klienta
                    System.out.println("Liste rachunkow ktorego klienta chcesz wygenerowac?");
                    drukujKlientow(listaKlientow);
                    int tempKlient = podajIndeksKlienta(listaKlientow);
                    System.out.println("Lista rachunkow Pana/Pani " + listaKlientow.get(tempKlient).getImie() + " " + listaKlientow.get(tempKlient).getNazwisko());
                    listaKlientow.get(tempKlient).printListaRachunkow();
                    break;
                }
                case 4 : { // Dodaj/usun rachunek
                    System.out.println("1 - Dodaj Rachunek\n2 - Usun rachunek");
                    int choice = liczby.nextInt();
                    if (choice == 1) {
                        drukujKlientow(listaKlientow);
                        System.out.println("Wybierz klienta, ktoremu chcesz dodac rachunek");
                        int wyborKlienta = podajIndeksKlienta(listaKlientow);
                        int zwyklyCzyOszczednosciowy;
                        System.out.println("1 - Rachunek zwykly\n2 - Rachunek oszczednosciowy");
                        zwyklyCzyOszczednosciowy = liczby.nextInt();
                        if (zwyklyCzyOszczednosciowy == 1) {
                            System.out.println("Podaj nazwe rachunku");
                            String tempNazwa = string.nextLine();
                            listaKlientow.get(wyborKlienta).dodajRachunekZwykly(tempNazwa);
                        }
                        else if (zwyklyCzyOszczednosciowy == 2) {
                            System.out.println("Podaj nazwe rachunku");
                            String tempNazwa = string.nextLine();
                            listaKlientow.get(wyborKlienta).dodajRachunekOszczednosciowy(tempNazwa);
                        }
                        else
                            System.out.println("Wybrano niepoprawna opcje");
                    }
                    else if (choice == 2) {
                        drukujKlientow(listaKlientow);
                        System.out.println("Wybierz klienta, ktoremu chcesz usunac rachunek");
                        int wyborKlienta = podajIndeksKlienta(listaKlientow);
                        System.out.println("Ktory rachunek?");
                        drukujRachunki(listaKlientow, wyborKlienta);
                        int tempRachunek = podajIndeksKonta(listaKlientow, wyborKlienta);
                        listaKlientow.get(wyborKlienta).usunRachunek(tempRachunek);
                    }
                    else
                        System.out.println("Wybrano niepoprawna opcje");
                    break;
                }
                case 5 : { // Wypisanie historii operacji
                    drukujKlientow(listaKlientow);
                    System.out.println("Historia operacji ktorego klienta?");
                    int wyborKlienta = podajIndeksKlienta(listaKlientow);
                    historiaOperacji(listaKlientow.get(wyborKlienta));
                    break;
                }

                case 6 : { // Wplata pieniedzy
                    drukujKlientow(listaKlientow);
                    System.out.println("Ktory klient wplacil pieniadze?");
                    int wyborKlienta = podajIndeksKlienta(listaKlientow);
                    System.out.println("Ile pieniedzy wplacil?");
                    double tempKwota = podajKwote();
                    drukujRachunki(listaKlientow, wyborKlienta);
                    System.out.println("Na ktory rachunek?");
                    int tempKonto = podajIndeksKonta(listaKlientow, wyborKlienta);
                    listaKlientow.get(wyborKlienta).listaRachunkow.get(tempKonto).wplacGotowke(tempKwota);
                    System.out.println("Wplacono " + tempKwota + " | " + listaKlientow.get(wyborKlienta).listaRachunkow.get(tempKonto).sprawdzSaldo());
                    break;
                }
                case 7 : { // Wyplata pieniedzy
                    drukujKlientow(listaKlientow);
                    System.out.println("Ktory klient wyplacil pieniadze?");
                    int wyborKlienta = podajIndeksKlienta(listaKlientow);
                    System.out.println("Ile pieniedzy wyplacil?");
                    double tempKwota = podajKwote();
                    drukujRachunki(listaKlientow, wyborKlienta);
                    System.out.println("Z ktorego rachunku?");
                    int tempKonto = podajIndeksKonta(listaKlientow, wyborKlienta);
                    listaKlientow.get(wyborKlienta).listaRachunkow.get(tempKonto).wyplacGotowke(tempKwota);
                    System.out.println("Wyplacono " + tempKwota + " | " + listaKlientow.get(wyborKlienta).listaRachunkow.get(tempKonto).sprawdzSaldo());
                    break;
                }
                case 8 : { // Przelew
                    System.out.println("Wprowadz dane do Przelewu \nWybierz nadawce");
                    drukujKlientow(listaKlientow);
                    int tempNadawca = podajIndeksKlienta(listaKlientow);

                    System.out.println("Z ktorego rachunku?");
                    drukujRachunki(listaKlientow, tempNadawca);
                    int tempKontoNadawcy = podajIndeksKonta(listaKlientow, tempNadawca);

                    System.out.println("Wybierz obiorce?");
                    drukujKlientow(listaKlientow);
                    int tempOdbiorca = podajIndeksKlienta(listaKlientow);

                    System.out.println("Na ktory rachunek odbiorcy?");
                    drukujRachunki(listaKlientow, tempOdbiorca);
                    int tempKontoOdbiorcy = podajIndeksKonta(listaKlientow, tempOdbiorca);

                    System.out.println("Jaka kwote przelac?");
                    double tempKwota = podajKwote();

                    System.out.println("Zlecono przelew z konta Pana/Pani " + listaKlientow.get(tempNadawca).getImie() + " " + listaKlientow.get(tempNadawca).getNazwisko() +
                            " na konto Pana/Pani " + listaKlientow.get(tempOdbiorca).getImie() + " " + listaKlientow.get(tempOdbiorca).getNazwisko() + " na kwote " + tempKwota);
                    sprawdzDaneWykonajPrzelew(listaKlientow.get(tempNadawca), tempKontoNadawcy, listaKlientow.get(tempOdbiorca), tempKontoOdbiorcy, tempKwota);
                    break;
                }
                case 9 : { // Wyszukiwarka
                    System.out.println("Wyszukiwanie po imieniu, nazwisku lub nazwie wlasnej konta");
                    System.out.println("Podaj szukana fraze");
                    String szukanaFraza = string.nextLine();

                        for (int i = 0 ; i < listaKlientow.size() ; i++) {
                            if (listaKlientow.get(i).getImie().toLowerCase().equals(szukanaFraza.toLowerCase()) || listaKlientow.get(i).getNazwisko().toLowerCase().equals(szukanaFraza.toLowerCase())) {
                                System.out.println(i+1 + ". " + listaKlientow.get(i).getImie() + " " + listaKlientow.get(i).getNazwisko());
                            }
                            else {
                                for (int j = 0 ; j < listaKlientow.get(i).listaRachunkow.size() ; j++) {
                                    if (listaKlientow.get(i).listaRachunkow.get(j).getLabel().toLowerCase().equals(szukanaFraza.toLowerCase())) {
                                        System.out.println((i+1) + ". " + listaKlientow.get(i).getImie() + " " + listaKlientow.get(i).getNazwisko() + "\n  " + (j+1) + ". Rachunek " + listaKlientow.get(i).listaRachunkow.get(j).getLabel());

                                    }
                                }
                            }
                        }
                        break;
                    }
                case 10 : { // Zapis/odczyt z pliku
                    System.out.println("Obsluga plikow\n1 - Zapisz dane do pliku\n2 - zaladuj dane z pliku");
                    int choice = liczby.nextInt();
                    if (choice == 1) {
                        zapiszDaneDoPliku(listaKlientow);
                    }
                    else if (choice == 2) {
                        listaKlientow = wczytajDaneZPliku();
                    }
                }
            }

        } while (switchMenu < 11);

    }


}