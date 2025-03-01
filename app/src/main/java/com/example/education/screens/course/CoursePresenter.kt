package com.example.education.screens.course
import android.util.Log
import com.example.education.model.CourseData
/**
 * Creator: Javohir Oromov
 * Date: 24/02/25
 * Project: Education
 * Javohir's MacBook Air
 */
class CoursePresenter(private val view: CourseContract.View) : CourseContract.Presenter {
    private val model: CourseContract.Model = CourseModel()

    init {
        view.setCourseListToView(model.getAllCourse())
    }

    override fun clickAddCourseBtn() {
        view.showAddDialog()
    }

    override fun clickDialogAddBtn(inputName: String) {
        val newCourse = CourseData(0, inputName, 0, 0)
        model.addCourse(newCourse)
        view.setCourseListToView(model.getAllCourse())
    }

    override fun clickMenuDelete(id: Int) {
        model.deleteCourse(id)
        Log.d("KKK","$id")
        view.setCourseListToView(model.getAllCourse())
    }
    override fun clickMenuUpdate() {
        view.showUpdateDialog()
    }

    override fun clickDialogUpdateBtn(id: Int, inputName: String) {
        val updateCourse = CourseData(id,inputName,0,0)
        model.updateCourse(updateCourse)
        view.setCourseListToView(model.getAllCourse())
    }

    override fun clickItem(id: Int) {
        view.openGroupScreen(id)
    }
}