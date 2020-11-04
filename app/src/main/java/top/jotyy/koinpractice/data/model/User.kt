package top.jotyy.koinpractice.data.model


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("bio")
    val bio: String,
    @SerializedName("blog")
    val blog: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("followers")
    val followers: Int,
    @SerializedName("following")
    val following: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val login: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("node_id")
    val nodeId: String,
    @SerializedName("public_gists")
    val publicGists: Int,
    @SerializedName("public_repos")
    val publicRepos: Int,
    @SerializedName("twitter_username")
    val twitterUsername: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("url")
    val url: String
)
