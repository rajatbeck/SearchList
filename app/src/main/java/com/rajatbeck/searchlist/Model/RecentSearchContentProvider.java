package com.rajatbeck.searchlist.Model;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.util.HashMap;

/**
 * Created by Rajat on 11/14/2016.
 */

public class RecentSearchContentProvider extends ContentProvider {

    static final String PROVIDER_NAME = "com.rajatbeck.searchlist.Model.RecentSearchContentProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/recentsearches";
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final String _ID = "_id";
    static final String NAME = "name";
    static final int recentSearches = 1;
    static final int recentSearch = 2;
    static final UriMatcher uriMatcher;
    private static HashMap<String, String> values;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "recentsearches", recentSearches);
        uriMatcher.addURI(PROVIDER_NAME, "recentsearches/*", recentSearch);
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        RecentSearchHelper recentSearchHelper = new RecentSearchHelper(context);
        db = recentSearchHelper.getWritableDatabase();
        return (db == null) ? false : true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        long rowId = db.insert(TABLE_NAME,"",contentValues);
        if(rowId>0){
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI,rowId);
            getContext().getContentResolver().notifyChange(_uri,null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);

    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    private SQLiteDatabase db;
    static final String DATABASE_NAME = "recent_searches_db";
    static final String TABLE_NAME = "recent_search_table";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_TABLE = " CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + " name TEXT UNIQUE NOT NULL);";

    private static class RecentSearchHelper extends SQLiteOpenHelper {

        public RecentSearchHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
    }
}
