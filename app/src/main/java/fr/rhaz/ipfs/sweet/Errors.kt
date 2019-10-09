package fr.rhaz.ipfs.sweet

import android.content.Context
import android.widget.Toast

fun <T> Context.catcher(action: () -> T) =
    try {
        action()
    } catch (ex: Exception) {
        Toast.makeText(this, ex.localizedMessage, Toast.LENGTH_LONG).show()
    }
