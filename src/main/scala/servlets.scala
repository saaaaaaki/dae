import java.util.UUID
import javax.servlet.http.{HttpServletResponse,HttpServletRequest,HttpServlet}
import javax.servlet.annotation.WebServlet
import akka.actor.{ActorRef,ActorSystem,Props}
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.util.{Failure,Success}


@WebServlet(name = "bookmarkServlet",urlPatterns = Array("/"),asyncSupported=true)
class BookmarkServlet extends HttpServlet{
  import BookmarkStore.{AddBookmark,GetBookmark}
  val system:ActorSystem = ActorSystem("BookmarkStoreActors")
  val bookmarkStore:ActorRef = system.actorOf(Props[BookmarkStore],name="bookmarkStore")

  override def doPost(req:HttpServletRequest,res:HttpServletResponse): Unit ={
    import ExecutionContext.Implicits.global
    val asyncCtx = req.startAsync()
    asyncCtx.setTimeout(5 * 1000)
    val writer = asyncCtx.getResponse.getWriter
    val title = req.getParameter("title")
    val url = req.getParameter("url")
    println(s"title: $title, url: $url")

    implicit val timeout = Timeout(5 seconds)

    val uuidFuture = bookmarkStore ? AddBookmark(title,url)
    uuidFuture.mapTo[Option[UUID]].onComplete{
      case Success(uuid) =>
        println(s"Success($uuid)")
        writer.write(s"Successfully created bookmark with uuid = $uuid")
      case Failure(error) =>
        println(s"Failure($error)")
        writer.write("Failure creating bookmark: " + error.getMessage)
    }
  }

  override def doGet(req:HttpServletRequest,res:HttpServletResponse){
    implicit val ec = ExecutionContext.Implicits.global

    val asyncCtx = req.startAsync()
    val writer = asyncCtx.getResponse.getWriter
    val bookmarkId = req.getParameter("uuid")

    implicit val timeout = Timeout(5 seconds)
    asyncCtx.setTimeout(5 * 1000)
    val bookmarkFuture = bookmarkStore ? GetBookmark(UUID.fromString(bookmarkId))
    bookmarkFuture.mapTo[Option[Bookmark]].onComplete{
      case Success(bm) =>
        writer.write(bm.getOrElse("Not found").toString)
      case Failure(error) =>
        writer.write("Could not retrieve bookmark: " + error.getMessage)
    }
  }
  override def destroy(): Unit ={
    system.shutdown()
  }
}
