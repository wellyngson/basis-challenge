package basis.challenge.utils.extensions

import androidx.compose.runtime.MutableState

fun MutableState<Boolean>.show() {
    this.value = true
}

fun MutableState<Boolean>.hide() {
    this.value = false
}
