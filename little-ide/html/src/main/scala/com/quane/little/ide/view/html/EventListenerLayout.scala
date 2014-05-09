package com.quane.little.ide.view.html

import com.vaadin.ui.{ComboBox, HorizontalLayout, VerticalLayout}
import com.quane.little.ide.view.EventListenerView
import com.quane.little.ide.presenter.FunctionReferencePresenter
import com.quane.little.language.event.Event
import com.quane.little.language.event.Event.Event
import com.vaadin.data.Property.{ValueChangeEvent, ValueChangeListener}

object EventListenerLayout {
  val Style = "l-event-listener"
}

/** An HTML layout view representing an event listener.
  *
  * @author Sean Connolly
  */
class EventListenerLayout
  extends VerticalLayout
  with EventListenerView
  with RemovableComponent {

  val header = new EventListenerHeader(this)
  var function: Option[FunctionReferenceLayout] = None

  setStyleName(EventListenerLayout.Style)
  addComponent(header)

  override def setEvent(event: Event) = header.setEvent(event)

  override def createFunctionReference() = {
    function match {
      case Some(f) => f.removeFromParent()
      case None => // no problem
    }
    val view = new FunctionReferenceLayout
    addComponent(view)
    function = Some(view)
    new FunctionReferencePresenter(view)
  }

}

class EventListenerHeader(view: EventListenerLayout)
  extends HorizontalLayout {

  var event: Option[Event] = None

  val eventBox = new ComboBox() {
    // TODO this doesn't seem to fire right away? =\
    addValueChangeListener(new ValueChangeListener {
      override def valueChange(changeEvent: ValueChangeEvent) = {
        val value = changeEvent.getProperty.getValue
        println("New event selected: " + value)
        value match {
          case newEvent: Event =>
            event match {
              case Some(oldEvent) if newEvent != oldEvent =>
                view.presenter.onEventChange(newEvent)
              case None =>
                view.presenter.onEventChange(newEvent)
            }
          case _ =>
            throw new IllegalArgumentException("Expected " + Event)
        }
      }
    })
  }
  Event.values foreach {
    event => eventBox.addItem(event)
  }
  addComponent(eventBox)

  def setEvent(e: Event) = {
    event = Some(e)
    eventBox.setValue(event)
  }

}