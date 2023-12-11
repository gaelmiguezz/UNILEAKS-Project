package com.esei.dm.unileaks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Constantes para el nombre y la versión de la base de datos
    private static final String DATABASE_NAME = "UNILeaks.db";
    private static final int DATABASE_VERSION = 2;

    // Constantes para el nombre de la tabla y los campos de la tabla de usuarios
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_RANK = "rank";

    // Constructor de la clase
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Método para crear la tabla de usuarios en la base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_RANK + " INTEGER DEFAULT 0" + ")"; // Nueva columna
        db.execSQL(CREATE_USERS_TABLE);
    }


    // Método para actualizar la estructura de la base de datos si se cambia la versión
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Método para añadir un nuevo usuario a la base de datos
    public void addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, hashPassword(password));
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public void setAdmin(int userId, boolean isAdmin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RANK, isAdmin ? 1 : 0); // 1 si es administrador, 0 si no lo es
        String whereClause = COLUMN_ID + " = ?";
        String[] whereArgs = {String.valueOf(userId)};
        db.update(TABLE_USERS, values, whereClause, whereArgs);
        db.close();
    }


    // Método para comprobar si un usuario existe en la base de datos
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_USERNAME + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, hashPassword(password)};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    // Método para aplicar la función hash SHA-256 a la contraseña
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}