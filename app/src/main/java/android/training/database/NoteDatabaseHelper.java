package android.training.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.training.recyclerviewdemo.model.CapooCat;
import android.training.recyclerviewdemo.model.Note;
import android.training.recyclerviewdemo.model.NoteColor;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NoteDatabaseHelper extends SQLiteOpenHelper {
    //database constant
    private static final String DATABASE_NAME = "note_sqlite";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "notes";

    //table constant
    private static final String KEY_ID = "id";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_IS_DONE = "is_done";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_COLOR = "color";

    //query String format
    //create new table      table_name        key_id  key_content     key is_done     key_image       key_color
    private static final String QUERY_CREATE_TABLE = "CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s INTEGER, %s TEXT, %s TEXT)";
    //drop table    table_name
    private static final String QUERY_DROP_TABLE = "DROP TABLE IF EXISTS %s";
    //select * from $table_name
    private static final String QUERY_SELECT_ALL = "SELECT * FROM %s";

    public NoteDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_notes_table = String.format(
                QUERY_CREATE_TABLE,
                TABLE_NAME,
                KEY_ID,
                KEY_CONTENT,
                KEY_IS_DONE,
                KEY_IMAGE,
                KEY_COLOR);

        sqLiteDatabase.execSQL(create_notes_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String drop_students_table = String.format(QUERY_DROP_TABLE, TABLE_NAME);

        sqLiteDatabase.execSQL(drop_students_table);

        onCreate(sqLiteDatabase);
    }

    public boolean addNote(Note note) {

        try (SQLiteDatabase database = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(KEY_CONTENT, note.getContent());
            values.put(KEY_IS_DONE, note.getDone() ? 1 : 0);
            values.put(KEY_COLOR, note.getColor().name());
            values.put(KEY_IMAGE, note.getImgNote().name());

            database.insert(TABLE_NAME, null, values);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Note> getAllNotes() {
        List<Note> noteList = new ArrayList<>();

        String selectAllQuery = String.format(QUERY_SELECT_ALL, TABLE_NAME);
        SQLiteDatabase database = this.getReadableDatabase();

        try (Cursor cursor = database.rawQuery(selectAllQuery, null)) {
            if (cursor.moveToFirst()) {
                do {
                    int id = Integer.parseInt(cursor.getString(0));
                    String content = cursor.getString(1);
                    int isDone = Integer.parseInt(cursor.getString(2));
                    String image = cursor.getString(3);
                    String color = cursor.getString(4);

                    noteList.add(new Note(
                            id,
                            content,
                            isDone == 1,
                            CapooCat.valueOf(image),
                            NoteColor.valueOf(color)
                    ));

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return noteList;
    }

    public boolean deleteAllNotes() {
        try (SQLiteDatabase database = this.getWritableDatabase()) {
            database.delete(TABLE_NAME, null, null);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}