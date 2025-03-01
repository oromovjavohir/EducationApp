package com.example.education.dialogs
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import com.example.education.databinding.DialogAddcourseBinding
/**
 * Creator: Javohir Oromov
 * Date: 24/02/25
 * Project: Education
 * Javohir's MacBook Air
 */
class CourseAddDialog(context: Context): AlertDialog(context) {
    private val binding: DialogAddcourseBinding = DialogAddcourseBinding.inflate(LayoutInflater.from(context))

    private var addClickListener: ((String) -> Unit)? = null

    fun setAddClickListener(addClickListener: (String) -> Unit){
        this.addClickListener = addClickListener
    }

    init {
        setView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.btnAddCourse.setOnClickListener {
            val inputName = binding.inputCourseName.text.toString().trim()
            var isValid = true
            if (inputName.isEmpty()){
                binding.containerCourseName.error = "Nomi kiritilmagan"
                isValid = false
            }else{
                binding.containerCourseName.error = null
            }
            if (isValid){
                addClickListener?.invoke(inputName)
                dismiss()
            }
        }
        binding.inputCourseName.addTextChangedListener(object: MyTextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                    binding.containerCourseName.error = null
            }
        } )
    }
}
interface MyTextWatcher: TextWatcher{

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }
}