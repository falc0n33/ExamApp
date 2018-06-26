package com.falc0n.android.examapp;


import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        setupDrawerContent(navigationView);
        try {
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.frame_content, (Fragment) (MainFragment.class).newInstance()).commit();
        } catch (InstantiationException | IllegalAccessException e) {
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    public void selectItemDrawer(MenuItem item) {
        Fragment fragment = null;
        Class fragmentClass = null;
        switch (item.getItemId()) {
            case R.id.rsa:
                fragmentClass = RSAFragment.class;
                break;
            case R.id.rsa_sig:
                fragmentClass = RSASignatureFragment.class;
                break;
            case R.id.legendre:
                fragmentClass = LegendreFragment.class;
                break;
            case R.id.mod:
                fragmentClass = ModFragment.class;
                break;
            case R.id.galois:
                fragmentClass = GaloisFragment.class;
                break;
            case R.id.fourier:
                fragmentClass = FourierFragment.class;
                break;
            case R.id.figure:
                fragmentClass = FigureFragment.class;
                break;
            case R.id.curve:
                fragmentClass = EllipticCurveFragment.class;
                break;
            case R.id.el_gamal:
                fragmentClass = ElGamalFragment.class;
                break;
            case R.id.el_gamal_sig:
                fragmentClass = ElGamalSignatureFragment.class;
                break;
            case R.id.diffi_hellman:
                fragmentClass = DiffiHellmanFragment.class;
                break;
            case R.id.asmuth_bloom_encrypt:
                fragmentClass = AsmuthBloomEnctyptFragment.class;
                break;
            case R.id.asmuth_bloom_decrypt:
                fragmentClass = AsmuthBloomDecryptFragment.class;
                break;
            case R.id.china_theorem:
                fragmentClass = ChinaTheoremFragment.class;
                break;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frame_content, fragment).commit();
        item.setChecked(true);
        setTitle(item.getTitle());
        mDrawerLayout.closeDrawers();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItemDrawer(item);
                return false;
            }
        });
    }
}
