package com.example.todoapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_task.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

const val DB_NAME = "todo.db"

class TaskActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var myCalendar: Calendar

    lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    lateinit var timeSetListener: TimePickerDialog.OnTimeSetListener

    private val labels = arrayListOf("Personal", "Business", "Shopping", "Banking")

    var finalDate = 0L
    var finalTime = 0L

    val db by lazy{
        AppDatabase.getDatabase(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        setupSpinner()

        dateEdt.setOnClickListener(this)
        timeEdt.setOnClickListener(this)
        saveBtn.setOnClickListener(this)
    }

    private fun setupSpinner() {
        labels.sort()
        val adapter = ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,labels)
        spinnerCategory.adapter =adapter
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.dateEdt -> {
                setListener()
            }
            R.id.timeEdt -> {
                setTimeListener()
            }
            R.id.saveBtn -> {
                saveTodoItem()
            }
        }
    }

    private fun saveTodoItem() {
        val category = spinnerCategory.selectedItem.toString()
        val title = titleInput.editText?.text.toString()
        val descr = descInput.editText?.text.toString()

        GlobalScope.launch(Dispatchers.Main) {
            val id = withContext(Dispatchers.IO) {
                return@withContext db.todoDao().insertTodoItem(
                    TodoItem(
                        title,
                        descr,
                        category,
                        finalDate,
                        finalTime
                    )
                )
            }
            finish()
        }
    }

    private fun setTimeListener() {
        myCalendar = Calendar.getInstance()
        timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker: TimePicker, hoursOfDay: Int, min: Int ->
            myCalendar.set(Calendar.HOUR_OF_DAY,hoursOfDay)
            myCalendar.set(Calendar.MINUTE,min)
            updateTime()
        }
        val timePickerDialog = TimePickerDialog(
            this,timeSetListener,
            myCalendar.get(Calendar.HOUR_OF_DAY),
            myCalendar.get(Calendar.MINUTE), false
        )
        timePickerDialog.show()
    }

    private fun updateTime() {
        val format = "hh:mm a"
        val sdf = SimpleDateFormat(format)
        timeEdt.setText(sdf.format(myCalendar.time))
        finalTime = myCalendar.time.time
    }

    private fun setListener() {
        myCalendar = Calendar.getInstance()
        dateSetListener = DatePickerDialog.OnDateSetListener { datePicker: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            myCalendar.set(Calendar.YEAR,year)
            myCalendar.set(Calendar.MONTH,month)
            myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            updateDate()
        }
        val datePickerDialog = DatePickerDialog(
            this,dateSetListener,myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun updateDate() {
        val format = "EEE, dd MMM yyyy"
        val sdf = SimpleDateFormat(format)
        dateEdt.setText(sdf.format(myCalendar.time))
        timeInput.visibility = View.VISIBLE
        finalDate = myCalendar.time.time
    }
}