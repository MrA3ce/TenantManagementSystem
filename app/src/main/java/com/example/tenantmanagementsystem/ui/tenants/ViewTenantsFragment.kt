package com.example.tenantmanagementsystem.ui.tenants

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.tenantmanagementsystem.databinding.FragmentViewTenantsBinding
import androidx.fragment.app.activityViewModels
import com.example.tenantmanagementsystem.ui.viewmodel.TenantViewModel

class ViewTenantsFragment : Fragment() {

    private var _binding: FragmentViewTenantsBinding? = null
    private val binding get() = _binding!!
    // The SHARED ViewModel: every Fragment in MainActivity gets the SAME one
    private val viewModel: TenantViewModel by activityViewModels()
    private val args: ViewTenantsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewTenantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // The tenant sent from Add Tenant, or null if we came from the Dashboard
        val newTenant = args.newTenant

        // Data Binding: gives the tenant to <variable name="tenant"> in the layout
        binding.tenant = newTenant

        // No new tenant? Hide "Just added", the box and the Call button together
        if (newTenant == null) {
            binding.newTenantGroup.visibility = View.GONE
        }

        binding.callButton.setOnClickListener {
            if (newTenant != null) {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${newTenant.phone}"))
                startActivity(intent)
            }
        }

        // OBSERVER: runs now, and again every time allTenantsText changes
        viewModel.allTenantsText.observe(viewLifecycleOwner) { text ->
            binding.allTenantsTextView.text = text
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}