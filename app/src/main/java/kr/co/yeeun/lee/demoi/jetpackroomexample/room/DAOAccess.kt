package kr.co.yeeun.lee.demoi.jetpackroomexample.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kr.co.yeeun.lee.demoi.jetpackroomexample.model.LoginTableModel

@Dao
interface DAOAccess {
    // 충돌전략으로 Replace 외에도 IGNORE, REPLACE, ROLLBACK, FAIL, ABORT가 있음
    @Insert(onConflict = OnConflictStrategy.REPLACE) // 데이터 충돌 시 삽입 데이터로 대체하기 때문에 절대 -1을 반환하지 않음
    suspend fun InsertData(loginTableModel: LoginTableModel) // suspend로 선언하여 background에서 별도의 스레드로 동작하게 함(Room에서 메인스레드에서 동작하지 않게 함)

    @Query("SELECT * FROM Login WHERE Username =:username")
    fun getLoginDetails(username: String?) : LiveData<LoginTableModel> // Room에서 LiveData(또는 Flow도 가능) 관련 코드를 자동 생성함
}