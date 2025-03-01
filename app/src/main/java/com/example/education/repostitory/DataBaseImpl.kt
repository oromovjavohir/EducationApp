package com.example.education.repostitory
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.education.model.CourseData
import com.example.education.model.GroupData
import com.example.education.model.StudentData
/**
 * Creator: Javohir Oromov
 * Date: 23/02/25
 * Project: Education
 * Javohir's MacBook Air
 */
class DataBaseImpl private constructor(context: Context, name: String, version: Int):
    SQLiteOpenHelper(context,name,null,version),DataBase {

        companion object{
            private val DB_NAME = "Javohir's DataBase"
            private val DB_VERSION = 1
            private val COURSE_TABLE = "courses"
            private val COURSE_ID = "id"
            private val COURSE_NAME = "name"
            private val GROUP_TABLE = "groups"
            private val GROUP_ID = "id"
            private val GROUP_NAME = "name"
            private val GROUP_SIZE = "size"
            private val STUDENT_TABLE = "students"
            private val STUDENT_ID = "id"
            private val STUDENT_NAME = "name"
            private val STUDENT_PHONE = "phone"
            private val COURSE_GROUP_TABLE = "course_group"
            private val COURSE_GROUP_ID = "id"
            private val GROUP_STUDENT_TABLE = "group_student"
            private val GROUP_STUDENT_ID = "id"
            lateinit var myDatabaseInstance: DataBase

             fun init(context: Context){
                 if (!(::myDatabaseInstance.isInitialized)){
                     myDatabaseInstance = DataBaseImpl(context, DB_NAME, DB_VERSION)
                 }
            }
            fun getInstance(): DataBase {
                return myDatabaseInstance
            }
        }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "create table $COURSE_TABLE (" +
                    "$COURSE_ID integer primary key autoincrement," +
                    "$COURSE_NAME text not null)")
        db.execSQL(
           "create table $GROUP_TABLE (" +
                   "$GROUP_ID integer primary key autoincrement," +
                   "$GROUP_NAME text not null," +
                   "$GROUP_SIZE integer not null)"
        )
        db.execSQL(
            "create table $STUDENT_TABLE (" +
                    "$STUDENT_ID integer primary key autoincrement," +
                    "$STUDENT_NAME text not null," + "$STUDENT_PHONE text not null)"
        )
        db.execSQL(
            "create table $COURSE_GROUP_TABLE (" +
                    "$COURSE_GROUP_ID integer primary key autoincrement," +
                    "course_id integer not null," +
                    "group_id integer not null," +
                    "foreign key(course_id) references $COURSE_TABLE($COURSE_ID) on delete cascade," +
                    "foreign key(group_id) references $GROUP_TABLE($GROUP_ID) on delete cascade)"
        )
        db.execSQL(
            "create table $GROUP_STUDENT_TABLE("+
                    "$GROUP_STUDENT_ID integer primary key autoincrement," +
                    "group_id integer not null," +
                    "student_id integer not null," +
                    "foreign key(group_id) references $GROUP_TABLE($GROUP_ID) on delete cascade," +
                    "foreign key(student_id) references $STUDENT_TABLE($STUDENT_ID) on delete cascade)"
        )
    }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    override fun addCourse(course: CourseData): Long{
        Log.d("TTT","ishlayapti")
        val cv = ContentValues()
        cv.put(COURSE_NAME, course.name)
        val db = this.writableDatabase
        return db.insert(COURSE_TABLE,null,cv)
    }

    override fun updateCourse(course: CourseData) {
        val cv = ContentValues()
        cv.put(COURSE_NAME,course.name)
        val db = this.writableDatabase
        db.update(COURSE_TABLE,cv, "$COURSE_ID = ?", arrayOf("${course.id}"))
    }

    override fun deleteCourse(id: Int) {
        val db = this.writableDatabase
        db.delete(COURSE_TABLE, "$COURSE_ID = ?", arrayOf("$id"))
    }

    override fun getCourseById(id: Int): CourseData? {
        val db = this.writableDatabase
        val cursor = db.rawQuery("select * from $COURSE_TABLE where $COURSE_ID = ?", arrayOf("$id"))
        if (cursor.moveToFirst()){
            val name = cursor.getString(cursor.getColumnIndex(COURSE_NAME)?: 0)
            val courseId = cursor.getInt(cursor.getColumnIndex(COURSE_ID)?: 0)
            val groupCount = getGroupCountByCourseId(courseId)
            val studentCount = getStudentCountByCourseId(courseId)
            cursor.close()
            return CourseData(courseId,name,groupCount,studentCount)
        }
        return null
    }

    private fun getGroupCountByCourseId(courseId: Int): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT COUNT(*) FROM $COURSE_GROUP_TABLE WHERE course_id = ?",
            arrayOf(courseId.toString())
        )

        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }

        cursor.close()
        return count
    }
    private fun getStudentCountByCourseId(courseId: Int): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT COUNT(*) FROM $GROUP_STUDENT_TABLE WHERE group_id IN (SELECT group_id FROM $COURSE_GROUP_TABLE WHERE course_id = ?)",
            arrayOf(courseId.toString())
        )

        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        return count
    }

    override fun getAllCourses(): MutableList<CourseData> {
        val courses = mutableListOf<CourseData>()
        val db = this.writableDatabase
        val cursor = db.rawQuery("select * from $COURSE_TABLE", null)
        cursor.moveToFirst()

        while (!cursor.isAfterLast){
            val name = cursor.getString(cursor.getColumnIndex(COURSE_NAME)?: 0)
            val courseId = cursor.getInt(cursor.getColumnIndex(COURSE_ID)?: 0)
            val groupCount = getGroupCountByCourseId(courseId)
            val studentCount = getStudentCountByCourseId(courseId)
            courses.add(CourseData(courseId,name,studentCount,groupCount))
            cursor.moveToNext()
        }
        cursor.close()
        return courses
    }

    override fun addGroup(group: GroupData): Long {
        val cv = ContentValues()
        cv.put(GROUP_NAME, group.name)
        cv.put(GROUP_SIZE, group.size)
        val db = this.writableDatabase
       return db.insert(GROUP_TABLE, null,cv)
    }

    override fun updateGroup(group: GroupData) {
        val cv = ContentValues()
        cv.put(GROUP_NAME,group.name)
        cv.put(GROUP_SIZE,group.size)
        val db = this.writableDatabase
        db.update(GROUP_TABLE,cv, "$GROUP_ID = ?", arrayOf("${group.id}"))
    }

    override fun deleteGroup(id: Int) {
        val db = this.writableDatabase
        db.delete(GROUP_TABLE,"$GROUP_ID = ?", arrayOf("$id"))
    }

    override fun getGroupById(id: Int): GroupData? {
        val db = this.writableDatabase
        val cursor = db.rawQuery("select * from $GROUP_TABLE where $GROUP_ID = ?", arrayOf("$id"))
        if (cursor.moveToFirst()){
            val groupId = cursor.getInt(cursor.getColumnIndex(GROUP_ID)?: 0)
            val name = cursor.getString(cursor.getColumnIndex(GROUP_NAME)?: 0)
            val size = cursor.getInt(cursor.getColumnIndex(GROUP_SIZE)?: 0)
            val studentCount = getStudentCountByGroupId(groupId)
            cursor.close()
            return GroupData(groupId,name,size,studentCount)
        }
        return null
    }
    private fun getStudentCountByGroupId(groupId: Int): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT COUNT(*) FROM $GROUP_STUDENT_TABLE WHERE group_id = ?",
            arrayOf(groupId.toString())
        )
        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        return count
    }

    override fun getAllGroups(): List<GroupData> {
        val db = this.writableDatabase
        val groups = mutableListOf<GroupData>()
        val cursor = db.rawQuery("select * from $GROUP_TABLE",null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast){
            val groupId = cursor.getInt(cursor.getColumnIndex(GROUP_ID)?: 0)
            val name = cursor.getString(cursor.getColumnIndex(GROUP_NAME)?: 0)
            val size = cursor.getInt(cursor.getColumnIndex(GROUP_SIZE)?: 0)
            val studentCount = getStudentCountByGroupId(groupId)
            groups.add(GroupData(groupId,name,size,studentCount))
            cursor.moveToNext()
        }
        cursor.close()
        Log.d("WWW","$groups")
        return groups
    }
    override fun getGroupsByCourseId(courseId: Int): List<GroupData> {
        val groups = ArrayList<GroupData>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $GROUP_TABLE WHERE $GROUP_ID IN (SELECT group_id FROM course_group WHERE course_id = ?)"
        Log.d("WWW", query)
        val cursor = db.rawQuery(query, arrayOf("$courseId"))
        Log.d("WWW","$courseId")
        if (cursor.moveToFirst()) {
            do {
                val groupId = cursor.getInt(cursor.getColumnIndexOrThrow(GROUP_ID))
                Log.d("WWW","$groupId")
                val name = cursor.getString(cursor.getColumnIndexOrThrow(GROUP_NAME))
                Log.d("WWW", name)
                val groupSize = cursor.getInt(cursor.getColumnIndexOrThrow(GROUP_SIZE))
                Log.d("WWW","$groupSize")
                val studentCount = getStudentCountByGroupId(groupId)
                Log.d("WWW","$studentCount")
                groups.add(GroupData(groupId, name, groupSize, studentCount))
            } while (cursor.moveToNext())
        }
        cursor.close()
        Log.d("WWW","$groups")
        return groups
    }

    override fun addCourseGroup(courseId: Int, groupId: Int): Long {
        val db = writableDatabase
        val cv = ContentValues().apply {
            put("course_id", courseId)
            put("group_id", groupId)
        }
        return db.insert("course_group", null, cv)
    }

    override fun deleteCourseGroup(courseId: Int, groupId: Int): Int {
        val db = writableDatabase
        return db.delete("course_group", "course_id=? AND group_id=?", arrayOf(courseId.toString(), groupId.toString()))
    }

    override fun updateCourseGroup(oldGroupId: Int, newGroupId: Int, courseId: Int): Int {
        val db = writableDatabase
        val cv = ContentValues().apply {
            put("group_id", newGroupId)
        }
        return db.update("course_group", cv, "course_id=? AND group_id=?", arrayOf(courseId.toString(), oldGroupId.toString()))
    }

    override fun getStudentsByGroupId(groupId: Int): List<StudentData> {
        val students = ArrayList<StudentData>()
        val db = this.writableDatabase
        val query = "select * from $STUDENT_TABLE where $STUDENT_ID in (select student_id from $GROUP_STUDENT_TABLE where group_id = ?)"
        val cursor = db.rawQuery(query, arrayOf("$groupId"))
        if (cursor.moveToFirst()){
            do {
                val studentId = cursor.getInt(cursor.getColumnIndex(STUDENT_ID)?: 0)
                val name = cursor.getString(cursor.getColumnIndex(STUDENT_NAME)?: 0)
                val phone = cursor.getString(cursor.getColumnIndex(STUDENT_PHONE)?: 0)
                students.add(StudentData(studentId,name,phone))
            }while (cursor.moveToNext())
        }
        cursor.close()
        return students
    }

    /*
    val groups = ArrayList<GroupData>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $GROUP_TABLE WHERE $GROUP_ID IN (SELECT group_id FROM course_group WHERE course_id = ?)"
        Log.d("WWW", query)
        val cursor = db.rawQuery(query, arrayOf("$courseId"))
        Log.d("WWW","$courseId")
        if (cursor.moveToFirst()) {
            do {
                val groupId = cursor.getInt(cursor.getColumnIndexOrThrow(GROUP_ID))
                Log.d("WWW","$groupId")
                val name = cursor.getString(cursor.getColumnIndexOrThrow(GROUP_NAME))
                Log.d("WWW", name)
                val groupSize = cursor.getInt(cursor.getColumnIndexOrThrow(GROUP_SIZE))
                Log.d("WWW","$groupSize")
                val studentCount = getStudentCountByGroupId(groupId)
                Log.d("WWW","$studentCount")
                groups.add(GroupData(groupId, name, groupSize, studentCount))
            } while (cursor.moveToNext())
        }
        cursor.close()
        Log.d("WWW","$groups")
        return groups
    * */
    override fun addGroupStudent(groupId: Int, studentId: Int): Long {
        val db = writableDatabase
        val cv = ContentValues().apply {
            put("group_id", groupId)
            put("student_id", studentId)
        }
        return db.insert("group_student", null, cv)
    }

    override fun deleteGroupStudent(groupId: Int, studentId: Int): Int {
        val db = writableDatabase
        return db.delete("group_student", "group_id=? AND student_id=?", arrayOf(groupId.toString(), studentId.toString()))
    }

    override fun updateGroupStudent(oldStudentId: Int, newStudentId: Int, groupId: Int): Int {
        val db = writableDatabase
        val cv = ContentValues().apply {
            put("student_id", newStudentId)
        }
        return db.update("group_student", cv, "group_id=? AND student_id=?", arrayOf(groupId.toString(), oldStudentId.toString()))
    }


    override fun addStudent(student: StudentData): Long {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(STUDENT_NAME,student.name)
        cv.put(STUDENT_PHONE,student.phone)
       return db.insert(STUDENT_TABLE,null,cv)
    }

    override fun updateStudent(student: StudentData) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(STUDENT_NAME,student.name)
        cv.put(STUDENT_PHONE,student.phone)
        db.update(STUDENT_TABLE,cv,"$STUDENT_ID = ?", arrayOf("${student.id}"))
    }

    override fun deleteStudent(id: Int) {
        val db = this.writableDatabase
        db.delete(STUDENT_TABLE,"$STUDENT_ID = ?", arrayOf("$id"))
    }

    override fun getStudentById(id: Int): StudentData? {
        return null
    }


    override fun getAllStudents(): List<StudentData> {
        val db = this.writableDatabase
        val students = mutableListOf<StudentData>()
        val cursor = db.rawQuery("select * from $STUDENT_TABLE",null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast){
            val studentId = cursor.getInt(cursor.getColumnIndex(STUDENT_ID)?: 0)
            val name = cursor.getString(cursor.getColumnIndex(STUDENT_NAME)?: 0)
            val phone = cursor.getString(cursor.getColumnIndex(STUDENT_TABLE)?: 0)
            students.add(StudentData(studentId,name,phone))
            cursor.moveToNext()
        }
        cursor.close()
        return students
    }
}