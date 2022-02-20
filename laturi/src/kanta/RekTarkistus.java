package kanta;

/**
 * Tarkistaa rekisteritunnusksen syöttömuodon oikeellisuuden
 * Tämä on vasta placeholder, johon kerätty ajatuksia ja mahdollisia koodirakenteita hyödynnettäväksi
 * @author panos
 * @version 19.2.2022
 *
 */
public class RekTarkistus {

    
    //
    
}
/**
public class CharacterIsNumberOrDigitTest {
    public static void main(String[] args) {
       String str = "Tutorials123";
       for(int i=0; i < str.length(); i++) {
          Boolean flag = Character.isDigit(str.charAt(i));
          if(flag) {
             System.out.println("'"+ str.charAt(i)+"' is a number");
          }
          else {
             System.out.println("'"+ str.charAt(i)+"' is a letter");
          }
       }
    }
 }


 * Palauttaa mikä olisi hetun tarkistumerkki. Tuotava parametrinä
 * laillista muotoa oleva hetu, josta mahdollisesti tarkistumerkki 
 * puuttuu.
 * @param hetu tutkittava hetu
 * @return hetun tarkistusmerkki
 * @example
 * <pre name="test">
 *    hetunTarkistusMerkki("121212-222")    === 'N';
 *    hetunTarkistusMerkki("121212-222S")   === 'N';
 *    hetunTarkistusMerkki("121212-222N")   === 'N';
 *    hetunTarkistusMerkki("121212-231Y")   === 'Y';
 *    hetunTarkistusMerkki("311212-2317")   === '7';
 *    hetunTarkistusMerkki("311212-2317XY") === '7'; // vaikka on liikaa merkkejä
 *    hetunTarkistusMerkki("999999-9999XY") === 'F'; // vaikka on pvm väärin
 *    hetunTarkistusMerkki("12121A-222S")   === 'N'; #THROWS NumberFormatException
 *    hetunTarkistusMerkki("12121A-22")     === 'N'; #THROWS StringIndexOutOfBoundsException
 *    hetunTarkistusMerkki("121")           === 'N'; #THROWS StringIndexOutOfBoundsException
 * </pre>
 *
public static char hetunTarkistusMerkki(String hetu) {
    String pvm = hetu.substring(0,6);
    String yksilo = hetu.substring(7,10);
    long luku = Long.parseLong(pvm+yksilo);
    int jakojaannos = (int)(luku % 31L);
    return TARKISTUSMERKIT.charAt(jakojaannos);
}

*/



