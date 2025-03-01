package com.example.education.screens.course
import com.example.education.model.CourseData
import com.example.education.repostitory.DataBase
import com.example.education.repostitory.DataBaseImpl
/**
 * Creator: Javohir Oromov
 * Date: 24/02/25
 * Project: Education
 * Javohir's MacBook Air
 */
class CourseModel: CourseContract.Model {
    private val dataBase: DataBase = DataBaseImpl.getInstance()

    override fun getAllCourse(): MutableList<CourseData> {
       return dataBase.getAllCourses()
    }

    override fun addCourse(course: CourseData) {
        dataBase.addCourse(course)
    }

    override fun updateCourse(course: CourseData) {
        dataBase.updateCourse(course)
    }

    override fun deleteCourse(id: Int) {
        dataBase.deleteCourse(id)
    }
}