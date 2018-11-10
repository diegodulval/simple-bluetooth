package demo.dulval.com.simplebluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOError;
import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    //inputs
    Button btnConexao, btnLed1, btnLed2, btnLed3;

    private static final int SOLICITA_ATIVACAO_BLUE = 1;
    private static final int SOLICITA_CONEXAO_BLUE = 2;

    BluetoothAdapter meuBlue = null;
    BluetoothDevice meuDevice = null;
    BluetoothSocket meuSocket = null;

    boolean conexao = false;

    private static String MAC =null;

    // Default UUID
    private UUID DEFAULT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConexao = (Button)findViewById(R.id.btnConexao);
        btnLed1 = (Button) findViewById(R.id.btnLed1);
        btnLed2 = (Button)findViewById(R.id.btnLed2);
        btnLed3 = (Button)findViewById(R.id.btnLed3);

        meuBlue = BluetoothAdapter.getDefaultAdapter();
        if(meuBlue == null){
            Toast.makeText(getApplicationContext(), "Seu dispositivo nao possui bluetooth", Toast.LENGTH_LONG).show();;
        }else if(!meuBlue.isEnabled()){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, SOLICITA_ATIVACAO_BLUE);
        }

        btnConexao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(conexao){
                    //desconect
                    try{
                        meuSocket.close();
                        Toast.makeText(getApplicationContext(), "Dispositivo desconectado!", Toast.LENGTH_LONG).show();;
                        conexao = false;
                        btnConexao.setText("CONECTAR");
                    }catch(IOException erro){
                        Toast.makeText(getApplicationContext(), "Erro ao desconectar!", Toast.LENGTH_LONG).show();;
                    }
                }else {
                    //connect
                    Intent abreLista = new Intent(MainActivity.this, ListaDispositivos.class);
                    startActivityForResult(abreLista, SOLICITA_CONEXAO_BLUE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case SOLICITA_ATIVACAO_BLUE:
                if(resultCode == Activity.RESULT_OK){
                    Toast.makeText(getApplicationContext(), "O Bluetooth foi ativado!", Toast.LENGTH_LONG).show();;
                }else {
                    Toast.makeText(getApplicationContext(), "Bluetooth nao conectado, aplicacao será finalizada!", Toast.LENGTH_LONG).show();;
                    finish();
                }
                break;
             case SOLICITA_CONEXAO_BLUE:
                 if(resultCode == Activity.RESULT_OK){
                     MAC = data.getExtras().getString(ListaDispositivos.ENDERECO_MAC);
                     meuDevice = meuBlue.getRemoteDevice(MAC);

                     try{
                         meuSocket = meuDevice.createRfcommSocketToServiceRecord(DEFAULT_UUID);
                         meuSocket.connect();
                         conexao = true;
                         btnConexao.setText("DESCONECTAR");
                         Toast.makeText(getApplicationContext(), "Dispositivo conectado!", Toast.LENGTH_LONG).show();;
                     }catch(IOException erro){
                         conexao = false;
                         Toast.makeText(getApplicationContext(), "Ocorreu um erro ao tentar conectar com o dispositivo selecionado!", Toast.LENGTH_LONG).show();;
                     }

                 }else {
                     Toast.makeText(getApplicationContext(), "Falha ao obter o MAC", Toast.LENGTH_LONG).show();;
                 }

        }
    }
}
