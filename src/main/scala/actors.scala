import java.util.UUID
import akka.actor.Actor


case class Bookmark(uuid:UUID, title:String, url:String)

object BookmarkStore{
  case class AddBookmark(title:String, url:String)
  case class GetBookmark(uuid:UUID)
}
class BookmarkStore extends Actor{
  import BookmarkStore.{GetBookmark,AddBookmark}

  var bookmarks = Map[UUID,Bookmark]()
  def receive = {
    case AddBookmark(title, url) =>
      val exists = bookmarks.values.exists {
        bm =>
          (bm.title == title && bm.url == url) || false
      }
      if (!exists) {
        val id = UUID.randomUUID
        bookmarks += (id -> Bookmark(id, title, url))
        sender ! Some(id)
      } else {
        sender ! None
      }
    case GetBookmark(uuid) =>
      sender ! bookmarks.get(uuid)
  }
}
