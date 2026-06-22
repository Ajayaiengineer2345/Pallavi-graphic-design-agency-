package com.example.ui

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.Consultation
import com.example.data.ConsultationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AgencyViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ConsultationRepository
    val allConsultations: StateFlow<List<Consultation>>

    // Track services selected by the user to prefill into the consultation request
    val selectedServiceDrafts = mutableStateListOf<String>()

    init {
        val database = AppDatabase.getDatabase(application)
        repository = ConsultationRepository(database.consultationDao())
        allConsultations = repository.allConsultations.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    fun addServiceToDraft(serviceName: String) {
        if (!selectedServiceDrafts.contains(serviceName)) {
            selectedServiceDrafts.add(serviceName)
        }
    }

    fun removeServiceFromDraft(serviceName: String) {
        selectedServiceDrafts.remove(serviceName)
    }

    fun clearServiceDrafts() {
        selectedServiceDrafts.clear()
    }

    fun submitConsultation(
        fullName: String,
        companyName: String,
        email: String,
        phone: String,
        notes: String,
        contactMethod: String,
        preferredTime: String
    ) {
        viewModelScope.launch {
            val listString = selectedServiceDrafts.joinToString(", ")
            val consultation = Consultation(
                fullName = fullName,
                companyName = companyName.ifEmpty { "Individual" },
                email = email,
                phone = phone,
                servicesSelected = listString.ifEmpty { "General Inquiry" },
                notes = notes,
                contactMethod = contactMethod,
                preferredTime = preferredTime
            )
            repository.insert(consultation)
            // Once successfully inserted, clear selected service drafts
            clearServiceDrafts()
        }
    }

    fun deleteConsultation(id: Int) {
        viewModelScope.launch {
            repository.deleteById(id)
        }
    }
}
