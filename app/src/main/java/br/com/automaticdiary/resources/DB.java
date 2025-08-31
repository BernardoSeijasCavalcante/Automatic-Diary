package br.com.automaticdiary.resources;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageItemInfo;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.automaticdiary.entities.Activity;
import br.com.automaticdiary.ui.register_fragments.RegisterActivity;

public class DB {

    public static final String databaseName = "AutomaticDiaryDB";

    public static void startDB(Context context){
        SQLiteDatabase database = context.openOrCreateDatabase(databaseName, context.MODE_PRIVATE,null);

        database.execSQL("CREATE TABLE IF NOT EXISTS activitys (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title VARCHAR(255) NOT NULL," +
                "performance REAL NOT NULL," +
                "score REAL NOT NULL," +
                "start INTEGER NOT NULL," +
                "finish INTEGER NOT NULL," +
                "description TEXT NOT NULL," +
                "category VARCHAR(50)" +
                ");");

        database.close();
    }
    public static Activity registerActivity(Activity activity, RegisterActivity context){
        SQLiteDatabase database = context.getActivity().openOrCreateDatabase(databaseName, context.getActivity().MODE_PRIVATE,null);

        database.execSQL("INSERT INTO activitys (title, performance, score, start," +
                "finish, description, category) VALUES (('" +
                activity.getTitle() + "')," +
                activity.getPerformance() + "," +
                activity.getScore() + "," +
                activity.getStart().getTimeInMillis() + "," +
                activity.getFinish().getTimeInMillis() + ",('" +
                activity.getDescription() + "'),('" +
                activity.getCategory() +
                "'))");

        ContentValues values = new ContentValues();
        values.put("title", activity.getTitle());
        values.put("performance", activity.getPerformance());
        values.put("score", activity.getScore());
        values.put("start", activity.getStart().getTimeInMillis());
        values.put("finish", activity.getFinish().getTimeInMillis());
        values.put("description", activity.getDescription());
        values.put("category", activity.getCategory());

        long id = database.insert("activitys",null,values);

        activity.setId(id);

        database.close();
        Toast.makeText(context.getContext(), "Atividade registrada com sucesso!", Toast.LENGTH_LONG).show();
        return activity;
    }

    public static void updateActivity(Activity activity, RegisterActivity context){
        SQLiteDatabase database = context.getActivity().openOrCreateDatabase(databaseName,context.getActivity().MODE_PRIVATE,null);

        database.execSQL("UPDATE activitys SET " +
                "title = '" + activity.getTitle() + "'," +
                "performance = " + activity.getPerformance() + "," +
                "score = " + activity.getScore() + "," +
                "start = " + activity.getStart().getTimeInMillis() + "," +
                "finish = " + activity.getFinish().getTimeInMillis() + "," +
                "description = '" + activity.getDescription() + "'," +
                "category = '" + activity.getCategory() +
                 "' WHERE id = " + activity.getId());

        database.close();
    }

    public static Activity deleteLastActivity(RegisterActivity context){
        try {
            SQLiteDatabase database = context.getActivity().openOrCreateDatabase(databaseName, context.getContext().MODE_PRIVATE, null);

            Cursor cursor = database.rawQuery("SELECT * FROM activitys ORDER BY id LIMIT 1", null);

            cursor.moveToNext();

            int idColumn = cursor.getColumnIndex("id");
            int titleColumn = cursor.getColumnIndex("title");
            int performanceColumn = cursor.getColumnIndex("performance");
            int scoreColumn = cursor.getColumnIndex("score");
            int startColumn = cursor.getColumnIndex("start");
            int finishColumn = cursor.getColumnIndex("finish");
            int descriptionColumn = cursor.getColumnIndex("description");
            int categoryColumn = cursor.getColumnIndex("category");

            Long id = cursor.getLong(idColumn);
            String title = cursor.getString(titleColumn);
            Double performance = cursor.getDouble(performanceColumn);
            Double score = cursor.getDouble(scoreColumn);
            Long start = cursor.getLong(startColumn);
            Long finish = cursor.getLong(finishColumn);
            String description = cursor.getString(descriptionColumn);
            String category = cursor.getString(categoryColumn);

            Activity activity = SystemAD.toActivity(id, title, performance, start, finish, score, description, category);

            database.execSQL("DELETE FROM activitys WHERE id = " + id);

            return activity;
        }catch (CursorIndexOutOfBoundsException e){
            Toast.makeText(context.getContext(), "Não há nenhum registro de atividade!", Toast.LENGTH_LONG);
            return null;
        }

    }

    public static List<Activity> findAllRegisterActivity(RegisterActivity context){
        SQLiteDatabase database = context.getActivity().openOrCreateDatabase(databaseName,context.getActivity().MODE_PRIVATE, null);

        Cursor cursor = database.rawQuery("SELECT * FROM activitys ORDER BY id DESC;",null);

        List<Activity> listActivity = new ArrayList<>();

        while(cursor.moveToNext()){
            int idColumn = cursor.getColumnIndex("id");
            int titleColumn = cursor.getColumnIndex("title");
            int performanceColumn = cursor.getColumnIndex("performance");
            int scoreColumn = cursor.getColumnIndex("score");
            int startColumn = cursor.getColumnIndex("start");
            int finishColumn = cursor.getColumnIndex("finish");
            int descriptionColumn = cursor.getColumnIndex("description");
            int categoryColumn = cursor.getColumnIndex("category");

            Long id = cursor.getLong(idColumn);
            String title = cursor.getString(titleColumn);
            Double performance = cursor.getDouble(performanceColumn);
            Double score = cursor.getDouble(scoreColumn);
            Long start = cursor.getLong(startColumn);
            Long finish = cursor.getLong(finishColumn);
            String description = cursor.getString(descriptionColumn);
            String category = cursor.getString(categoryColumn);

            Activity activity = SystemAD.toActivity(id, title,performance,start,finish,score,description,category);

            listActivity.add(activity);

        }
        cursor.close();
        database.close();

        return listActivity;
    }

}
