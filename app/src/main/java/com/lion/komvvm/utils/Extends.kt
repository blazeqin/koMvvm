import androidx.collection.ArrayMap
import com.lion.komvvm.data.dao.BaseDao
import com.lion.mvvmlib.base.BaseResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async

const val DATABASE_NAME = "komvvm-db"

const val PAGE_SIZE = 15

const val CHAPTER_ID = "chapter_id"

//first load data from internet
val mFlagMap = ArrayMap<BaseDao<*>,Boolean>()

/**
 * a common method use for getting data
 */
suspend fun <T> getCommonData(
    scope: CoroutineScope,
    dao: BaseDao<*>,
    dbData: suspend () -> List<T>,
    networkData:suspend ()-> BaseResult<List<T>>,
    insertData:suspend (List<T>)->Unit
): BaseResult<List<T>> {
    //first load from internet
    val flag = mFlagMap.get(dao)
    var result: List<T>? = null
    if (flag == null || !flag) {
        mFlagMap[dao] = true
    }else
        result = scope.async { dbData() }.await()//需要获取返回结果时使用async
    if (result.isNullOrEmpty()) {
        val data = scope.async { networkData() }.await()
        if (data.isSuccess()) {
            insertData(data.data)
        }
        return data
    }
    return BaseResult(0, "",result)
}