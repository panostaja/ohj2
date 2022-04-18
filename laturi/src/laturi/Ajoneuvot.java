package laturi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

import fi.jyu.mit.ohj2.WildChars;

/**
 * |------------------------------------------------------------------------|
 * | Luokan nimi:   Ajoneuvot                           | Avustajat:        |
 * |-------------------------------------------------------------------------
 * | Vastuualueet:                                      |                   | 
 * |                                                    | - Ajoneuvo        | 
 * | - pitää yllä varsinaista ajoneuvorekisteriä,       |                   | 
 * |   eli osaa lisätä ja poistaa ajoneuvon             |                   | 
 * | - lukee ja kirjoittaa ajoneuvot tiedostoon         |                   |
 * | - osaa etsiä ja lajitella                          |                   | 
 * |                                                    |                   |
 * |-------------------------------------------------------------------------
 * @author panos
 * @version 18.4.2022
 *
 */
public class Ajoneuvot {

    private static final int MAX_AJONEUVOJA   = 5;
    private int              lkm              = 0;
    private Ajoneuvo         alkiot[]      = new Ajoneuvo[MAX_AJONEUVOJA];
    private boolean muutettu = false;
    
    /**
     * Oletusmuodostaja
     */
    public Ajoneuvot() {
        // alustettu atribuuteissa
    }
    
    /**
     * Lisää uuden jäsenen tietorakenteeseen.  Ottaa jäsenen omistukseensa.
     * @param ajoneuvo lisätäävän ajoneuvon viite.  Huom tietorakenne muuttuu omistajaksi
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * Ajoneuvot ajoneuvot = new Ajoneuvot();
     * Ajoneuvo auto1 = new Ajoneuvo(), auto2 = new Ajoneuvo();
     * ajoneuvot.getLkm() === 0;
     * ajoneuvot.lisaa(auto1); ajoneuvot.getLkm() === 1;
     * ajoneuvot.lisaa(auto2); ajoneuvot.getLkm() === 2;
     * ajoneuvot.lisaa(auto1); ajoneuvot.getLkm() === 3;
     * ajoneuvot.anna(0) === auto1;
     * ajoneuvot.anna(1) === auto2;
     * ajoneuvot.anna(2) === auto1;
     * ajoneuvot.anna(1) == auto1 === false;
     * ajoneuvot.anna(1) == auto2 === true;
     * ajoneuvot.anna(3) === auto1; #THROWS IndexOutOfBoundsException 
     * ajoneuvot.lisaa(auto1); ajoneuvot.getLkm() === 4;
     * ajoneuvot.lisaa(auto1); ajoneuvot.getLkm() === 5;
     * </pre>
     */
    public void lisaa(Ajoneuvo ajoneuvo)  {
        if (lkm >= alkiot.length) alkiot = Arrays.copyOf(alkiot, lkm+20);
        this.alkiot[this.lkm] = ajoneuvo;
        lkm++;
        muutettu = true;
    }

    /**
     * Palauttaa Laturille kirjattujen ajoneuvojen lukumäärän
     * @return ajoneuvojen lukumäärä
     */
    public int getLkm() {
        return lkm;
    }

