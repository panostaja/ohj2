/**
 * 
 */
package laturi;


import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Comparator;

import fi.jyu.mit.ohj2.Mjonot;
import kanta.RekTarkistus;
import kanta.Tietue;


/**
*|------------------------------------------------------------------------|
*| Luokan nimi:   Ajoneuvo                            | Avustajat:        |
*|-------------------------------------------------------------------------
*| Vastuualueet:                                      |                   | 
*|                                                    |                   | 
*| (- ei tiedä laturista, eikä käyttöliittymästä)     |                   | 
*| - tietää ajoneuvon kentät (rek.nro, malli, jne.)   |                   | 
*| - osaa tarkistaa tietyn kentän oikeellisuuden      |                   |
*|   (syntaksin)                                      |                   | 
*| - osaa muuttaa 1|ABC-123|..| - merkkijonon         |                   |
*|   ajoneuvon tiedoiksi                              |                   | 
*| - osaa antaa merkkijonona i:n kentän tiedot        |                   | 
*| - osaa laittaa merkkijonon i:neksi kentäksi        |                   | 
*|                                                    |                   | 
 |-------------------------------------------------------------------------
 * 
 * @author panos
 * @version 19.2.2022
 *
 */
public class Ajoneuvo  implements Cloneable, Tietue {
    
    private int         tunnusNro        = 0;
    private String      rekisteriTunnus  ="";
    private String      merkki           ="";
    private String      malli            ="";
    private String      haltija          ="";
    private String      puhelin          ="";
    private String      email            ="";
    private double      akunKoko         = 0;
    private double      maxACLatausTeho  = 0;
    
    private static int  seuraavaNro      = 1;
    
    /**
     * Tulostetaan Ajotenuvon tiedot
     * @param out Tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
            out.println(String.format("%03d", tunnusNro, 3) + "  " + rekisteriTunnus) ;
            out.println("  " + merkki + "  " + malli);
            out.println(" " + haltija +
                        " " + puhelin +
                        " " + email);
            out.print("  Akun koko " + String.format("%4.1f",akunKoko) + " kWh.");
            out.println("  Max AC latausteho " + String.format("%3.1f", maxACLatausTeho) + " kW.");
                    
        }

    
    /**
     * Selvitää ajoneuvon tiedot | erotellusta merkkijonosta
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro.
     * @param rivi josta ajoneuvon tiedot otetaan
     * 
     * @example
     * <pre name="test">
     *   Ajoneuvo ajoneuvo = new Ajoneuvo();
     *   ajoneuvo.parse("   2  |  VAU-456   | Seat |  Mii");
     *   ajoneuvo.getTunnusNro() === 2;
     *   ajoneuvo.toString().startsWith(2|VAU-456|Seat|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
     *
     *   ajoneuvo.rekisteroi();
     *   int n = ajoneuvo.getTunnusNro();
     *   ajoneuvo.parse(""+(n+20));       // Otetaan merkkijonosta vain tunnusnumero
     *   ajoneuvo.rekisteroi();           // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
     *   ajoneuvo.getTunnusNro() === n+20+1;
     *     
     * </pre>
     */
    public void parse(String rivi) {
        var sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        rekisteriTunnus = Mjonot.erota(sb, '|', rekisteriTunnus);
        merkki = Mjonot.erota(sb, '|', merkki);
        malli = Mjonot.erota(sb, '|', malli);
        akunKoko = Mjonot.erota(sb, '|', akunKoko);
        maxACLatausTeho = Mjonot.erota(sb, '|', maxACLatausTeho);
        haltija = Mjonot.erota(sb, '|', haltija);
        puhelin = Mjonot.erota(sb, '|', puhelin);
        email = Mjonot.erota(sb, '|', email);
        
    }

    /**
     * Asettaa tunnusnumeron ja samalla varmistaa että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava tunnusnumero
     */
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
    }

    
    /**
     * Tulostetaan Ajoneuvot tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }

    /**
     * Antaa Ajoneuvolle seuraavan rekisteröintinumeron.
     * @return Ajoneuvon uusi tunnusNro
     * @example
     * <pre name="test">
     *   Ajoneuvo auto1 = new Ajoneuvo();
     *   auto1.getTunnusNro() === 0;
     *   auto1.rekisteroi();
     *   Ajoneuvo auto2 = new Ajoneuvo();
     *   auto2.rekisteroi();
     *   int n1 = auto1.getTunnusNro();
     *   int n2 = auto2.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }

    /**
     * Palauttaa Ajoneuvon tunnusnumeron.
     * @return ajoneuvon tunnusnumero
     */
    public int getTunnusNro() {
        return tunnusNro;
    }

    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot ajoneuvolle.
     */
    public void taytaTiedoilla() {
       
        rekisteriTunnus = "ABC-" + (int)(Math.random() * 999 + 1);
        merkki = "Seat";
        malli = "Mii";
        haltija = "Mikko Mallikas";
        puhelin = "+358400123456";
        email = "mikko.mallikas@mail.fi";
        akunKoko = 36.8;
        maxACLatausTeho = 11;
     }

    /**
     * Palauttaa ajoneuvon tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return ajoneuvo tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Ajoneuvo ajoneuvo = new Ajoneuvo();
     *   ajoneuvo.parse("   2  |  VAU-456   |Seat |   Mii");
     *   ajoneuvo.toString().startsWith("2|VAU-456|Seat|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
     * </pre>  
     */
    @Override
    public String toString() {
        return "" +
                getTunnusNro() + "|" +
                rekisteriTunnus + "|" +
                merkki + "|" +
                malli + "|" +
                akunKoko + "|" +
                maxACLatausTeho + "|" +
                haltija + "|" +
                puhelin + "|" +
                email;
                
    }


    
    /**
     * @param args ei käytössä 
     */
    public static void main(String[] args) {
    Ajoneuvo auto = new Ajoneuvo();
    Ajoneuvo auto2 = new Ajoneuvo();
        
        auto.rekisteroi();    
        auto.tulosta(System.out);
        auto.taytaTiedoilla();
        auto.tulosta(System.out);
        
        auto2.rekisteroi();
        auto2.tulosta(System.out);
        auto2.taytaTiedoilla();
        auto2.tulosta(System.out);
        
    
    }

    /**
     * @return ajoneuvon rekisteritunnus
     */
    public String getRekisteriTunnus() {
        return rekisteriTunnus;
    }


    /**
     * @return Ajoneuvon merkki
     * 
     */
    public String getMerkki() {
        
        return merkki;
    }


    /**
     * @return ajoneuvon malli
     */
    public String getMalli() {
        
        return malli;
    }


    /**
     * @return ajoneuvon akun koko
     */
    public Double getAkku() {
       
        return akunKoko;
    }

