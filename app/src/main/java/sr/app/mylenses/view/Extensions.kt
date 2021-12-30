package sr.app.mylenses.view

import android.view.View

fun View.enable(enabled: Boolean) {
    this.alpha = if (enabled) {
        1f
    } else {
        0.45f
    }
    this.isEnabled = enabled
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}