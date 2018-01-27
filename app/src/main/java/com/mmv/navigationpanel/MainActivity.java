package com.mmv.navigationpanel;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.mmv.navigationpanel.alarma.AlarmaUtils;
import com.mmv.navigationpanel.jobscheduler.MyJobService;
import com.mmv.navigationpanel.servicio.RSSPullService;
import com.mmv.navigationpanel.servicio.RSSPullStateReceiver;


public class MainActivity extends AppCompatActivity {

    private Toolbar appbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appbar = (Toolbar)findViewById(R.id.appbar);
        setSupportActionBar(appbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        /*
        //Eventos del Drawer Layout
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        */

        navView = (NavigationView)findViewById(R.id.navview);
        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        boolean fragmentTransaction = false;
                        Fragment fragment = null;

                        switch (menuItem.getItemId()) {
                            case R.id.menu_seccion_1:
                                fragment = new Fragment1();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_seccion_2:
                                fragment = new Fragment2();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_seccion_3:
                                fragment = new Fragment3();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_opcion_1_1:
                                Log.i("NavigationView", "Pulsada opción 1_1");

                                AlarmaUtils.iniciarAlarma(MainActivity.this);
                                break;
                            case R.id.menu_opcion_1_2:
                                Log.i("NavigationView", "Pulsada opción 1_2");

                                AlarmaUtils.pararAlarma(MainActivity.this);
                                break;
                            case R.id.menu_opcion_2:
                                Log.i("NavigationView", "Pulsada opción 2");

                                ComponentName mServiceComponent = new ComponentName(MainActivity.this, MyJobService.class);
                                int i = 1;
                                JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
                                //Not periodic Job
                                JobInfo jobInfo = new JobInfo.Builder(i, mServiceComponent)
                                        .setMinimumLatency(1000) // 1 seconds (no se puede sobreescribr un tiempo de latencia en uno job periodico)
                                        .setOverrideDeadline(10000) // 10 seconds (no se puede sobreescribr un tiempo maximo en uno job periodico)
                                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY) // WiFi or data connections
                                        .build();

                                Log.i("TAG","Scheduling job " + i + "!");
                                scheduler.schedule(jobInfo);

                                break;
                            case R.id.menu_opcion_3:
                                Log.i("NavigationView", "Pulsada opción 3");

                                Intent mServiceIntent = new Intent(MainActivity.this, RSSPullService.class);
                                //mServiceIntent.setData(Uri.parse(dataUrl));
                                startService(mServiceIntent);

                                break;
                        }

                        if(fragmentTransaction) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, fragment)
                                    .commit();

                            menuItem.setChecked(true);
                            getSupportActionBar().setTitle(menuItem.getTitle());
                        }

                        drawerLayout.closeDrawers();

                        return true;
                    }
                });

        // Instantiates a new RSSPullStateReceiver
        RSSPullStateReceiver mStateReceiver = new RSSPullStateReceiver();
        // The filter's action is BROADCAST_ACTION
        IntentFilter statusIntentFilter = new IntentFilter(RSSPullService.BROADCAST_ACTION);

        // Registers the RSSPullStateReceiver and its intent filters
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mStateReceiver,
                statusIntentFilter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch(item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
