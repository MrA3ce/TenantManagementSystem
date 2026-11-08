package com.example.tenantmanagementsystem.data.repository

import com.example.tenantmanagementsystem.data.model.Tenant

object TenantRepository {

    // One list, shared by the whole app
    val tenants = mutableListOf<Tenant>()

    fun addTenant(tenant: Tenant) {
        tenants.add(tenant)
    }

    // Adds up all the rents. If a rent isn't a valid number, it counts as 0.0
    fun totalRent(): Double {
        return tenants.sumOf { it.rent.toDoubleOrNull() ?: 0.0 }
    }
}