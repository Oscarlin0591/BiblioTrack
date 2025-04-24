import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.bibliotrack.data.Book
import com.example.bibliotrack.data.BookDao
import com.example.bibliotrack.data.BookDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.Date

@RunWith(AndroidJUnit4::class)
class BookDaoTest {
    private lateinit var bookDao: BookDao
    private lateinit var bookDatabase: BookDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        bookDatabase = Room.inMemoryDatabaseBuilder(context, BookDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        bookDao = bookDatabase.bookDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        bookDatabase.close()
    }

    private var book1 = Book(1, "Title1", "Author1", 20, 3, 230, 45, false, 5.0, Date().toString())
    private var book2 = Book(2, "Title2", "Author2", 20, 3, 230, 45, false, 3.6, Date().toString())

    private suspend fun addOneItemToDb() {
        bookDao.insert(book1)
    }

    private suspend fun addTwoItemToDb() {
        bookDao.insert(book1)
        bookDao.insert(book2)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsBookIntoDB() = runBlocking {
        addOneItemToDb()
        val allBooks = bookDao.getAllBooks().first()
        assertEquals(allBooks[0], book1)
    }
}