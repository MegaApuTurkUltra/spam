/**
 * 
 */
package matu.dustbin;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * @author MegaApuTurkUltra
 *
 */
public class Run {
	public static void main(String[] args) throws Exception {
		ObjectInputStream oin = new ObjectInputStream(new FileInputStream("Markov.java_serialize"));
		Markov markov = (Markov) oin.readObject();
		oin.close();
		
		for(int i = 0; i < 10; i++) {
			System.out.println(markov.generate(1024));
			System.out.println();
		}
	}
}
