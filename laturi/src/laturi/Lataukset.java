package laturi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;


/**
 * @author panos
 * @version 26.2.2022
 * |------------------------------------------------------------------------|
 * | Luokan nimi:   Lataukset                           | Avustajat:        |
 * |-------------------------------------------------------------------------
 * | Vastuualueet:                                      |                   | 
 * |                                                    | - Lataus          | 
 * | - pitää yllä varsinaista latausekisteriä,          |                   | 
 * |   eli osaa lisätä ja poistaa latauksen             |                   | 
 * | - lukee ja kirjoittaa latauksen tiedostoon         |                   |
 * | - osaa etsiä ja lajitella                          |                   | 
 * |                                                    |                   |
 * |-------------------------------------------------------------------------
 */
public class Lataukset implements Iterable<Lataus> {

    private String                      tiedostonNimi = "";

    /** Taulukko latauksista */
    private final Collection<Lataus> alkiot        = new ArrayList<Lataus>();


    /**
     * Latausten alustaminen
     */
    public Lataukset() {
        // toistaiseksi ei tarvitse tehdä mitään
    }


    /**
     * Lisää uuden latauksen tietorakenteeseen.  Ottaa latauksen omistukseensa.
     * @param lat lisättävä lataus.  Huom tietorakenne muuttuu omistajaksi
     */
    public void lisaa(Lataus lat) {
        alkiot.add(lat);
    }


    /**
     * Lukee ajoneuvon tiedostosta.  Kesken.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        String nimi = hakemisto + "/lataukset.dat";
        File ftied = new File(nimi);
        
        try (Scanner fi = new Scanner(new FileInputStream(ftied))) { // Jotta UTF8/ISO-8859 toimii'
            while ( fi.hasNext() ) {
                String s = fi.nextLine().trim();
                if ( s == null || "".equals(s) || s.charAt(0) == ';') continue;
                Lataus lat = new Lataus();
                lat.parse(s); // kertoisi onnistumista ???
                lisaa(lat);
            }
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Ei saa luettua tiedostoa " + nimi);
        // } catch ( IOException e ) {
        //     throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }     
    }


    /**
     * Tallentaa ajoneut tiedostoon.  
     * TODO Kesken.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void talleta() throws SailoException {
        throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
    }


    /**
     * Palauttaa laturin latausten lukumäärän
     * @return latausten lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
    }


    /**
     * Iteraattori kaikkien latausten läpikäymiseen
     * @return latausiteraattori
     * 
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     *  Lataukset latausKerrat = new Lataukset();
     *  Lataus auto21 = new Lataus(2); latausKerrat.lisaa(auto21);
     *  Lataus auto11 = new Lataus(1); latausKerrat.lisaa(auto11);
     *  Lataus auto22 = new Lataus(2); latausKerrat.lisaa(auto22);
     *  Lataus auto12 = new Lataus(1); latausKerrat.lisaa(auto12);
     *  Lataus auto23 = new Lataus(2); latausKerrat.lisaa(auto23);
     * 
     *  Iterator<Lataus> i2=latausKerrat.iterator();
     *  i2.next() === auto21;
     *  i2.next() === auto11;
     *  i2.next() === auto22;
     *  i2.next() === auto12;
     *  i2.next() === auto23;
     *  i2.next() === auto12;  #THROWS NoSuchElementException  
     *  
     *  int n = 0;
     *  int anrot[] = {2,1,2,1,2};
     *  
     *  for ( Lataus lat:latausKerrat ) { 
     *    lat.getAjoneuvoNro() === anrot[n]; n++;  
     *  }
     *  
     *  n === 5;
     *  
     * </pre>
     */
    @Override
    public Iterator<Lataus> iterator() {
        return alkiot.iterator();
    }


    /**
     * Etsitään kaikki Ajoneuvon lataukset
     * @param tunnusnro ajoneuvon tunnusnumero jolle latauksia haetaan
     * @return tietorakenne jossa viiteet löydetteyihin latauksiin
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Lataukset latausKerrat = new Lataukset();
     *  Lataus kerta21 = new Lataus(2); latausKerrat.lisaa(kerta21);
     *  Lataus kerta11 = new Lataus(1); latausKerrat.lisaa(kerta11);
     *  Lataus kerta22 = new Lataus(2); latausKerrat.lisaa(kerta22);
     *  Lataus kerta12 = new Lataus(1); latausKerrat.lisaa(kerta12);
     *  Lataus kerta23 = new Lataus(2); latausKerrat.lisaa(kerta23);
     *  Lataus kerta51 = new Lataus(5); latausKerrat.lisaa(kerta51);
     *  
     *  List<Lataus> loytyneet;
     *  loytyneet = latausKerrat.annaLataukset(3);
     *  loytyneet.size() === 0; 
     *  loytyneet = latausKerrat.annaLataukset(1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == kerta11 === true;
     *  loytyneet.get(1) == kerta12 === true;
     *  loytyneet = latausKerrat.annaLataukset(5);
     *  loytyneet.size() === 1; 
     *  loytyneet.get(0) == kerta51 === true;
     * </pre> 
     */
    public List<Lataus> annaLataukset(int tunnusnro) {
        List<Lataus> loydetyt = new ArrayList<Lataus>();
        for (Lataus lat : alkiot)
            if (lat.getAjoneuvoNro() == tunnusnro) loydetyt.add(lat);
        return loydetyt;
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
        File ftied = new File(hakemisto + "/lataukset.dat");
        try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))) {
           for (var lat: alkiot) {
               fo.println(lat.toString());
           }
        } catch (FileNotFoundException ex)  {
            throw new SailoException("Tiedosto " + ftied.getAbsolutePath() + " ei aukea");
        }
    }
    

    /**
     * Testiohjelma harrastuksille
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Lataukset latausKerrat = new Lataukset();
        
        try {
            latausKerrat.lueTiedostosta("humppavaara");
        } catch (SailoException ex) {
            System.err.println(ex.getMessage());
        }
         

        
        Lataus kerta1 = new Lataus();
        kerta1.taytaLatausTiedoilla(2);
        Lataus kerta2 = new Lataus();
        kerta2.taytaLatausTiedoilla(1);
        Lataus kerta3 = new Lataus();
        kerta3.taytaLatausTiedoilla(2);
        Lataus kerta4 = new Lataus();
        kerta4.taytaLatausTiedoilla(2);

        latausKerrat.lisaa(kerta1);
        latausKerrat.lisaa(kerta2);
        latausKerrat.lisaa(kerta3);
        latausKerrat.lisaa(kerta4);
       

        System.out.println("============= Lataukset testi =================");

        List<Lataus> latausKerrat2 = latausKerrat.annaLataukset(2);

        for (Lataus lat : latausKerrat2) {
            System.out.print(lat.getAjoneuvoNro() + " ");
            lat.tulosta(System.out);
        }

        try {
            latausKerrat.tallenna("humppavaara");
        } catch (SailoException e) {
            e.printStackTrace();
        }        

        
    }

}
