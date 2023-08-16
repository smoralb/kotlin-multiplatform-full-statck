import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

object Database {
    private const val DB_NAME = "shoppingList"
    var collection: CoroutineCollection<ShoppingListItem>

    init {
        val client = KMongo.createClient().coroutine
        val database = client.getDatabase(DB_NAME)
        collection = database.getCollection<ShoppingListItem>()
    }
}