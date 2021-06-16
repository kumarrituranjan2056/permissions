package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import java.io.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest

private const val FILE_PICKER_ID=12
private const val PERMISSION_REQUEST=10
class MainActivity : AppCompatActivity() {
    private var permissions= arrayOf(android.Manifest.permission.CAMERA,android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE,)
    private lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context=this
        btn_request.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission(context, permissions)) {
                Toast.makeText(context, "permission are already provided", Toast.LENGTH_SHORT).show()
            } else {
                    requestPermissions(permissions, PERMISSION_REQUEST)
                }
            }else{
                Toast.makeText(context, "permission are already provided", Toast.LENGTH_SHORT).show()
            }
        }

        btn_camera.setOnClickListener({
            val intent=Intent("android.media.action.IMAGE_CAPTURE")
            startActivity(intent)
        })
        btn_file.setOnClickListener({
            intent=Intent(Intent.ACTION_GET_CONTENT)
            intent.type="*/*"
            startActivity(intent)
        })
    }

    fun checkPermission(context: Context,permissionArray: Array<String>):Boolean{
        var allSuccess=true
        for(i in permissionArray.indices){
            if(checkCallingOrSelfPermission(permissionArray[i])==PackageManager.PERMISSION_DENIED){
                allSuccess=false
            }
        }
        return allSuccess
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode== PERMISSION_REQUEST){
            var allSuccess=true
            for(i in permissions.indices){
                if(grantResults[i]==PackageManager.PERMISSION_DENIED){
                    allSuccess=false
                    var requestAgain= Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(permissions[i])
                    if(requestAgain){
                        Toast.makeText(context, "permission denied", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context, "go to setting and enable the permission", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            if(allSuccess){
                Toast.makeText(context, "permission Granted", Toast.LENGTH_SHORT).show()
            }
        }
    }

}