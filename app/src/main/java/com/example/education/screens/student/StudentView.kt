package com.example.education.screens.student
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.education.R
import com.example.education.adapters.StudentAdapter
import com.example.education.databinding.FragmentStudentBinding
import com.example.education.dialogs.StudentAddDialog
import com.example.education.dialogs.StudentUpdateDialog
import com.example.education.model.StudentData
import dev.androidbroadcast.vbpd.viewBinding
/**
 * Creator: Javohir Oromov
 * Date: 24/02/25
 * Project: Education
 * Javohir's MacBook Air
 */
class StudentView: StudentContract.View,Fragment(R.layout.fragment_student) {
    private val binding: FragmentStudentBinding by viewBinding(FragmentStudentBinding::bind)
    private var groupId: Int = -1
    private var presenter: StudentContract.Presenter? = null
    private var addDialog: StudentAddDialog? = null
    private var updateDialog: StudentUpdateDialog? = null
    private var id = 0
    private val adapter by lazy { StudentAdapter() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            groupId = it.getInt("group_id")
        }
        presenter = StudentPresenter(this, groupId)
        addDialog = StudentAddDialog(requireContext())
        updateDialog = StudentUpdateDialog(requireContext())
        initAdapter()
        addClickEvents()
    }

    private fun addClickEvents() {
        binding.btnAddStudent.setOnClickListener {
            presenter?.clickAddStudent()
        }
        addDialog?.setAddClickListener { inputName, inputPhone ->
            presenter?.clickDialogAddBtn(inputName, inputPhone)
        }
        adapter.setDeleteClickListener {
            presenter?.clickDeleteDelete(it)
        }
        adapter.setUpdateClickListener {
            id = it
            presenter?.clickMenuUpdate()
        }
        updateDialog?.setUpdateClickListener { inputName, inputPhone ->
            presenter?.clickDialogUpdateBtn(id,inputName,inputPhone)
        }
    }

    private fun initAdapter() {
        binding.listStudent.adapter = adapter
        binding.listStudent.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun showAddDialog() {
        addDialog?.show()
    }

    override fun setStudentListToView(list: List<StudentData>) {
        adapter.submitList(list)
    }

    override fun showUpdateDialog() {
        updateDialog?.show()
    }

    override fun showToast(message: String) {
        Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()
    }
}