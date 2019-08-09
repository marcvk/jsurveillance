package com.vankempen.jsurveillance.controllers

import java.util

import javax.inject._
import com.vankempen.jsurveillance.shared.{Camera, SharedMessages}
import play.api.mvc._
import be.teletask.onvif.DiscoveryManager
import be.teletask.onvif.listeners.DiscoveryListener
import be.teletask.onvif.models.Device
import io.circe.syntax._

import scala.concurrent.{ExecutionContext, Future, Promise}

@Singleton
class Application @Inject()(cc: ControllerComponents, ec: ExecutionContext) extends AbstractController(cc) {

  implicit val iec = ec

  def discoverCameras = {
    import scala.collection.JavaConverters._

    val promise = Promise[List[Camera]]()

    val manager = new DiscoveryManager
    manager.setDiscoveryTimeout(10000)
    manager.discover(new DiscoveryListener {
      override def onDiscoveryStarted(): Unit = println("Discovery started")

      override def onDevicesFound(devices: util.List[Device]): Unit = {
        promise.success(devices.asScala.toList map {
          d => Camera(d.getHostName, d.getHostName, d.getUsername, d.getPassword)
        })
      }
    })
    promise.future
  }

  def getCameras = Action.async {
    import com.vankempen.jsurveillance.shared.Camera._
    discoverCameras map { devices =>
      Ok(devices.asJson.toString())
    }
  }

  def index = Action.async {
    Future {
      Ok(views.html.index("hallo"))
    }
  }

}
