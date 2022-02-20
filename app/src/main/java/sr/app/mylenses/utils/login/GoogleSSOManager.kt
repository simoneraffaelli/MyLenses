package sr.app.mylenses.utils.login

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.Scope
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import sr.app.mylenses.MyLensesApp
import sr.app.mylenses.utils.log.w
import java.util.*

object GoogleSSOManager {
    private val ctx: Context get() = MyLensesApp.instance

    val client: GoogleSignInClient
        get() {
            return GoogleSignIn.getClient(
                ctx,
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestScopes(
                        Scope(Scopes.DRIVE_APPFOLDER)
                    )
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

    fun signOut() {
        client.signOut().continueWithTask { client.revokeAccess() }
            .addOnCompleteListener {
                _account.value = null
            }
    }

    val credential: GoogleAccountCredential?
        get() {
            _account.value?.let {
                return GoogleAccountCredential.usingOAuth2(
                    ctx,
                    Collections.singleton(Scopes.DRIVE_APPS)
                )
                    .apply {
                        selectedAccount = it.account
                    }
            }

            return null
        }
}