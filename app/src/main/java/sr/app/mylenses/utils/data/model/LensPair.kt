package sr.app.mylenses.utils.data.model

class LensPair(val leftLens: Lens, val rightLens: Lens) {
    val equalExpiration: Boolean
        get() = leftLens.expirationDate.dayOfYear() == rightLens.expirationDate.dayOfYear()
}