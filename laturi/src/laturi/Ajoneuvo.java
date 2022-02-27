/**
 * 
 */
package laturi;


import java.io.OutputStream;
import java.io.PrintStream;

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
public class Ajoneuvo {
    
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

}
