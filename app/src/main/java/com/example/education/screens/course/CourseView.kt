package com.example.education.screens.course

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.education.R
import com.example.education.adapters.CourseAdapter
import com.example.education.databinding.FragmentCourseBinding
import com.example.education.dialogs.CourseAddDialog
import com.example.education.dialogs.CourseUpdateDialog
import com.example.education.model.CourseData
import com.example.education.screens.group.GroupView

/**
 * Creator: Javohir Oromov
 * Date: 24/02/25
 * Project: Education
 * Javohir's MacBook Air
 */
class CourseView : CourseContract.View, Fragment() {
    private var _binding: FragmentCourseBinding? = null
    private val binding get() = _binding!!
    private var presenter: CourseContract.Presenter? = null
    private lateinit var addDialog: CourseAddDialog
    private lateinit var updateDialog: CourseUpdateDialog
    private lateinit var adapter: CourseAdapter
    private var updateId = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCourseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addDialog = CourseAddDialog(requireContext())
        updateDialog = CourseUpdateDialog(requireContext())
        initAdapter()
        addClickEvents()
        presenter = CoursePresenter(this)
    }

    private fun initAdapter() {
        adapter = CourseAdapter()
        binding.courseList.adapter = adapter
        binding.courseList.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun addClickEvents() {
        binding.btnAddCourse.setOnClickListener {
            presenter?.clickAddCourseBtn()
        }
        addDialog.setAddClickListener {
            presenter?.clickDialogAddBtn(it)
        }
        adapter.setDeleteClickListener {
            presenter?.clickMenuDelete(it)
        }
        adapter.setUpdateClickListener {
            updateId = it
            presenter?.clickMenuUpdate()
        }
        updateDialog.setUpdateClickListener { inputName ->
            presenter?.clickDialogUpdateBtn(updateId, inputName)
        }
        adapter.setItemClickListener {
            presenter?.clickItem(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showAddDialog() {
        addDialog.show()
    }

    override fun setCourseListToView(list: MutableList<CourseData>) {
        adapter.submitList(list)
    }

    override fun showUpdateDialog() {
        updateDialog.show()
    }

    override fun openGroupScreen(id: Int) {
        val bundle = Bundle().apply {
            putInt("course_id", id)
        }
        val groupFragment = GroupView().apply {
            arguments = bundle
        }
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.container, groupFragment)
            .addToBackStack("course")
            .commit()
    }
}