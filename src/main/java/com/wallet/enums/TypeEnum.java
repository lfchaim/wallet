package com.wallet.enums;

public enum TypeEnum {
    IN("INCOME"),
    OC("OUTCOME");

    private final String value; // Retorna o valor dos enums

    TypeEnum(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

    public static TypeEnum getEnum(String value){
        for (TypeEnum typeEnums : values()){
            if (value.equals(typeEnums.getValue())) {
                return typeEnums;
            }
        }
        return  null;
    }
}
