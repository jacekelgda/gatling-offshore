import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class OffshoreSimulation extends Simulation {

  val httpConf = http
    //ec2-52-7-167-16.compute-1.amazonaws.com
    //.baseURL("http://ec2-52-6-114-144.compute-1.amazonaws.com") // Here is the root for all relative URLs
    .baseURL("http://awseb-e-a-AWSEBLoa-BZ3R8GJHNH9W-1571054632.us-east-1.elb.amazonaws.com")
    //.baseURL("http://offshore.lh")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Here are the common headers
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val headers_10 = Map("Content-Type" -> """application/x-www-form-urlencoded""") // Note the headers specific to a given request

  val scn = scenario("Offshore_1") // A scenario is a chain of requests and pauses
    .exec(http("homepage")
      .get("/")
      .check(regex("""<h2 class="visible-lg">Siste Nytt:</h2>""").exists))
      .pause(10) // Note that Gatling has recorded real time pauses
    // .exec(http("riggdata")
    //   .get("/rigg"))
    //   .pause(10)
    // .exec(http("search1")
    //   .get("/?s=Viking"))
    //   .pause(10)

  //setUp(scn.inject(atOnceUsers(1)).protocols(httpConf))
  setUp(scn.inject(rampUsers(500) over (60 seconds))).protocols(httpConf)
}
