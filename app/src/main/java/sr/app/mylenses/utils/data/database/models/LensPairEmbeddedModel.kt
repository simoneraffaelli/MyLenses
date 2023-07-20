package sr.app.mylenses.utils.data.database.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import sr.app.mylenses.utils.data.mapper.Mapper
import sr.app.mylenses.utils.data.model.LensPair

data class LensPairEmbeddedModel(
    @Embedded val pair: LensPairModel,
    @Relation(
        parentColumn = "left_lens_id",
        entityColumn = "id"
    )
    val leftLens: LensModel,
    @Relation(
        parentColumn = "right_lens_id",
        entityColumn = "id"
    )
    val rightLens: LensModel
) : Mapper<LensPair> {
    override fun map(): LensPair {
        return LensPair(
            rightLens = this.rightLens.map(),
            leftLens = this.leftLens.map(),
            isActive = this.pair.isActive
        )
    }
}