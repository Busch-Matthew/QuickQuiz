package com.e.quickquiz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
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

public class MainActivity extends AppCompatActivity {

    private Button addCardSetButton;
    ListView cardSetListView;
    ArrayList<CardSet> cardSetItems;
    ArrayAdapter<CardSet> cardSetItemsAdapter;
    int cardSetListPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Load_Data();
        cardSetListView = (ListView) findViewById(R.id.card_set_ListView);
        cardSetItemsAdapter = new ArrayAdapter<CardSet>(MainActivity.this, android.R.layout.simple_list_item_1, cardSetItems);
        cardSetListView.setAdapter(cardSetItemsAdapter);

        cardSetListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                show_card_set_options_dialog(parent, position);
                return true;
            }
        });

        cardSetListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, FlashCardQuiz.class);
                intent.putExtra("current card set", cardSetItems.get(position));
                startActivity(intent);
            }
        });

    }

    private void Save_Data() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cardSetItems);
        editor.putString("card set list", json);
        editor.apply();
    }

    private void Load_Data() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("card set list", null);
        Type type = new TypeToken<ArrayList<CardSet>>() {}.getType();
        cardSetItems = gson.fromJson(json, type);

        if(cardSetItems == null) {
            cardSetItems = new ArrayList<>();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        Save_Data();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    CardSet currentCardSet = (CardSet) bundle.getSerializable("result");
                    cardSetItems.get(cardSetListPosition).setCards(currentCardSet.getCards());
                    cardSetItems.get(cardSetListPosition).setCardSetName(currentCardSet.getCardSetName());
                    cardSetItemsAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    public void show_edit_card_set_dialog(View view) {
        final AlertDialog.Builder editCardSetDialog = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.edit_card_set_dialog, null);
        final EditText editCardSetEditText = (EditText) mView.findViewById(R.id.edit_card_set_EditText);
        Button cancel_btn = (Button) mView.findViewById(R.id.edit_card_set_btn_cancel);
        Button ok_btn = (Button) mView.findViewById(R.id.edit_card_set_btn_ok);

        editCardSetDialog.setView(mView);

        final AlertDialog alertDialog = editCardSetDialog.create();

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
                CardSet newCardSet = new CardSet(editCardSetEditText.getText().toString());
                cardSetItems.add(newCardSet);
                alertDialog.dismiss();

            }
        });

        alertDialog.show();

    }

    public void show_card_set_options_dialog(View view, final int position) {
        final AlertDialog.Builder editCardSetDialog = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.card_set_options_dialog, null);
        Button btnEditCardSet = (Button) mView.findViewById(R.id.card_set_options_btn_edit);
        Button btnDeleteCardSet = (Button) mView.findViewById(R.id.card_set_options_btn_delete);
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

        btnDeleteCardSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardSetItems.remove(position);
                cardSetItemsAdapter.notifyDataSetChanged();
                alertDialog.dismiss();
            }
        });

        btnEditCardSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardSetListPosition = position;
                Intent intent = new Intent(MainActivity.this, FlashCards.class);
                intent.putExtra("current card set", cardSetItems.get(position));
                startActivityForResult(intent, 1);
                alertDialog.dismiss();
            }
        });



        alertDialog.show();

    }
}
