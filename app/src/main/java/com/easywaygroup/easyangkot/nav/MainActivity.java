package com.easywaygroup.easyangkot.nav;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.acer.angkotroutes.R;
import com.easywaygroup.easyangkot.util.PermissionChecker;
import com.easywaygroup.easyangkot.util.RouteListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, RouteListener {
    BottomSheetBehavior bottomSheet;
    FloatingActionButton fabRoutes;
    Fragment fragment;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    GoogleMap googleMap;
    SupportMapFragment mapFragment;
    LocationManager location;
    Context context = this;

    int stateExpanded = BottomSheetBehavior.STATE_EXPANDED;
    int stateCollapsed = BottomSheetBehavior.STATE_COLLAPSED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ensurePermission(MainActivity.this);
        initComponents();
    }

    /**
     * Inisialisasi Komponen UI dan Maps
     */
    private void initComponents() {
        fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_show_routes);

        View coordinatorLayout = findViewById(R.id.fragment_show_routes);
        bottomSheet = BottomSheetBehavior.from(coordinatorLayout);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);

        location = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        fabRoutes = findViewById(R.id.fab_show_routes);
        fabRoutes.setOnClickListener(new FABListener());

        bottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheet.setHideable(true);

        drawerLayout = findViewById(R.id.drawer_layout);

        mapFragment.getMapAsync(this);

        navigationView = findViewById(R.id.navigationview);
        navigationView.setNavigationItemSelectedListener(new NavListener());
    }

    /**
     * Cek apakah GPS permission telah di Granted oleh user
     * @param context application context
     */
    private void ensurePermission(Context context) {
        if (!PermissionChecker.checkPermissionIsGranted(context, PermissionChecker.fineLocationPermission)) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        } else {
            Toast.makeText(context, "GPS aktif", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(context, "GPS access granted", Toast.LENGTH_SHORT).show();
                    googleMap.setMyLocationEnabled(true);//checkSelfPermission is done by permissionChecker in onCreate()
                } else {
                    Toast.makeText(context, "Aplikasi memerlukan akses GPS ", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (bottomSheet.getState() == stateExpanded) {
            collapseBottomSheet();
        } else {
            super.onBackPressed();
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setPadding(0, 0, 0, 250);
        this.googleMap = googleMap;
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

    /**
     * Expand the UI bottom sheet
     */
    private void expandBottomSheet() {
        bottomSheet.setState(stateExpanded);
    }

    /**
     * Collapse the UI bottom sheet
     */
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

    /**
     * Class Listener untuk FAB Nav
     */
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

    /**
     * Class Listener untuk Nav Drawer
     */
    private class NavListener implements NavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Intent intent;

            switch (menuItem.getItemId()) {
                case R.id.nav_trayek:
                    intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.navigation_maps:
                    intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    break;
                case  R.id.nav_help:
                    break;
                case R.id.nav_about:
                    intent = new Intent(context, AboutActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_feedback:
                    intent = new Intent(context, FeedBackActivity.class);
                    startActivity(intent);
                    break;
            }
            return false;
        }
    }


}
