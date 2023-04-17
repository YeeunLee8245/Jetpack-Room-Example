package kr.co.yeeun.lee.demoi.jetpackroomexample.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kr.co.yeeun.lee.demoi.jetpackroomexample.R
import kr.co.yeeun.lee.demoi.jetpackroomexample.model.LoginTableModel

// entities: DB에 존재하는 테이블
// version: DB 스키마 변경될 때마다 올려줘야함, 이것을 통해 이전 버전 DB를 최신 DB로 데이터 이전 가능
// exportSchema: DB 스키마를 폴더로 저장해서 json 파일로 export 할 수 있음(테스트 버전일 때는 필요없으니 false로)
@Database(entities = arrayOf(LoginTableModel::class), version = 1, exportSchema = false)
abstract class LoginDatabase : RoomDatabase() {
    abstract fun loginDao() : DAOAccess

    companion object {
        // Volatitle: 일반적은 객체는 내부 성능향상을 위해 메인 메모리로부터 읽어온 값을 CPU 캐시에 저장하지만
        // 멀티스레드 앱에서는 각 스레드를 통해 CPU에 캐싱한 값이 상이할 수 있음(ex. 참조는 캐시로하는 중인데 중간에 값 변경이 메인에서 일어날 때)
        // 이때 Volatitle를 붙이면 객체의 값이 메인 메모리에만 저장되기 때문에 멀티스레드 환경에서의 값 불일치를 해결할 수 있음
        // 단, CPU 캐시를 참조하는 것보다 성능이 떨어질 수 밖에 없다는 점 유의
        @Volatile
        private var INSTANCE: LoginDatabase? = null

        fun getDatabaseClient(context: Context) : LoginDatabase {
            INSTANCE?.let { //  if (INSTANCE != null) return INSTANCE!!
                return it
            }

            synchronized(this) { // this 객체에 lock을 걸어서 block의 내용을 일관된 결과로 실행시킬 수 있음
                INSTANCE = Room
                    .databaseBuilder(context, LoginDatabase::class.java, context.getString(R.string.login_database))
                    .fallbackToDestructiveMigration() // 기기의 DB 버전으로 마이그레이션할 DB가 없는 경우 IllegalStateException이 발생하는 대신 DB를 재생성하도록함
                    .build()
                return INSTANCE!!
            }
        }
    }
}