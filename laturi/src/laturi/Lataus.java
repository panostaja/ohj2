package laturi;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;


/**
 * @author panos
 * @version 26.2.2022
      * |------------------------------------------------------------------------|
      * | Luokan nimi:   Lataus                              | Avustajat:        |
      * |-------------------------------------------------------------------------
      * | Vastuualueet:                                      |                   | 
      * |                                                    |                   | 
      * | (- ei tiedä laturista, eikä käyttöliittymästä)     |                   | 
      * | - tietää latausten kentät                          |                   | 
      * | - osaa tarkistaa tietyn kentän oikeellisuuden      |                   |
      * |   (syntaksin)                                      |                   | 
      * | - osaa muuttaa 2|8.9|15.1.2020..| - merkkijonon    |                   |
      * |   latauksen tiedoiksi                              |                   | 
      * | - osaa antaa merkkijonona i:n kentän tiedot        |                   | 
      * | - osaa laittaa merkkijonon i:neksi kentäksi        |                   | 
      * |                                                    |                   | 
      * |-------------------------------------------------------------------------
  *
  */

public class Lataus {
 
 
  
        
        private int         tunnusNro;
        private int         ajoneuvoNro;
        private String      pvm="";
        private double      kwh; 
        private int         kesto;
        
        private static int  seuraavaNro      = 1;
        
        
        /**
         * Alustetaan lataus
         */
        public Lataus() {
            // Vielä toistaiseksi tyhjä
        }
        
        /**
         * Alustetaan tietyn ajoneuvon lataus.  
         * @param ajoneuvoNro ajoneuvon viitenumero 
         */
        public Lataus(int ajoneuvoNro) {
            this.ajoneuvoNro = ajoneuvoNro;
        }

        /**
        * Palauttaa latauksen tiedot merkkijonona jonka voi tallentaa tiedostoon.
        * @return lataus tolppaeroteltuna merkkijonona 
        * @example
        * <pre name="test">
        *   Lataus lataus = new Lataus();
        *   lataus.parse("   3   |  2  |   8.9  | 15.1.2020 | 20 ");
        *   lataus.toString()    === "3|2|8.9|15.1.2020|20";
        * </pre>
        */
       @Override
       public String toString() {
           return "" + getTunnusNro() + "|" + ajoneuvoNro + "|" + kwh + "|" + pvm + "|" + kesto;
       }

      
       /**
        * Asettaa tunnusnumeron ja samalla varmistaa että
        * seuraava numero on aina suurempi kuin tähän mennessä suurin.
        * @param nr asetettava tunnusnumero
        */
       private void setTunnusNro(int nr) {
           tunnusNro = nr;
           if ( tunnusNro >= seuraavaNro ) seuraavaNro = tunnusNro + 1;
       }

       
       /**
        * Selvitää latauksen tiedot | erotellusta merkkijonosta.
        * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusnro.
        * @param rivi josta latauksen tiedot otetaan
        * @example
        * <pre name="test">
        *   Lataus lataus = new Lataus();
        *   lataus.parse("   3   |  2  |   8.9  | 15.1.2020 | 20 ");
        *   lataus.getAjoneuvoNro() === 2;
        *   lataus.toString()    === "3|2|8.9|15.1.2020|20";
        *   
        *   lataus.rekisteroi();
        *   int n = lataus.getTunnusNro();
        *   lataus.parse(""+(n+20));
        *   lataus.rekisteroi();
        *   lataus.getTunnusNro() === n+20+1;
        *   lataus.toString()     === "" + (n+20+1) + "|2|8.9|15.1.2020|20";
        * </pre>
        */
       public void parse(String rivi) {
           StringBuffer sb = new StringBuffer(rivi);
           setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
           ajoneuvoNro = Mjonot.erota(sb, '|', ajoneuvoNro);
           kwh = Mjonot.erota(sb, '|', kwh);
           pvm = Mjonot.erota(sb, '|', pvm);
           kesto = Mjonot.erota(sb, '|', kesto);
       }

       
       
        /**
         * Tulostetaan latauksen tiedot
         * @param out Tietovirta johon tulostetaan
         */
        public void tulosta(PrintStream out) {
                out.println("PVM: " + pvm + " kWh: " + String.format("%5.2f",kwh) + " kesto(min): " + kesto); 
                
            }

        /**
         * Tulostetaan ajoneuvot tiedot
         * @param os tietovirta johon tulostetaan
         */
        public void tulosta(OutputStream os) {
            tulosta(new PrintStream(os));
        }

        /**
         * Antaa lataukselle seuraavan rekisteröintinumeron.
         * @return latauksen uusi tunnusNro
         * @example
         * <pre name="test">
         *   Lataus virta1 = new Lataus();
         *   virta1.getTunnusNro() === 0;
         *   virta1.rekisteroi();
         *   Lataus virta2 = new Lataus(); 
         *   virta2.rekisteroi();
         *   int n1 = virta1.getTunnusNro();
         *   int n2 = virta2.getTunnusNro();
         *   n1 === n2-1;
         * </pre>
         */
        public int rekisteroi() {
            tunnusNro = seuraavaNro;
            seuraavaNro++;
            return tunnusNro;
        }

        /**
         * Palauttaa latauksen oman tunnusnumeron.
         * @return latauksen id
         */
        public int getTunnusNro() {
            return tunnusNro;
        }

        /**
         * Palautetaan mille ajoneuvolle lataus kuuluu
         * @return ajoneuvon id
         */
        public int getAjoneuvoNro() {
            return ajoneuvoNro;
        }

        
        /**
         * Apumetodi, jolla saadaan täytettyä testiarvot ajoneuvolle.
         * @param nro viite ajoneuvoon jonka latauksesta kyse
         */
        public void taytaLatausTiedoilla(int nro) {
           
            ajoneuvoNro = nro;
            pvm = "1.1.2021";
            kwh = Math.random() * 99;
            kesto = (int) (Math.random() * 600);

         }

        
        /**
         * @param args ei käytössä 
         */
        public static void main(String[] args) {
        Lataus lataus = new Lataus();
         
            
            lataus.taytaLatausTiedoilla(3);
            lataus.tulosta(System.out);
            
           
        
        }

     }
