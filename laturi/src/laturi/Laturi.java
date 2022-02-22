package laturi;

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

    private final Ajoneuvot ajoneuvot = new Ajoneuvot();
    
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
     * laturi.lisaa(auto1);            #THROWS SailoException
     * </pre>
     */

    public void lisaa(Ajoneuvo ajoneuvo) throws SailoException {
        ajoneuvot.lisaa(ajoneuvo);
        
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
     * @param args ei käytössä 
     */
    public static void main(String[] args) {
    //
        Laturi laturi = new Laturi();
        try {
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
                        
            laturi.lisaa(auto);
            laturi.lisaa(auto2);
            
            for (int i = 0; i < laturi.getAjoneuvoja(); i++) {
                Ajoneuvo ajoneuvo = laturi.annaAjoneuvo(i);
                System.out.println("Ajoneuvo paikassa: " + i);
                ajoneuvo.tulosta(System.out);
            }
        } catch (SailoException e) {
              System.out.println(e.getMessage());
        }
    }
    
}
