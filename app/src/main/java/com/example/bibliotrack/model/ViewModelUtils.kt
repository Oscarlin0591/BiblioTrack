package com.example.bibliotrack.model

import android.os.Bundle
import androidx.lifecycle.SAVED_STATE_REGISTRY_OWNER_KEY
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.savedstate.SavedStateRegistryOwner

fun CreationExtras.withSavedState(
    savedStateRegistryOwner: SavedStateRegistryOwner,
    viewModelStoreOwner: ViewModelStoreOwner,
    key:String,
    value: String): CreationExtras {

    val extras = MutableCreationExtras(this)
    extras[SAVED_STATE_REGISTRY_OWNER_KEY] = savedStateRegistryOwner
    extras[VIEW_MODEL_STORE_OWNER_KEY] = viewModelStoreOwner

    val bundle = Bundle().apply {putString(key, value)}
    return extras
}