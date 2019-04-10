package com.cadrac.mylibrary;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;

import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchableSpinner extends AppCompatSpinner implements View.OnTouchListener {

    Context context;
    ListView listView;
    android.support.v7.widget.SearchView searchView;
    ArrayAdapter arrayAdapter;
    ArrayList<String> arrayList,newarraylist;

    public SearchableSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;

        setOnTouchListener(this);
    }

    public SearchableSpinner(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        this.context=context;
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View view = inflate(context, R.layout.dialog_spinner, null);
            searchView = view.findViewById(R.id.search_spinner);
            listView = (ListView) view.findViewById(R.id.listview_spinner);
            arrayAdapter = (ArrayAdapter) getAdapter();
            arrayList = new ArrayList<>(10);
            newarraylist = new ArrayList<>(10);

            final Dialog dialog = new Dialog(context);
            dialog.setContentView(view);

            arrayList.clear();
            for (int i = 0; i < arrayAdapter.getCount(); i++) {
                arrayList.add(arrayAdapter.getItem(i).toString());
                newarraylist.add(arrayAdapter.getItem(i).toString());
            }

            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_list_item_1, arrayList);

            listView.setAdapter(adapter);
            listView.setTextFilterEnabled(true);

            listView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    for(int i=0;i<arrayList.size();i++){
                        if(arrayList.get(i).equals(newarraylist.get(position))){
                            setSelection(i);
                        }
                    }

                    view.getId();
                    dialog.dismiss();
                }
            });



            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {

//to filter the listview
                    ((ArrayAdapter) listView.getAdapter()).getFilter().filter(s);

                    newarraylist.clear();
                    for(int i=0;i<listView.getAdapter().getCount();i++){
                        newarraylist.add(listView.getAdapter().getItem(i).toString());
                    }

                    return true;
                }
            });


            dialog.show();
        }
        return true;
    }
}

