package com.example.weatherapp.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.weatherapp.databinding.WeatherDetailLayoutBinding

class WeatherDetailDailog: DialogFragment() {

    lateinit var binding: WeatherDetailLayoutBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= WeatherDetailLayoutBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
        setupClickListeners(view)
    }

    private fun setView() {
        binding.tvTemp.text = "Temp = " +this.arguments?.get(TEMPERATURE).toString()
        binding.tvHumidity.text = "Humidity ="+this.arguments?.get(HUMIDITY).toString()
        binding.tvWeatherSpeed.text = "Wind Speed ="+this.arguments?.get(WIND_SPEED).toString()
        binding.tvWeatherCondition.text = "Condition ="+this.arguments?.get(WEATHER_CONDITION).toString()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupClickListeners(view: View) {
       binding.btnOk.setOnClickListener {
            dismiss()
        }
    }

    companion object {

        private const val TEMPERATURE = "temperature"
        private const val WIND_SPEED = "wind_speed"
        private const val HUMIDITY = "humidity"
        private const val WEATHER_CONDITION = "weather_condition"

        fun newInstance(temp: String,
                        humidity: String,
                        speed: String,
                        weather_condition:String): WeatherDetailDailog {
            val args = Bundle()
            args.putString(TEMPERATURE, temp)
            args.putString(WIND_SPEED, speed)
            args.putString(HUMIDITY, humidity)
            args.putString(WEATHER_CONDITION, weather_condition)
            val fragment = WeatherDetailDailog()
            fragment.arguments = args
            return fragment
        }

    }

}