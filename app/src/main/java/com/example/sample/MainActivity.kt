package com.example.sample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import com.example.sample.utils.ApiFailureTypes
import com.example.sample.viewmodel.CommentViewModel
import java.io.File
import java.util.Objects

class MainActivity : AppCompatActivity() {

    var imgPath1: String = ""

    private val cropImage: ActivityResultLauncher<CropImageContractOptions> =
        registerForActivityResult(CropImageContract()) { result: CropImageView.CropResult? ->
            this.onCropImageResult(result)
        }

    public fun onCropImageResult(result: CropImageView.CropResult?) {
        if (result?.isSuccessful == true) {
            val uri = Objects.requireNonNull(result.uriContent)
                .toString()
                .replace("file:", "")
            result.uriContent?.let {
                imgPath1 = commentViewModel.getFilePathFromURI(this, it).toString()
                Glide.with(this).load(File(imgPath1)).into(image)
            }
        }
    }


    lateinit var commentViewModel: CommentViewModel
    lateinit var image:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        commentViewModel = ViewModelProvider(this)[CommentViewModel::class.java]
        initializeObservers()

        val submit=findViewById<Button>(R.id.submit)
        val edtFristName=findViewById<EditText>(R.id.edtname)
        val edtLastName=findViewById<EditText>(R.id.edtLstName)
        val edtEmail=findViewById<EditText>(R.id.edtEmail)
        val edtPassword=findViewById<EditText>(R.id.edtPwd)
        val edtPhoneNo=findViewById<EditText>(R.id.edtPhone)
        image=findViewById<ImageView>(R.id.action_image)
        image.setOnClickListener {
            val options: CropImageContractOptions =
                CropImageContractOptions(null, CropImageOptions())
            cropImage.launch(options)
        }
        submit.setOnClickListener { 
            commentViewModel.postData(edtFristName.text.toString(),
                edtLastName.text.toString(),
                edtEmail.text.toString(),
                edtPassword.text.toString(),
                edtPhoneNo.text.toString(),
                "randomstring1234",
                "1","+91",imgPath1)
        }
    }

    private fun initializeObservers() {
        commentViewModel.commentResponse.observe(
            this, Observer {
                it.let {
                    Log.e("klklk", "jljljlj" + it.status)
                    if (it.status == "success") {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    } else {
                        showMessage(it.message)
                    }
                }
            }
        )
        commentViewModel.isLoading.observe(
            this, Observer {
                it?.let {
                    if (it) {
//                        binding.progressBar.visibility = View.VISIBLE
                    } else {
                        //binding.progressBar.visibility = View.GONE
                    }
                }
            }
        )
        commentViewModel.apiError.observe(
            this, Observer {
                Log.e("apiError", "inside")
                it?.let {
                    Log.e("api error ", "inside" + it)
                    showMessage(it)
                }
            }
        )
        commentViewModel.onFailure.observe(
            this, Observer {
                Log.e("onFailure", "inside")
                it?.let {
                    showMessage(ApiFailureTypes().getFailureMessage(it, this))
                }
            }
        )
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
