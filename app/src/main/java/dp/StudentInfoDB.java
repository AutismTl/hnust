package dp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tools.Person;

/**
 * Created by gesangdianzi on 2016/10/29.
 */
public class StudentInfoDB {
    /*
    数据库名
     */
    public static final String DB_NAME="student_info";
    /*
    数据库版本
     */
    public static final int VERSION=1;

    private static StudentInfoDB studentInfoDB;
    private  SQLiteDatabase db;

    //构造方法私有化
    private StudentInfoDB(Context context){
        StudentInfoOpenHelper dbHelper=new StudentInfoOpenHelper(context,DB_NAME,null,VERSION);
        db=dbHelper.getWritableDatabase();
    }
    //获得StudentInfoDB实例
    public synchronized static StudentInfoDB getInstance(Context context){
        if(studentInfoDB==null){
            studentInfoDB=new StudentInfoDB(context);
        }
        return studentInfoDB;
    }

    //从数据库读取学生列表
    public List<Person> loadStudentList(){
        final List <Person> list=new ArrayList<Person>();
        final Cursor cursor=db.query("StudentInfo",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                final String name=cursor.getString(cursor.getColumnIndex("name"));
                final int id=cursor.getInt(cursor.getColumnIndex("code"));
                final String imageId=cursor.getString(cursor.getColumnIndex("image"));
                final String sex=cursor.getString(cursor.getColumnIndex("sex"));
                final String home=cursor.getString(cursor.getColumnIndex("home"));
                final String phone=cursor.getString(cursor.getColumnIndex("phone"));
                final String address=cursor.getString(cursor.getColumnIndex("address"));
                final String number=cursor.getString(cursor.getColumnIndex("number"));
                final String qq=cursor.getString(cursor.getColumnIndex("qq"));
                final String remarks=cursor.getString(cursor.getColumnIndex("remarks"));
                Person person=new Person(name,id,"http://"+imageId,address,sex,number,qq,remarks,home,phone);
                list.add(person);
            }while (cursor.moveToNext());
        }
        if(cursor!=null){
            cursor.close();
        }
        return list;
    }
    //处理返回数据存到数据库
    public synchronized  boolean handleStudentListResponse(String response){
            db.execSQL("delete from StudentInfo");
        try{
            JSONArray jsonArray=new JSONArray(response);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String name=jsonObject.getString("XM");
                int id=jsonObject.getInt("XH");
                String imageId=jsonObject.getString("image");
                String sex=jsonObject.getString("sex");
                String address=jsonObject.getString("address");
                String phone=jsonObject.getString("phone");
                String home=jsonObject.getString("home");
                String number=jsonObject.getString("number");
                String qq=jsonObject.getString("QQ");
                String remarks=jsonObject.getString("remarks");
                ContentValues values=new ContentValues();
                values.put("sex",sex);
                values.put("address",address);
                values.put("home",home);
                values.put("phone",phone);
                values.put("qq",qq);
                values.put("number",number);
                values.put("remarks",remarks);
                values.put("name",name);
                values.put("code",id);
                values.put("image",imageId);
                db.insert("StudentInfo",null,values);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

}
