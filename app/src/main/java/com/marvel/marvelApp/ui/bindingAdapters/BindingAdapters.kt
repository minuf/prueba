package com.marvel.marvelApp.ui.bindingAdapters

import android.graphics.Color
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.marvel.domain.model.Character
import com.marvel.marvelApp.extensions.loadUrl
import com.marvel.marvelApp.ui.home.CharactersViewModel
import com.marvel.marvelApp.ui.home.charactersList.CharacterListAdapter

@BindingAdapter("data")
fun setRecyclerViewProperties(recyclerView: RecyclerView?, data: MutableList<Character>?) {
    val adapter = recyclerView?.adapter
    if (adapter is CharacterListAdapter && data != null) {
        adapter.submitList(data)
    }
}

@BindingAdapter("imageUrl")
fun setCharacterThumbnail(imageView: ImageView?, imageUrl: String?) {
    if (imageUrl != null) {
        imageView?.loadUrl(imageUrl)
    }
}

@BindingAdapter("error", "viewModel")
fun showSnackBarError(layout: FrameLayout?, error: Boolean?, viewModel: CharactersViewModel?) {
    if (layout != null && error != null && error) {
        Snackbar.make(
            layout,
            "Error",
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction("RETRY") {
                viewModel?.fetchCharacters()
            }
            .setBackgroundTint(
                Color.RED
            )
            .show()
    }
}