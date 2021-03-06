package com.ruviapps.tacklingnephrotic.utility
import androidx.navigation.NavDirections

/**
 * Code Credit : Udacity Kotlin Android Developer Nanodegree Program
 * Sealed class used with the live data to navigate between the fragments
 */
sealed class NavigationCommand {
    /**
     * navigate to a direction
     */
    data class ToDirection(val directions: NavDirections) : NavigationCommand()
        data class To(val resId : Int) : NavigationCommand()
    /**
     * navigate back to the previous fragment
     */
    object Back : NavigationCommand()

    /**
     * navigate back to a destination in the back stack
     */
    data class BackTo(val destinationId: Int) : NavigationCommand()

    /**
     * don't navigate instead show Error
     */
    data class ShowError(val errMsg : String?) : NavigationCommand()
}