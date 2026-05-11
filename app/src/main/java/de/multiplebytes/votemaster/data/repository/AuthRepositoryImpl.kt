package de.multiplebytes.votemaster.data.repository

import de.multiplebytes.votemaster.domain.model.Vote
import de.multiplebytes.votemaster.domain.repository.AuthRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.user.UserInfo
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.storage

class AuthRepositoryImpl(
    private val supabaseClient: SupabaseClient
) : AuthRepository {
    private val votesTable = "votes"
    private val imagesBucket = "images"

    override fun sessionStatus() = supabaseClient.auth.sessionStatus

    override suspend fun signIn(
        email: String,
        password: String
    ): Result<Unit> = runCatching {
        supabaseClient.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
    }

    override suspend fun signUp(
        email: String,
        password: String,
        photo: ByteArray?
    ): Result<UserInfo?> = runCatching {
        val userInfo = supabaseClient.auth.signUpWith(Email) {
            this.email = email
            this.password = password
        } ?: throw Exception("Sign up failed")

        val userId = userInfo.id
        var publicUrl: String? = null

        if (photo != null) {
            val fileName = "$userId/thumbnail.jpg"
            val bucket = supabaseClient.storage.from(imagesBucket)

            bucket.upload(fileName, photo) {
                upsert = true
            }
            publicUrl = bucket.publicUrl(fileName)
        }

        val vote = Vote(
            id = userId,
            displayName = "Anonymous",
            image = publicUrl
        )

        supabaseClient.from(votesTable).upsert(vote)

        userInfo
    }

    override suspend fun signOut() {
        supabaseClient.auth.signOut()
    }
}
