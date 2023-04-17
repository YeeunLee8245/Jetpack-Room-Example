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
    suspend fun insertData(loginTableModel: LoginTableModel) // suspend로 선언하여 background에서 별도의 스레드로 동작하게 함(Room에서 메인스레드에서 동작하지 않게 함)

    // 다른 CRUD를 통해 데이터 구성의 변경이 일어났다면 LiveData 값 재할당됨
    // collection 타입이 아닌 단일 타입으로 받을 때 저장한 순서 기준 가장 오래된 순부터(가장 마지막엔 최신 데이터) 할당
    @Query("SELECT * FROM Login WHERE Username =:username") // 가져올 값이 없을 땐 liveData{null}로 할당
    fun getLoginDetails(username: String?) : LiveData<LoginTableModel> // Room에서 LiveData(또는 Flow도 가능) 관련 코드를 자동 생성함
}