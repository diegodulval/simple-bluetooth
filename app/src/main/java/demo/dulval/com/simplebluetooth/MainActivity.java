package demo.dulval.com.simplebluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int ATIVA_BLUE = 1;
    BluetoothAdapter meuBlue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        meuBlue = BluetoothAdapter.getDefaultAdapter();
        if(meuBlue == null){
            Toast.makeText(getApplicationContext(), "Seu dispositivo nao possui bluetooth", Toast.LENGTH_LONG).show();;
        }else if(!meuBlue.isEnabled()){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, ATIVA_BLUE);
        }
    }
}
