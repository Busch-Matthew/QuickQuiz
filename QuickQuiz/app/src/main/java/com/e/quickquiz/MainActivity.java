package com.e.quickquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView testTextView;
    private Button addCardSetButton;
    ListView cardSetListView;
    ArrayList<String> cardSetItems = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testTextView = (TextView) findViewById(R.id.test_textview);
        addCardSetButton = (Button) findViewById(R.id.add_card_set_button);

        addCardSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });


    }

    public void openDialog() {
        EditCardSetDialog editCardSetDialog = new EditCardSetDialog();
        editCardSetDialog.show(getSupportFragmentManager(), "edit card set dialog");

    }
}
