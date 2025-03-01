package com.example.education.dialogs
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import com.example.education.databinding.DialogAddstudentBinding
/**
 * Creator: Javohir Oromov
 * Date: 28/02/25
 * Project: Education
 * Javohir's MacBook Air
 */
class StudentAddDialog(context: Context): AlertDialog(context) {

    private val binding: DialogAddstudentBinding = DialogAddstudentBinding.inflate(LayoutInflater.from(context))

    private var addClickListener: ((String,String) -> Unit)? = null

    fun setAddClickListener(addClickListener: (String,String) -> Unit){
        this.addClickListener =  addClickListener
    }
    init {
        setView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnAddStudent.setOnClickListener {
            var isValid = true
            val inputName = binding.inputStudentName.text.toString().trim()
            val inputPhone = binding.inputStudentPhone.text.toString().trim()
        if (inputName.isEmpty()) {
            Log.d("TTT",inputName)
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
            Log.d("TTT",inputPhone)
            binding.containerStudentPhone.error =
                "Telefon raqami +998 bilan boshlanib, jami 13 ta raqam bo‘lishi kerak!"
            isValid = false
        } else {
            binding.containerStudentPhone.error = null
        }
        if (isValid) {
            addClickListener?.invoke(inputName, inputPhone)
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