package com.example.education.screens.student
import com.example.education.model.StudentData
/**
 * Creator: Javohir Oromov
 * Date: 24/02/25
 * Project: Education
 * Javohir's MacBook Air
 */
class StudentPresenter(private val view: StudentView,private val groupId: Int): StudentContract.Presenter {
    private val model: StudentContract.Model = StudentModel()

    init {
        view.setStudentListToView(model.getGroupIdAllStudent(groupId))
    }

    override fun clickAddStudent() {
        view.showAddDialog()
    }

    override fun clickDialogAddBtn(inputName: String, inputPhone: String) {
        val groupSize = model.getGroupById(groupId)
        val allStudents = model.getGroupIdAllStudent(groupId)
        if (groupSize?.size!! <= allStudents.size){
            view.showToast("Guruh to'lgan")
            return
        }else {
            val studentId = model.addStudent(StudentData(0, inputName, inputPhone))
            model.addGroupStudent(groupId, studentId)
            view.setStudentListToView(model.getGroupIdAllStudent(groupId))
        }
    }

    override fun clickDeleteDelete(id: Int) {
        model.deleteStudent(id)
        model.deleteGroupStudent(groupId,id)
        view.setStudentListToView(model.getGroupIdAllStudent(groupId))
    }

    override fun clickMenuUpdate() {
        view.showUpdateDialog()
    }

    override fun clickDialogUpdateBtn(id: Int, inputName: String, inputPhone: String) {
        model.updateStudent(StudentData(id,inputName,inputPhone))
        view.setStudentListToView(model.getGroupIdAllStudent(groupId))
    }
}