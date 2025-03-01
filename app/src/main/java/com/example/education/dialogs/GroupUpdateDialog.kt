package com.example.education.dialogs
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.view.LayoutInflater
import com.example.education.databinding.DialogUpdategroupBinding
/**
 * Creator: Javohir Oromov
 * Date: 28/02/25
 * Project: Education
 * Javohir's MacBook Air
 */
class GroupUpdateDialog(context: Context) : AlertDialog(context) {
    private val binding: DialogUpdategroupBinding = DialogUpdategroupBinding.inflate(LayoutInflater.from(context))

    private var updateClickListener: ((String,String) -> Unit)? = null

    fun setUpdateClickListener(updateClickListener: (String,String) -> Unit){
        this.updateClickListener = updateClickListener
    }
    init {
        setView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnUpdateGroup.setOnClickListener {
            val inputName = binding.inputGroupName.text.toString().trim()
            val inputSize = binding.inputGroupSize.text.toString().trim()
            var isValid = true

            if (inputName.isEmpty()){
                binding.containerGroupName.error = "Nomi kiritilmagan"
                isValid = false
            }else{
                binding.containerGroupName.error = null
            }
            if (inputSize.isEmpty()){
                binding.containerGroupSize.error = "Soni kiritilmagan"
                isValid = false
            }else{
                binding.containerGroupSize.error = null
            }
            if (inputSize.isNotEmpty()) {
                if (inputSize.toInt() > 50) {
                    binding.containerGroupSize.error = "O'quvchilar soni 50 tadan oshmasligi kerak"
                    isValid = false
                } else {
                    binding.containerGroupSize.error = null
                }
            }
            if (isValid){
                updateClickListener?.invoke(inputName,inputSize)
            }
        }
        binding.inputGroupName.addTextChangedListener(object: MyTextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                binding.containerGroupName.error = null
            }
        })
        binding.inputGroupSize.addTextChangedListener(object: MyTextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                binding.containerGroupSize.error = null
            }
        })
    }
}