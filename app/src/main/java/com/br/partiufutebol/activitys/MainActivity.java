package com.br.partiufutebol.activitys;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.br.partiufutebol.R;
import com.br.partiufutebol.fragments.ChatFragment;
import com.br.partiufutebol.fragments.MapsFragment;
import com.br.partiufutebol.util.Util;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView txtNomeUsuario;
    TextView txtDiaSemana;
    ImageView imgPerfil;

    String matricula;
    String nomeUsuario;
    ProfilePictureView profilePictureView;


    FragmentManager fragmentManager;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRefLer;
    private DatabaseReference myRefGravar;
    private  FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        new Util().verificaPermissoes(this);
        fragmentManager = getSupportFragmentManager();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRefLer = firebaseDatabase.getReference();

        if(user == null){
            goLoginActivity();
        }else{
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            View navHeader = ((LayoutInflater)navigationView.getRootView().getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.nav_header_main2, null);

            navigationView.addHeaderView(navHeader);

            txtNomeUsuario = (TextView)navHeader.findViewById(R.id.txtNomeUsuario);
            txtDiaSemana = (TextView)navHeader.findViewById(R.id.txtDiaSemana);
            profilePictureView = (ProfilePictureView) navHeader.findViewById(R.id.friendProfilePicture);

                    if(user!=null){
                    nomeUsuario = user.getDisplayName();
                    matricula = user.getEmail();



                    }

                    txtNomeUsuario.setText("OLÁ "+nomeUsuario);
                    txtDiaSemana.setText("EMAIL: " + matricula);
                    profilePictureView.setProfileId(user.getProviderId());






        }




        myRefGravar = FirebaseDatabase.getInstance().getReference();


        myRefLer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
                });





    }

    private void goLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
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






    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            MapsFragment mapsFragment = new MapsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.rl_fragment_container, mapsFragment).commit();

        } else if (id == R.id.nav_gallery) {

            ChatFragment  chatFragment = new ChatFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.rl_fragment_container, chatFragment).commit();


        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
            goLoginActivity();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
