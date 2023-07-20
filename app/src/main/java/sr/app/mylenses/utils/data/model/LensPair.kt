package sr.app.mylenses.utils.data.model

class LensPair(val leftLens: Lens, val rightLens: Lens, val isActive: Boolean) {
    val equalExpiration: Boolean =
        leftLens.expirationDate.dayOfYear() == rightLens.expirationDate.dayOfYear()
}