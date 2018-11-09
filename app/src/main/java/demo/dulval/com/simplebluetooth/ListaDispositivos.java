package demo.dulval.com.simplebluetooth;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.widget.ArrayAdapter;

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
}
