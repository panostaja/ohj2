package laturi.test;
// Generated by ComTest BEGIN
import laturi.*;
import static org.junit.Assert.*;
import org.junit.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2022.04.18 09:36:08 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class AjoneuvotTest {


  // Generated by ComTest BEGIN
  /** 
   * testLisaa50 
   * @throws SailoException when error
   */
  @Test
  public void testLisaa50() throws SailoException {    // Ajoneuvot: 50
    Ajoneuvot ajoneuvot = new Ajoneuvot(); 
    Ajoneuvo auto1 = new Ajoneuvo(), auto2 = new Ajoneuvo(); 
    assertEquals("From: Ajoneuvot line: 54", 0, ajoneuvot.getLkm()); 
    ajoneuvot.lisaa(auto1); assertEquals("From: Ajoneuvot line: 55", 1, ajoneuvot.getLkm()); 
    ajoneuvot.lisaa(auto2); assertEquals("From: Ajoneuvot line: 56", 2, ajoneuvot.getLkm()); 
    ajoneuvot.lisaa(auto1); assertEquals("From: Ajoneuvot line: 57", 3, ajoneuvot.getLkm()); 
    assertEquals("From: Ajoneuvot line: 58", auto1, ajoneuvot.anna(0)); 
    assertEquals("From: Ajoneuvot line: 59", auto2, ajoneuvot.anna(1)); 
    assertEquals("From: Ajoneuvot line: 60", auto1, ajoneuvot.anna(2)); 
    assertEquals("From: Ajoneuvot line: 61", false, ajoneuvot.anna(1) == auto1); 
    assertEquals("From: Ajoneuvot line: 62", true, ajoneuvot.anna(1) == auto2); 
    try {
    assertEquals("From: Ajoneuvot line: 63", auto1, ajoneuvot.anna(3)); 
    fail("Ajoneuvot: 63 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); }
    ajoneuvot.lisaa(auto1); assertEquals("From: Ajoneuvot line: 64", 4, ajoneuvot.getLkm()); 
    ajoneuvot.lisaa(auto1); assertEquals("From: Ajoneuvot line: 65", 5, ajoneuvot.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testKorvaaTaiLisaa197 
   * @throws SailoException when error
   * @throws CloneNotSupportedException when error
   */
  @Test
  public void testKorvaaTaiLisaa197() throws SailoException,CloneNotSupportedException {    // Ajoneuvot: 197
    Ajoneuvot ajoneuvot = new Ajoneuvot(); 
    Ajoneuvo ajo1 = new Ajoneuvo(), ajo2 = new Ajoneuvo(); 
    ajo1.rekisteroi(); ajo2.rekisteroi(); 
    assertEquals("From: Ajoneuvot line: 203", 0, ajoneuvot.getLkm()); 
    ajoneuvot.korvaaTaiLisaa(ajo1); assertEquals("From: Ajoneuvot line: 204", 1, ajoneuvot.getLkm()); 
    ajoneuvot.korvaaTaiLisaa(ajo2); assertEquals("From: Ajoneuvot line: 205", 2, ajoneuvot.getLkm()); 
    Ajoneuvo ajo3 = ajo1.clone(); 
    ajo3.setRekisteriTunnus("AAA-111"); 
    Iterator<Ajoneuvo> it = ajoneuvot.iterator(); 
    assertEquals("From: Ajoneuvot line: 209", true, it.next() == ajo1); 
    ajoneuvot.korvaaTaiLisaa(ajo3); assertEquals("From: Ajoneuvot line: 210", 2, ajoneuvot.getLkm()); 
    it = ajoneuvot.iterator(); 
    Ajoneuvo j0 = it.next(); 
    assertEquals("From: Ajoneuvot line: 213", ajo3, j0); 
    assertEquals("From: Ajoneuvot line: 214", true, j0 == ajo3); 
    assertEquals("From: Ajoneuvot line: 215", false, j0 == ajo1); 
  } // Generated by ComTest END
}