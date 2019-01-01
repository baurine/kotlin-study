package com.google.codelabs.mdc.kotlin.shrine

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.my_colors_view.view.*

/**
 * Fragment representing the login screen for Shrine.
 */
class ColorsFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.my_colors_view, container, false)
        val clickableViews: List<View> = listOf(
                view.container,
                view.box_one,
                view.box_two,
                view.box_three,
                view.box_four,
                view.box_five
        )
        clickableViews.forEach {
            it.setOnClickListener { view -> makeColor(view)}
        }
        return view
    }

    private fun makeColor(view: View) {
        when (view.id) {
            R.id.box_one -> view.setBackgroundColor(Color.DKGRAY)
            R.id.box_two -> view.setBackgroundColor(Color.GRAY)
            R.id.box_three -> view.setBackgroundColor(Color.BLUE)
            R.id.box_four -> view.setBackgroundColor(Color.MAGENTA)
            R.id.box_five -> view.setBackgroundColor(Color.BLUE)
            else -> view.setBackgroundColor(Color.LTGRAY)
        }
    }
}
