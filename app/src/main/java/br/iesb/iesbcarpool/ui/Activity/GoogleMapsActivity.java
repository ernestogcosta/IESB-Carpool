package br.iesb.iesbcarpool.ui.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import br.iesb.iesbcarpool.R;

import static br.iesb.iesbcarpool.Constants.ERROR_DIALOG_REQUEST;
import static br.iesb.iesbcarpool.Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static br.iesb.iesbcarpool.Constants.PERMISSIONS_REQUEST_ENABLE_GPS;

public class GoogleMapsActivity extends AppCompatActivity {

    private static final String TAG = "GoogleMapsActivity";
    private boolean mLocationPermissionGranted = false;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.google_maps_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUsuarios();

        /*Se o GPS está ativado, então pergunta se a permissão de fine location é true, se sim, normal
        * se não roda o método getLocationPermission para pedir pela permissão de fine location*/
        if(checkMapServices()){
            if(mLocationPermissionGranted){
                getUsuarios();
            }else{
                getLocationPermission();
            }
        }
    }

    private void getUsuarios() {
        Toast.makeText(GoogleMapsActivity.this,"Funcionou",Toast.LENGTH_LONG).show();
    }

    /*Primeiro roda o isServicesOk para saber se Google Services pode ser utilizado, se sim, então
    * é rodado o isMapsEnabled para saber se o GPS está ativado. Se tudo voltar verdadeiro,
    * return true, se não, return false.*/
    private boolean checkMapServices(){
        if(isServicesOK()){
            if(isMapsEnabled()){
                return true;
            }
        }
        return false;
    }

    /*É mostrada uma mensagem falando que o GPS é necessário e perguntando se ele quer ativá-lo
    * Se clicar em sim, uma nova intent irá mandar o usuário para a tela de configurações do celular
    * onde podem ativar o gps para esta aplicação, ou também podem deixar desativado
    * É usado o startActivityForResult porque é necessário saber se o usuário aceitou ou recusou as
    * permissões
    * Após receber o resultado, irá rodar OnActivityResult()*/
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Está aplicação precisa do GPS para funcionar corretamente, você deseja ativá-lo?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    /*Verificar se o GPS está ativado, vê se a atividade atual que o usuário está utilizando tem
    * o GPS ativado no celular. Se tiver, retorna true, se não tiver, retorna false.
    * Caso retorne false, também irá chamar um método buildAlertMessageNoGps().*/
    public boolean isMapsEnabled(){
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    /*Requisição de uma permissão para utilizar a localização, para assim ser possível pegar a
    * localização do celular. Inicialmente é verificado se a permissão para acessar a Fine Location
    * já havia sido aceita anteriormente. Caso sim, mLocationPermissionGranted vira true, e então
    * pode-se usar a aplicação normalmente, se não aceitaram anteriormente, é pedido para aceitarem
    * onde um dialog vai aparecer perguntando se é ok usar a permission*/
    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            getUsuarios();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    /*Método com o objetivo de verificar se o Google Play Services está instalado no celular
    * Se não puder usar o Google Services, é mostrado um dialog
    * Retorna true se ele é utilizável e false se não estiver*/
    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checando versao do google play");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(GoogleMapsActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //Tudo está ok e o usuário pode fazer requisições de mapa
            Log.d(TAG, "isServicesOK: Google Play Services esta funcionando");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //Um erro ocorreu, mas podemos resolvê-lo
            Log.d(TAG, "isServicesOK: um erro ocorreu mas podemos consertar");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(GoogleMapsActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "Você não pode fazer requisições de mapa.", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    /*Este método irá rodar depois do usuário ter aceito ou recusado as permissões do else do método
    * getLocationPermission(). Lá é pedido um PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION, e aqui é
    * checado por ele. Se o resultado do grantResults for maior que 0, então temos alguns resultados
    * e também é checado pra ver quais resultados são esses. Se foi PERMISSION_GRANTED, sabemos que
    * foi aceito a permissão de fine location, então o mLocationPermissionGranted vira true*/
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    /*Aqui é checado pela constante PERMISSIONS_REQUEST_ENABLE_GPS. Caso o usuário tenha aceitou as
    * permissões, aí entramos num if. SE mLocationPermissionGranted é true, é resolvido normalmente,
    * se não, é necessário pegar as permissões da localização*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: called.");
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if(mLocationPermissionGranted){
                    getUsuarios();
                }
                else{
                    getLocationPermission();
                }
            }
        }

    }
}
