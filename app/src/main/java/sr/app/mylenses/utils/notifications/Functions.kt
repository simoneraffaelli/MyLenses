package sr.app.mylenses.utils.notifications

import sr.app.mylenses.utils.data.utils.Type

fun getNotificationId(lensType: Type): Int {
    return when (lensType) {
        Type.Left -> leftLensNotificationId
        Type.Right -> rightLensNotificationId
        Type.Undefined -> lensesNotificationId
    }
}