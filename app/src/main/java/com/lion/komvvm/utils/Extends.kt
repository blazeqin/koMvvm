import com.lion.mvvmlib.base.BaseResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async

const val DATABASE_NAME = "komvvm-db"

const val PAGE_SIZE = 15

const val CHAPTER_ID = "chapter_id"

/**
 * a common method use for getting data
 */
suspend fun <T> getCommonData(
    scope: CoroutineScope,
    dbData: suspend () -> List<T>,
    networkData:suspend ()-> BaseResult<List<T>>,
    insertData:suspend (List<T>)->Unit
): BaseResult<List<T>> {
    val result = scope.async { dbData() }.await()
    if (result.isNullOrEmpty()) {
        val data = scope.async { networkData() }.await()
        if (data.isSuccess()) {
            insertData(data.data)
        }
        return data
    }
    return BaseResult(0, "",result)
}