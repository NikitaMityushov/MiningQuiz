package com.mityushovn.miningquiz.utils

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import java.util.Date
import java.util.concurrent.TimeUnit

/**
 * @author
 * @since 1.0
 * Utility functions for application
 */

/**
 * Converts UNIX time in the java.util.Date instance
 */
fun Long.toDate(): Date {
    return Date(TimeUnit.MILLISECONDS.convert(this, TimeUnit.SECONDS))
}

/**
 * Returns current time from the Date instance
 */
fun Date.now(): Date {
    return Date(System.currentTimeMillis())
}

/**
 * SQLite doesn't have boolean data types.
 * false == 0, true == 1
 */
fun Boolean.toIntForDB(): Int {
    return if (this) 1 else 0
}

/**
 * @see toIntForDB javadoc
 */
fun Int.toBooleanForDB(): Boolean {
    return this != 0
}

/**
 * for convenient handling EditText
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // do nothing
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // do nothing
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

/**
 * for convenient handling SearchView text change
 */
fun SearchView.onQueryTextChange(onQueryTextChange: (String?) -> Unit) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            onQueryTextChange.invoke(newText)
            return true
        }
    })
}

/**
 * Hides keyboard if need
 */
fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

/**
 * simple changes visibility
 */
fun View.toGone() {
    visibility = View.GONE
}

/**
 * @see View.toGone
 */
fun View.toVisible() {
    visibility = View.VISIBLE
}


fun View.disable() {
    isClickable = false
}
