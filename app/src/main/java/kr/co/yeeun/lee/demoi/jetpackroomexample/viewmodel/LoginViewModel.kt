package kr.co.yeeun.lee.demoi.jetpackroomexample.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kr.co.yeeun.lee.demoi.jetpackroomexample.model.LoginTableModel
import kr.co.yeeun.lee.demoi.jetpackroomexample.repository.LoginRepository

class LoginViewModel : ViewModel() {
    var liveDataLogin: LiveData<LoginTableModel>? = null

    fun insertData(context: Context, username: String, password: String) {
        LoginRepository.insertData(context, username, password)
    }

    fun getLoginDetails(context: Context, username: String) : LiveData<LoginTableModel>? {
        liveDataLogin = LoginRepository.getLoginDetails(context, username)
        return liveDataLogin
    }
}