package com.e.quickquiz;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

class Card implements Serializable{
    String cardQuestion;
    String cardAnswer;

     Card(String newQuestion, String newAnswer) {
        cardQuestion = newQuestion;
        cardAnswer = newAnswer;
    }

    @NonNull
    @Override
    public String toString() {
        return "Q: " + this.getCardQuestion() + "\n" + "A: " + this.getCardAnswer();
    }

    void setCardQuestion(String newQuestion) {
        cardQuestion = newQuestion;
    }

    String getCardQuestion() {
        return cardQuestion;
    }

    void setCardAnswer(String newAnswer) {
        cardAnswer = newAnswer;
    }

    String getCardAnswer() {
        return cardAnswer;
    }

}

public class CardSet implements Serializable {
    private ArrayList<Card> cards; //ArrayList containing card objects
    private String cardSetName; //name of the CardSet

    private CardSet() {
        cardSetName = "";
        cards = new ArrayList<Card>();
    }

    private CardSet(String newCardSetName) {
        cardSetName = newCardSetName;
        cards = new ArrayList<Card>();
    }

    @NonNull
    @Override
    public String toString() {
        return this.getCardSetName();
    }


    private void setCardSetName(String newCardSetName) {
        cardSetName = newCardSetName;
    }

    private String getCardSetName() {
        return  cardSetName;
    }


    private void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    private ArrayList<Card> getCards() {
        return cards;
    }

    private void addCard(String newQuestion, String newAnswer) {
        Card newCard = new Card(newQuestion, newAnswer);
        cards.add(newCard);
    }
}
