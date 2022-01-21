package com.ctu.planitstudy.feature.presentation.home.fragment.my.termsofservicedetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ctu.planitstudy.R
import com.ctu.planitstudy.databinding.ItemTermOfServiceBinding
import com.ctu.planitstudy.feature.presentation.home.fragment.my.termsofservicedetail.TermsOfServiceList

class TermsOfServiceAdapter(val clickTermsOfService: (Int) -> Unit?) : RecyclerView.Adapter<TermsOfServiceAdapter.TermsOfServiceHolder>() {

    private var termsOfServiceList: MutableList<TermsOfServiceList> = ArrayList()

    inner class TermsOfServiceHolder(private val binding: ItemTermOfServiceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(termsOfServiceList: TermsOfServiceList) {
            binding.item = termsOfServiceList
            binding.root.setOnClickListener { clickTermsOfService(layoutPosition) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TermsOfServiceHolder {
        return TermsOfServiceHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_term_of_service,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TermsOfServiceHolder, position: Int) {
        holder.bind(termsOfServiceList[position])
    }

    override fun getItemCount(): Int {
        return termsOfServiceList.size
    }

    fun setList(termsOfServiceList: List<TermsOfServiceList>) {
        this.termsOfServiceList = termsOfServiceList.toMutableList()
        notifyDataSetChanged()
    }
}
