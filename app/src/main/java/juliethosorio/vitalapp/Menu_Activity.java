package juliethosorio.vitalapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;


public class Menu_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
        ,MenuFragment.OnFragmentInteractionListener,Historial_IncidenciasFragment.OnFragmentInteractionListener {

    MenuFragment menuFragment;
    Historial_IncidenciasFragment miHistorial;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_);

        menuFragment= new MenuFragment();
        miHistorial=new Historial_IncidenciasFragment();

        /*---------------------------------------------------------*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().add(R.id.contenedorMenuFragments,menuFragment).commit();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.salir:
               Intent irLogin= new Intent(this,LoginActivity.class);
                startActivity(irLogin);
                break;
            case R.id.ayuda:
                break;
            case R.id.acercaDe:
                break;
        }
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragmento=null;
        boolean seleccion=false;
        int id = item.getItemId();
        UsuarioVO usuarioVO2= (UsuarioVO)getIntent().getSerializableExtra("usuario");

        if (id == R.id.miPerfil) {
          Intent perfil= new Intent(Menu_Activity.this,PerfilActivity.class);

            Bundle usuarioperfilbundle=new Bundle();
            usuarioperfilbundle.putSerializable("usuario",usuarioVO2);
            perfil.putExtras(usuarioperfilbundle);

            startActivity(perfil);

        } else if (id == R.id.profesionalSalud) {

            Intent profesional=new Intent(Menu_Activity.this,ProfesionalActivity.class);
            startActivity(profesional);

        } else if (id == R.id.historialIncidencias) {
            fragmento= new Historial_IncidenciasFragment();
            seleccion=true;
        }
        if(seleccion){
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedorMenuFragments,fragmento).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClick(View view)
    {
        UsuarioVO usuarioVO2= (UsuarioVO)getIntent().getSerializableExtra("usuario");
        switch (view.getId()){

            case R.id.btnCrearQR:
                Intent intent= new Intent(this,RegistroUsuarioActivity.class);
                startActivity(intent);
                break;

            case R.id.btnDependiente:
                Intent intentdependiente= new Intent(this,ListaDependientesActivity.class);

                Bundle usuarioperfildependientebundle=new Bundle();
                usuarioperfildependientebundle.putSerializable("usuario",usuarioVO2);
                intentdependiente.putExtras(usuarioperfildependientebundle);

                startActivity(intentdependiente);

                break;
            case R.id.btnGrupos:
                Intent intentgrupo= new Intent(this,GruposActivity.class);

                Bundle usuarioperfilgrupobundle=new Bundle();
                usuarioperfilgrupobundle.putSerializable("usuario",usuarioVO2);
                intentgrupo.putExtras(usuarioperfilgrupobundle);

                startActivity(intentgrupo);

                break;
            case R.id.btnCompras:
                Intent intentcompras= new Intent(Menu_Activity.this,TiendaVitalActivity.class);

                Bundle usuarioperfiltiendabundle=new Bundle();
                usuarioperfiltiendabundle.putSerializable("usuario",usuarioVO2);
                intentcompras.putExtras(usuarioperfiltiendabundle);

                startActivity(intentcompras);

                break;
            case R.id.btnMenuQR:
                Intent intentmenuQR=new Intent(this,RegistroUsuarioActivity.class);
                startActivity(intentmenuQR);
                break;
            case R.id.btnMenuGrupos:
                Intent intentmenugrupo=new Intent(this,GruposActivity.class);
                startActivity(intentmenugrupo);
                break;
            case R.id.btnMenuDependiente:
               Intent intentmenudependiente=new Intent(this, ListaDependientesActivity.class);
                startActivity(intentmenudependiente);
                break;
            case R.id.btnMenuCompras:
                Intent intentmenucompras=new Intent(this,TiendaVitalActivity.class);
                startActivity(intentmenucompras);
                break;
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


// metodo para llamar la url de conexion

    private String downloadUrl(String miurl) throws IOException {
        InputStream inputStream=null;

        int len=500;

        try {
            URL url=new URL(miurl);
            HttpURLConnection conexion=(HttpURLConnection)url.openConnection();
            conexion.setReadTimeout(10000);
            conexion.setConnectTimeout(15000);
            conexion.setRequestMethod("GET");
            conexion.setDoInput(true);

            conexion.connect();

            int respuesta= conexion.getResponseCode();
            Log.d("respuesta","La respuesta es: "+respuesta);

            inputStream=conexion.getInputStream();

            String contentAsString= readIt(inputStream, len);
            return contentAsString;
        }finally {
            if (inputStream!=null){
                inputStream.close();
            }
        }
    }


    //metodo para consultar datos usuario
    private class consultarDatos extends AsyncTask<String, Void,String> {

        @Override
        protected String doInBackground(String... urls) {
            try{
                return downloadUrl(urls[0]);
            }catch (IOException e){
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        protected void onPostExecute(String resultado){
            JSONArray jsonArray=null;
            TextView id,nom,fecha,tipo,eps,correo,tel,dir,contact,telcon;

            id=(TextView)findViewById(R.id.campoIdPerfil);
            nom=(TextView)findViewById(R.id.campoNombresPerfil);
            fecha=(TextView)findViewById(R.id.campoFechaNacimientoPerfil);
            tipo= (TextView) findViewById(R.id.campotipoSangrePerfil);
            eps= (TextView) findViewById(R.id.campoEPSperfil);
            correo= (TextView) findViewById(R.id.campoCorreoPerfil);
            tel= (TextView) findViewById(R.id.campotelefonoPerfil);
            dir= (TextView) findViewById(R.id.campoDireccionPerfil);
            contact= (TextView) findViewById(R.id.campoContactoPerfil);
            telcon= (TextView) findViewById(R.id.campotelContactoPerfil);

            try {
                jsonArray=new JSONArray(resultado);
                id.setText(jsonArray.getString(0));
                nom.setText(jsonArray.getString(1));
                fecha.setText(jsonArray.getString(2));
                tipo.setText(jsonArray.getString(3));
                eps.setText(jsonArray.getString(4));
                correo.setText(jsonArray.getString(5));
                tel.setText(jsonArray.getString(6));
                dir.setText(jsonArray.getString(7));
                contact.setText(jsonArray.getString(8));
                telcon.setText(jsonArray.getString(9));

            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    //lee el inputstream y lo convierte en un string

    private String readIt(InputStream inputStream, int len)
            throws IOException, UnsupportedEncodingException {
        Reader leer=null;
        leer=new InputStreamReader(inputStream, "UTF-8");
        char[] buffer=new char[len];
        leer.read(buffer);
        return new String(buffer);
    }

}
