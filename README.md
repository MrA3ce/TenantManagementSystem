# Tenant Management System

An Android app for landlords to record tenants, see total rent, and call tenants.

## Download
Latest APK: See **Releases** on the right of this GitHub page.

## Features
- Register and log in
- Dashboard showing the number of tenants and total rent
- Add tenants and view all tenants
- Save tenant information using Room Database
- Call tenants
- Navigation drawer and options menu

## Architecture

**UI Layer**
- Activities
- Fragments
- TenantViewModel

**Data Layer**
- Tenant
- TenantDao
- TenantDatabase
- TenantRepository

Screens → TenantViewModel → TenantRepository → Room Database

## Team

| Stage | Member | Admission No. |
|---|---|---|
| 1 Lead | Isaac Mionki | 192100 |
| 2 ViewModel | Irene Maithya | 172079 |
| 3 Room | Jeremy Mwaka | 152715 |
| 4 Screens | Terryanne Wanza | 189817 |
| 5 Data integration | Wagakira Kimberly | 190761 |
| 6 Release | Okemwa Fredrick | 182753 |        