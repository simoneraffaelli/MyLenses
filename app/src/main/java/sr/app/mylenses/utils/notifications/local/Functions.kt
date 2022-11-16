package sr.app.mylenses.utils.notifications.local

import sr.app.mylenses.utils.data.enums.Type

fun getNotificationId(lensType: Type): Int {
    return when (lensType) {
        Type.Left -> leftLensNotificationId
        Type.Right -> rightLensNotificationId
        Type.Undefined -> lensesNotificationId
    }
}