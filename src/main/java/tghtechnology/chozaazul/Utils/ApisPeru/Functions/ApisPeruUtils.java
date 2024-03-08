package tghtechnology.chozaazul.Utils.ApisPeru.Functions;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatterBuilder;

import tghtechnology.chozaazul.Utils.TghUtils;


public class ApisPeruUtils {
	
	public static String dateFormat(LocalDateTime datetime) {
		return datetime.atOffset(ZoneOffset.UTC).format(new DateTimeFormatterBuilder()
		        .append(TghUtils.formatter) // use the existing formatter for date time
		        .appendOffset("+HH:MM", "+05:00") // set 'noOffsetText' to desired '+00:00'
		        .toFormatter());
		
		//return datetime.atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	}
	
}
