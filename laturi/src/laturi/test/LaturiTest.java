package laturi.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import laturi.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2022.02.22 12:50:06 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class LaturiTest {



  // Generated by ComTest BEGIN
  /** 
   * testLisaa29 
   * @throws SailoException when error
   */
  @Test
  public void testLisaa29() throws SailoException {    // Laturi: 29
    Laturi laturi = new Laturi(); 
    Ajoneuvo auto1 = new Ajoneuvo(), auto2 = new Ajoneuvo(); 
    auto1.rekisteroi(); auto2.rekisteroi(); 
    assertEquals("From: Laturi line: 34", 0, laturi.getAjoneuvoja()); 
    laturi.lisaa(auto1); assertEquals("From: Laturi line: 35", 1, laturi.getAjoneuvoja()); 
    laturi.lisaa(auto2); assertEquals("From: Laturi line: 36", 2, laturi.getAjoneuvoja()); 
    laturi.lisaa(auto1); assertEquals("From: Laturi line: 37", 3, laturi.getAjoneuvoja()); 
    assertEquals("From: Laturi line: 38", 3, laturi.getAjoneuvoja()); 
    assertEquals("From: Laturi line: 39", auto1, laturi.annaAjoneuvo(0)); 
    assertEquals("From: Laturi line: 40", auto2, laturi.annaAjoneuvo(1)); 
    assertEquals("From: Laturi line: 41", auto1, laturi.annaAjoneuvo(2)); 
    try {
    assertEquals("From: Laturi line: 42", auto1, laturi.annaAjoneuvo(3)); 
    fail("Laturi: 42 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); }
    laturi.lisaa(auto1); assertEquals("From: Laturi line: 43", 4, laturi.getAjoneuvoja()); 
    laturi.lisaa(auto1); assertEquals("From: Laturi line: 44", 5, laturi.getAjoneuvoja()); 
    try {
    laturi.lisaa(auto1); 
    fail("Laturi: 45 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
  } // Generated by ComTest END
}