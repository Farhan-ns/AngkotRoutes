package com.acer.angkotroutes.nav;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.acer.angkotroutes.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    BottomNavigationView bottomNav;
    BottomSheetBehavior bottomSheet;
    Fragment fragment;

    int stateExpanded = BottomSheetBehavior.STATE_EXPANDED;
    int stateCollapsed = BottomSheetBehavior.STATE_COLLAPSED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.view_toolbar);
        setSupportActionBar(toolbar);

        bottomNav = findViewById(R.id.view_bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavListener());

        FloatingActionButton fabRoutes = findViewById(R.id.fab_show_routes);
        fabRoutes.setOnClickListener(new FABListener());

        fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_show_routes);

        View coordinatorLayout = findViewById(R.id.fragment_show_routes);
        bottomSheet = BottomSheetBehavior.from(coordinatorLayout);
        bottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    public void onBackPressed() {
        if (bottomSheet.getState() == stateExpanded) {
            collapseBottomSheet();
        } else {
            super.onBackPressed();
        }
    }

    private void expandBottomSheet() {
        bottomSheet.setState(stateExpanded);
    }

    private void collapseBottomSheet() {
        bottomSheet.setState(stateCollapsed);
    }

    private class BottomNavListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_favorite:
                    toolbar.setTitle("Favorite");
                    return true;
                case R.id.navigation_maps:
                    toolbar.setTitle("Maps");
                    return true;
                case R.id.navigation_recent:
                    toolbar.setTitle("Recent");
                    return true;
            }
            return false;
        }
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

}
