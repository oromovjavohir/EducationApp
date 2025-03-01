package com.example.education.screens.group
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.education.R
import com.example.education.adapters.GroupAdapter
import com.example.education.databinding.FragmentGroupBinding
import com.example.education.dialogs.GroupAddDialog
import com.example.education.dialogs.GroupUpdateDialog
import com.example.education.model.GroupData
import com.example.education.screens.student.StudentView
/**
 * Creator: Javohir Oromov
 * Date: 24/02/25
 * Project: Education
 * Javohir's MacBook Air
 */
class GroupView: GroupContract.View, Fragment() {
    private var _binding: FragmentGroupBinding? = null
    private val binding get() = _binding!!
    private var courseId: Int = -1
    private var presenter: GroupContract.Presenter? = null
    private var addDialog: GroupAddDialog? = null
    private var updateDialog: GroupUpdateDialog? = null
    private var id = 0
    private val adapter by lazy { GroupAdapter() }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentGroupBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            courseId = it.getInt("course_id")
        }
        presenter = GroupPresenter(this,courseId)
        addDialog = GroupAddDialog(requireContext())
        updateDialog = GroupUpdateDialog(requireContext())
        initAdapter()
        addClickEvents()
    }
    private fun initAdapter(){
        binding.listGroup.adapter = adapter
        binding.listGroup.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun addClickEvents(){
        binding.btnAddGroup.setOnClickListener {
            presenter?.clickAddGroup()
        }
        addDialog?.setAddClickListener { inputName, inputSize ->
            presenter?.clickDialogAddBtn(inputName,inputSize,courseId)
        }
        adapter.setDeleteClickListener {
            presenter?.clickMenuDelete(it,courseId)
        }
        adapter.setUpdateClickListener {
            id = it
            presenter?.clickMenuUpdate()
        }
        updateDialog?.setUpdateClickListener { inputName, inputSize ->
            presenter?.clickDialogUpdateBtn(id,inputName,inputSize,courseId)
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
        addDialog?.show()
    }

    override fun setGroupListToVIew(list: List<GroupData>) {
        adapter.submitList(list)
    }

    override fun showUpdateDialog() {
        updateDialog?.show()
    }

    override fun openStudentScreen(groupId: Int) {
        val bundle = Bundle().apply {
            putInt("group_id",groupId)
        }
        val studentFragment = StudentView().apply {
            arguments = bundle
        }
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.container,studentFragment)
            .addToBackStack(studentFragment.javaClass.name)
            .commit()
    }

    override fun showToast(message: String) {
        Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()
    }

    override fun updateDialogDismiss() {
        updateDialog?.dismiss()
    }
}