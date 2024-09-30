package com.example.weather_app_iti.view.alert

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mvvm.view_model.WeatherViewModelFactory
import com.example.weather_app_iti.model.local.Alert
import com.example.weather_app_iti.model.local.LocalDataSource
import com.example.weather_app_iti.model.remote.RemoteDataSource
import com.example.weather_app_iti.model.repo.Repository
import com.example.weather_app_iti.view.setting.Setting
import com.example.weather_app_iti.databinding.FragmentAlertBinding
import com.example.weather_app_iti.view_model.WeatherViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Calendar

class AlertFragment : Fragment() {
    lateinit var binding: FragmentAlertBinding
    lateinit var viewModel: WeatherViewModel
    lateinit var factory: WeatherViewModelFactory
    lateinit var adapter: AlertAdapter
    lateinit var alarmManager: AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        viewModel.getAlerts()
        alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    private fun setupViewModel() {
        factory = WeatherViewModelFactory(
            Repository(
                RemoteDataSource(), LocalDataSource(requireContext())
            )
        )
        viewModel = ViewModelProvider(this, factory)[WeatherViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlertBinding.inflate(inflater, container, false)
        lifecycleScope.launch {
            viewModel._alerts.collectLatest {
                adapter= AlertAdapter(it){
                 viewModel.deleteAlert(it)
                 cancelAlarm(requireContext(),it.id.toInt())
                }
                binding.alertRecycle.adapter=adapter
            }
        }

        binding.addAlert.setOnClickListener {
            pickDateTime()
        }
        return binding.root
    }
    @SuppressLint("ScheduleExactAlarm", "SuspiciousIndentation")
    fun setAlarm(context: Context, timeInMillis: Long, alert: Alert) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlertReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, alert.id.toInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT)
            Setting.alert.add(alert)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
    }
    fun cancelAlarm(context: Context, alarmId: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlertReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        Setting.alert.removeAt(0)
        alarmManager.cancel(pendingIntent)
    }

    @SuppressLint("ScheduleExactAlarm", "WrongConstant")
    private fun pickDateTime() {
        val calendar = Calendar.getInstance()
        val alert= Alert()
        // Time picker
        val timePicker = TimePickerDialog(requireContext(), { _, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)

            if (calendar.timeInMillis > System.currentTimeMillis()) {
                alert.time=" $hourOfDay:$minute \n"
                alert.id=minute.toString()
               viewModel.insertAlert(alert)
               setAlarm(requireContext(),calendar.timeInMillis,alert)
                Toast.makeText(requireContext(), "Alarm set!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Invalid time!", Toast.LENGTH_SHORT).show()
            }

        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)

        // Date picker
        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                 alert.date=" $year:$month:$dayOfMonth \n"
                // After picking the date, show the time picker
                timePicker.show()

            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePicker.show()
    }
}