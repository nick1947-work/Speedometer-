package com.creationunicorn.speedometer.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.creationunicorn.speedometer.data.LocationDataSource
import com.creationunicorn.speedometer.domain.SpeedCalculator
import com.creationunicorn.speedometer.domain.SpeedMetrics
import com.creationunicorn.speedometer.domain.SpeedUnit
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class SpeedometerViewModel(application: Application) : AndroidViewModel(application) {

    private val locationDataSource = LocationDataSource(application)
    private val speedCalculator = SpeedCalculator()

    private val _metrics = MutableStateFlow(SpeedMetrics())
    val metrics: StateFlow<SpeedMetrics> = _metrics.asStateFlow()

    private val _unit = MutableStateFlow(SpeedUnit.KMH)
    val unit: StateFlow<SpeedUnit> = _unit.asStateFlow()

    private val _isHudMode = MutableStateFlow(false)
    val isHudMode: StateFlow<Boolean> = _isHudMode.asStateFlow()

    private val _isTripActive = MutableStateFlow(false)
    val isTripActive: StateFlow<Boolean> = _isTripActive.asStateFlow()

    private var trackingJob: Job? = null
    private var timerJob: Job? = null
    private var tripStartTime = 0L

    fun toggleUnit() {
        val nextUnit = when (_unit.value) {
            SpeedUnit.KMH -> SpeedUnit.MPH
            SpeedUnit.MPH -> SpeedUnit.KNOTS
            SpeedUnit.KNOTS -> SpeedUnit.KMH
        }
        _unit.value = nextUnit
    }

    fun toggleHudMode() {
        _isHudMode.value = !_isHudMode.value
    }

    fun setSpeedLimit(kmh: Float) {
        val limitMps = kmh / 3.6f
        _metrics.value = _metrics.value.copy(speedLimitMps = limitMps)
    }

    fun startTrip() {
        if (_isTripActive.value) return
        _isTripActive.value = true
        tripStartTime = System.currentTimeMillis()

        timerJob = viewModelScope.launch {
            while (isActive) {
                val elapsed = System.currentTimeMillis() - tripStartTime
                _metrics.value = _metrics.value.copy(elapsedTimeMillis = elapsed)
                delay(1000)
            }
        }

        startTracking()
    }

    fun stopTrip() {
        _isTripActive.value = false
        timerJob?.cancel()
        timerJob = null
    }

    fun resetTrip() {
        stopTrip()
        speedCalculator.reset()
        _metrics.value = SpeedMetrics(speedLimitMps = _metrics.value.speedLimitMps)
    }

    fun startTracking() {
        if (trackingJob != null) return
        trackingJob = viewModelScope.launch {
            locationDataSource.locationFlow().collect { location ->
                val newMetrics = speedCalculator.processLocation(
                    location,
                    _metrics.value.speedLimitMps
                )
                _metrics.value = newMetrics.copy(elapsedTimeMillis = _metrics.value.elapsedTimeMillis)
            }
        }
    }

    fun stopTracking() {
        trackingJob?.cancel()
        trackingJob = null
    }
}
