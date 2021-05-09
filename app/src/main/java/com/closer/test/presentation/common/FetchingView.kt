package com.closer.test.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.closer.test.databinding.FetchingLayoutBinding

class FetchingView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var _binding: FetchingLayoutBinding? = null
    private val binding get() = _binding!!

    init {
        _binding = FetchingLayoutBinding.inflate(LayoutInflater.from(context),this)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        performClick()
        return true
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    fun show() {
        binding.root.run {
            visibility = View.VISIBLE
            bringToFront()
        }
    }

    fun hide() {
        binding.root.visibility = View.GONE
    }
}