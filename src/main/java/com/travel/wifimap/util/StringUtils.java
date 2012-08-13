package com.travel.wifimap.util;

public final class StringUtils {

	public static boolean isEmpty(String string) {
		return string == null || string.trim().isEmpty();
	}

	public static String formPersonName(String firstName, String lastName) {
		String result = "";
		if (StringUtils.isEmpty(firstName)) {
			if (!StringUtils.isEmpty(lastName)) {
				result = lastName.trim();
			}
		} else {
			if (StringUtils.isEmpty(lastName)) {
				result = firstName.trim();
			} else {
				result = new StringBuffer(firstName.trim()).append(" ")
						.append(lastName.trim()).toString();
			}
		}
		return result;
	}
}
