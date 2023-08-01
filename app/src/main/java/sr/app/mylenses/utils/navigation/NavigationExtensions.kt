package sr.app.mylenses.utils.navigation

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import sr.app.mylenses.utils.log.w

fun NavController.safeNavigate(destination: NavDirections, navOptions: NavOptions? = null) {
    this.takeIf { currentDestination?.getAction(destination.actionId) != null }
        ?.navigate(destination, navOptions)
        ?: w("Destinazione ${destination.actionId} non trovata!")
}

fun NavController.safeNavigate(destination: NavDirections, navExtra: Navigator.Extras) {
    this.takeIf { currentDestination?.getAction(destination.actionId) != null }
        ?.navigate(destination, navExtra)
        ?: w("Destinazione ${destination.actionId} non trovata!")
}

fun NavController.safeNavigate(
    @IdRes resId: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null
) {
    this.takeIf { currentDestination?.getAction(resId) != null }?.navigate(resId, args, navOptions)
        ?: w("Destinazione $resId non trovata!")
}