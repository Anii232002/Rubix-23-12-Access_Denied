package com.example.csiapp.Fragments.diary

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.csiapp.databinding.FragmentIndividualMealBinding
import com.example.csiapp.viewmodel.MealsViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*


class IndividualMealFragment : Fragment() {
    private lateinit var _individualMealBinding: FragmentIndividualMealBinding;
    private val viewModel:MealsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _individualMealBinding = FragmentIndividualMealBinding.inflate(inflater);
        return _individualMealBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

        _individualMealBinding.calendarPickerText.setOnClickListener {
            datePicker.show(requireActivity().supportFragmentManager, "Date-Picker")

            val dateString: String = ""

            datePicker.addOnPositiveButtonClickListener {
                val dateSelected = datePicker.selection
                val formatter = SimpleDateFormat("dd/MM/yyyy")
                val dateString = formatter.format(Date(dateSelected!!))
                _individualMealBinding.calendarPickerText.setText(dateString)
            }




        }


        _individualMealBinding.mealSubmitButton.setOnClickListener {
            viewModel.getMeal(_individualMealBinding.mealInputField.text.toString().trim())
            viewModel.mealList.observe(viewLifecycleOwner) {
                Log.d("LIST_TAG", it.toString())
                _individualMealBinding.titleFoodText.text=_individualMealBinding.mealInputField.text.toString();
                _individualMealBinding.titleCaloriesText.text= "Calories (in cal): "+viewModel.mealList.value?.get(0)?.calories.toString()
                _individualMealBinding.servingSizeText.text="Serving Size (in g): "+viewModel.mealList.value?.get(0)?.serving_size_g.toString()
                _individualMealBinding.fatTotalText.text="Fat total (in g): "+viewModel.mealList.value?.get(0)?.fat_total_g.toString()
                _individualMealBinding.cholestrolFoodText.text="Cholesterol (in mg): "+viewModel.mealList.value?.get(0)?.cholesterol_mg.toString()
                _individualMealBinding.carbsText.text="Carbohydrates (in g): "+viewModel.mealList.value?.get(0)?.carbohydrates_total_g.toString()
                _individualMealBinding.sugarText.text="Sugar (in g): "+viewModel.mealList.value?.get(0)?.sugar_g.toString()
                _individualMealBinding.proteinFoodText.text="Protein (in g): "+viewModel.mealList.value?.get(0)?.protein_g.toString()
                _individualMealBinding.mealCardView.visibility=View.VISIBLE
            }


        }







    }


}