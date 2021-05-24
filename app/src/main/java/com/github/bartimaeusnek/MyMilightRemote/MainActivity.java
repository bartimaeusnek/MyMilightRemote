package com.github.bartimaeusnek.MyMilightRemote;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import lombok.var;

public class MainActivity extends AppCompatActivity {

    public MainActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerButtonBasicCommand(R.id.btnOn, BasicCommands.GROUP_ALL_ON);
        registerButtonBasicCommand(R.id.btnOff, BasicCommands.GROUP_ALL_OFF);
        registerButtonBasicCommand(R.id.btnWhite, BasicCommands.COLOR_WHITE);
        registerButtonBasicCommand(R.id.btnDisco, BasicCommands.DISCO);

        var input = (SeekBar) findViewById(R.id.seekBar);
        input.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                NetworkingThread.request(
                        getIp(),
                        getPort(),
                        BasicCommands.COLOR_BIT,
                        ColorPayloads.values()[input.getProgress()]
                );
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                NetworkingThread.request(
                        getIp(),
                        getPort(),
                        BasicCommands.GROUP_ALL_ON,
                        ColorPayloads.none
                );
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }

    private void registerButtonBasicCommand(@IdRes int id, BasicCommands basicCommand) {
        findViewById(id).setOnClickListener(view ->
                NetworkingThread.request(
                        getIp(),
                        getPort(),
                        basicCommand,
                        ColorPayloads.none
                )
        );
    }

    private String getIp() {
        var input = (EditText) findViewById(R.id.editIp2);
        var editable = input.getText();
        return editable.toString();
    }

    private int getPort() {
        var input = (EditText) findViewById(R.id.editPort);
        var editable = input.getText();
        return Integer.parseInt(editable.toString());
    }
}