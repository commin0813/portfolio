package com.commin.pro.lecture.model;

import java.io.Serializable;


/**************************
 * 강의정보를 담고있는 모델입니다.
 */
public class Model2Course implements Serializable {
    public final String sectionID = "20172";
    private String courseUniverse = null;
    private String universeCode = null;
    private String courseMajor = null;
    private String courseCampus = null;
    private String courseDivide = null;
    private String courseGrade = null;
    private String courseID = null;
    private String courseName = null;
    private String courseCredit = null;
    private String courseTheory = null;
    private String coursePractice = null;
    private String courseProfessor = null;
    private String courseTime = null;
    private String courseRoom = null;
    private String courseLimit = null;
    private String coursePeople = null;
    private String coursePackage = null;
    private String coursePackageEnabled = null;
    private String courseNote = null;
    private String student_class = null;


    private String id = null;
    private int row_index;
    private int column_index;
    private boolean isData; // 그리드뷰 행의 첫 시작은 시간영역이고 나머지 영역은 데이터영역이라 생각해보세요. 데이터영역인지 시간영역인지 확인하기위해 만듭니다.
    private boolean isLecture;
    private String start_time;
    private String end_time;
    private String time_name;
    private String day_name;
    private int group_id;
    private String name_value;

    private int backgroundColor;

    public String getCourseUniverse() {
        return courseUniverse;
    }

    public void setCourseUniverse(String courseUniverse) {
        this.courseUniverse = courseUniverse;
    }

    public String getUniverseCode() {
        return universeCode;
    }

    public void setUniverseCode(String universeCode) {
        this.universeCode = universeCode;
    }

    public String getCourseCampus() {
        return courseCampus;
    }

    public void setCourseCampus(String courseCampus) {
        this.courseCampus = courseCampus;
    }

    public String getCourseDivide() {
        return courseDivide;
    }

    public void setCourseDivide(String courseDivide) {
        this.courseDivide = courseDivide;
    }

    public String getCourseGrade() {
        return courseGrade;
    }

    public void setCourseGrade(String courseGrade) {
        this.courseGrade = courseGrade;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(String courseCredit) {
        this.courseCredit = courseCredit;
    }

    public String getCourseTheory() {
        return courseTheory;
    }

    public void setCourseTheory(String courseTheory) {
        this.courseTheory = courseTheory;
    }

    public String getCoursePractice() {
        return coursePractice;
    }

    public void setCoursePractice(String coursePractice) {
        this.coursePractice = coursePractice;
    }

    public String getCourseProfessor() {
        return courseProfessor;
    }

    public void setCourseProfessor(String courseProfessor) {
        this.courseProfessor = courseProfessor;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public String getCourseRoom() {
        return courseRoom;
    }

    public void setCourseRoom(String courseRoom) {
        this.courseRoom = courseRoom;
    }

    public String getCourseLimit() {
        return courseLimit;
    }

    public void setCourseLimit(String courseLimit) {
        this.courseLimit = courseLimit;
    }

    public String getCoursePeople() {
        return coursePeople;
    }

    public void setCoursePeople(String coursePeople) {
        this.coursePeople = coursePeople;
    }

    public String getCoursePackage() {
        return coursePackage;
    }

    public void setCoursePackage(String coursePackage) {
        this.coursePackage = coursePackage;
    }

    public String getCoursePackageEnabled() {
        return coursePackageEnabled;
    }

    public void setCoursePackageEnabled(String coursePackageEnabled) {
        this.coursePackageEnabled = coursePackageEnabled;
    }

    public String getCourseNote() {
        return courseNote;
    }

    public void setCourseNote(String courseNote) {
        this.courseNote = courseNote;
    }

    public String getCourseMajor() {
        return courseMajor;
    }

    public void setCourseMajor(String courseMajor) {
        this.courseMajor = courseMajor;
    }

    public String getStudent_class() {
        return student_class;
    }

    public void setStudent_class(String student_class) {
        this.student_class = student_class;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRow_index() {
        return row_index;
    }

    public void setRow_index(int row_index) {
        this.row_index = row_index;
    }

    public int getColumn_index() {
        return column_index;
    }

    public void setColumn_index(int column_index) {
        this.column_index = column_index;
    }

    public boolean isData() {
        return isData;
    }

    public void setData(boolean data) {
        isData = data;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getName_value() {
        return name_value;
    }

    public void setName_value(String name_value) {
        this.name_value = name_value;
    }

    public boolean isLecture() {
        return isLecture;
    }

    public void setLecture(boolean lecture) {
        isLecture = lecture;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }


    public String getDay_name() {
        return day_name;
    }

    public void setDay_name(String day_name) {
        this.day_name = day_name;
    }

    public String getTime_name() {
        return time_name;
    }

    public void setTime_name(String time_name) {
        this.time_name = time_name;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
