package com.easywaygroup.easyangkot.component;


import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.acer.angkotroutes.R;
import com.easywaygroup.easyangkot.util.Angkot;
import com.easywaygroup.easyangkot.util.JSONParser;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoutesBottomSheetFragment extends Fragment {

    private Context context;

    public RoutesBottomSheetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = Objects.requireNonNull(getActivity()).getApplicationContext();

        LinearLayout linearLayout = (LinearLayout) inflater.inflate(
                R.layout.fragment_routes_bottom_sheet,
                container,
                false
        );

        RecyclerView recyclerView = linearLayout.findViewById(R.id.recycler_view_bottom_sheet);

        AngkotRoutesAdapter adapter = new AngkotRoutesAdapter(getAngkotListFromJSON());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter.setListener(new AngkotRoutesAdapter.Listener() {
            @Override
            public void onClick(int position) {
                int pos = position + 1;
                CharSequence sequence = "Card " + pos + " Clicked";
                Snackbar snackbar = Snackbar.make(
                        Objects.requireNonNull(getActivity()).findViewById(R.id.coordinator_layout),
                        sequence,
                        Snackbar.LENGTH_SHORT
                );
                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) snackbar.getView().getLayoutParams();
                //layoutParams.setAnchorId(R.id.view_bottom_navigation);
                layoutParams.anchorGravity = Gravity.TOP;
                layoutParams.gravity = Gravity.TOP;
                snackbar.getView().setLayoutParams(layoutParams);
                snackbar.show();
            }
        });

        return linearLayout;
    }

    private List<Angkot> getAngkotListFromJSON() {
        List<Angkot> angkotList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(JSONParser.get(
                    context,
                    R.raw.data_angkot
            ));

            JSONObject jsonObject;
            String nama_jurusan;
            String gambarResId;

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                nama_jurusan = jsonObject.getString("nama_jurusan");
                gambarResId = jsonObject.getString("nama_gambar");
                angkotList.add(new Angkot(
                        nama_jurusan,
                        gambarResId
                ));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return angkotList;
    }

}
