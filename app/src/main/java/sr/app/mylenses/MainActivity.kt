package sr.app.mylenses

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.lorenzofelletti.permissions.PermissionManager
import com.lorenzofelletti.permissions.dispatcher.dsl.checkPermissions
import com.lorenzofelletti.permissions.dispatcher.dsl.doOnDenied
import com.lorenzofelletti.permissions.dispatcher.dsl.doOnGranted
import com.lorenzofelletti.permissions.dispatcher.dsl.withRequestCode
import sr.app.mylenses.databinding.ActivityMainBinding
import sr.app.mylenses.utils.log.d


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val pm = PermissionManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPermissions()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        pm.dispatchOnRequestPermissionsResult(requestCode, grantResults)
        pm checkRequestAndDispatch 1
    }

    private fun initPermissions() {
        pm.buildRequestResultsDispatcher {
            withRequestCode(1) {
                checkPermissions(android.Manifest.permission.POST_NOTIFICATIONS)
                doOnGranted {
                    d("Post notification permission granted")
                }
                doOnDenied {
                    d("Post notification permission denied")
                }
            }
        }
    }
}