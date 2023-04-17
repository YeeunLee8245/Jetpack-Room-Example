package kr.co.yeeun.lee.demoi.jetpackroomexample.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import kr.co.yeeun.lee.demoi.jetpackroomexample.R
import kr.co.yeeun.lee.demoi.jetpackroomexample.databinding.ActivityMainBinding
import kr.co.yeeun.lee.demoi.jetpackroomexample.repository.LoginRepository
import kr.co.yeeun.lee.demoi.jetpackroomexample.viewmodel.LoginViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var context: Context
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!
    private lateinit var strUsername: String
    private lateinit var strPassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        context = this@MainActivity

        binding.apply {
            btnAddLogin.setOnClickListener { clickAddLogin() }
            btnReadLogin.setOnClickListener { clickReadLogin() }
        }
    }

    private fun clickAddLogin() {
        binding.apply {
            strUsername = txtUsername.text.toString().trim()
            strPassword = txtPassword.text.toString().trim()

            if (strUsername.isEmpty()) {
                txtUsername.error = "Please enter the username"
            } else if (strPassword.isEmpty()) {
                txtPassword.error = "Please enter the username"
            } else {
                viewModel.insertData(context, strUsername, strPassword)
                lblInsertResponse.text = "Inserted Successfully"
            }
        }
    }

    private fun clickReadLogin() {
        binding.apply {
            strUsername = txtUsername.text.toString().trim()
            viewModel.getLoginDetails(context, strUsername)!!.observe(this@MainActivity) {
                Log.d("데베 값으로 UI 변경","${it}")
                if (it == null) {
                    lblReadResponse.text = "Data Not Found"
                    lblUseraname.text = "- - -"
                    lblPassword.text = "- - -"
                } else {
                    lblUseraname.text = it.Username
                    lblPassword.text = it.Password

                    lblReadResponse.text = "Data Found Successfully"
                }
            }
        }
    }
}