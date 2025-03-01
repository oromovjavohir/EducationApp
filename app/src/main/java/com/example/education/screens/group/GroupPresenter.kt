package com.example.education.screens.group
import com.example.education.model.GroupData
/**
 * Creator: Javohir Oromov
 * Date: 24/02/25
 * Project: Education
 * Javohir's MacBook Air
 */
class GroupPresenter(private val view: GroupContract.View, courseId: Int): GroupContract.Presenter {
    private val model: GroupContract.Model = GroupModel()

    init {
        view.setGroupListToVIew(model.getCourseIdAllGroup(courseId))
    }

    override fun clickAddGroup() {
        view.showAddDialog()
    }

    override fun clickDialogAddBtn(inputName: String, inputSize: String,courseId: Int) {
        val newGroup = GroupData(0,inputName,inputSize.toInt(),0)
        model.addGroup(newGroup)
        val groupId = model.addGroup(newGroup)
        model.addCourseGroup(courseId,groupId)
        view.setGroupListToVIew(model.getCourseIdAllGroup(courseId))
    }
    override fun clickMenuDelete(id: Int,courseId: Int) {
        model.deleteGroup(id)
        model.deleteCourseGroup(id,courseId)
        view.setGroupListToVIew(model.getCourseIdAllGroup(courseId))
    }

    override fun clickMenuUpdate() {
        view.showUpdateDialog()
    }

    override fun clickDialogUpdateBtn(id: Int, inputName: String, inputSize: String,courseId: Int) {
        val students = model.getStudentsByGroupId(id)
        if (students.size > inputSize.toInt()){
            view.showToast("${students.size} ta o'quvchi hajmini $inputSize ga o'zgartirib bo'lmaydi")
        }else{
            val updateGroup = GroupData(id,inputName,inputSize.toInt(),0)
            model.updateGroup(updateGroup)
            view.setGroupListToVIew(model.getCourseIdAllGroup(courseId))
            view.updateDialogDismiss()
        }
    }
    override fun clickItem(groupId: Int) {
        view.openStudentScreen(groupId)
    }
}