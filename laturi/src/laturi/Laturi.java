package laturi;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * |------------------------------------------------------------------------|
 * | Luokan nimi:   Laturi                              | Avustajat:        |
 * |-------------------------------------------------------------------------
 * | Vastuualueet:                                      |                   | 
 * |                                                    | - Ajoneuvot       | 
 * | - huolehtii Lataukset ja Ajoneuvot -luokkien       | - Lataukset       | 
 * |   välisestä yhteistyöstä ja välittää näitä tietoja | - Ajoneuvo        | 
 * |   pyydettäessä                                     | - Lataus          |
 * | - lukee ja kirjoittaa laturin tiedostoon           |                   | 
 * |   pyytämällä apua avustajiltaan                    |                   |
 * |                                                    |                   | 
 * |-------------------------------------------------------------------------
 * @author panos
 * @version 19.2.2022
 *
 */
public class Laturi {

    private  Ajoneuvot ajoneuvot = new Ajoneuvot();
    private  Lataukset lataukset = new Lataukset(); 
    private String hakemisto = "humppavaara";
    
    
    /**
     * Lisää Laturiin uuden ajoneuvon
     * @param ajoneuvo lisättävä ajoneuvo
     * @throws SailoException jos lisäystä ei voida tehdä
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Laturi laturi = new Laturi();
     * Ajoneuvo auto1 = new Ajoneuvo(), auto2 = new Ajoneuvo();
     * auto1.rekisteroi(); auto2.rekisteroi();
     * laturi.getAjoneuvoja() === 0;
     * laturi.lisaa(auto1); laturi.getAjoneuvoja() === 1;
     * laturi.lisaa(auto2); laturi.getAjoneuvoja() === 2;
     * laturi.lisaa(auto1); laturi.getAjoneuvoja() === 3;
     * laturi.getAjoneuvoja() === 3;
     * laturi.annaAjoneuvo(0) === auto1;
     * laturi.annaAjoneuvo(1) === auto2;
     * laturi.annaAjoneuvo(2) === auto1;
     * laturi.annaAjoneuvo(3) === auto1; #THROWS IndexOutOfBoundsException 
     * laturi.lisaa(auto1); laturi.getAjoneuvoja() === 4;
     * laturi.lisaa(auto1); laturi.getAjoneuvoja() === 5;
     * 
     * </pre>
     */

    public void lisaa(Ajoneuvo ajoneuvo) throws SailoException {
        ajoneuvot.lisaa(ajoneuvo);
        
    }
    
    /** 
     * Korvaa latauksen tietorakenteessa.  Ottaa latauksen omistukseensa. 
     * Etsitään samalla tunnusnumerolla oleva lataus.  Jos ei löydy, 
     * niin lisätään uutena latauksena. 
     * @param lataus lisättävän latauksen viite.  Huom tietorakenne muuttuu omistajaksi 
     * @throws SailoException jos tietorakenne on jo täynnä 
     */ 
    public void korvaaTaiLisaa(Lataus lataus) throws SailoException { 
        lataukset.korvaaTaiLisaa(lataus); 
    } 

    
    
    /**
     * Listään uusi lataus laturiin
     * @param lat lisättävä lataus 
     */
    public void lisaa(Lataus lat) {
        lataukset.lisaa(lat);
    }


    
    
    /**
     * Palautaa Laturiin rekisteröidyn ajoneuvomäärä
     * @return ajoneuvojen määrä
     */

    public int getAjoneuvoja() {
        return ajoneuvot.getLkm();
    }
    
    
    /**
     * Palauttaa i:n ajoneuvon
     * @param i monesko ajoneuvo palautetaan
     * @return viite i:teen ajoneuvoon
     * @throws IndexOutOfBoundsException jos i väärin
     */
    public Ajoneuvo annaAjoneuvo(int i) throws IndexOutOfBoundsException {
        return ajoneuvot.anna(i);
    }

