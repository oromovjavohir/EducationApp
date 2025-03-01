package com.example.education.screens.student

import com.example.education.model.GroupData
import com.example.education.model.StudentData

/**
 * Creator: Javohir Oromov
 * Date: 24/02/25
 * Project: Education
 * Javohir's MacBook Air
 */

interface StudentContract {

    interface Model{
        fun getGroupIdAllStudent(groupId: Int): List<StudentData>
        fun addStudent(student: StudentData): Int
        fun updateStudent(student: StudentData)
        fun deleteStudent(id: Int)
        fun addGroupStudent(groupId: Int,studentId: Int)
        fun getStudentById(id: Int)
        fun deleteGroupStudent(groupId: Int,studentId: Int)
        fun getGroupById(groupId: Int): GroupData?
    }
    interface View{
        fun showAddDialog()
        fun setStudentListToView(list: List<StudentData>)
        fun showUpdateDialog()
        fun showToast(message: String)
    }
    interface Presenter{
        fun clickAddStudent()
        fun clickDialogAddBtn(inputName: String,inputPhone:String)
        fun clickDeleteDelete(id: Int)
        fun clickMenuUpdate()
        fun clickDialogUpdateBtn(id: Int,inputName: String,inputPhone: String)
    }
}
// Dagger
// Android Broadcast Receiver
// Server
// Api