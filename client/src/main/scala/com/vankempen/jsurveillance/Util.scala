package com.vankempen.jsurveillance

import org.scalajs.dom.ext.Ajax
import io.circe.parser.decode
import io.circe.syntax._
import io.circe.generic.auto._

import com.vankempen.jsurveillance.shared.Camera
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

object Util {
  def getCameras = {
    val f = Ajax.get("/api/cameras") map {
      result => decode[List[Camera]](result.responseText)
//      result => println(result.responseText)
    }
  }

}
