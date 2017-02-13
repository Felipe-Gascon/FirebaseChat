package com.example.felip.firebasechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private static int codigo_Acceso = 1;
    private FirebaseListAdapter<Mensajes> adaptador;
    RelativeLayout activity_main;
    FloatingActionButton fab;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_sign_out) {
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Snackbar.make(activity_main, "Haz cerrado el chat", Snackbar.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
        return true;
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == codigo_Acceso) {
            if (resultCode == RESULT_OK) {
                Snackbar.make(activity_main, "Successfully signed in.Welcome!", Snackbar.LENGTH_SHORT).show();
                muestraChat();
            } else {
                Snackbar.make(activity_main, "We couldn't sign you in.Please try again later", Snackbar.LENGTH_SHORT).show();
                finish();
            }
        }
    }



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity_main = (RelativeLayout) findViewById(R.id.activity_main);
        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText et_envia =(EditText)findViewById(R.id.et_msje);


                //obtenemos los datos del usuario
                FirebaseDatabase.getInstance().getReference().push().setValue(new Mensajes(et_envia.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                et_envia.setText("");
            }
        });

        //comprobamos si ha iniciado secion correctamente
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), codigo_Acceso);
        } else {
            Snackbar.make(activity_main, "Hola " + FirebaseAuth.getInstance().getCurrentUser().getEmail(), Snackbar.LENGTH_SHORT).show();
        // cargamos el contenido
            muestraChat();
        }
    }

    private void muestraChat() {

        ListView mensajes = (ListView) findViewById(R.id.lv_msj);
        adaptador = new FirebaseListAdapter<Mensajes>(this, Mensajes.class, R.layout.list_item, FirebaseDatabase.getInstance().getReference()) {
            @Override
            protected void populateView(View v, Mensajes model, int position) {
                TextView textoMEnsaje, mensajeUsuario, horaMensaje;
                textoMEnsaje = (TextView) v.findViewById(R.id.message_text);
                mensajeUsuario = (TextView) v.findViewById(R.id.message_user);
                horaMensaje = (TextView) v.findViewById(R.id.message_time);


                textoMEnsaje.setText(model.getMensajeTexto());
                mensajeUsuario.setText(model.getUsuarioMensaje());
                horaMensaje.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMensajeHora()));


            }
        };
        mensajes.setAdapter(adaptador);
    }
}