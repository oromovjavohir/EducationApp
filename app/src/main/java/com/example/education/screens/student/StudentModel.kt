package com.example.education.screens.student
import com.example.education.model.GroupData
import com.example.education.model.StudentData
import com.example.education.repostitory.DataBase
import com.example.education.repostitory.DataBaseImpl
/**
 * Creator: Javohir Oromov
 * Date: 24/02/25
 * Project: Education
 * Javohir's MacBook Air
 */
class StudentModel: StudentContract.Model {

    private val dataBase: DataBase = DataBaseImpl.getInstance()

    override fun getGroupIdAllStudent(groupId: Int): List<StudentData> {
       return dataBase.getStudentsByGroupId(groupId)
    }

    override fun addStudent(student: StudentData): Int {
       return dataBase.addStudent(student).toInt()
    }

    override fun updateStudent(student: StudentData) {
        return dataBase.updateStudent(student)
    }

    override fun deleteStudent(id: Int) {
        dataBase.deleteStudent(id)
    }

    override fun addGroupStudent(groupId: Int, studentId: Int) {
        dataBase.addGroupStudent(groupId,studentId)
    }

    override fun getStudentById(id: Int) {
        dataBase.getStudentById(id)
    }

    override fun deleteGroupStudent(groupId: Int, studentId: Int) {
        dataBase.deleteGroupStudent(groupId,studentId)
    }

    override fun getGroupById(groupId: Int): GroupData? {
        return dataBase.getGroupById(groupId)
    }
}