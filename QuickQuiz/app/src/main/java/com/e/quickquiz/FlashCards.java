package com.e.quickquiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FlashCards extends AppCompatActivity {
    private TextView cardSetNameTextView;
    private Button addCardButton;
    ListView flashCardListView;
    ArrayAdapter<Card> flashCardListViewAdapter;
    CardSet currentCardSet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_cards);

        Intent intent = getIntent();
        currentCardSet = (CardSet) intent.getSerializableExtra("current card set");


        cardSetNameTextView = (TextView) findViewById(R.id.flashcard_set_name_TextView);
        cardSetNameTextView.setText(currentCardSet.getCardSetName());


        flashCardListView = (ListView) findViewById(R.id.flashcard_ListView);
        flashCardListViewAdapter = new ArrayAdapter<Card>(FlashCards.this,android.R.layout.simple_list_item_1, currentCardSet.cards);
        flashCardListView.setAdapter(flashCardListViewAdapter);

        cardSetNameTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                show_edit_card_set_dialog(v);
                return true;
            }
        });

        flashCardListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                show_card_set_options_dialog(parent, position);
                return true;
            }
        });




    }



    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.putExtra("result", currentCardSet);
        setResult(Activity.RESULT_OK, intent);
        finish();
        super.onBackPressed();
    }

    public void show_edit_card_set_dialog(View view) {
        final AlertDialog.Builder editCardSetDialog = new AlertDialog.Builder(FlashCards.this);
        View mView = getLayoutInflater().inflate(R.layout.edit_card_set_dialog, null);
        final EditText editCardSetEditText = (EditText) mView.findViewById(R.id.edit_card_set_EditText);
        Button cancel_btn = (Button) mView.findViewById(R.id.edit_card_set_btn_cancel);
        Button ok_btn = (Button) mView.findViewById(R.id.edit_card_set_btn_ok);

        editCardSetDialog.setView(mView);

        final AlertDialog alertDialog = editCardSetDialog.create();

        alertDialog.setCanceledOnTouchOutside(false);

        editCardSetEditText.setText(currentCardSet.getCardSetName());

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCardSet.setCardSetName(editCardSetEditText.getText().toString());
                cardSetNameTextView.setText(currentCardSet.getCardSetName());
                alertDialog.dismiss();

            }
        });

        alertDialog.show();

    }

    public void show_card_set_options_dialog(View view, final int position) {
        final AlertDialog.Builder editCardSetDialog = new AlertDialog.Builder(FlashCards.this);
        View mView = getLayoutInflater().inflate(R.layout.card_set_options_dialog, null);
        Button btnEdit = (Button) mView.findViewById(R.id.card_set_options_btn_edit);
        Button btnDelete = (Button) mView.findViewById(R.id.card_set_options_btn_delete);
        Button btnCancel = (Button) mView.findViewById(R.id.card_set_options_btn_cancel);

        editCardSetDialog.setView(mView);

        final AlertDialog alertDialog = editCardSetDialog.create();

        alertDialog.setCanceledOnTouchOutside(false);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCardSet.cards.remove(position);
                flashCardListViewAdapter.notifyDataSetChanged();
                alertDialog.dismiss();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_edit_flashcard_dialog(v, position);
                alertDialog.dismiss();
            }
        });



        alertDialog.show();

    }

    public void show_edit_flashcard_dialog(View view) {
        final AlertDialog.Builder editFlashcardDialog = new AlertDialog.Builder(FlashCards.this);
        View mView = getLayoutInflater().inflate(R.layout.edit_flashcard_dialog, null);
        final EditText editQuestionEditText = (EditText) mView.findViewById(R.id.edit_card_question_EditText);
        final EditText editAnswerEditText = (EditText) mView.findViewById(R.id.edit_card_answer_EditText);
        Button cancel_btn = (Button) mView.findViewById(R.id.edit_flashcard_btn_cancel);
        Button ok_btn = (Button) mView.findViewById(R.id.edit_flashcard_btn_ok);

        editFlashcardDialog.setView(mView);

        final AlertDialog alertDialog = editFlashcardDialog.create();

        alertDialog.setCanceledOnTouchOutside(false);

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Card newCard = new Card(editQuestionEditText.getText().toString(),editAnswerEditText.getText().toString());
                currentCardSet.cards.add(newCard);
                alertDialog.dismiss();

            }
        });

        alertDialog.show();

    }

    public void show_edit_flashcard_dialog(View view, final int position) {
        final AlertDialog.Builder editFlashcardDialog = new AlertDialog.Builder(FlashCards.this);
        View mView = getLayoutInflater().inflate(R.layout.edit_flashcard_dialog, null);
        final EditText editQuestionEditText = (EditText) mView.findViewById(R.id.edit_card_question_EditText);
        final EditText editAnswerEditText = (EditText) mView.findViewById(R.id.edit_card_answer_EditText);
        Button cancel_btn = (Button) mView.findViewById(R.id.edit_flashcard_btn_cancel);
        Button ok_btn = (Button) mView.findViewById(R.id.edit_flashcard_btn_ok);

        editFlashcardDialog.setView(mView);

        final AlertDialog alertDialog = editFlashcardDialog.create();

        alertDialog.setCanceledOnTouchOutside(false);

        editQuestionEditText.setText(currentCardSet.cards.get(position).getCardQuestion());
        editAnswerEditText.setText(currentCardSet.cards.get(position).getCardAnswer());

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCardSet.cards.get(position).setCardQuestion(editQuestionEditText.getText().toString());
                currentCardSet.cards.get(position).setCardAnswer((editAnswerEditText.getText().toString()));
                flashCardListViewAdapter.notifyDataSetChanged();
                alertDialog.dismiss();

            }
        });

        alertDialog.show();

    }


}
