package com.example.tenantmanagementsystem.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
// The ViewModel (ui layer) USES the data layer, so it imports from "data"
import com.example.tenantmanagementsystem.data.model.Tenant
import com.example.tenantmanagementsystem.data.repository.TenantRepository

// A Jetpack ViewModel: Android keeps it alive while the screen rotates,
// and clears it when MainActivity finishes
class TenantViewModel : ViewModel() {

    // ── 1. THE DATA ───────────────────────────────────────────────
    // PRIVATE and MUTABLE: only this ViewModel can change it
    private val _tenants = MutableLiveData<List<Tenant>>(TenantRepository.tenants.toList())

    // PUBLIC and READ-ONLY: screens can watch it, not change it
    val tenants: LiveData<List<Tenant>> = _tenants

    // ── 2. TRANSFORMATIONS: text ready for the screens ────────────
    // map() makes a NEW LiveData that updates whenever tenants changes
    val tenantCountText: LiveData<String> = tenants.map { list ->
        "Tenants\n${list.size}"
    }

    val totalRentText: LiveData<String> = tenants.map { list ->
        val total = list.sumOf { it.rent.toDoubleOrNull() ?: 0.0 }
        "Total rent\nKSh %,.0f".format(total)
    }

    val allTenantsText: LiveData<String> = tenants.map { list ->
        if (list.isEmpty()) "No tenants yet. Add one from the Dashboard."
        else list.joinToString("\n\n") { it.summary() }
    }

    // ── 3. ACTIONS the screens can ask for ────────────────────────
    // Checks the input and saves the tenant.
    // Returns the new Tenant, or null if the input was not valid
    fun addTenant(name: String, phone: String, rent: String): Tenant? {
        val cleanName = name.trim()
        val cleanPhone = phone.trim()
        val cleanRent = rent.trim()

        // Business rule: all three fields are required
        if (cleanName.isEmpty() || cleanPhone.isEmpty() || cleanRent.isEmpty()) {
            return null
        }

        val tenant = Tenant(cleanName, cleanPhone, cleanRent)

        TenantRepository.addTenant(tenant)                    // ask the DATA LAYER to store it
        _tenants.value = TenantRepository.tenants.toList()    // every observer updates

        return tenant
    }

    // ── 4. LIFECYCLE LOGS ─────────────────────────────────────────
    init {
        Log.d("TENANT_LIFECYCLE", "   TenantViewModel: created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("TENANT_LIFECYCLE", "   TenantViewModel: onCleared")
    }
}