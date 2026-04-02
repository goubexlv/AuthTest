package cm.daccvo.auth.security

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import java.security.MessageDigest

object AppVerifier {

//    fun verifyAppAuthenticity(
//        context: Context,
//        targetPackageName: String,
//        expectedSha256: String
//    ): Boolean {
//        return try {
//            val pm = context.packageManager
//
//            val currentSignatures = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                val info = pm.getPackageInfo(targetPackageName, PackageManager.GET_SIGNING_CERTIFICATES)
//                val signingInfo = info.signingInfo
//                if (signingInfo?.hasMultipleSigners() == true) {
//                    signingInfo?.apkContentsSigners
//                } else {
//                    signingInfo?.signingCertificateHistory
//                }
//            } else {
//                val info = pm.getPackageInfo(targetPackageName, PackageManager.GET_SIGNATURES)
//                info.signatures
//            }
//
//            val md = MessageDigest.getInstance("SHA-256")
//
//            // On vérifie si l'un des certificats de la chaîne correspond à l'attendu
//            currentSignatures?.any { signature ->
//                val digest = md.digest(signature.toByteArray())
//                val actualSha = digest.joinToString("") { "%02X".format(it) }
//                actualSha.equals(expectedSha256, ignoreCase = true)
//            } ?: false
//
//        } catch (e: Exception) {
//            false
//        }
//    }

    fun isAppTrusted(context: Context, packageName: String, allowedSignatures: Set<String>): Boolean {
        return try {
            val pm = context.packageManager
            val signatures = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                pm.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES)
                    .signingInfo?.apkContentsSigners
            } else {
                @Suppress("DEPRECATION")
                pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES).signatures
            }

            val md = MessageDigest.getInstance("SHA-256")

            // On vérifie si AU MOINS UNE des signatures de l'APK appelant
            // est présente dans notre liste blanche
            signatures?.any { signature ->
                val actualSha = md.digest(signature.toByteArray())
                    .joinToString("") { "%02X".format(it) }

                allowedSignatures.contains(actualSha.uppercase())
            } ?: false

        } catch (e: Exception) {
            false
        }
    }

}