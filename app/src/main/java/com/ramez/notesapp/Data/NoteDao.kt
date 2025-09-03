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
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(noteEntity: NoteEntity)


    @Query("SELECT * FROM note_table WHERE id =:id")
    suspend fun getNoteById(id: Int): NoteEntity

    @Query("SELECT * From Note_Table  WHERE userId = :UserId ")
    fun readAllData (UserId:Int): Flow<List<NoteEntity>>

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Update
    suspend fun updateNote(noteEntity: NoteEntity)


//    // 3shan a read el notes bta3t el user el mo7dd
//    @Query("SELECT * FROM note_table WHERE userId = :userId")
//    fun readUserNotes(userId: String): Flow<List<NoteEntity>>
}
