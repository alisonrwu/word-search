package com.wu.alison.wordsearch;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WordSearchActivity extends AppCompatActivity {

    private GridView letterBoard;
    private TextView textSelected;
    private static int numColumns = 10;
    private ArrayList<Integer> selectedPositions;
    private String orientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textSelected = (TextView) findViewById(R.id.textSelected);

        letterBoard = (GridView) findViewById(R.id.gridView);
        letterBoard.setAdapter(new LetterAdapter(this));
        letterBoard.setNumColumns(numColumns);

        selectedPositions = new ArrayList<Integer>();

        letterBoard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                //deselect
                if (selectedPositions.contains(position)) {
                    v.setBackgroundColor(Color.TRANSPARENT);
                    Toast.makeText(WordSearchActivity.this, "Deselected " + position, Toast.LENGTH_SHORT).show();

                    int index=-1;
                    for (int i=0; i<selectedPositions.size(); i++) {
                        if(selectedPositions.get(i).equals(position)) {
                            index = i;
                        }
                    }
                    if(index != -1) {
                        selectedPositions.remove(index);
                    } else {
                        Toast.makeText(WordSearchActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }

                }
                //select
                else if(selectedPositions.isEmpty()
                        || (selectedPositions.size() == 1 && isAdjacentToAll(position))) {
                    if(selectedPositions.size() == 1) {
                        orientation = checkCurrentOrientation(position);
                    }
                    v.setBackgroundColor(Color.GREEN);
                    Toast.makeText(WordSearchActivity.this, "Selected " + position, Toast.LENGTH_SHORT).show();
                    selectedPositions.add(position);
                } else if(selectedPositions.size() >= 2 && checkAllowed(position)) {
                    v.setBackgroundColor(Color.GREEN);
                    Toast.makeText(WordSearchActivity.this, "Selected " + position, Toast.LENGTH_SHORT).show();
                    selectedPositions.add(position);
                } else {
                    //do nothing
                }

                //display selected letters
                String printThis = "";
                for(int i=0; i<selectedPositions.size(); i++) {
                    printThis += LetterAdapter.getLetter(selectedPositions.get(i));
                }
                textSelected.setText(printThis);
            }
        });

    }

    private static boolean isAdjacent(int a, int b) {
        int ax = a % numColumns, ay = a / numColumns, bx = b % numColumns, by = b / numColumns;
        return Math.abs(ax - bx) <= 1 && Math.abs(ay - by) <= 1;
    }

    private boolean isAdjacentToAll(int position) {
        boolean adjacent = false;
        for(Integer next: selectedPositions) {
            if(isAdjacent(next, position)) {
                adjacent = true;
                break;
            }
        }
        return adjacent;
    }

    private boolean checkAllowed(int position) {
        int check = 0;
        switch (orientation) {
            case "horizontal":
                check = 1;
                break;
            case "vertical":
                check = numColumns;
                break;
            case "diagonalDown":
                check = numColumns+1;
                break;
            case "diagonalUp":
                check = numColumns-1;
                break;
            default:
                Toast.makeText(WordSearchActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }

        boolean allow = false;
        for(Integer next: selectedPositions) {
            if(Math.abs(next - position) == check) {
                allow = true;
                break;
            }
        }
        return allow;
    }

    private String checkCurrentOrientation(int position) {
        int test = Math.abs(selectedPositions.get(selectedPositions.size()-1) - position);

        if(test == 1) {
            orientation = "horizontal";
        } else if(test == numColumns) {
            orientation = "vertical";
        } else if(test == numColumns+1) {
            orientation = "diagonalDown";
        } else if(test == numColumns-1) {
            orientation = "diagonalUp";
        } else {
            Toast.makeText(WordSearchActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }
        return orientation;
    }
}
