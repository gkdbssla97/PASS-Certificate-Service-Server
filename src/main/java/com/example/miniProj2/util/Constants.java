package com.example.miniProj2.util;

import java.util.ArrayList;
import java.util.List;

public final class Constants {
    public final static String COMPANYCD = "90001";
    public final static String BEARER_TOKEN = "Bearer ";
    public final static String ACCESS_TOKEN = "AT-111111";
    public final static String ENCRYPT_KEY = "YzNmOGQ2OGI1ZDEwNDA5YmJmZmRhMTI5";
    public final static List<String> requiredKeysByRegister = List.of("companyCd", "reqTxId", "serviceTycd", "telcoTycd", "phoneNo",
            "userNm", "birthday", "gender", "reqTitle", "reqCSPhoneNo", "reqEndDttm", "signTargetTycd", "signTarget");
    public final static List<String> optionKeysByRegister = List.of("isUserAgreement", "isDigitalSign", "isNotification", "isPASSVerify");
    public final static List<String> requiredKeysBySearch = List.of("companyCd", "reqTxId", "certTxId", "phoneNo", "userNm");
    public final static List<String> serviceTycd = List.of("S1001", "S1002", "S1003", "S2001", "S3001", "S3002");
    public final static List<String> telcoTycd = List.of("S", "K", "L");

    public static List<String> allKeysByRegister() {
        List<String> joined = new ArrayList<>();
        joined.addAll(requiredKeysByRegister);
        joined.addAll(optionKeysByRegister);
        return joined;
    }
}
