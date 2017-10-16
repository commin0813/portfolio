package commin.pro.lectureschedule.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



import java.util.ArrayList;

import commin.pro.lectureschedule.ApplicationProperty;
import commin.pro.lectureschedule.model.Model2Lecture;

/**
 * Created by user on 2016-12-15.
 * 이 클래스는 DataBase를 사용하기 위한 클래스입니다.
 * Singlton Pattern을 이용해 DBHelper 클래스의 객체를 최초에 한번 생성하게끔 하여 재사용합니다.
 * 최초생성은 Application 클래스에서 생성하여 이곳으로 객체를 넘깁니다.
 */
public class Dao2Lecture {
    private static final String LOG_TAG = "Dao2Lecture";

    private static class Singleton_DB {
        private static DBHelper db = null;//최초에는 DB는 NUll 입니다.
    }

    public static void setDatabase(final DBHelper db) {
        Singleton_DB.db = db;//Application 클래스에서 DBHelper 클래스를 만들어 이곳으로 넘깁니다.
    }

    public static void insertDatabaseForMemo(Model2Lecture model) {
        /**
         * Memo 데이터가 담긴 Model2Lecture를 받아 Database에 저장합니다.
         * 이때 강의 데이터는 없습니다.
         **/

        String id = model.getId();
        int groupid = model.getGroupid();
        String memotitle = model.getMemo_title();
        String memo = model.getMemo();
        String ismemo = String.valueOf(model.isMemo());
        String isdata = String.valueOf(model.isData());

        String sql = "insert into " + ApplicationProperty.DATABASE_TABLE_NAME
                + " (id,groupid,memotitle,memo,ismemo,isdata)" +
                " values" +
                "('" + id + "'," +
                "'" + groupid + "'," +
                "'" + memotitle + "'," +
                "'" + memo + "'," +
                "'" + ismemo + "'," +
                "'" + isdata + "');";

        SQLiteDatabase db = Singleton_DB.db.getWritableDatabase();
        db.execSQL(sql);
    }

    public static void insertDatabase(Model2Lecture model) {
        /**
         * 강의 데이터가 담긴 Model2Lecture를 받아 Database에 저장합니다.
         * 이때 Memo 데이터는 없습니다.
         */
        String id = model.getId();
        int groupid = model.getGroupid();
        String classname = model.getClass_name();
        String classroomname = model.getClassroom_name();
        String professorname = model.getProfessor_name();
        String position = model.getPosition();
        String starttime = model.getStart_time();
        String endtime = model.getEnd_time();
        String isevent = String.valueOf(model.isEvents());
        String isdata = String.valueOf(model.isData());


        String sql = "insert into " + ApplicationProperty.DATABASE_TABLE_NAME
                + " (id,groupid,classname,professorname,classroomname,starttime,endtime,position,isevent,isdata)" +
                " values" +
                "('" + id + "'," +
                "'" + groupid + "'," +
                "'" + classname + "'," +
                "'" + professorname + "'," +
                "'" + classroomname + "'," +
                "'" + starttime + "'," +
                "'" + endtime + "'," +
                "'" + position + "'," +
                "'" + isevent + "'," +
                "'" + isdata + "');";

        SQLiteDatabase db = Singleton_DB.db.getWritableDatabase();
        db.execSQL(sql);

    }

    public static void insertDatabase(ArrayList<Model2Lecture> models) {
        /**
         * 이 메서드는 강의를 Database에 저장할때, 예를 들어 10시부터 12시까지의 스케줄이라면,
         * 같은 Group id 를 가지는 모델 여러개를 Array Collection에 담아 이곳으로 보내게되고
         * ArrayList에서 하나씩 뽑아 데이타 베이스에 저장합니다.
         */
        for (Model2Lecture model : models) {
            insertDatabase(model);
        }
    }

    public static ArrayList<Model2Lecture> queryAllData() {
        /**
         * 이 메서드는 Databas에 있는 모든 데이터를 ArrayList형태로 return해주는 메서드입니다.
         */
        try {
            String sql = "select * from " + ApplicationProperty.DATABASE_TABLE_NAME;
            SQLiteDatabase db = Singleton_DB.db.getReadableDatabase();

            Cursor cursor = db.rawQuery(sql, null);
            ArrayList<Model2Lecture> models = new ArrayList<Model2Lecture>();
            if (cursor != null) {

                while (cursor.moveToNext()) {
                    Model2Lecture model = new Model2Lecture();
                    model.setId(cursor.getString(1));
                    model.setGroupid(cursor.getInt(2));
                    model.setClass_name(cursor.getString(3));
                    model.setProfessor_name(cursor.getString(4));
                    model.setClassroom_name(cursor.getString(5));
                    model.setStart_time(cursor.getString(6));
                    model.setEnd_time(cursor.getString(7));
                    model.setPosition(cursor.getString(8));
                    model.setMemo_title(cursor.getString(9));
                    model.setMemo(cursor.getString(10));
                    model.setMemo(Boolean.valueOf(cursor.getString(11)));
                    model.setEvents(Boolean.valueOf(cursor.getString(12)));
                    model.setData(Boolean.valueOf(cursor.getString(13)));
                    models.add(model);
                }

                return models;
            }
            return null;
        } catch (Exception e) {
            throw e;
        }
    }

    public static void deleteData(Model2Lecture model) {
        /**
         * Database delete ~ where 을 이용하여 조건식으로 데이터베이스에 모델을 지웁니다.
         * 이때 지우는 조건은 groupid 입니다.
         * 같은 groupid 를 가진 모델은 모두 데이터베이스에서 삭제되기때문에
         * 여러 줄에 걸쳐 그려진 강의가 화면에서 삭제되게 됩니다.
         */
        int groupid = model.getGroupid();
        String sql = "delete from " + ApplicationProperty.DATABASE_TABLE_NAME + " where groupid = '" + groupid + "';";
        SQLiteDatabase db = Singleton_DB.db.getWritableDatabase();
        db.execSQL(sql);

    }


}
