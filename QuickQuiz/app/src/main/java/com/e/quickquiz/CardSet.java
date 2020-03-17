package com.e.quickquiz;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

class Card implements Serializable{
    String cardQuestion;
    String cardAnswer;

    public Card(String newQuestion, String newAnswer) {
        cardQuestion = newQuestion;
        cardAnswer = newAnswer;
    }

    @NonNull
    @Override
    public String toString() {
        return "Q: " + this.getCardQuestion() + "\n" + "A: " + this.getCardAnswer();
    }

    public void setCardQuestion(String newQuestion) {
        cardQuestion = newQuestion;
    }

    public String getCardQuestion() {
        return cardQuestion;
    }

    public void setCardAnswer(String newAnswer) {
        cardAnswer = newAnswer;
    }

    public String getCardAnswer() {
        return cardAnswer;
    }

}

public class CardSet implements Serializable {
    int numOfCards; //number of cards in the set
    ArrayList<Card> cards; //ArrayList containing card objects
    String cardSetName; //name of the CardSet

    public CardSet() {
        cardSetName = "";
        numOfCards = 0;
        cards = new ArrayList<Card>();
    }

    public CardSet(String newCardSetName) {
        cardSetName = newCardSetName;
        numOfCards = 0;
        cards = new ArrayList<Card>();
    }

    @NonNull
    @Override
    public String toString() {
        return this.getCardSetName();
    }

    public int getNumOfCards() {
        return numOfCards;
    }

    public void setCardSetName(String newCardSetName) {
        cardSetName = newCardSetName;
    }

    public String getCardSetName() {
        return  cardSetName;
    }

    public void setNumOfCards(int numOfCards) {
        this.numOfCards = numOfCards;
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
