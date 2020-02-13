package com.easywaygroup.easyangkot.nav;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.acer.angkotroutes.R;
import com.easywaygroup.easyangkot.util.RouteListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, RouteListener {
    BottomSheetBehavior bottomSheet;
    FloatingActionButton fabRoutes;
    Fragment fragment;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle togel;

    GoogleMap googleMap;
    SupportMapFragment mapFragment;
    LocationManager location;
    Context context = this;

    int stateExpanded = BottomSheetBehavior.STATE_EXPANDED;
    int stateCollapsed = BottomSheetBehavior.STATE_COLLAPSED;

    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();

        //bottomNav.setOnNavigationItemSelectedListener(new BottomNavListener());

        fabRoutes = findViewById(R.id.fab_show_routes);
        fabRoutes.setOnClickListener(new FABListener());

        bottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheet.setHideable(true);

        drawerLayout = findViewById(R.id.drawer_layout);

        mapFragment.getMapAsync(this);
    }

    @SuppressLint("MissingPermission")
    private void initComponents() {
        //bottomNav = findViewById(R.id.view_bottom_navigation);

        fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_show_routes);

        View coordinatorLayout = findViewById(R.id.fragment_show_routes);
        bottomSheet = BottomSheetBehavior.from(coordinatorLayout);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);

        location = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        location.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
    }

    @Override
    public void onBackPressed() {
        if (bottomSheet.getState() == stateExpanded) {
            collapseBottomSheet();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setPadding(0, 0, 0, 250);
        this.googleMap = googleMap;

        googleMap.setMyLocationEnabled(true);

//        String locationProvider = LocationManager.GPS_PROVIDER;
//        Location lastLocation = location.getLastKnownLocation(locationProvider);
//
//        LatLng latlong = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
//        CameraUpdate cm = CameraUpdateFactory.newLatLngZoom(latlong, 10);
//        googleMap.animateCamera(cm);
    }

    @Override
    public void showRoute() {
        PolylineOptions routeLine = new PolylineOptions()
                .add(new LatLng(-6.946580, 107.662574))
                .add(new LatLng(-6.939250, 107.663912))
                .add(new LatLng(-6.945035, 107.641905))
                .add(new LatLng(-6.913827, 107.643670))
                .color(Color.BLUE).width(8);

        googleMap.addPolyline(routeLine);
    }

    private void expandBottomSheet() {
        bottomSheet.setState(stateExpanded);
    }

    private void collapseBottomSheet() {
        bottomSheet.setState(stateCollapsed);
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);

        googleMap.moveCamera(cameraUpdate);
        this.location.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private class FABListener implements FloatingActionButton.OnClickListener {
        @Override
        public void onClick(View v) {
            if (bottomSheet.getState() == stateExpanded) {
                collapseBottomSheet();
            } else {
                expandBottomSheet();

            }
        }
    }
    private class NavListener implements NavigationView.OnNavigationItemSelectedListener{


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {

                case R.id.nav_trayek:
                    Intent intent_trayek = new Intent(context, MainActivity.class);
                    break;
                case R.id.navigation_maps:
                    Intent intent_maps = new Intent(context, MainActivity.class);
                    break;
                case  R.id.nav_help:
                    break;
                case R.id.nav_about:
                    Intent intent_about = new Intent(context, AboutActivity.class);
                    break;
                case R.id.nav_feedback:
                    Intent intent_feedback = new Intent(context, FeedBackActivity.class);
                    break;
            }
            return false;
        }
    }


}
