package poojab26.contactmanageribm;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends Activity implements
        LoaderManager.LoaderCallbacks<Cursor>{

    private SimpleCursorAdapter dataAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayListView();

        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // starts a new Intent to add a Country
                Intent contactEdit = new Intent(getBaseContext(), ContactEdit.class);
                Bundle bundle = new Bundle();
                bundle.putString("mode", "add");
                contactEdit.putExtras(bundle);
                startActivity(contactEdit);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Starts a new or restarts an existing Loader in this manager
        getLoaderManager().restartLoader(0, null, this);
    }

    private void displayListView() {


        // The desired columns to be bound
        String[] columns = new String[] {
                ContactsDb.KEY_PHONE,
                ContactsDb.KEY_NAME,
                ContactsDb.KEY_EMAIL,
                ContactsDb.KEY_CATEGORY
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.tvPhone,
                R.id.tvname,
                R.id.tvemail,
                R.id.tvCate,
        };

        // create an adapter from the SimpleCursorAdapter
        dataAdapter = new SimpleCursorAdapter(
                this,
                R.layout.contact_info,
                null,
                columns,
                to,
                0);

        // get reference to the ListView
        ListView listView = (ListView) findViewById(R.id.contactList);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);
        //Ensures a loader is initialized and active.
        getLoaderManager().initLoader(0, null, this);


        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // display the selected contact
                String contactNumber =
                        cursor.getString(cursor.getColumnIndexOrThrow(ContactsDb.KEY_PHONE));
                Toast.makeText(getApplicationContext(),
                        contactNumber, Toast.LENGTH_SHORT).show();

                String rowId =
                        cursor.getString(cursor.getColumnIndexOrThrow(ContactsDb.KEY_ROWID));

                // starts a new Intent to update/delete a Country
                // pass in row Id to create the Content URI for a single row
                Intent contactEdit = new Intent(getBaseContext(), ContactEdit.class);
                Bundle bundle = new Bundle();
                bundle.putString("mode", "update");
                bundle.putString("rowId", rowId);
                contactEdit.putExtras(bundle);
                startActivity(contactEdit);

            }
        });

    }

    // This is called when a new Loader needs to be created.
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                ContactsDb.KEY_ROWID,
                ContactsDb.KEY_PHONE,
                ContactsDb.KEY_NAME,
                ContactsDb.KEY_CATEGORY,
                ContactsDb.KEY_EMAIL};
        CursorLoader cursorLoader = new CursorLoader(this,
                MyContentProvider.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
        dataAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        dataAdapter.swapCursor(null);
    }


}
