package com.pomocnikprogramisty.ftpfilecreation;

class Entry {

    private String field;
    private String anotherField;

    Entry(String field, String anotherField) {
        this.field = field;
        this.anotherField = anotherField;
    }

    static String generateHeader(){
        return "FIELD,ANOTHER_FIELD\n";
    }

    String generateRow(){
        return this.field + ',' + this.anotherField + "\n";
    }
}
