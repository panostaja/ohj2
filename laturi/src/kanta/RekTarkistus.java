package kanta;
import static kanta.SisaltaaTarkastaja.*;

import fi.jyu.mit.ohj2.Mjonot;


/**
 * Tarkistaa rekisteritunnusksen syöttömuodon oikeellisuuden
 * Tämä on vasta placeholder, johon kerätty ajatuksia ja mahdollisia koodirakenteita hyödynnettäväksi
 * @author panos
 * @version 19.2.2022
 *
 */
public class RekTarkistus {
  
    
    
    /**
     * @param rek tarkistettava rekisteritunnus 
     * @return null jos kaikki ok
     */
    public String tarkista(String rek) {
        int pituus = rek.length();
        if ( pituus < 3 ) return "Rekisteritunnus liian lyhyt";
        var sb = new StringBuilder(rek);
        String etu = Mjonot.erota(sb, '-');
        String taka = sb.toString();
        
        if ( !onkoVain(etu,KIRJAIMET)) return "Alkuosassa saa olla vain kirjaimia"; 
        
        if ( !onkoVain(taka,NUMEROT)) return "Loppuosassa saa olla vain numeroita";
        
        return null;
    }

}

