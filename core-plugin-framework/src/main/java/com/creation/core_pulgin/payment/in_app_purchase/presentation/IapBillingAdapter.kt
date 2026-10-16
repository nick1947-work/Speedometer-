package com.creation.core_pulgin.payment.in_app_purchase.presentation

import android.content.Context
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
 import com.creation.core_pulgin.core.base.extention.FONT
import com.creation.core_pulgin.core.base.extention.setTextFont
import com.creation.core_pulgin.payment.in_app_purchase.data.model.InAppProductData
import com.creation.core_pulgin.payment.in_app_purchase.data.model.ProductListingData
import com.creation.core_pulgin.R

class IapBillingAdapter(
    private val context: Context,
    private var dataList: List<ProductListingData>,
    var itemCallbackListener: ItemCallbackListener,
    private var onSelect: (String) -> Unit,


    ) :
    RecyclerView.Adapter<IapBillingAdapter.IapBillingViewHolder>() {

    var mPositon: Int = -1
    var productStatusMap: Map<String, InAppProductData> = emptyMap()

    interface ItemCallbackListener {
        fun togglePurchaseBtn(buttonStatus: Boolean)
    }

    fun getSelectedProduct(): ProductListingData? {
        var selectedProduct: ProductListingData? = null
        dataList.forEach { productListingData ->

            if (productListingData.isSelected) {
                selectedProduct = productListingData
            }
        }
        return selectedProduct
    }

    fun getSelectedProductIndex(): Int {
        var selectedIndex = -1
        dataList.forEachIndexed { index, productListingData ->
            if (productListingData.isSelected) {
                selectedIndex = index
            }
        }
        return selectedIndex
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IapBillingViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_in_app_purchase_item, parent, false)
        return IapBillingViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: IapBillingViewHolder, position: Int) {
        val iapItem = dataList[position]


        bindDataToViews(holder, iapItem, position)
        calculatePriceAndSetData(holder, iapItem)
        setViewStatus(holder, iapItem, position)
        setClickListener(holder, iapItem, position)
    }

    private fun setViewStatus(
        holder: IapBillingViewHolder,
        iapItem: ProductListingData,
        position: Int
    ) {
        if (iapItem.isSelected) {
            holder.clParent.setBackgroundResource(R.drawable.shape_gradient_selected)
//            holder.clParent.alpha = 1f
            holder.radioButton.isChecked = true
        } else {
            holder.clParent.setBackgroundResource(R.drawable.shape_gradient_unselected)
            holder.radioButton.isChecked = false
        }
    }

    private fun bindDataToViews(
        holder: IapBillingViewHolder,
        iapItem: ProductListingData,
        position: Int
    ) {
//        var save = iapItem.save
//        var offerName = iapItem.offerName
//        Log.d("checkingAdapter", iapItem.packName.toString())

        var price = iapItem.price
        var trialText = iapItem.trialText
        if (price?.isNotEmpty() == true) {
            holder.tvStrikeThroughPrice?.text = getFormattedPrice(iapItem.showPrice ?: "")
        }
        holder.tvPackName.text = iapItem.packName
        holder.tvPackName.setTextFont(FONT.SEMI_BOLD)

        if (trialText?.isNotEmpty() == true) {

            var priceText = ""
            var formattedPrice = getFormattedPrice(iapItem.price ?: "")
            if(formattedPrice.isEmpty().not()){
                priceText = ", then \n$formattedPrice / " +  iapItem.packName?.toLowerCase()

            }

            holder.tvValidity.text = trialText + priceText
            holder.tvValidity.visibility = View.VISIBLE
        } else {
            holder.tvValidity.visibility = View.GONE
        }


        holder.tvPackName.setTextFont(FONT.SEMI_BOLD)
        holder.tvShowPrice.setTextFont(FONT.SEMI_BOLD)
        holder.tvStrikeThroughPrice.setTextFont(FONT.SEMI_BOLD)
        holder.tvRecommended.setTextFont(FONT.MEDIUM)

        if (position == 0) {
            holder.tvRecommended.visibility = View.VISIBLE
            holder.tvValidity.visibility = View.GONE
        } else {
            holder.tvRecommended.visibility = View.GONE
//            holder.tvValidity.visibility = View.VISIBLE
        }
    }

    private fun calculatePriceAndSetData(
        holder: IapBillingViewHolder,
        iapItem: ProductListingData
    ) {
        if (dataList != null) {
            Log.d("checkingAdapter", iapItem.productIdPrice.toString())
            val productId = iapItem.productIdPrice
            val packId = iapItem.productIdPurchase
            if (productId?.isNotEmpty() == true && packId?.isNotEmpty() == true) {
                try {
                    holder.tvShowPrice.text = getFormattedPrice(iapItem.price ?: "")
                    holder.tvShowPrice.visibility = View.VISIBLE
                } catch (e: java.lang.Exception) {
                    holder.tvShowPrice.visibility = View.INVISIBLE
                }
            } else {
                holder.tvShowPrice.visibility = View.INVISIBLE
            }
        }
        if(iapItem.price.isNullOrEmpty()){
            holder.itemView.visibility = View.GONE
        }else{
            holder.itemView.visibility = View.VISIBLE
        }
    }

    private fun getFormattedPrice(productPrice: String): String {
        var formattedPrice = productPrice
        if (productPrice.endsWith(".00")) {
            formattedPrice = productPrice.replace(".00", "")
        }
        if (productPrice.endsWith(".0")) {
            formattedPrice = productPrice.replace(".0", "")
        }
        return formattedPrice
    }

    private fun setClickListener(
        holder: IapBillingViewHolder,
        iapItem: ProductListingData,
        position: Int
    ) {

        holder.itemView.setOnClickListener {
            onClickViewItem(position, iapItem)
        }
    }

    override fun getItemCount(): Int {
//        Log.d("checkingAdapter", dataList.size.toString())
        return dataList.size
    }

    fun onClickViewItem(position: Int, iapItem: ProductListingData) {
        dataList.forEach {
            if (it != iapItem)
                it.isSelected = false
        }
        iapItem.isSelected = !iapItem.isSelected
        iapItem.packName?.let { onSelect.invoke(it) }
        // notifyItemChanged(position)
        notifyDataSetChanged()
        itemCallbackListener.togglePurchaseBtn(iapItem.isSelected)
    }


    private fun setTypeFaceOpenSensSmBold(textView: TextView) {
        val typeface = Typeface.createFromAsset(
            this?.context?.assets,
            this?.context?.resources?.getString(R.string.text_2_path)
        )
        textView.typeface = typeface
    }

    inner class IapBillingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var clParent: ConstraintLayout = view.findViewById(R.id.clParent)
        var radioButton: RadioButton = view.findViewById(R.id.radioButton)
        var tvPackName: TextView = view.findViewById(R.id.tvPackName)
        var tvValidity: TextView = view.findViewById(R.id.tvValidity)
        var tvRecommended: TextView = view.findViewById(R.id.tvRecommended)
        var tvShowPrice: TextView = view.findViewById(R.id.tvPrice)
        var tvStrikeThroughPrice: TextView = view.findViewById(R.id.tvStrikeThroughPrice)
    }
}