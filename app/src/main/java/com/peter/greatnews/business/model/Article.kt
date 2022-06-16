package com.peter.greatnews.business.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.format.DateTimeFormatter

@Entity(tableName = "article_tbl")
data class Article(
    @ColumnInfo(name = "author")
    var author: String?,

    @ColumnInfo(name = "title")
    var title: String?,

    @ColumnInfo(name = "description")
    var description: String?,

    @PrimaryKey
    var url: String,

    @ColumnInfo(name = "image")
    var image: String?,

    @ColumnInfo(name = "date")
     var date: String?,

    @ColumnInfo(name = "content")
    var content: String?,

    var isFavorite: Boolean = false
) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun formattedDate(): String {
        return date!!.format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy"))
    }

}