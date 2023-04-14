package com.github.bartimaeusnek.MyMilightRemote;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import com.github.bartimaeusnek.MyMilightLib.*;
import com.github.bartimaeusnek.MyMilightRemote.Watcher.PersistentTextWatcher;

public class MainActivity extends AppCompatActivity {

    public MainActivity() {
    }

    private void setInputsPersitence(){
        EditText changeIp = (EditText) findViewById(R.id.editIp2);
        EditText changePort = (EditText) findViewById(R.id.editPort);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String preselectedIp = sharedPref.getString("preselectedIp", "");
        String preselectedPort = sharedPref.getString("preselectedPort", "8899");
        changeIp.setText(preselectedIp);
        changePort.setText(preselectedPort);

        changeIp.addTextChangedListener(new PersistentTextWatcher(sharedPref,"preselectedIp"));
        changePort.addTextChangedListener(new PersistentTextWatcher(sharedPref,"preselectedPort"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInputsPersitence();
        registerButtonBasicCommand(R.id.btnOn, BasicCommands.GROUP_ALL_ON);
        registerButtonBasicCommand(R.id.btnOff, BasicCommands.GROUP_ALL_OFF);
        registerButtonBasicCommand(R.id.btnWhite, BasicCommands.COLOR_WHITE);
        registerButtonBasicCommand(R.id.btnDisco, BasicCommands.DISCO);

        SeekBar brightnessBar = findViewById(R.id.disSeekBrightness);

        brightnessBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                byte brightnessValue = (byte) (brightnessBar.getProgress() + 2);
                NetworkingThread.sendPayloadTo(
                        getIp(),
                        getPort(),
                        new NetworkPayloadBuilder()
                                .addBasicCommand(BasicCommands.BRIGHTNESS_BIT)
                                .addICommandValue(new RawCommandValue(brightnessValue))
                );
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                NetworkingThread.sendPayloadTo(
                        getIp(),
                        getPort(),
                        new NetworkPayloadBuilder()
                                .addBasicCommand(BasicCommands.GROUP_ALL_ON)
                );
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        SeekBar colorBar = (SeekBar) findViewById(R.id.seekBar);
        colorBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                NetworkingThread.sendPayloadTo(
                        getIp(),
                        getPort(),
                        new NetworkPayloadBuilder()
                                .addBasicCommand(BasicCommands.COLOR_BIT)
                                .addICommandValue(ColorCommandValue.values()[colorBar.getProgress()])
                );
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                NetworkingThread.sendPayloadTo(
                        getIp(),
                        getPort(),
                        new NetworkPayloadBuilder()
                                .addBasicCommand(BasicCommands.GROUP_ALL_ON)
                );
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void registerButtonBasicCommand(@IdRes int id, BasicCommands basicCommand) {
        findViewById(id).setOnClickListener(view ->
                NetworkingThread.sendPayloadTo(
                        getIp(),
                        getPort(),
                        new NetworkPayloadBuilder().addBasicCommand(basicCommand)
                )
        );
    }

    private String getIp() {
        EditText input = findViewById(R.id.editIp2);
        Editable editable = input.getText();
        return editable.toString();
    }

    private int getPort() {
        EditText input = findViewById(R.id.editPort);
        Editable editable = input.getText();
        return Integer.parseInt(editable.toString());
    }
}