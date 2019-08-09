package com.vankempen.jsurveillance.shared

import io.circe.syntax._
import io.circe.generic.auto._

case class Camera(name: String, hostName: String, userName: String, password: String)
//object Camera {
//  implicit val fooDecoder: Decoder[Camera] = deriveDecoder[Camera]
//  implicit val fooEncoder: Encoder[Camera] = deriveEncoder[Camera]
//}
