package com.example.final_project.presentation.screen.cart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.R
import com.example.final_project.presentation.screen.cart.adapter.CartRecyclerViewAdapter

class CartItemTouchHelper(val context: Context, private val listener: RecyclerItemTouchHelperListener) : ItemTouchHelper.Callback() {
    private val paint = Paint()
    private val icon = ContextCompat.getDrawable(context, R.drawable.ic_trash)

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {

        return true
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val direction = ItemTouchHelper.LEFT
        return makeMovementFlags(0, direction)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if (viewHolder is CartRecyclerViewAdapter.CartItemViewHolder)
            listener.onSwiped(viewHolder, direction, viewHolder.adapterPosition)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        viewHolder?.let {
            val foregroundView = (it as? CartRecyclerViewAdapter.CartItemViewHolder)?.itemView
            foregroundView?.let { view ->
                getDefaultUIUtil().onSelected(view)
            }
        }
    }

    override fun onChildDrawOver(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder?,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        viewHolder?.let {
            val foregroundView = (it as? CartRecyclerViewAdapter.CartItemViewHolder)?.itemView
            foregroundView?.let { view ->
                getDefaultUIUtil().onDrawOver(
                    c,
                    recyclerView,
                    view,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        val foregroundView = (viewHolder as? CartRecyclerViewAdapter.CartItemViewHolder)?.itemView
        foregroundView?.let {
            getDefaultUIUtil().clearView(it)
        }
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                             dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        if (dX != 0f && isCurrentlyActive) {
            val itemView = viewHolder.itemView
            paint.color = context.getColor(R.color.light_red)
            val top = itemView.top + (itemView.height - icon!!.intrinsicHeight) / 2
            val left = itemView.width - icon.intrinsicHeight - (itemView.height - icon.intrinsicHeight) / 2

            icon.setTint(Color.WHITE)

            if (dX < 0) {
                val background = RectF(itemView.right.toFloat() + dX, itemView.top.toFloat(),
                    itemView.right.toFloat(), itemView.bottom.toFloat())
                c.drawRect(background, paint)
                icon.setBounds(left, top, left + icon.intrinsicWidth, top + icon.intrinsicHeight)
            } else {
                val background = RectF(itemView.left.toFloat() + dX, itemView.top.toFloat(),
                    itemView.left.toFloat(), itemView.bottom.toFloat())
                c.drawRect(background, paint)
                icon.setBounds(top, top, top + icon.intrinsicWidth, top + icon.intrinsicHeight)
            }
            icon.draw(c)
        }
    }

    interface RecyclerItemTouchHelperListener {
        fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int)
    }
}