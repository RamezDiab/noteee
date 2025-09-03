import com.ramez.notesapp.data.UserEntity
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ramez.notesapp.data.NoteEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: UserEntity,)

    @Query("SELECT * FROM user_table WHERE id =:id")
    suspend fun getUserById(id: Int): UserEntity

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Update
    suspend fun updateNote(userEntity: UserEntity)

    @Query("SELECT * From user_table ")
    fun readAllData(): Flow<List<UserEntity>>

    @Query("SELECT * FROM user_table WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): UserEntity?

}

