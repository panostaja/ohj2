package laturi;


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
 * @version 19.2.2022
 *
 */
public class Ajoneuvot {

    private static final int MAX_AJONEUVOJA   = 5;
    private int              lkm              = 0;
    private Ajoneuvo         alkiot[]      = new Ajoneuvo[MAX_AJONEUVOJA];
    
    /**
     * Oletusmuodostaja
     */
    public Ajoneuvot() {
        // alustettu atribuuteissa
    }
    
    /**
     * Lisää uuden jäsenen tietorakenteeseen.  Ottaa jäsenen omistukseensa.
     * @param ajoneuvo lisätäävän ajoneuvon viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
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
     * ajoneuvot.lisaa(auto1);  #THROWS SailoException
     * </pre>
     */
    public void lisaa(Ajoneuvo ajoneuvo) throws SailoException {
        if (lkm >= alkiot.length) throw new SailoException("Liikaa alkioita");
        this.alkiot[this.lkm] = ajoneuvo;
        lkm++;
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
     * @param args ei käytössä 
     * 
     */
    public static void main(String[] args) {
        Ajoneuvot ajoneuvot = new Ajoneuvot();
        Ajoneuvo auto = new Ajoneuvo();
        Ajoneuvo auto2 = new Ajoneuvo();
            
        auto.rekisteroi(); 
        auto.taytaTiedoilla();
        auto2.rekisteroi();
        auto2.taytaTiedoilla();
        
        try {
            ajoneuvot.lisaa(auto);
            ajoneuvot.lisaa(auto2);
        } catch (SailoException e) {
            System.err.println(e.getMessage());
        }
       
        
        for (int i = 0; i < ajoneuvot.getLkm(); i++) {
            Ajoneuvo ajoneuvo = ajoneuvot.anna(i);
            System.out.println("Ajoneuvo nro: " + i);
            ajoneuvo.tulosta(System.out);
        }

  //      
    }
    
}