    /**
     * Palauttaa viitteen i:teen ajoneuvoon.
     * @param i monennenko ajoneuvon viite halutaan
     * @return viite ajoneuvoon, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella  
     */
    public Ajoneuvo anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }

    /**
     * Lukee ajoneuvon tiedostosta. 
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        String nimi = hakemisto + "/autot.dat";
        File ftied = new File(nimi);
        
        try (Scanner fi = new Scanner(new FileInputStream(ftied))) { // Jotta UTF8/ISO-8859 toimii'
            while ( fi.hasNext() ) {
                String s = fi.nextLine();
                if ( s == null || "".equals(s) || s.charAt(0) == ';') continue;
                Ajoneuvo ajoneuvo = new Ajoneuvo();
                ajoneuvo.parse(s); // kertoisi onnistumista ???
                lisaa(ajoneuvo);
            }
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Ei saa luettua tiedostoa " + nimi);
        // } catch ( IOException e ) {
        //     throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }     
        muutettu = false;
    }

    
    /**
     * Tallentaa Ajoneuvot tiedostoon.  
     * Tiedoston muoto:
     * <pre>
     * 1|ABC-123|Seat|Mii|36.8|11|Mikko Mallikas|+358400123456|mikko.mallikas@mail.fi
     * 2|VAU-456|Mercedes-Benz|EQC|80|11|Jeppe Olvi|+358509988776|joolvi@moon.fi
     * </pre>
     * @param hakemisto tallennettavan tiedoston hakemisto
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna(String hakemisto) throws SailoException {
        if (!muutettu) return; 
        File ftied = new File(hakemisto + "/autot.dat");
        try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))) {
           for (int i=0; i<this.getLkm(); i++) {
               Ajoneuvo ajoneuvo = this.anna(i);
               fo.println(ajoneuvo.toString());
           }
        } catch (FileNotFoundException ex)  {
            throw new SailoException("Tiedosto " + ftied.getAbsolutePath() + " ei aukea");
        }
    }
    
    
    /**
     * @param args ei käytössä 
     * 
     */
    public static void main(String[] args) {
        Ajoneuvot ajoneuvot = new Ajoneuvot();
        
        try {
            ajoneuvot.lueTiedostosta("humppavaara");
        } catch (SailoException ex) {
            System.err.println(ex.getMessage());
        }
         

        
        Ajoneuvo auto = new Ajoneuvo();
        Ajoneuvo auto2 = new Ajoneuvo();
            
        auto.rekisteroi(); 
        auto.taytaTiedoilla();
        auto2.rekisteroi();
        auto2.taytaTiedoilla();
        

            ajoneuvot.lisaa(auto);
            ajoneuvot.lisaa(auto2);

       
        
        for (int i = 0; i < ajoneuvot.getLkm(); i++) {
            Ajoneuvo ajoneuvo = ajoneuvot.anna(i);
            System.out.println("Ajoneuvo nro: " + i);
            ajoneuvo.tulosta(System.out);
        }

        try {
            ajoneuvot.tallenna("humppavaara");
        }  catch (SailoException e) {
            // e.printStackTrace();
            System.err.println(e.getMessage());
        }
    

        
    }

    /**
     * Korvaa ajoneuvon tietorakenteessa.  Ottaa ajoneuvon omistukseensa.
     * Etsitään samalla tunnusnumerolla oleva ajoneuvo.  Jos ei löydy,
     * niin lisätään uutena ajoneuvona.
     * @param ajoneuvo lisätäävän ajoneuvon viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     * <pre name="test">
     * #THROWS SailoException,CloneNotSupportedException
     * #PACKAGEIMPORT
     * Ajoneuvot ajoneuvot = new Ajoneuvot();
     * Ajoneuvo ajo1 = new Ajoneuvo(), ajo2 = new Ajoneuvo();
     * ajo1.rekisteroi(); ajo2.rekisteroi();
     * ajoneuvot.getLkm() === 0;
     * ajoneuvot.korvaaTaiLisaa(ajo1); ajoneuvot.getLkm() === 1;
     * ajoneuvot.korvaaTaiLisaa(ajo2); ajoneuvot.getLkm() === 2;
     * </pre>
     */
    public void korvaaTaiLisaa(Ajoneuvo ajoneuvo) throws SailoException {
        int id = ajoneuvo.getTunnusNro();
        for (int i = 0; i < lkm; i++) {
            if ( alkiot[i].getTunnusNro() == id ) {
                alkiot[i] = ajoneuvo;
                muutettu = true;
                return;
            }
        }
        lisaa(ajoneuvo);
    }

    /**
     * @param ehto hakuehto mitä etsitään
     * @param k kentan indeksi mita etsitaan
     * @return mitä löydetty
     */
    public Collection<Ajoneuvo> etsi(String ehto, int k) {
        ArrayList<Ajoneuvo> loytyneet = new ArrayList<Ajoneuvo>();
        int hk = k;
        if (hk < 0) hk =1;
        for (int i=0; i<getLkm();i++) {
            Ajoneuvo ajoneuvo = anna(i);
            String sisalto = ajoneuvo.anna(hk);
            if (WildChars.onkoSamat(sisalto,  ehto))
               loytyneet.add(ajoneuvo);
        }
         Collections.sort(loytyneet, new Ajoneuvo.Vertailija(k));
         return loytyneet;

       
    }

    /**
     * @param id etsittävä id   
     * @return palauttaa indeksin tai -1 jos ei löydy
     */
    public int etsiId(int id) { 
        for (int i = 0; i < lkm; i++) 
            if (id == alkiot[i].getTunnusNro()) return i; 
        return -1; 
    }
    
    /**
     * @param id poistettava id
     * @return palauttaa 0 jos rakenne tyhjä, muuten yksi jos poisto onnistui
     */
    public int poista(int id) { 
        int ind = etsiId(id); 
        if (ind < 0) return 0; 
        lkm--;
        for (int i = ind; i < lkm; i++)
            alkiot[i] = alkiot[i + 1];
        alkiot[lkm] = null;
        muutettu = true; 
        return 1; 
    }
    
}
