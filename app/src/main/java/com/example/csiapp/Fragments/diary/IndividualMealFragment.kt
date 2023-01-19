package com.example.csiapp.Fragments.diary

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.csiapp.R
import com.example.csiapp.databinding.FragmentIndividualMealBinding
import com.example.csiapp.models.MealInfoModel
import com.example.csiapp.models.NutriInfoModel
import com.example.csiapp.viewmodel.MealsViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt
import kotlin.math.roundToLong


class IndividualMealFragment : Fragment() {
    private lateinit var _individualMealBinding: FragmentIndividualMealBinding;
    private val viewModel: MealsViewModel by viewModels()
    private lateinit var mealLogged: MealInfoModel
    val mealList: MutableList<NutriInfoModel> = mutableListOf()
    val finalList: MutableList<NutriInfoModel> = mutableListOf()

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

//        (activity as MainActivity).findViewById<SmoothBottomBar>(R.id.bottom_navigation).visibility=View.GONE

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

        var dateString = ""
        var dayOfTheWeek = ""
        val sdf = SimpleDateFormat("EEEE")

        _individualMealBinding.calendarPickerText.setOnClickListener {
            datePicker.show(requireActivity().supportFragmentManager, "Date-Picker")




            datePicker.addOnPositiveButtonClickListener {
                val dateSelected = datePicker.selection
                val formatter = SimpleDateFormat("dd/MM/yyyy")
                dateString = formatter.format(Date(dateSelected!!))
                dayOfTheWeek = sdf.format(Date(dateSelected))
                _individualMealBinding.calendarPickerText.setText(dateString)
            }


        }

        var totalCal=0L;


        _individualMealBinding.mealSubmitButton.setOnClickListener {
            viewModel.getMeal(_individualMealBinding.mealInputField.text.toString().trim())
            viewModel.mealList.observe(viewLifecycleOwner) {
                mealList.clear()
                Log.d("LIST_TAG", it.toString())
                _individualMealBinding.titleFoodText.text =
                    _individualMealBinding.mealInputField.text.toString();
                _individualMealBinding.titleCaloriesText.text =
                    "Calories (in cal): " + it[0].calories.toString()
                _individualMealBinding.servingSizeText.text =
                    "Serving Size (in g): " + it[0].serving_size_g.toString()
                _individualMealBinding.fatTotalText.text =
                    "Fat total (in g): " + it[0].fat_total_g.toString()
                _individualMealBinding.cholestrolFoodText.text =
                    "Cholesterol (in mg): " + it[0].cholesterol_mg.toString()
                _individualMealBinding.carbsText.text =
                    "Carbohydrates (in g): " + it[0].carbohydrates_total_g.toString()
                _individualMealBinding.sugarText.text =
                    "Sugar (in g): " + it[0].sugar_g.toString()
                _individualMealBinding.proteinFoodText.text =
                    "Protein (in g): " + it[0].protein_g.toString()
                _individualMealBinding.mealCardView.visibility = View.VISIBLE

                mealList.add(it[0])



            }




        }

        _individualMealBinding.doneFab.setOnClickListener {
            finalList.add(mealList[0])
            totalCal= (totalCal+mealList[0].calories).roundToLong()
            Snackbar.make(it, "Meal Logged Successfully", Snackbar.LENGTH_SHORT).show()
        }

        _individualMealBinding.mealDoneButton.setOnClickListener {
            mealLogged = MealInfoModel(dateString, dayOfTheWeek,totalCal, finalList)
            FirebaseFirestore.getInstance().collection("Meals").add(mealLogged)
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DiaryFragment::class.java, null)
                .setReorderingAllowed(true) // name can be null
                .commit()
        }


    }


}