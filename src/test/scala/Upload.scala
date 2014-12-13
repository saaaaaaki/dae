import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class Upload extends Simulation{
	val proto = http.baseURL("http://localhost:8080")
	.baseHeaders(
		Map("X-Requested-With" -> "Gatling"))

	val scn = senario("First").exec(
		println("===========test======================="))
}