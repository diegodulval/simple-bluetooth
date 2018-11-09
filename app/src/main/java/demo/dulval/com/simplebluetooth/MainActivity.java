package demo.dulval.com.simplebluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //inputs
    Button btnConexao, btnLed1, btnLed2, btnLed3;

    private static final int SOLICITA_ATIVACAO_BLUE = 1;
    private static final int SOLICITA_CONEXAO_BLUE = 2;
    BluetoothAdapter meuBlue = null;
    boolean conexao = false;

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
        }
    }
}
