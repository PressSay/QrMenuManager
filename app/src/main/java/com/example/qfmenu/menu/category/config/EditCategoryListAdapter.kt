package com.example.qfmenu.menu.category.config

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.qfmenu.viewmodels.CategoryViewModel
import com.example.qfmenu.R
import com.example.qfmenu.database.entity.CategoryDb
import com.example.qfmenu.viewmodels.SaveStateViewModel

class EditCategoryListAdapter(
    private val categoryViewModel: CategoryViewModel,
    private val context: Context,
    private val stateViewCategoryModel: SaveStateViewModel
) : ListAdapter<CategoryDb, EditCategoryListAdapter.ItemViewHolder>(DiffCallback) {
    class ItemViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val btnConfig = view.findViewById<AppCompatImageButton>(R.id.btnConfigItemEditCategoryOrDish)!!
        val titleDishOrCategory = view.findViewById<TextView>(R.id.titleItemEditCategoryOrDish)!!
        val btnTrash = view.findViewById<AppCompatImageButton>(R.id.btnTrashItemEditCategoryOrDish)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapter = LayoutInflater.from(parent.context).inflate(R.layout.item_edit_category_or_dish, parent, false)
        return ItemViewHolder(adapter)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item: CategoryDb =
            currentList[holder.adapterPosition]
        holder.btnConfig.setOnClickListener {
            stateViewCategoryModel.setStateCategory(item)
            holder.view.findNavController().navigate(R.id.action_editCreateCategoryFragment_to_editCreateDishFragment)
        }

        holder.titleDishOrCategory.text = item.categoryName
        holder.btnTrash.setOnClickListener {
            categoryViewModel.deleteCategoryWithDishes(item)
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<CategoryDb>() {
            override fun areItemsTheSame(oldItem: CategoryDb, newItem: CategoryDb): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: CategoryDb, newItem: CategoryDb): Boolean {
                return oldItem.categoryId == newItem.categoryId
            }
        }
    }
}