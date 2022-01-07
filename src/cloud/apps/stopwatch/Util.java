package cloud.apps.stopwatch;

import java.awt.Image;
import java.util.concurrent.TimeUnit;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Util {
	static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
	    Image img = icon.getImage();
	    Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);  
	    return new ImageIcon(resizedImage);
	}
	static String hhmmss(long ms) {
		return String.format("%02d:%02d:%02d", 
			    TimeUnit.MILLISECONDS.toHours(ms),
			    TimeUnit.MILLISECONDS.toMinutes(ms) - 
			    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(ms)),
			    TimeUnit.MILLISECONDS.toSeconds(ms) - 
			    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms)));
	}
	static String hhmmss(long ms, Boolean showMS) {
		if (showMS) return Util.hhmmss(ms) + Util.getMS(ms);
		else return Util.hhmmss(ms);
	}
	static String getMS(long time) {
		String timeStr = Long.toString(time);
		
		switch(timeStr.length()) {
			case 0: return ".00";
			case 1: return ".0"+time;
			case 2: return "."+time;
			default: return "." + timeStr.substring(timeStr.length()-3, timeStr.length()-1);
		}
	}
}
