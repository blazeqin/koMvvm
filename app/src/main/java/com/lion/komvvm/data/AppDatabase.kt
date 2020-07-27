package com.lion.komvvm.data

import DATABASE_NAME
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lion.komvvm.data.dao.ArticleDao
import com.lion.komvvm.data.dao.BannerDao
import com.lion.komvvm.data.dao.TabDao
import com.lion.komvvm.data.dao.UserDao
import com.lion.komvvm.entity.ArticlesBean
import com.lion.komvvm.entity.BannerBean
import com.lion.komvvm.entity.NavTypeBean
import com.lion.komvvm.entity.UserBean

/**
 * a room database for this app
 */
@Database(entities = [NavTypeBean::class, ArticlesBean::class,BannerBean::class, UserBean::class], version = 1, exportSchema = false)
//@TypeConverters(Converters::class) //when using complex data
abstract class AppDatabase : RoomDatabase() {
    //get dao
    abstract fun tabDao(): TabDao
    abstract fun articleDao(): ArticleDao
    abstract fun bannerDao(): BannerDao
    abstract fun userDao(): UserDao

    //init database
    companion object{
        //singleton
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context):AppDatabase{
            return instance ?: synchronized(this){
                instance ?: buildDatabase(context).also{ instance = it}
            }
        }
        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context):AppDatabase{
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}