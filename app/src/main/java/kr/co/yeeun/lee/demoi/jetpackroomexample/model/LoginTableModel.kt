package kr.co.yeeun.lee.demoi.jetpackroomexample.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Login")
data class LoginTableModel (
    @ColumnInfo(name = "username")
    var Username: String,

    @ColumnInfo(name = "password")
    var Password: String
) { // class body 안에 정의된 id의 값이 달라도 나머지 property가 같은 객체는 같은 것으로 취급함
    // 생성자에 정의된 property만 toString(), equals(), hashCode(), copy()가 사용되기 때문(component() 함수 포함)
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int? = null
}