package com.closer.test.presentation.common

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.closer.test.R
import com.closer.test.databinding.ErrorLayoutBinding
import com.closer.test.util.error.AppError

class ErrorView @JvmOverloads constructor(context: Context, val attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var showImage: Boolean = true

    private var _binding: ErrorLayoutBinding? = null
    private val binding get() = _binding!!

    init {
        _binding = ErrorLayoutBinding.inflate(LayoutInflater.from(context),this)
        readAttributes()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        performClick()
        return true
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

    }

    private fun readAttributes() {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ErrorView)

        (0 until typedArray.indexCount).forEach { index ->
            when (typedArray.getIndex(index)) {
                R.styleable.ErrorView_showImage -> handleShowImage(typedArray)
            }
        }

        typedArray.recycle()
    }

    private fun handleShowImage(typedArray: TypedArray) {
        showImage = typedArray.getBoolean(R.styleable.ErrorView_showImage, true)
    }

    private fun showErrorMessage(error: String) {
        binding.lytErrorTvDescription.run {
            text = error
            visibility = View.VISIBLE
        }
    }

    fun setCommandListener(listener: OnClickListener) {
        binding.lytErrorTvCommand.setOnClickListener(listener)
    }

    fun show(error: AppError?) {
        error ?: return

        showErrorMessage(error.getMessage())

        binding.root.run {
            visibility = View.VISIBLE
            bringToFront()
        }
    }

    fun hide() {
        binding.root.visibility = View.GONE
    }
}
