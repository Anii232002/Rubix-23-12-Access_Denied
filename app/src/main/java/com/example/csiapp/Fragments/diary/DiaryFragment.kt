package com.example.csiapp.Fragments.diary

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.csiapp.Adapter.MealLogAdapter
import com.example.csiapp.R
import com.example.csiapp.databinding.FragmentDiaryBinding
import com.example.csiapp.models.MealInfoModel
import com.example.csiapp.models.NutriInfoModel

import com.example.csiapp.viewmodel.MealsViewModel
import com.google.firebase.firestore.FirebaseFirestore


class DiaryFragment : Fragment() {
    private var _fragmentDiaryBinding: FragmentDiaryBinding? = null
    private val model:MealsViewModel by viewModels()
    private val mealList:MutableList<MealInfoModel> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragmentDiaryBinding = FragmentDiaryBinding.inflate(inflater)
//        (activity as MainActivity?)!!.findViewById<View>(R.id.bottom_navigation).visibility =
//            View.VISIBLE



        return _fragmentDiaryBinding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val docRef = FirebaseFirestore.getInstance().collection("Meals").get().addOnSuccessListener {
            for(document in it){
                    val mealMap=document.data

                    val nutriList=mealMap["nutriList"] as List<NutriInfoModel>

                    Log.d("NUTRI",nutriList.toString())

                mealList.add(MealInfoModel(mealMap["date"].toString(),mealMap["day"].toString(),
                    mealMap["total_calories"] as Long,nutriList))

                _fragmentDiaryBinding!!.mealList.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

                Log.d("HERE",mealList.toString())
                _fragmentDiaryBinding!!.mealList.adapter = MealLogAdapter(mealList)


            }
        }






//        model.singleDayList.observe(viewLifecycleOwner){
//            _fragmentDiaryBinding!!.mealList.adapter = MealLogAdapter(it)
//        }


        _fragmentDiaryBinding!!.logButton.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, IndividualMealFragment::class.java, null)
                .setReorderingAllowed(true)
                .addToBackStack("name") // name can be null
                .commit()
        }




    }



}