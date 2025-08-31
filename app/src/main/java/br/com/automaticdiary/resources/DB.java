package br.com.automaticdiary.resources;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import br.com.automaticdiary.entities.Activity;
import br.com.automaticdiary.ui.register_fragments.RegisterActivity;

public class DB {

    public static void registerActivity(Activity activity, RegisterActivity context){
        SQLiteDatabase database = context.getActivity().openOrCreateDatabase("AutomaticDiaryDB", context.getActivity().MODE_PRIVATE,null);

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

    }

}
