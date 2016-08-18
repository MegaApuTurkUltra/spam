/**
 * 
 */
package matu.dustbin;

import java.util.HashMap;

/**
 * @author MegaApuTurkUltra
 *
 */
public class Forums {
	protected static HashMap<String, String> emoticonMap = new HashMap<>();
	static {
		emoticonMap.put("smile.png", " :) ");
		emoticonMap.put("neutral.png", " :| ");
		emoticonMap.put("sad.png", " :( ");
		emoticonMap.put("big_smile.png", " :D ");
		emoticonMap.put("yikes.png", " :o ");
		emoticonMap.put("wink.png", " ;) ");
		emoticonMap.put("hmm.png", " :/ ");
		emoticonMap.put("tongue.png", " :P ");
		emoticonMap.put("smile.png", " :) ");
		emoticonMap.put("lol.png", " :lol: ");
		emoticonMap.put("mad.png", " :mad: ");
		emoticonMap.put("roll.png", " :rolleyes: ");
		emoticonMap.put("cool.png", " :cool: ");
	}
	public static String translateEmoticon(String imgUrl) {
		String lookup =  emoticonMap.get(imgUrl);
		if(lookup != null) return lookup;
		
		String[] urlPieces = imgUrl.split("/");
		lookup = emoticonMap.get(urlPieces[urlPieces.length - 1]);
		if(lookup != null) {
			emoticonMap.put(imgUrl, lookup);
		}
		return lookup;
	}
}
