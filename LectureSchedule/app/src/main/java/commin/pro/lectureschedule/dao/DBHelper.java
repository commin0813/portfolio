package commin.pro.lectureschedule.dao;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import commin.pro.lectureschedule.ApplicationProperty;


/**
 * Created by user on 2016-12-15.
 */
public class DBHelper extends SQLiteOpenHelper {
    /**
     * @param context
     * @param name    : DBname - lecture.db
     * @param factory : null
     * @param version : version 이 올라가면 DB의 Table을 삭제하고 재생성합니다.
     */

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //최초에 새로운 Database 를 생성합니다.
        db.execSQL("CREATE TABLE " + ApplicationProperty.DATABASE_TABLE_NAME + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," + //DB내부에서 사용되는 id 값
                "id TEXT NOT NULL," + // Row + Column 값으로 만들어진 모든 그리드 뷰에 적용된 구별을 위한 식별 id
                "groupid INTEGER," + // 여러개의 이벤트가 같은 이벤트인지 다른 이벤트인지 알기위한 식별 id
                "classname TEXT," +  // 강의 이름
                "professorname TEXT," + // 교수 이름
                "classroomname TEXT," + // 강의실 이름
                "starttime TEXT," + // 시작시간
                "endtime TEXT," + // 종료시간
                "position TEXT," + // 상 중 하
                "memotitle TEXT," + // 메모 제목
                "memo TEXT," + // 메모 내용
                "ismemo TEXT," + // 이것이 메모를 위한 데이터모델인지 식별하기위한 boolean value
                "isevent TEXT," + // 이것이 강의를 위한 데이터모델인지 식별하기 위한 boolean value
                "isdata TEXT)"); // 이것이 시간을위한 그리드영역인지 데이터가 저장될 작업 영역인지 식별하기 위한  boolean value
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE LECTURE"); // 버전이 올라가면 Table을 삭제합니다.
        onCreate(db);// TABLE 을 재생성 합니다.
    }
}
