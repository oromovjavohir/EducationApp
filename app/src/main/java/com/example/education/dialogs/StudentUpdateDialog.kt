package com.example.education.dialogs
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.view.LayoutInflater
import com.example.education.databinding.DialogUpdatestudentBinding
/**
 * Creator: Javohir Oromov
 * Date: 28/02/25
 * Project: Education
 * Javohir's MacBook Air
 */
class StudentUpdateDialog(context: Context): AlertDialog(context) {
    private val binding: DialogUpdatestudentBinding = DialogUpdatestudentBinding.inflate(LayoutInflater.from(context))

    private var updateClickListener:((String,String) -> Unit)? = null

    fun setUpdateClickListener(updateClickListener: (String,String) -> Unit){
        this.updateClickListener = updateClickListener
    }

    init {
        setView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnUpdateStudent.setOnClickListener {

            var isValid = true
            val inputName = binding.inputStudentName.text.toString()
            val inputPhone = binding.inputStudentPhone.text.toString()

        if (inputName.isEmpty()) {
            binding.containerStudentName.error = "Ism kiritilmagan"
            isValid = false
        } else {
            binding.containerStudentName.error = null
        }
        if (inputPhone.isEmpty()) {
            binding.containerStudentPhone.error = "Telefon raqam kiritilmagan"
            isValid = false
        } else {
            binding.containerStudentPhone.error = null
        }
        if (!inputPhone.matches(Regex("\\+998\\d{9}"))) {
            binding.containerStudentPhone.error =
                "Telefon raqami +998 bilan boshlanib, jami 13 ta raqam bo‘lishi kerak!"
            isValid = false
        } else {
            binding.containerStudentPhone.error = null
        }
        if (isValid) {
            updateClickListener?.invoke(inputName, inputPhone)
            dismiss()
        }
    }
        binding.inputStudentName.addTextChangedListener(object: MyTextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                binding.containerStudentName.error = null
            }
        })
        binding.inputStudentPhone.addTextChangedListener(object: MyTextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                binding.containerStudentPhone.error = null
            }
        })
    }
}