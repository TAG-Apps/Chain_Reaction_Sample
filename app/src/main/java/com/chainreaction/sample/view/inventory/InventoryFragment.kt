package com.chainreaction.sample.view.inventory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.cainreaction.sample.R
import com.cainreaction.sample.databinding.FragmentInventoryBinding
import com.chainreaction.sample.SecondFragmentDirections
import com.chainreaction.sample.model.model.InventoryModel
import com.chainreaction.sample.model.utils.INVENTORY_ITEM
import com.chainreaction.sample.view.fragment_factory.ParentFragment
import com.chainreaction.sample.view.interfaces.OnAdapterItemClicked
import com.chainreaction.sample.viewmodel.InventoryViewModel


class InventoryFragment :  ParentFragment(R.layout.fragment_inventory), OnAdapterItemClicked {


    private val mBinding: FragmentInventoryBinding by viewBinding()


    private val mViewModel: InventoryViewModel by hiltNavGraphViewModels(R.id.nav_graph)
    private val mAdapter: InventoryAdapter by lazy { InventoryAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mBinding.inventoryRecy.layoutManager = WrapContentLinearLayoutManager(requireContext())
        mBinding.inventoryRecy.setHasFixedSize(true)
        mBinding.inventoryRecy.adapter = mAdapter

        emitFlow()

        mViewModel.getInventoriesFromDatabase()
    }

    private fun emitFlow() {

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mViewModel.mInventoriesFlow.collect { status ->
                when (status) {
                    is InventoryViewModel.InventoryStatus.Failed -> {
                    }
                    is InventoryViewModel.InventoryStatus.Success -> {
                        mAdapter.setInventories(status.model)
                    }
                    InventoryViewModel.InventoryStatus.SuccessNoDataFound -> showNoDataFound()
                    InventoryViewModel.InventoryStatus.EmptyInventory -> {}
                }
            }
        }

    }
    override fun onItemClicked(position: Int, data: Any?) {
       if (position == INVENTORY_ITEM) {
            findNavController().navigate(
                SecondFragmentDirections.actionSecondFragmentToFirstFragment(
                    data as InventoryModel
                )
            )
            mAdapter.clearData()
        }

    }


}