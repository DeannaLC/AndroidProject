package edu.vassar.cmpu203.high_noon_heist.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.vassar.cmpu203.high_noon_heist.R;
import edu.vassar.cmpu203.high_noon_heist.model.PlayerList;

public class MainActivity extends AppCompatActivity {

    int curDay = 0;
    int dayLim;
    int curMoney = 0;
    int moneyLim;
    int playerCount;
    private PlayerList players;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}