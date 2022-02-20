package sr.app.mylenses.view.lensesarea

import org.joda.time.DateTime
import sr.app.mylenses.MyLensesApp
import sr.app.mylenses.utils.data.model.Lens
import sr.app.mylenses.utils.format

class LensesAreaItem(_eta: Int, _expDate: DateTime, val percentage: Float) {

    constructor(lens: Lens) : this(lens.timeLeft, lens.expirationDate, lens.usage)

    val expirationDate: String = _expDate.format(MyLensesApp.instance)
    val timeLeft: String = "$_eta"
}