package com.example.pokersessiontracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class PokerSessionDatabase extends SQLiteOpenHelper {

    public static final String DB_NAME = "PokerSessions.db";
    public static final int DB_VERSION = 1;

    public PokerSessionDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE sessions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "type TEXT NOT NULL, " +
                "name TEXT, " +
                "blinds TEXT, " +
                "buy_in INTEGER, " +
                "cash_out INTEGER, " +
                "start_time TEXT, " +
                "end_time TEXT, " +
                "date TEXT);");

        // Dummy data
        db.execSQL("INSERT INTO sessions (type, blinds, buy_in, cash_out, start_time, end_time, date) " +
                "VALUES ('cash', '1 / 2', 200, 800, '04/16/2025', '6:00 PM', '04/16/2025');");

        db.execSQL("INSERT INTO sessions (type, name, blinds, buy_in, cash_out, start_time, end_time, date) " +
                "VALUES ('tournament', NULL, 150, 500, NULL, NULL, '04/16/2025');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS sessions");
        onCreate(db);
    }

//    public void insertSession(String type, String blinds, int buyIn, int cashOut, String startTime, String endTime, String date) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put("type", type);
//        values.put("blinds", blinds);
//        values.put("buy_in", buyIn);
//        values.put("cash_out", cashOut);
//        values.put("start_time", startTime);
//        values.put("end_time", endTime);
//        values.put("date", date);
//
//        db.insert("sessions", null, values);
//    }
public void insertSession(String type, String name, String blinds, int buyIn, int cashOut, String startTime, String endTime, String date) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();

    values.put("type", type);
    values.put("name", name); // 👈 Add this
    values.put("blinds", blinds);
    values.put("buy_in", buyIn);
    values.put("cash_out", cashOut);
    values.put("start_time", startTime);
    values.put("end_time", endTime);
    values.put("date", date);

    db.insert("sessions", null, values);
}


    //    public ArrayList<String> getAllSessions() {
//        ArrayList<String> sessionList = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM sessions", null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
//                String blinds = cursor.getString(cursor.getColumnIndexOrThrow("blinds"));
//                int buyIn = cursor.getInt(cursor.getColumnIndexOrThrow("buy_in"));
//                int cashOut = cursor.getInt(cursor.getColumnIndexOrThrow("cash_out"));
//                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
//
//
//                int profit = cashOut - buyIn;
//
//                // If it's a cash game, show blinds & times. If it's a tourney, show date.
//                String sessionDetails;
//                if (type.equals("cash")) {
//                    String fullDateTime = cursor.getString(cursor.getColumnIndexOrThrow("start_time"));
//                    String dateOnly = fullDateTime.split(" ")[0];
//
//                    sessionDetails = "Cash Game (" + blinds + ")\nBuy-in: $" + buyIn +
//                            " | Cash-out: $" + cashOut + "\nProfit: $" + profit +
//                            "\nDate: " + dateOnly;
//                } else {
//                    sessionDetails = "Tournament\nBuy-in: $" + buyIn +
//                            " | Cash-out: $" + cashOut + "\nProfit: $" + profit +
//                            "\nDate: " + date;
//                }
//
//
//                sessionList.add(sessionDetails);
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        return sessionList;
//    }
public ArrayList<SessionItem> getAllSessions() {
    ArrayList<SessionItem> sessionList = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM sessions", null);

    if (cursor.moveToFirst()) {
        do {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
            String blinds = cursor.getString(cursor.getColumnIndexOrThrow("blinds"));
            int buyIn = cursor.getInt(cursor.getColumnIndexOrThrow("buy_in"));
            int cashOut = cursor.getInt(cursor.getColumnIndexOrThrow("cash_out"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            String fullDateTime = cursor.getString(cursor.getColumnIndexOrThrow("start_time"));
            int profit = cashOut - buyIn;
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));

            String sessionDetails;
            if (type.equals("cash")) {
                String dateOnly = (fullDateTime != null && fullDateTime.contains(" ")) ?
                        fullDateTime.split(" ")[0] : fullDateTime;
                sessionDetails = "Cash Game (" + blinds + ")\nBuy-in: $" + buyIn +
                        " | Cash-out: $" + cashOut + "\nProfit: $" + profit +
                        "\nDate: " + dateOnly;
            } else {
                sessionDetails = "Tournament: " + name + "\nBuy-in: $" + buyIn +
                        " | Cash-out: $" + cashOut + "\nProfit: $" + profit +
                        "\nDate: " + date;
            }

            sessionList.add(new SessionItem(id, sessionDetails));
        } while (cursor.moveToNext());
    }

    cursor.close();
    return sessionList;
}


    public void deleteSessionById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("sessions", "id = ?", new String[]{String.valueOf(id)});
    }

    public void insertDummySessions() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if sessions already exist
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sessions", null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        if (count > 0) {
            return; // 🚫 Skip inserting if data already exists
        }

        // ✅ Insert dummy sessions
        db.execSQL("INSERT INTO sessions (type, name, blinds, buy_in, cash_out, start_time, end_time, date) " +
                "VALUES ('cash', NULL, '1 / 2', 200, 350, '04/16/2025 3:00 PM', '6:00 PM', '04/16/2025');");

        db.execSQL("INSERT INTO sessions (type, name, blinds, buy_in, cash_out, start_time, end_time, date) " +
                "VALUES ('tournament', '2k Bounty', NULL, 150, 500, NULL, NULL, '04/16/2025');");

    }

    public StatsResult getCashStats() {
        int totalProfit = 0;
        int wins = 0;
        int losses = 0;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT buy_in, cash_out FROM sessions WHERE type = 'cash'", null);

        if (cursor.moveToFirst()) {
            do {
                int buyIn = cursor.getInt(cursor.getColumnIndexOrThrow("buy_in"));
                int cashOut = cursor.getInt(cursor.getColumnIndexOrThrow("cash_out"));
                int profit = cashOut - buyIn;
                totalProfit += profit;

                if (profit >= 0) {
                    wins++;
                } else {
                    losses++;
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        return new StatsResult(totalProfit, wins, losses);
    }

    public StatsResult getTournamentStats() {
        int totalProfit = 0;
        int totalBuyIn = 0;
        int cashes = 0;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT buy_in, cash_out FROM sessions WHERE type = 'tournament'", null);

        if (cursor.moveToFirst()) {
            do {
                int buyIn = cursor.getInt(cursor.getColumnIndexOrThrow("buy_in"));
                int cashOut = cursor.getInt(cursor.getColumnIndexOrThrow("cash_out"));
                int profit = cashOut - buyIn;

                totalProfit += profit;
                totalBuyIn += buyIn;

                if (cashOut > buyIn) {
                    cashes++;
                }
            } while (cursor.moveToNext());
        }

        cursor.close();

        // ROI = ((totalProfit) / totalBuyIn) * 100
        int roi = totalBuyIn > 0 ? (totalProfit * 100 / totalBuyIn) : 0;

        return new StatsResult(totalProfit, cashes, roi);
    }






}
