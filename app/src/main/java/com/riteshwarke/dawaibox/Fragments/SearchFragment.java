package com.riteshwarke.dawaibox.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.riteshwarke.dawaibox.Adapters.DrugsSearchAdapter;
import com.riteshwarke.dawaibox.Adapters.InventoryAdapter;
import com.riteshwarke.dawaibox.BuildConfig;
import com.riteshwarke.dawaibox.Helpers.ApiService;
import com.riteshwarke.dawaibox.Helpers.AppConstants;
import com.riteshwarke.dawaibox.Helpers.RecyclerTouchListener;
import com.riteshwarke.dawaibox.Models.DrugSearch;
import com.riteshwarke.dawaibox.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    private static final Logger LOG = LoggerFactory.getLogger(SearchFragment.class.getSimpleName());
@BindView(R.id.autoCompleteTextView)
    AutoCompleteTextView autoCompleteTextView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerViewInventory)
    RecyclerView recyclerViewInventory;
    @BindView(R.id.progressBar)
    ProgressBar progress;
    @BindView(R.id.progressBar1)
    ProgressBar progress1;
    private Timer timer=new Timer();
    private final long DELAY = 1000;
    private boolean loading = true;
    private int page = 0;
    private int totalCount = 0;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private String constraint = "";
    LinearLayoutManager mLayoutManager;
    LinearLayoutManager iLayoutManager;
    DrugsSearchAdapter adapter;
    InventoryAdapter iadapter;
    private List<DrugSearch> drugList = new ArrayList<>();
    private List<DrugSearch> inventoryList = new ArrayList<>();
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this,view);
        init();
        return view;
    }

    private void init() {
        adapter = new DrugsSearchAdapter(drugList);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        iadapter = new InventoryAdapter(inventoryList);
        iLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewInventory.setLayoutManager(iLayoutManager);
        recyclerViewInventory.setItemAnimator(new DefaultItemAnimator());
        recyclerViewInventory.setAdapter(iadapter);



        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading)
                    {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                        {
                            loading = false;
                            page = page+10;
                            if(totalCount>page) {
                                progress.setVisibility(View.VISIBLE);
                                progress.bringToFront();
                                getSearches(constraint);
                            }
                        }
                    }
                }
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                try {
                    DrugSearch drug = drugList.get(position);
                    DrugSearch i = new DrugSearch(drug.getDrugId(), drug.getDrugName(),drug.getDrugType(),drug.getPharmaCompName(),drug.getCompound(),drug.getDrugInteractions(),"");
                    //inventoryList.add(i);
                    List<DrugSearch> inventoryListTemp = new ArrayList<>();
                    inventoryListTemp.addAll(inventoryList);
                    inventoryListTemp.add(i);
                    if(inventoryList.contains(i)){
                        Toast.makeText(getContext(), drug.getDrugName() + " Drug is already present in your inventory!", Toast.LENGTH_SHORT).show();

                    }else{
                        inventoryList.add(i);
                        Toast.makeText(getContext(), drug.getDrugName() + " is added to your inventory!", Toast.LENGTH_SHORT).show();

                    }

                    /*for (DrugSearch element : inventoryListTemp) {
                        if (!inventoryList.contains(element)) {
                            inventoryList.add(element);
                            Toast.makeText(getContext(), drug.getDrugName() + " is added to your inventory!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(), drug.getDrugName() + " Drug is already added to your inventory!", Toast.LENGTH_SHORT).show();
                        }
                    }*/
                    //inventoryList = new ArrayList<DrugSearch>(new LinkedHashSet<DrugSearch>(inventoryList));
                    iadapter.notifyDataSetChanged();
                    recyclerView.setVisibility(View.GONE);
                    recyclerViewInventory.setVisibility(View.VISIBLE);
                    AppConstants.ISSEARCHING = false;
                    drugList.clear();
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));



        recyclerViewInventory.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerViewInventory, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                try {
                    DrugSearch drug = inventoryList.get(position);
                    //Toast.makeText(getContext(), drug.getDrugName() + " is clicked!", Toast.LENGTH_SHORT).show();
                    TextView title = (TextView) view.findViewById(R.id.title);
                    CardView card_view = (CardView) view.findViewById(R.id.card_view);
                    TextView name = (TextView) view.findViewById(R.id.name);
                    TextView company = (TextView) view.findViewById(R.id.company);
                    TextView type = (TextView) view.findViewById(R.id.type);
                    TextView compound = (TextView) view.findViewById(R.id.compound);
                    TextView interactions = (TextView) view.findViewById(R.id.interactions);
                    TextView compound1 = (TextView) view.findViewById(R.id.compound1);
                    TextView interactions1 = (TextView) view.findViewById(R.id.interactions1);
                    TextView initial = (TextView) view.findViewById(R.id.initial);
                    TextView initial1 = (TextView) view.findViewById(R.id.initial1);

if(!drug.getExpanded().equalsIgnoreCase("yes")) {
    drug.setExpanded("yes");
    initial1.setVisibility(View.GONE);
    title.setVisibility(View.GONE);
    initial.setVisibility(View.VISIBLE);
    name.setVisibility(View.VISIBLE);
    company.setVisibility(View.VISIBLE);
    type.setVisibility(View.VISIBLE);
    compound.setVisibility(View.VISIBLE);
    interactions.setVisibility(View.VISIBLE);
    compound1.setVisibility(View.VISIBLE);
    interactions1.setVisibility(View.VISIBLE);
} else{
    drug.setExpanded("");
    initial1.setVisibility(View.VISIBLE);
    title.setVisibility(View.VISIBLE);
    initial.setVisibility(View.GONE);
    name.setVisibility(View.GONE);
    company.setVisibility(View.GONE);
    type.setVisibility(View.GONE);
    compound.setVisibility(View.GONE);
    interactions.setVisibility(View.GONE);
    compound1.setVisibility(View.GONE);
    interactions1.setVisibility(View.GONE);

}

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));





        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                progress1.setVisibility(View.VISIBLE);
                progress1.bringToFront();

                constraint = charSequence.toString().trim();
                page = 0;
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (!charSequence.toString().equals("") ) {
                                            getSearches(constraint);
                                        }else {
                                            drugList.clear();
                                            adapter.notifyDataSetChanged();
                                            progress1.setVisibility(View.GONE);

                                        }
                                    }
                                });
                            }
                        },
                        DELAY
                );

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




    }

    public void getSearches(String constraint){
        recyclerView.setVisibility(View.VISIBLE);
        recyclerViewInventory.setVisibility(View.GONE);
        AppConstants.ISSEARCHING = true;
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(AppConstants.SERVER_BASE_URL)
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        apiService.createTask("14120",constraint.toString(),""+ page,"10","Doctor").enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
          if(BuildConfig.DEBUG) {
              LOG.debug("get drugs API call success with page start {}", page);
          }

                if (page == 0) {
                    drugList.clear();
                    adapter.notifyDataSetChanged();
                }
                try {
                    JSONObject jsonResponse = new JSONObject(response.body().toString());

                    totalCount = jsonResponse.getInt("totalCount");
                    if(totalCount == 0){
                        Toast.makeText(getContext(), "No Drugs found for this query, Please try again.", Toast.LENGTH_LONG).show();
                        progress1.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        recyclerViewInventory.setVisibility(View.VISIBLE);

                    }else {
                        JSONArray j = jsonResponse.getJSONArray("drugList");
                        for (int i = 0; i < j.length(); i++) {

                            JSONObject k = j.getJSONObject(i);
                            final DrugSearch d = new DrugSearch(k.getString("drugId"), k.getString("drugName"), k.getString("drugType"), k.getString("pharmaCompName"), k.getString("compound"), k.getString("drugInteractions"), "");
                            drugList.add(d);

                            adapter.notifyDataSetChanged();
                            if (page == 0 && i == 0)
                                progress1.setVisibility(View.GONE);
                        }
                        progress.setVisibility(View.GONE);
                        loading = true;
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Our servers are busy, Please try again.", Toast.LENGTH_LONG).show();
                    //Log.i(TAG, "Caught in the exception " + e.getMessage());
                    LOG.debug("Caught in the exception in get drugs API call {}",e.getMessage());
                }

            }


            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                progress.setVisibility(View.GONE);
                loading = true;
                progress1.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Our servers are busy, Please try again.", Toast.LENGTH_LONG).show();
                //Log.i(TAG, "failed to connect " + t.getMessage());
                LOG.debug("get drugs API call failed with page start {} with message {}", page, t.getMessage());
            }
        });


    }


    public void backToInventory() {
        recyclerView.setVisibility(View.GONE);
        recyclerViewInventory.setVisibility(View.VISIBLE);
        AppConstants.ISSEARCHING = false;
        drugList.clear();
        adapter.notifyDataSetChanged();
    }
}
