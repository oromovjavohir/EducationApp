package com.example.education.screens.group
import com.example.education.model.GroupData
import com.example.education.model.StudentData

/**
 * Creator: Javohir Oromov
 * Date: 24/02/25
 * Project: Education
 * Javohir's MacBook Air
 */
interface GroupContract {

    interface Model{
        fun getCourseIdAllGroup(courseId: Int): List<GroupData>
        fun addGroup(group: GroupData): Int
        fun updateGroup(group: GroupData)
        fun deleteGroup(id: Int)
        fun addCourseGroup(courseId: Int,groupId: Int)
        fun getGroupById(id: Int)
        fun getStudentsByGroupId(groupId: Int): List<StudentData>
        fun deleteCourseGroup(groupId: Int,courseId: Int)

    }
    interface View{
        fun showAddDialog()
        fun setGroupListToVIew(list: List<GroupData>)
        fun showUpdateDialog()
        fun openStudentScreen(groupId: Int)
        fun showToast(message: String)
        fun updateDialogDismiss()
    }
    interface Presenter{
        fun clickAddGroup()
        fun clickDialogAddBtn(inputName: String,inputSize: String,courseId: Int)
        fun clickMenuDelete(id: Int,courseId: Int)
        fun clickMenuUpdate()
        fun clickDialogUpdateBtn(id: Int,inputName: String,inputSize: String,courseId: Int)
        fun clickItem(groupId: Int)
    }
}