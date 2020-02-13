package com.easywaygroup.easyangkot.nav;

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
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    //BottomNavigationView bottomNav;
    BottomSheetBehavior bottomSheet;
    SupportMapFragment mapFragment;
    FloatingActionButton fabRoutes;
    GoogleMap googleMap;
    Fragment fragment;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle togel;

    int stateExpanded = BottomSheetBehavior.STATE_EXPANDED;
    int stateCollapsed = BottomSheetBehavior.STATE_COLLAPSED;

    Context context = this;
    NavigationView navigationView;

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
    }

    private void initComponents() {
        //bottomNav = findViewById(R.id.view_bottom_navigation);

        fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_show_routes);

        View coordinatorLayout = findViewById(R.id.fragment_show_routes);
        bottomSheet = BottomSheetBehavior.from(coordinatorLayout);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);
        navigationView = findViewById(R.id.navigationview);
        navigationView.setNavigationItemSelectedListener(new NavListener());
    }

    private void expandBottomSheet() {
        bottomSheet.setState(stateExpanded);
    }

    private void collapseBottomSheet() {
        bottomSheet.setState(stateCollapsed);
    }

    /*private class BottomNavListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_favorite:

                    return true;
                case R.id.navigation_maps:

                    return true;
                case R.id.navigation_recent:

                    return true;
            }
            return false;
        }
    }
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
