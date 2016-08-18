/**
 * 
 */
package matu.dustbin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

/**
 * @author MegaApuTurkUltra
 *
 */
public class Generate {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		if(!new File("scratch_dustbin.sqlite3").exists()) {
			throw new FileNotFoundException("Missing database file: scratch_dustbin.sqlite3");
		}
		
		Class.forName("org.sqlite.JDBC");
		Connection c = DriverManager.getConnection("jdbc:sqlite:scratch_dustbin.sqlite3");
		System.out.println("Opened database");

		Markov markov = new Markov();
		
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM dustbin");
		System.out.println("Executed query");
		while (rs.next()) {
			String postHtml = rs.getString("post");
			
			Document doc = Jsoup.parse(postHtml);
			doc.select("blockquote").remove();
			doc.select("pre.blocks").remove();
			doc.select("div.code").remove();
			Elements imgs = doc.select("img");
			for(int j = 0; j < imgs.size(); j++) {
				Element el = imgs.get(j);
				String emoticon = Forums.translateEmoticon(imgs.attr("src"));
				if(emoticon != null) {
					el.replaceWith(new TextNode(emoticon, ""));
				} else {
					el.remove();
				}
			}
			
			String[] words = doc.text().split(" ");
			markov.train(words);
		}
		rs.close();
		stmt.close();
		c.close();
		
		System.out.println("Loaded markov chain");
		
		FileOutputStream out = new FileOutputStream("Markov.java_serialize");
		ObjectOutputStream oout = new ObjectOutputStream(out);
		oout.writeObject(markov);
		oout.close();
		
		System.out.println("Done");
	}

}
