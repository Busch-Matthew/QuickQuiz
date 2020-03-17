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
    ArrayList<Card> cards; //ArrayList containing card objects
    String cardSetName; //name of the CardSet

    public CardSet() {
        cardSetName = "";
        cards = new ArrayList<Card>();
    }

    public CardSet(String newCardSetName) {
        cardSetName = newCardSetName;
        cards = new ArrayList<Card>();
    }

    @NonNull
    @Override
    public String toString() {
        return this.getCardSetName();
    }


    public void setCardSetName(String newCardSetName) {
        cardSetName = newCardSetName;
    }

    public String getCardSetName() {
        return  cardSetName;
    }


    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void addCard(String newQuestion, String newAnswer) {
        Card newCard = new Card(newQuestion, newAnswer);
        cards.add(newCard);
    }
}
