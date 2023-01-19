package com.example.csiapp

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.github.anastr.speedviewlib.TubeSpeedometer
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.DataReadRequest
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit


class WaterTracker : AppCompatActivity() {
    val GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1
    lateinit var tubeSpeedometer:TubeSpeedometer
    lateinit var steps : TextView
    lateinit var rem_steps : TextView
    lateinit var spinner: Spinner
    var total_st = 0;
    var sharedpreferences: SharedPreferences? = null
    val MyPREFERENCES = "myprefs"
    val value = "key"


//    val mPrefs = getSharedPreferences("label", 0)
//    val mEditor: SharedPreferences.Editor = mPrefs.edit()



//    public lateinit var stepCountTextView : TextView
    private var stepCount = 0
    private val STEP_COUNT = "step_count"

    private var fitnessOptions  = FitnessOptions.builder()
        .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .build()

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.context_menu, menu)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water_tracker)

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE)
        val mString = sharedpreferences?.getString(value, "1000")
//        stepCount = sharedPreferences.getInt(STEP_COUNT, 0);
         tubeSpeedometer = findViewById(R.id.tubeSpeedometer)

          steps = findViewById(R.id.context_menu_tv)

        rem_steps = findViewById(R.id.remain_steps)

//        val mString = sharedPreferences.getString("TAG", "default value")
        steps.setText( mString.toString())

        tubeSpeedometer.setMinMaxSpeed(0f,mString.toString().toFloat())



        registerForContextMenu(steps)
//        stepCountTextView.setText(stepCount)
        fitnessOptions = FitnessOptions.builder()
            .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .build()
        val account = GoogleSignIn.getAccountForExtension(this, fitnessOptions)


        if (!GoogleSignIn.hasPermissions(account, fitnessOptions)) {


            GoogleSignIn.requestPermissions(
                this, // your activity
                GOOGLE_FIT_PERMISSIONS_REQUEST_CODE, // e.g. 1
                account,
                fitnessOptions)
        } else {
            accessGoogleFit()
        }
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
//        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId) {
            R.id.option_1 -> {
                // Respond to context menu item 1 click.
                steps.setText("1000")
                rem_steps.setText((steps.text.toString().toInt()-total_st).toString())
                tubeSpeedometer.setMinMaxSpeed(0f,1000f)
//                mEditor.putString("tag", "1000").commit()
                updateStepCount("1000")
                true
            }
            R.id.option_2 -> {
                // Respond to context menu item 2 click.
                steps.setText("5000")
                rem_steps.setText((steps.text.toString().toInt()-total_st).toString())
                tubeSpeedometer.setMinMaxSpeed(0f,5000f)
                updateStepCount("5000")
                true
            }
            R.id.option_3 -> {
                // Respond to context menu item 2 click.
                steps.setText("10000")
                rem_steps.setText((steps.text.toString().toInt()-total_st).toString())
                tubeSpeedometer.setMinMaxSpeed(0f,10000f)
                updateStepCount("10000")
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> when (requestCode) {
                GOOGLE_FIT_PERMISSIONS_REQUEST_CODE -> accessGoogleFit()
                else -> {
                    // Result wasn't from Google Fit
                }
            }
            else -> {
                // Permission not granted
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun accessGoogleFit() {
        val end = LocalDateTime.now()
        val start = end.minusYears(1)
        val endSeconds = end.atZone(ZoneId.systemDefault()).toEpochSecond()
        val startSeconds = start.atZone(ZoneId.systemDefault()).toEpochSecond()

        val readRequest = DataReadRequest.Builder()
            .aggregate(DataType.AGGREGATE_STEP_COUNT_DELTA)
            .setTimeRange(startSeconds, endSeconds, TimeUnit.SECONDS)
            .bucketByTime(1, TimeUnit.DAYS)
            .build()
        val account = GoogleSignIn.getAccountForExtension(this, fitnessOptions)
//        Fitness.getHistoryClient(this, account)
//            .readData(readRequest)
//            .addOnSuccessListener({ response ->
//                // Use response data here
//                val stepData: DataSet = response.getDataSet(DataType.TYPE_STEP_COUNT_DELTA)
//
//                // Get the total step count from the DataSet object
//
//                // Get the total step count from the DataSet object
//                val totalStepCount = if (stepData.isEmpty()) 0 else stepData.getDataPoints().get(0)
//                    .getValue(Field.FIELD_STEPS).asInt()
//
//                updateStepCount(totalStepCount)
//                Log.i(ContentValues.TAG, "OnSuccess()")
//            })
//            .addOnFailureListener({ e -> Log.d(ContentValues.TAG, "OnFailure()", e) })
        Fitness.getHistoryClient(this, GoogleSignIn.getAccountForExtension(this, fitnessOptions))
            .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
            .addOnSuccessListener { result ->
                val totalSteps =
                    result.dataPoints.firstOrNull()?.getValue(Field.FIELD_STEPS)?.asInt() ?: 0
//                // Do something with totalSteps
//                stepCountTextView.setText(totalSteps.toString())
//               Log.d("Steps", totalSteps.toString());
//                tubeSpeedometer.maxSpeed = 10000f
// change speed to 140 Km/h

               tubeSpeedometer.withTremble = false
                total_st = totalSteps.toInt()
               tubeSpeedometer.speedTo(totalSteps.toFloat(), 1000)
                rem_steps.setText((steps.text.toString().toInt()-total_st).toString())
                tubeSpeedometer.setMinMaxSpeed(0f,steps.text.toString().toFloat())

            }
            .addOnFailureListener { e ->
                Log.i(TAG, "There was a problem getting steps.", e)
            }
    }
    private fun updateStepCount(newStepCount: String) {


//        stepCountTextView.text = stepCount.toString()
        val editor = sharedpreferences!!.edit()
        editor.putString(value, newStepCount)
        editor.apply()
    }
}