// private RekTarkistus rekisterinumerot = new RekTarkistus();

/**
 * @return kenttien lukumäärä
 */
@Override
public int getKenttia() {
    return 9;
}

/**
 * Palauttaa k:tta jäsenen kenttää vastaavan kysymyksen
 * @param k kuinka monennen kentän kysymys palautetaan (0-alkuinen)
 * @return k:netta kenttää vastaava kysymys
 */
@Override
public String getKysymys(int k) {
    switch ( k ) {
    case 0: return "Tunnus nro";
    case 1: return "rekisterituunnus";
    case 2: return "merkki";
    case 3: return "malli";
    case 4: return "akun koko";
    case 5: return "max latausteho";
    case 6: return "haltija";
    case 7: return "puhelinnumero";
    case 8: return "email";
    default: return "Äääliö";
    }
}

@Override
public String anna(int k) {
    switch ( k ) {
    case 0: return "" + tunnusNro;
    case 1: return "" + rekisteriTunnus;
    case 2: return "" + merkki;
    case 3: return "" + malli;
    case 4: return "" + akunKoko;
    case 5: return "" + maxACLatausTeho;
    case 6: return "" + haltija;
    case 7: return "" + puhelin;
    case 8: return "" + email;
    default: return "Äääliö";
    }
}


    
    
    @Override
    public Ajoneuvo clone() throws CloneNotSupportedException {
        Ajoneuvo uusi;
        uusi = (Ajoneuvo) super.clone();
        return uusi;
    }


    /**
     * @return ensimmäinen mielekäs kenttä kyseltäväksi
     */
    @Override
    public int ekaKentta() {
        return 1;
    }

    /**
     * TOOO: korjaa testit  
     * 
     * Asettaa k:n kentän arvoksi parametrina tuodun merkkijonon arvon
     * @param k kuinka monennen kentän arvo asetetaan
     * @param jono jonoa joka asetetaan kentän arvoksi
     * @return null jos asettaminen onnistuu, muuten vastaava virheilmoitus.
     * @example
     * <pre name="test">
     *   Jasen jasen = new Jasen();
     *   jasen.aseta(1,"Ankka Aku") === null;
     *   jasen.aseta(2,"kissa") =R= "Hetu liian lyhyt"
     *   jasen.aseta(2,"030201-1111") === "Tarkistusmerkin kuuluisi olla C"; 
     *   jasen.aseta(2,"030201-111C") === null; 
     *   jasen.aseta(9,"kissa") === "Liittymisvuosi väärin jono = \"kissa\"";
     *   jasen.aseta(9,"1940") === null;
     * </pre>
     */
   // @Override
    @Override
    public String aseta(int k, String jono) {
        String tjono = jono.trim();
        StringBuffer sb = new StringBuffer(tjono);
        switch ( k ) {
        case 0:
            setTunnusNro(Mjonot.erota(sb, '§', getTunnusNro()));
            return null;
        case 1:
            RekTarkistus rek = new RekTarkistus();
            String virhe = rek.tarkista(tjono);
            if ( virhe != null ) return virhe;
            rekisteriTunnus = tjono;
            return null;
        case 2:
            
            merkki = tjono;
            return null;
        case 3:
            malli = tjono;
            return null;
        case 4:
            akunKoko = Mjonot.erotaDouble(sb, 0);;
            return null;
        case 5:
            maxACLatausTeho = Mjonot.erotaDouble(sb, 0);
            return null;
        case 6:
            haltija = tjono;
            return null;
        case 7:
            puhelin = tjono;
            return null;
        case 8:
            email = tjono;
            return null;
        default:
            return "ÄÄliö";
        }
    }
    
    
    /** 
     * Antaa k:n kentän sisällön merkkijonona 
     * @param k monenenko kentän sisältö palautetaan 
     * @return kentän sisältö merkkijonona 
     */ 

    public String getAvain(int k) {
        switch ( k ) {
        case 0: return "" + tunnusNro;
        case 1: return "" + rekisteriTunnus;
        case 2: return "" + merkki;
        case 3: return "" + malli;
        case 4: return "" + akunKoko;
        case 5: return "" + maxACLatausTeho;
        case 6: return "" + haltija;
        case 7: return "" + puhelin;
        case 8: return "" + email;
        default: return "Äääliö";
        }
    }


  /**
   * Luokka joka osaa verrata kahta ajoneuvoa
   */
    public static class Vertailija implements Comparator<Ajoneuvo> {
       private int k;
       
       @SuppressWarnings("javadoc")
    public Vertailija(int k) {
           this.k = k;
       }

    @Override
    public int compare(Ajoneuvo ajo1, Ajoneuvo ajo2) {
        return ajo1.getAvain(k).compareTo(ajo2.getAvain(k));
    }
       
       
    }
    
}
