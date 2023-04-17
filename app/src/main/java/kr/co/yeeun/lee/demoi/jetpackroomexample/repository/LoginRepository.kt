package kr.co.yeeun.lee.demoi.jetpackroomexample.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.yeeun.lee.demoi.jetpackroomexample.model.LoginTableModel
import kr.co.yeeun.lee.demoi.jetpackroomexample.room.LoginDatabase

object LoginRepository {
    var loginDatabase: LoginDatabase? = null
    var loginTableModel: LiveData<LoginTableModel>? = null

    fun initializeDB(context: Context) : LoginDatabase {
        return LoginDatabase.getDatabaseClient(context)
    }

    fun insertData(context: Context, username: String, password: String) {
        loginDatabase = initializeDB(context)

        CoroutineScope(Dispatchers.IO).launch {
            val loginDetails = LoginTableModel(username, password)
            loginDatabase?.loginDao()?.insertData(loginDetails)
        }
    }

    fun getLoginDetails(context: Context, username: String) : LiveData<LoginTableModel>? {
        loginDatabase = initializeDB(context)
        loginTableModel = loginDatabase!!.loginDao().getLoginDetails(username)
        Log.d("데베 값 가져오기","${loginTableModel.toString()} ${
            loginTableModel?.value.toString()}")
        // LiveData의 getValue()는 비동기처리이므로 값이 할당되지 않는 순간이면 liveData{null}를 가지고 있을 수 있다는 것에 유의하자
        return loginTableModel
    }
}