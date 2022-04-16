package laturi;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;
import kanta.Tietue;


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

public class Lataus implements Cloneable, Tietue {
 
 
  
        
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
           StringBuffer sb = new StringBuffer("");
           String erotin = "";
           for (int k = 0; k < getKenttia(); k++) {
               sb.append(erotin);
               sb.append(anna(k));
               erotin = "|";
           }
           return sb.toString();

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
           for (int k = 0; k < getKenttia(); k++)
               aseta(k, Mjonot.erota(sb, '|'));

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

        @Override
        public int getKenttia() {
            // TODO Auto-generated method stub
            return 5;
        }

        @Override
        public int ekaKentta() {
            // TODO Auto-generated method stub
            return 2;
        }

        @Override
        public String getKysymys(int k) {
            switch (k) {
            case 0:
                return "id";
            case 1:
                return "AjoneuvoId";
            case 2:
                return "pvm";
            case 3:
                return "kwh";
            case 4:
                return "aika";
            default:
                return "???";
        }

        }

        @Override
        public String anna(int k) {
            switch (k) {
            case 0:
                return "" + tunnusNro;
            case 1:
                return "" + ajoneuvoNro;
            case 2:
                return "" + kwh;
            case 3:
                return pvm;
            case 4:
                return "" + kesto;
            default:
                return "???";
        }

        }

        @Override
        public String aseta(int k, String s) {
            String st = s.trim();
            StringBuffer sb = new StringBuffer(st);
            switch (k) {
                case 0:
                    setTunnusNro(Mjonot.erota(sb, '$', getTunnusNro()));
                    return null;
                case 1:
                    ajoneuvoNro = Mjonot.erota(sb, '$', ajoneuvoNro);
                    return null;
                case 2:
                    pvm = st;
                    return null;
                case 3:
                    try {
                        kwh = Mjonot.erotaDouble(sb, '§');
                    } catch (NumberFormatException ex) {
                        return "aloitusvuosi: Ei kokonaisluku ("+st+")";
                    }
                    return null;

                case 4:
                    try {
                        kesto = Mjonot.erotaEx(sb, '§', kesto);
                    } catch (NumberFormatException ex) {
                        return "Ei kokonaisluku ("+st+")";
                    }
                    return null;

                default:
                    return "Väärä kentän indeksi";
            }

        }

        @Override
        public Lataus clone() throws CloneNotSupportedException {
            return (Lataus)super.clone();
        }

        @Override
        public boolean equals(Object obj) {
            if ( obj == null ) return false;
            return this.toString().equals(obj.toString());
        }
        

        @Override
        public int hashCode() {
            return tunnusNro;
        }

        
     }
