package com.example.petpal.ui.payment;

public class CreditCard {
    String textViewCardNumber,textViewexpirecvc,textViewexpireDate;
    String cardId;

    public CreditCard(String textViewCardNumber, String textViewexpirecvc, String textViewexpireDate) {
        this.textViewCardNumber = textViewCardNumber;
        this.textViewexpirecvc = textViewexpirecvc;
        this.textViewexpireDate = textViewexpireDate;

    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public CreditCard() {

    }

    public String getTextViewCardNumber() {
        return textViewCardNumber;
    }

    public void setTextViewCardNumber(String textViewCardNumber) {
        this.textViewCardNumber = textViewCardNumber;
    }

    public String getTextViewexpirecvc() {
        return textViewexpirecvc;
    }

    public void setTextViewexpirecvc(String textViewexpirecvc) {
        this.textViewexpirecvc = textViewexpirecvc;
    }

    public String getTextViewexpireDate() {
        return textViewexpireDate;
    }

    public void setTextViewexpireDate(String textViewexpireDate) {
        this.textViewexpireDate = textViewexpireDate;
    }
}
