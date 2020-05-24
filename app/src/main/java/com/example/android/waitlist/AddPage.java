package com.example.android.waitlist;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android.waitlist.data.WaitlistContract;
import com.example.android.waitlist.data.WaitlistDbHelper;

public class AddPage extends AppCompatActivity {
    TextView text_name;
    EditText  edit_name;
    TextView text_number;
    EditText  edit_number;
    private GuestListAdapter mAdapter;
    private SQLiteDatabase mDb;
    private final static String LOG_TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_page);
        text_name = (TextView)this.findViewById(R.id.text_name);
        edit_name = (EditText)this.findViewById(R.id.edit_name);
        text_number = (TextView)this.findViewById(R.id.text_number);
        edit_number = (EditText)this.findViewById(R.id.edit_number);
        WaitlistDbHelper dbHelper = new WaitlistDbHelper(this);
        mDb = dbHelper.getWritableDatabase();
    }
    public void add_customer(View viwe){
        String name = "";
        int number = 0;
        name = edit_name.getText().toString();
        number = Integer.parseInt(edit_number.getText().toString());

        if (name.length() == 0 || number <= 0) {
            return;
        }
        //default party size to 1
        int partySize = 1;
        try {
            //mNewPartyCountEditText inputType="number", so this should always work
            partySize = number;
        } catch (NumberFormatException ex) {
            Log.e(LOG_TAG, "Failed to parse party size text to number: " + ex.getMessage());
        }

        // Add guest info to mDb
        addNewGuest(name, partySize);

        // Update the cursor in the adapter to trigger UI to display the new list
        //mAdapter.swapCursor(getAllGuests());


        Context context = AddPage.this;
        Class destinationActivity = MainActivity.class;
        Intent startMainActivityIntent = new Intent(context, destinationActivity);
        startActivity(startMainActivityIntent);
    }

    private long addNewGuest(String name, int partySize) {
        ContentValues cv = new ContentValues();
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME, name);
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE, partySize);
        return mDb.insert(WaitlistContract.WaitlistEntry.TABLE_NAME, null, cv);
    }

    public void cancel(View viwe){
        Context context = AddPage.this;
        Class destinationActivity = MainActivity.class;
        Intent startMainActivityIntent = new Intent(context, destinationActivity);
        startActivity(startMainActivityIntent);
    }
}
