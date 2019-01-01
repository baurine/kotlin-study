package com.google.codelabs.mdc.kotlin.shrine

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.shr_login_fragment.*
import kotlinx.android.synthetic.main.shr_login_fragment.view.*

/**
 * Fragment representing the login screen for Shrine.
 */
class LoginFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.shr_login_fragment, container, false)

        // Snippet from "Navigate to the next Fragment" section goes here.
        // we can't use `next_button.setOnClickListener` here, because now the next_button is null
        // isn't initiated yet
        view.next_button.setOnClickListener {
            // why we can use `password_text_edit` instead of `view.password_text_edit` here
            // because it is already initiated
            if (!isPasswordValid(password_text_edit.text)) {
                password_text_input.error = getString(R.string.shr_error_password)
            } else {
                password_text_input.error = null
                (activity as NavigationHost).navigateTo(ProductGridFragment(), false)
            }
        }

        view.password_text_edit.setOnKeyListener { _, _, _ ->
            if (isPasswordValid(password_text_edit.text)) {
                password_text_input.error = null
            }
            false
        }

        return view
    }

    // "isPasswordValid" from "Navigate to the next Fragment" section method goes here
    private fun isPasswordValid(text: Editable?): Boolean {
        return text != null && text.length >= 8
    }
}
