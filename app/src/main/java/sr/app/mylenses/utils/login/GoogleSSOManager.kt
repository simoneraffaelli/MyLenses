package sr.app.mylenses.utils.login

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import sr.app.mylenses.MyLensesApp
import sr.app.mylenses.utils.log.w

object GoogleSSOManager {
    private val ctx: Context get() = MyLensesApp.instance

    val client: GoogleSignInClient
        get() {
            return GoogleSignIn.getClient(
                ctx,
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build()
            )
        }

    private val currentAccount get() = GoogleSignIn.getLastSignedInAccount(ctx)
    private val _account = MutableLiveData<GoogleSignInAccount?>(currentAccount)
    val account: LiveData<GoogleSignInAccount?>
        get() = _account


    fun getAccountFromIntent(data: Intent) {
        runCatching {
            _account.value = GoogleSignIn.getSignedInAccountFromIntent(data).result
        }.onFailure {
            w(it)
        }
    }

}