    /**
     * Haetaan kaikki ajoneuvon lataukset
     * @param ajoneuvo ajoneuvo jolle latauksia haetaan
     * @return tietorakenne jossa viiteet löydetteyihin latauksiin
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Laturi laturi = new Laturi();
     *  Ajoneuvo auto1 = new Ajoneuvo(), auto2 = new Ajoneuvo(), auto3 = new Ajoneuvo();
     *  auto1.rekisteroi(); auto2.rekisteroi(); auto3.rekisteroi();
     *  int id1 = auto1.getTunnusNro();
     *  int id2 = auto2.getTunnusNro();
     *  Lataus kerta11 = new Lataus(id1); laturi.lisaa(kerta11);
     *  Lataus kerta12 = new Lataus(id1); laturi.lisaa(kerta12);
     *  Lataus kerta21 = new Lataus(id2); laturi.lisaa(kerta21);
     *  Lataus kerta22 = new Lataus(id2); laturi.lisaa(kerta22);
     *  Lataus kerta23 = new Lataus(id2); laturi.lisaa(kerta23);
     *  
     *  List<Lataus> loytyneet;
     *  loytyneet = laturi.annaLataukset(auto3);
     *  loytyneet.size() === 0; 
     *  loytyneet = laturi.annaLataukset(auto1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == kerta11 === true;
     *  loytyneet.get(1) == kerta12 === true;
     *  loytyneet = laturi.annaLataukset(auto2);
     *  loytyneet.size() === 3; 
     *  loytyneet.get(0) == kerta21 === true;
     *  
     *  
     *  
     *  
     * </pre> 
     */
    public List<Lataus> annaLataukset(Ajoneuvo ajoneuvo) {
        return lataukset.annaLataukset(ajoneuvo.getTunnusNro());
    }

    
    /**
     * Tallettaa laturin tiedot tiedostoon
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            ajoneuvot.tallenna(hakemisto);
        } catch ( SailoException ex ) {
            virhe = ex.getMessage();
        }

        try {
            lataukset.tallenna(hakemisto);
        } catch ( SailoException ex ) {
            virhe += ex.getMessage();
        }
        if ( !"".equals(virhe) ) throw new SailoException(virhe);
    }

    /**
     * Lukee laturin tiedot tiedostosta
     * @param nimi jota käyteään lukemisessa
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        File dir = new File(nimi);
        dir.mkdir();
        ajoneuvot = new Ajoneuvot(); // jos luetaan olemassa olevaan niin helpoin tyhjentää näin
        lataukset = new Lataukset();

        hakemisto = nimi;
        ajoneuvot.lueTiedostosta(nimi);
        lataukset.lueTiedostosta(nimi);
    }

    
    
    
    /**
     * @param args ei käytössä 
     */
    public static void main(String[] args) {
    //
        Laturi laturi = new Laturi();
       
        try {
            laturi.lueTiedostosta("koehumppavaara");
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }

        
            Ajoneuvo auto = new Ajoneuvo();
            Ajoneuvo auto2 = new Ajoneuvo();
            auto.rekisteroi();    
            auto.taytaTiedoilla();
            auto2.rekisteroi();
            auto2.taytaTiedoilla();
            
            try {               
            laturi.lisaa(auto);
            laturi.lisaa(auto2);
            
            for (int i = 0; i < laturi.getAjoneuvoja(); i++) {
                Ajoneuvo ajoneuvo = laturi.annaAjoneuvo(i);
                System.out.println("Ajoneuvo paikassa: " + i);
                ajoneuvo.tulosta(System.out);
            }
            laturi.tallenna();
        } catch (SailoException e) {
              System.out.println(e.getMessage());
        }
    }


    /**Korvaa tai lisää ajoneuvon tietorakenteessa
     * @param ajoneuvo lisättävän ajoneuvon viite. Tietorakenne on omistaja
     * @throws SailoException jos tietorakenne täynnä
     */
    public void korvaaTaiLisaa(Ajoneuvo ajoneuvo) throws SailoException{
        ajoneuvot.korvaaTaiLisaa(ajoneuvo);
        
    }

    /**
     * palauttaa listan ajoneuvoista jotka sopivat hakuehtoon
     * @param ehto hakuehto
     * @param k kentan indeksi jonka mukaan etsitaan
     * @return löytyneet ajoneuvot
     */
    public Collection<Ajoneuvo> etsi(String ehto, int k) {
       
        return ajoneuvot.etsi(ehto, k);
    }
    
}
