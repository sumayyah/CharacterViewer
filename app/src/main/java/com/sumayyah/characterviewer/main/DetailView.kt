package com.sumayyah.characterviewer.main

import com.sumayyah.characterviewer.main.Model.Character

/**
 * Created by sahmed336 on 11/29/17.
 */
interface DetailView {

    fun showEmptyDetailView()
    fun showDetailForCharacter(character: Character)
}