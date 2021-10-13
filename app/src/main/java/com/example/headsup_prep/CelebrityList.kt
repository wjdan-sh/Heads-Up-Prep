package com.example.headsup_prep

import com.google.gson.annotations.SerializedName

class CelebrityList {
        @SerializedName("pk")
        var pk: Int? = null
        @SerializedName("name")
        var name: String? = null
        @SerializedName("taboo1")
        var taboo1: String? = null
        @SerializedName("taboo2")
        var taboo2: String? = null
        @SerializedName("taboo3")
        var taboo3: String? = null

}
data class Celebrity(val name: String, val taboo1: String, val taboo2: String, val taboo3: String, val pk: Int)