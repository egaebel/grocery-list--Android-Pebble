package com.example.grocerylistcompanion;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class GroceryActivity extends Activity {

	private static final String TAG ="GROCERY_ACTIVITY";
	private static final int CHECK_BOX_PIXEL_LOCATION = 29;
	private ListView groceryListView;
	private CheckBoxAdapter<String> adapter;
	private List<String> groceryList;
	private Button addButton;
	private Button removeButton;
	private int selection;
	private int touchPositionX;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);
        
        groceryList = new ArrayList<String>();
        
        selection = -1;
        touchPositionX = -1;
        
        groceryListView = (ListView) this.findViewById(R.id.listView1);
        addButton = (Button) this.findViewById(R.id.addButton);
        removeButton = (Button) this.findViewById(R.id.removeButton);
        adapter = new CheckBoxAdapter<String>(this, R.layout.grocery_item, groceryList);
        groceryListView.setAdapter(adapter);
        groceryListView.setChoiceMode(1);
        
        //Grocery ListView onClickListener---------------------------------
        //TODO: Remove???
        groceryListView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				touchPositionX = (int) event.getX();
				Log.i(TAG, "TouchPositionX == " + touchPositionX);
				return false;
			}
        	
        });
        groceryListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
				
				groceryListView.setSelection(position);
				groceryListView.setSelected(true);
				selection = position;
				
				//If CheckBox touched
				if (touchPositionX < CHECK_BOX_PIXEL_LOCATION) {
				
					CheckBox chkBox = (CheckBox) view.findViewById(R.id.checkBox1);
					chkBox.setChecked(true);
					for (int i = 0; i < groceryList.size(); i++) {
						
						if (!((CompoundButton) parent.getChildAt(i)
								.findViewById(R.id.checkBox1))
								.isChecked()) {
							return;
						}
					}
					
					doneShoppingToast();
				}
			}
        });
        
        
        //Add Button onClickListener------------------------------------------
        addButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
		
				AlertDialog.Builder alert = addItemDialog();
				alert.create().show();
			}
        });
        
        //Remove Button onClickListener--------------------------------------
        removeButton.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		
    			if (selection != -1) {
    			
    				groceryList.remove(selection);
    				adapter.notifyDataSetChanged();
    				selection = -1;
    			}
    			else {
    				
    				showSelectionRequiredToast();
    			}
        	}
        });
    }
    
    private void showSelectionRequiredToast() {
    	
    	Toast.makeText(this, 
    			"You must select something to remove something!", 
    			Toast.LENGTH_LONG).show();
    }

    public void doneShoppingToast() {
    	
    	Toast.makeText(this, "You're done shopping!", Toast.LENGTH_LONG).show();
    }
    
    /**
     * Returns an AlertDialog.Builder prompting the user to enter a new item. 
     * 
     * @return
     */
    private AlertDialog.Builder addItemDialog() {
    	
    	LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.add_box, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(layout);
        alert.setTitle("Add a Grocery Item");
        
        final EditText itemAddView = (EditText) layout.findViewById(R.id.itemAddView);

        alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {

        	@Override
            public void onClick(DialogInterface dialog, int whichButton) {
        		
        		if (itemAddView.getText().toString() != "") {
        		
        			groceryList.add(itemAddView.getText().toString());
        			adapter.notifyDataSetChanged();
        			Log.i(TAG, "All elements of listITems: " + groceryList.toString());
        		}
            }
        });
        
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				dialog.cancel();
			}
		});
        
        return alert;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.grocery, menu);
        return true;
    }
}