package com.example.education.screens.course

import com.example.education.model.CourseData

/**
 * Creator: Javohir Oromov
 * Date: 24/02/25
 * Project: Education
 * Javohir's MacBook Air
 */
interface CourseContract {

    interface Model{
        fun getAllCourse(): MutableList<CourseData>
        fun addCourse(course: CourseData)
        fun updateCourse(course: CourseData)
        fun deleteCourse(id: Int)
    }
    interface View{
        fun showAddDialog()
        fun setCourseListToView(list: MutableList<CourseData>)
        fun showUpdateDialog()
        fun openGroupScreen(id: Int)
    }
    interface Presenter {
        fun clickAddCourseBtn()
        fun clickDialogAddBtn(inputName: String)
        fun clickMenuDelete(id: Int)
        fun clickMenuUpdate()
        fun clickDialogUpdateBtn(id: Int,inputName: String)
        fun clickItem(id: Int)
    }
}
