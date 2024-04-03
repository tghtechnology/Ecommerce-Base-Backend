package tghtechnology.tiendavirtual.Enums;

import java.util.Set;
import java.util.regex.Pattern;

public enum SettingType {

	STRING,
	INT,
	DECIMAL,
	BOOL,
	OTHER
	;
	
	public static Pattern intPattern = Pattern.compile("^\\d*[1-9]\\d*$");
	public static Pattern decimalPattern = Pattern.compile("^\\d*\\.?\\d*$");
	public static Set<String> boolValues = Set.of("true", "false");
}
