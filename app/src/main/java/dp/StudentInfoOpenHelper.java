package dp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gesangdianzi on 2016/10/29.
 */
public class StudentInfoOpenHelper extends SQLiteOpenHelper {
    /*
    建表
     */
    public  static final String CREATE_STUDENTLIST="create table StudentList("
            + "id integer primary key autoincrement,"
            + "name text,"
            + "code integer,"
            + "image text)";
    public static final String CREATE_STUDENTINFO ="create table StudentInfo("
            + "id integer primary key autoincrement,"
            + "address text,"
            + "number text,"
            + "sex text,"
            + "qq text,"
            + "remarks text)";
    public static final  String CREATE_CLASSINFO="create table ClassInfo("
            + "id integer primary key autoincrement,"
            + "class_name text,"
            + "control_name text,"
            + "class_image text)";
    public StudentInfoOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_STUDENTINFO);
        db.execSQL(CREATE_CLASSINFO);
        db.execSQL(CREATE_STUDENTLIST);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }
}
