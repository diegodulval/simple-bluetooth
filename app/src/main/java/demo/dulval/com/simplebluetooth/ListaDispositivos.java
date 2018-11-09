package demo.dulval.com.simplebluetooth;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class ListaDispositivos extends ListActivity {

    BluetoothAdapter meuBlue = null;

    static String ENDERECO_MAC = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter<String> arrayBluetooth = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        meuBlue = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> dispositivosPareados = meuBlue.getBondedDevices();

        if(dispositivosPareados.size() > 0){
            for(BluetoothDevice dispositivo : dispositivosPareados){
                String nomeBt = dispositivo.getName();
                String macBt = dispositivo.getAddress();
                arrayBluetooth.add(nomeBt + "\n" + macBt);
            }
        }
        setListAdapter(arrayBluetooth);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String informacaoGeral = ((TextView) v).getText().toString();
        //Toast.makeText(getApplicationContext(), "Ïnfo: "+ informacaoGeral, Toast.LENGTH_LONG).show();
        String enderecoMac = informacaoGeral.substring(informacaoGeral.length() - 17);

        Intent retornaMac = new Intent();
        retornaMac.putExtra(ENDERECO_MAC, enderecoMac);
        setResult(RESULT_OK, retornaMac);
        finish();
    }
}
