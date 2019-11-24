package com.example.aad4.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.aad4.entity.Comment;
import com.example.aad4.entity.TouristAttraction;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;


public class DBHelper extends OrmLiteSqliteOpenHelper {


    private static final String DATABASE_NAME = "ormlite.db";
    private static final int DATABASE_VERSION = 1;

    public static final String COLUMN_FOREIGN_ID = "foreign_id";

    private Dao<TouristAttraction, Integer> parentDao;
    private Dao<Comment, Integer> childDao;

    private static DBHelper instance;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        try {
            parentDao = getParentDao();
            childDao = getChildDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, TouristAttraction.class);
            TableUtils.createTable(connectionSource, Comment.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, TouristAttraction.class, true);
            TableUtils.dropTable(connectionSource, Comment.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DBHelper getInstance(Context context) {

        if (instance == null) {
            instance = new DBHelper(context);
        }
        return instance;
    }


    public Dao<TouristAttraction, Integer> getParentDao() throws SQLException {
        if (parentDao == null) {
            parentDao = getDao(TouristAttraction.class);
        }

        return parentDao;
    }

    public Dao<Comment, Integer> getChildDao() throws SQLException {
        if (childDao == null) {
            childDao = getDao(Comment.class);
        }

        return childDao;
    }


    @Override
    public void close() {
        parentDao = null;
        childDao = null;
        super.close();
    }


}