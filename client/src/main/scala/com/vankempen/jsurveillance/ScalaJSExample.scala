package com.vankempen.jsurveillance

import com.thoughtworks.binding.{Binding, dom}
import com.thoughtworks.binding.Binding.{BindingSeq, Var, Vars}
import com.vankempen.jsurveillance.shared.{Camera, SharedMessages}
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.{Event, Node, document, window}

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}


case class Contact(name: Var[String], email: Var[String])

@JSExportTopLevel("MvkMain")
object ScalaJSExample {

  def displayError(msg: String) = window.alert(msg)

  val data = Vars.empty[Contact]
  data.value += Contact(Var("Marc"), Var("marc@bowtie.nl"))

  implicit def makeIntellijHappy[T<:org.scalajs.dom.raw.Node](x: scala.xml.Node): Binding[T] =
    throw new AssertionError("This should never execute.")

  val onGetCameras: (Event => Unit) = (evt: Event) => Util.getCameras

  @dom
  def table: Binding[Node] = {
    <div>
      <div>
        <button type="button" class="btn btn-primary" onclick = { event: Event => data.value += Contact(Var("test"), Var("test@test.nl")) } >
          Add a camera
        </button>
        <button type="button" class="btn btn-primary" onclick = { onGetCameras }>Search cameras</button>
      </div>
      <table class="table">
        <thead>
          <tr>
            <th>Name</th>
            <th>E-mail</th>
          </tr>
        </thead>
        <tbody>
          {
            for (contact <- data) yield {
              <tr>
                <td>{contact.name.bind}</td>
                <td>{contact.email.bind}</td>
              </tr>
            }
          }
        </tbody>
      </table>
    </div>
  }


  @JSExport
  def main(args: Array[String]): Unit = {
    val v = table
    val elt = document.getElementById("dynamic-content")
    dom.render(elt, v)
  }
}
