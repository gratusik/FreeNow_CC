package com.gratus.home.presentation

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.gratus.core.domain.remote.Poi
import com.gratus.home.R
import com.gratus.home.databinding.ItemVehicleListBinding
import com.gratus.ui.base.BaseViewHolder
import javax.inject.Inject

class VehicleListAdapter @Inject constructor(private var vehicleList: ArrayList<Poi>) :
    RecyclerView.Adapter<BaseViewHolder>() {
    private var mListener: VehicleClickListener? = null
    private var viewModel: FragmentHomeViewModel? = null

    // setting listener to item click in the adapter
    fun setListener(mListener: FragmentRide) {
        this.mListener = mListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

        val itemVehicleListBinding: ItemVehicleListBinding = ItemVehicleListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ProductListViewHolder(itemVehicleListBinding, mListener, vehicleList, viewModel)
    }

    // View Holder 
    class ProductListViewHolder(
        itemVehicleListBinding: ItemVehicleListBinding,
        private val mListener: VehicleClickListener?,
        private val vehicleList: ArrayList<Poi>,
        private var viewModel: FragmentHomeViewModel?
    ) :
        BaseViewHolder(itemVehicleListBinding.root) {
        private val mBinding: ItemVehicleListBinding = itemVehicleListBinding
        private var vehicleListItemViewModel: VehicleListItemViewModel? = null
        override fun clear() {
            TODO("Not yet implemented")
        }

        @RequiresApi(Build.VERSION_CODES.M)
        override fun onBind(position: Int, holder: BaseViewHolder) {
            if (mListener != null) {
                vehicleListItemViewModel =
                    VehicleListItemViewModel(
                        vehicleList[position],
                        mListener,
                        viewModel,
                        holder.itemView.getContext().getResources()
                            .getString(R.string.direction_key)
                    )
            }
            mBinding.viewModel = vehicleListItemViewModel
        }
    }

    // Bind data to view holder
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position, holder)
    }

    override fun getItemCount(): Int {
        return vehicleList.size
    }

    // notify data change every time data has been added or removed
    fun updateVehicleListAdapter(updateVehicleList: ArrayList<Poi>) {
        vehicleList.clear()
        for (points in updateVehicleList) {
            if (points.vacant.toInt() > 0) {
                vehicleList.addAll(updateVehicleList)
            }
        }
        notifyDataSetChanged()
    }

    fun setViewModel(mViewModel: FragmentHomeViewModel) {
        viewModel = mViewModel
    }
}
