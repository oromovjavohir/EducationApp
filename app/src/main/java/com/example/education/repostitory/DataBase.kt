package com.example.education.repostitory

import com.example.education.model.CourseData
import com.example.education.model.GroupData
import com.example.education.model.StudentData

/**
 * Creator: Javohir Oromov
 * Date: 23/02/25
 * Project: Education
 * Javohir's MacBook Air
 */
interface DataBase {
    // Course
    fun addCourse(course: CourseData): Long
    fun updateCourse(course: CourseData)
    fun deleteCourse(id: Int)
    fun getCourseById(id: Int): CourseData?
    fun getAllCourses(): MutableList<CourseData>

    // Group
    fun addGroup(group: GroupData): Long
    fun updateGroup(group: GroupData)
    fun deleteGroup(id: Int)
    fun getGroupById(id: Int): GroupData?
    fun getAllGroups(): List<GroupData>


    // Student
    fun addStudent(student: StudentData): Long
    fun updateStudent(student: StudentData)
    fun deleteStudent(id: Int)
    fun getStudentById(id: Int): StudentData?
    fun getAllStudents(): List<StudentData>

    // course_group
    fun getGroupsByCourseId(courseId: Int): List<GroupData>
    fun addCourseGroup(courseId: Int, groupId: Int): Long
    fun deleteCourseGroup(courseId: Int, groupId: Int): Int
    fun updateCourseGroup(oldGroupId: Int, newGroupId: Int, courseId: Int): Int

    //group_student
    fun getStudentsByGroupId(groupId: Int): List<StudentData>
    fun addGroupStudent(groupId: Int, studentId: Int): Long
    fun deleteGroupStudent(groupId: Int, studentId: Int): Int
    fun updateGroupStudent(oldStudentId: Int, newStudentId: Int, groupId: Int): Int
}