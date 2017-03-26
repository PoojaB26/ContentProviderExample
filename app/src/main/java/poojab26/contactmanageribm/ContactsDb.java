package poojab26.contactmanageribm;

/**
 * Created by pblead26 on 20-Sep-16.
 */
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ContactsDb {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_NAME = "name";
    public static final String KEY_CATEGORY = "categ";
    public static final String KEY_EMAIL = "email";

    private static final String LOG_TAG = "ContactsDb";
    public static final String SQLITE_TABLE = "Contact";

    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
                    KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    KEY_PHONE + "," +
                    KEY_NAME + "," +
                    KEY_CATEGORY + "," +
                    KEY_EMAIL + "," +
                    " UNIQUE (" + KEY_PHONE +"));";

    public static void onCreate(SQLiteDatabase db) {
        Log.w(LOG_TAG, DATABASE_CREATE);
        db.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(LOG_TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
        onCreate(db);
    }

}