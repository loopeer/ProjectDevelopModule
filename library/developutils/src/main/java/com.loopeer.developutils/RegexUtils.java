package com.loopeer.developutils;

public final class RegexUtils {

    public static final String REGEX_PHONE = "^1[0-9]{10}$";

    public static final String REGEX_EMAIL
        = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    public static final String REGEX_ID_CARD = "(^\\d{15}$)|(\\d{17}(?:\\d|x|X)$)";

    private static final String REGEX_PLATE_NUMBER = "^[\\u4e00-\\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$";

    private RegexUtils(){}

    public static boolean isPhone(String phone){
        if (phone == null) return false;
        return phone.trim().matches(REGEX_PHONE);
    }

    public static boolean isEmail(String email){
        if (email == null) return false;
        return email.trim().matches(REGEX_EMAIL);
    }

    /**
     * the regex actually is not correct , but is enough for most situation
     * @param idCard ID Card number
     * @return true if the ID Card number is legal;
     */
    public static boolean isIDCard(String idCard){
        if (idCard == null) return false;
        return idCard.trim().matches(REGEX_ID_CARD);
    }

    public static boolean isPlateNumber(String plateNo){
        if (plateNo == null) return false;
        return plateNo.matches(REGEX_PLATE_NUMBER);
    }
}
