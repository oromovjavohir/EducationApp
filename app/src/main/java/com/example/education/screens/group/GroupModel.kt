package com.example.education.screens.group

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
class GroupModel: GroupContract.Model {
    private val dataBase: DataBase = DataBaseImpl.getInstance()

    override fun getCourseIdAllGroup(courseId: Int): List<GroupData> {
       return dataBase.getGroupsByCourseId(courseId)
    }

    override fun addGroup(group: GroupData): Int {
        return dataBase.addGroup(group).toInt()
    }

    override fun updateGroup(group: GroupData) {
        dataBase.updateGroup(group)
    }

    override fun deleteGroup(id: Int) {
        dataBase.deleteGroup(id)
    }

    override fun addCourseGroup(courseId: Int, groupId: Int) {
        dataBase.addCourseGroup(courseId,groupId)
    }

    override fun getGroupById(id: Int) {
        dataBase.getGroupById(id)
    }

    override fun getStudentsByGroupId(groupId: Int): List<StudentData> {
       return dataBase.getStudentsByGroupId(groupId)
    }

    override fun deleteCourseGroup(groupId: Int, courseId: Int) {
        dataBase.deleteCourseGroup(courseId,groupId)
    }
}