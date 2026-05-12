package de.multiplebytes.votemaster.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.multiplebytes.votemaster.domain.model.Vote

@Dao
interface VoteDao {
    @Query("SELECT * FROM votes")
    suspend fun votes(): List<Vote>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun create(vote: Vote)
}
