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
import org.junit.Assert.assertTrue
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
    fun daoInsert_insertBookIntoDB() = runBlocking {
        addOneItemToDb()
        val allBooks = bookDao.getAllBooks().first()
        assertEquals(allBooks[0], book1)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertTwoBooksIntoDB() = runBlocking {
        addTwoItemToDb()
        val allBooks = bookDao.getAllBooks().first()
        assertEquals(allBooks[0], book1)
        assertEquals(allBooks[1], book2)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_deleteBookFromDB() = runBlocking {
        addTwoItemToDb()
        bookDao.delete(book1)
        bookDao.delete(book2)
        val allBooks = bookDao.getAllBooks().first()
        assertTrue(allBooks.isEmpty())
    }

    @Test
    @kotlin.jvm.Throws(Exception::class)
    fun daoUpdateItems_updatesItemsInDB() = runBlocking {
        addTwoItemToDb()
        bookDao.update(Book(1, "Updated Title", "New Author", 20, 5, 230, 90, false, 4.5, Date().toString()))
        bookDao.update(Book(2, "Updated Title2", "New Author2", 30, 12, 560, 120, false, 2.2, Date().toString()))

        val allItems = bookDao.getAllBooks().first()
        assertEquals(allItems[0], Book(1, "Updated Title", "New Author", 20, 5, 230, 90, false, 4.5, Date().toString()))
        assertEquals(allItems[1], Book(2, "Updated Title2", "New Author2", 30, 12, 560, 120, false, 2.2, Date().toString()))
    }



}