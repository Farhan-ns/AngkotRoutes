package com.easywaygroup.easyangkot.nav;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

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

}
