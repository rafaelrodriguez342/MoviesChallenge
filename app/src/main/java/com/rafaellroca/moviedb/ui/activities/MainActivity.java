package com.rafaellroca.moviedb.ui.activities;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.rafaellroca.moviedb.R;
import com.rafaellroca.moviedb.models.Filter;
import com.rafaellroca.moviedb.models.VideoData;
import com.rafaellroca.moviedb.ui.adapter.VideosAdapter;
import com.rafaellroca.moviedb.viewmodels.MainViewModel;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {
    private MainViewModel viewModel;
    private VideosAdapter videosAdapter;
    private RadioGroup radioGroup;
    private Spinner spinner;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
        observeLiveData();
        initGalleryRecyclerView();
        initRadioButtons();
        initSpinner();
        initSearchEditText();
    }

    private void observeLiveData() {
        viewModel.getVideosDataLiveData().observe(this, this::submitList);
        viewModel.getErrorLiveData().observe(this, this::displayErrorMessage);
    }

    private void initGalleryRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.gallery_list);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        videosAdapter = new VideosAdapter((video, context, imageView, textView) -> {
            Intent intent = DetailsActivity.createNewIntent(context, video);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, new Pair<>(imageView,
                    ViewCompat.getTransitionName(imageView)), new Pair<>(textView,
                    ViewCompat.getTransitionName(textView)));

            context.startActivity(intent, options.toBundle());
        }, Collections.emptyList());
        recyclerView.setAdapter(videosAdapter);
    }

    private void initSpinner() {
        spinner = findViewById(R.id.category_selector);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String category = parent.getItemAtPosition(position).toString();
                requestVideoData(new Filter(getSelectedSpinnerCategory(category), getSelectedType()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Drawable spinnerDrawable = spinner.getBackground().getConstantState().newDrawable();
        spinnerDrawable.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        spinner.setBackground(spinnerDrawable);
        updateSpinnerList();
    }

    private void initSearchEditText() {
        EditText searchText = findViewById(R.id.search_text);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchTextData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initRadioButtons() {
        radioGroup = findViewById(R.id.type_radio_group);
        radioGroup = findViewById(R.id.type_radio_group);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            updateSpinnerList();
            requestVideoData(new Filter(getSelectedCategory(),
                    getSelectedRadioButtonType(checkedId)));

        });
    }

    private Filter.Type getSelectedRadioButtonType(@IdRes int radioButtonId) {
        switch (radioButtonId) {
            case R.id.type_movie:
                return Filter.Type.MOVIE;

            case R.id.type_tv:
                return Filter.Type.TV;
            default:
                return null;
        }
    }

    private Filter.Category getSelectedSpinnerCategory(String string) {
        return Filter.Category.valueOf(string);
    }

    private Filter.Type getSelectedType() {
        return getSelectedRadioButtonType(radioGroup.getCheckedRadioButtonId());
    }

    private Filter.Category getSelectedCategory() {
        return getSelectedSpinnerCategory(spinner.getSelectedItem().toString());
    }

    private void updateSpinnerList() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.category_selector_item, getCategoriesForSelectedType());
        spinner.setAdapter(adapter);
    }

    private void requestVideoData(Filter filter) {
        viewModel.requestVideosData(filter);
    }

    private void submitList(List<VideoData> videoData) {
        if (videoData != null) {
            videosAdapter.setList(videoData);
            videosAdapter.notifyDataSetChanged();
        }
    }

    private String[] getCategoriesForSelectedType() {
        return getSelectedType() == Filter.Type.MOVIE ? getResources().getStringArray(R.array.video_categories_movie) :
               getResources().getStringArray(R.array.video_categories_tv);
    }

    private void searchTextData(String keywords) {
        viewModel.searchVideosData(new Filter(getSelectedCategory(), getSelectedType()), keywords);
    }

    private void displayErrorMessage(Throwable throwable) {
        Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_LONG).show();
    }
}
