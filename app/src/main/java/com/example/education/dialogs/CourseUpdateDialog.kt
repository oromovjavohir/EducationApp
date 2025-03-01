package com.example.education.dialogs

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import com.example.education.databinding.DialogUpdatecourseBinding
/**
 * Creator: Javohir Oromov
 * Date: 25/02/25
 * Project: Education
 * Javohir's MacBook Air
 */
class CourseUpdateDialog(context: Context) : AlertDialog(context) {

    private val binding: DialogUpdatecourseBinding = DialogUpdatecourseBinding.inflate(LayoutInflater.from(context))

    private var updateClickListener: ((String) -> Unit)? = null

    fun setUpdateClickListener(updateClickListener: (String) -> Unit){
        this.updateClickListener = updateClickListener
    }

    init {
        setView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.btnUpdateCourse.setOnClickListener {
            val inputName = binding.inputCourseName.text.toString().trim()
            var isValid = true
            if (inputName.isEmpty()){
                binding.containerCourseName.error = "Nomi kiritilmagan"
                isValid = false
            }else{
                binding.containerCourseName.error = null
            }
            if (isValid){
                updateClickListener?.invoke(inputName)
                Log.d("TTT",inputName)
                dismiss()
            }
        }
        binding.inputCourseName.addTextChangedListener(object :MyTextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                binding.containerCourseName.error = null
            }
        })
    }
}