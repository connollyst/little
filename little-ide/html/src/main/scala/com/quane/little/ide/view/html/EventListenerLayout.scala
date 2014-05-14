package com.quane.little.ide.view.html

import com.vaadin.ui._
import com.quane.little.ide.view.EventListenerView
import com.vaadin.data.Property.{ValueChangeEvent, ValueChangeListener}
import com.quane.vaadin.scala.VaadinMixin
import scala.Some
import com.vaadin.server.Sizeable
import com.quane.little.language.event.Event
import com.quane.little.language.event.Event.Event

object EventListenerLayout {
  val Style = "l-event-listener"
  val StyleHeader = Style + "-head"
}

/** An HTML layout view representing an event listener.
  *
  * @author Sean Connolly
  */
class EventListenerLayout
  extends VerticalLayout
  with EventListenerView
  with RemovableComponent
  with VaadinMixin {

  setSizeFull()
  setStyleName(EventListenerLayout.Style)

  private val header = add(new EventListenerHeader(this))

  override def setEvent(event: Event) = header.setEvent(event)

  override def createBlock() = add(new BlockLayout)

}

/** The header of the HTML event listener layout.
  * Provides the controls enabling the user to configure the event listener.
  *
  * @param view the event listener layout
  * @author Sean Connolly
  */
private class EventListenerHeader(view: EventListenerLayout)
  extends HorizontalLayout
  with VaadinMixin {

  var event: Option[Event] = None
  val eventBox = new ComboBox() {
    setImmediate(true)
    addValueChangeListener(new EventChangeListener)
    Event.values foreach {
      addItem(_)
    }
  }
  val label = new Label("then..")
  val saveButton = Buttons.blueButton("Save", view.save)

  setSpacing(true)
  setWidth(100, Sizeable.Unit.PERCENTAGE)
  setDefaultComponentAlignment(Alignment.MIDDLE_LEFT)
  setStyleName(EventListenerLayout.StyleHeader)
  add(eventBox)
  add(label)
  add(saveButton)
  setExpandRatio(label, 1f)

  def setEvent(e: Event) = {
    event = Some(e)
    eventBox.setValue(e)
  }

  /** Listener for when a new [[com.quane.little.language.event.Event]] is
    * selected. Notifies the presenter when appropriate.
    *
    * @author Sean Connolly
    */
  private class EventChangeListener extends ValueChangeListener {
    // TODO the new event is sent to the server, even if just sent FROM there
    override def valueChange(changeEvent: ValueChangeEvent) =
      changeEvent.getProperty.getValue match {
        case newEvent: Event =>
          event match {
            case Some(oldEvent) if newEvent == oldEvent =>
            // do nothing
            case Some(e) =>
              view.presenter.onEventChange(newEvent)
            case None =>
              view.presenter.onEventChange(newEvent)
          }
        case null =>
        // empty selection
        case _ =>
          throw new IllegalArgumentException("Expected " + Event)
      }
  }

}
