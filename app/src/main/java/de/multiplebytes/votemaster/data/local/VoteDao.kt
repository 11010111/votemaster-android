package de.multiplebytes.votemaster.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import de.multiplebytes.votemaster.domain.model.VoteRecord

@Dao
interface VoteDao {
    @Query("SELECT * FROM votes")
    suspend fun votes(): List<VoteRecord>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun create(vote: VoteRecord)
}
