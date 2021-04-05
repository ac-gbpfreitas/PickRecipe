package com.example.pickrecipe.ui.pantry

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pickrecipe.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PantryAdapter(var ingredientList: MutableList<String> ,
                    private val listener: ListItemListener) : RecyclerView.Adapter<PantryAdapter.IngredientViewHolder>() {

    inner class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Three objects
        var fabDeleteIngredient: FloatingActionButton? = null
        var textViewIngredient: TextView? = null
        var ingredientItemView: View? = null

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {

        //inflate external layout. parent will be the recycler view
        var itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_pantry, parent, false)

        //create custom view holder object
        var myHolder = IngredientViewHolder(itemView)
        myHolder.fabDeleteIngredient = itemView.findViewById(R.id.fabDeleteIngredient)
        myHolder.textViewIngredient = itemView.findViewById(R.id.textViewPantryIngredient)
        myHolder.ingredientItemView = itemView

        return myHolder
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.textViewIngredient?.text = ingredientList[position].capitalize()

        holder.fabDeleteIngredient?.setOnClickListener {
            ingredientList.removeAt(position)
            changeData(ingredientList)
            listener.deleteIngredientOnBackend(holder.textViewIngredient?.text.toString().decapitalize())
        }
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    fun changeData(newIngredientList: MutableList<String>) {
        this.ingredientList = newIngredientList
        notifyDataSetChanged()
    }

    interface ListItemListener {
        fun deleteIngredientOnBackend(ingredient: String)
    }
}