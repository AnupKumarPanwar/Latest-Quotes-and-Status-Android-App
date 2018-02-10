package com.quotes.lateststatus;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {

    RecyclerView recyclerView;
    List<Categories> categoriesList=new ArrayList<>();
    CategoryAdapter categoryAdapter;

    String[] quotes_array={"Age", "Alone", "Amazing", "Anger", "Anniversary", "Architecture", "Art", "Attitude", "Beauty", "Best", "Birthday", "Brainy", "Business", "Car", "Chance", "Change", "Christmas", "Communication", "Computers", "Cool", "Courage", "Dad", "Dating", "Death", "Design", "Diet", "Dreams", "Easter", "Education", "Environmental", "Equality", "Experience", "Failure", "Faith", "Family", "Famous", "Father's Day", "Fear", "Finance", "Fitness", "Food", "Forgiveness", "Freedom", "Friendship", "Funny", "Future", "Gardening", "God", "Good", "Government", "Graduation", "Great", "Happiness", "Health", "History", "Home", "Hope", "Humor", "Imagination", "Independence", "Inspirational", "Intelligence", "Jealousy", "Knowledge", "Leadership", "Learning", "Legal", "Life", "Love", "Marriage", "Medical", "Memorial Day", "Men", "Mom", "Money", "Morning", "Mother's Day", "Motivational", "Movies", "Moving On", "Music", "Nature", "New Year's", "Parenting", "Patience", "Patriotism", "Peace", "Pet", "Poetry", "Politics", "Positive", "Power", "Relationship", "Religion", "Respect", "Romantic", "Sad", "Saint Patrick's Day", "Science", "Smile", "Society", "Space", "Sports", "Strength", "Success", "Sympathy", "Teacher", "Technology", "Teen", "Thankful", "Thanksgiving", "Time", "Travel", "Trust", "Truth", "Valentine's Day", "Veterans Day", "War", "Wedding", "Wisdom", "Women", "Work"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        savedInstanceState = null;
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_category, container, false);

        recyclerView=view.findViewById(R.id.recycler_view);

        categoryAdapter=new CategoryAdapter(categoriesList, getActivity());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(categoryAdapter);

        for (String quote : quotes_array)
        {
            categoriesList.add(new Categories(quote));
        }

        categoryAdapter.notifyDataSetChanged();

        return view;
    }

}
