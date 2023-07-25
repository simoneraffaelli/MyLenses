package sr.app.mylenses.utils.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.errorprone.annotations.Keep

@Keep
@Entity(tableName = "lens_pair")
data class LensPairModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,
    @ColumnInfo(name = "is_active")
    val isActive: Boolean,
    @ColumnInfo(name = "left_lens_id")
    var leftLensId: Long,
    @ColumnInfo(name = "right_lens_id")
    var rightLensId: Long
)