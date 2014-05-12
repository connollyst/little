package com.quane.little.ide.view.html

import com.vaadin.ui._
import com.quane.little.ide.view.EventListenerView
import com.quane.little.ide.presenter.BlockPresenter
import com.quane.little.language.event.Event
import com.quane.little.language.event.Event.Event
import com.vaadin.data.Property.{ValueChangeEvent, ValueChangeListener}
import com.quane.vaadin.scala.{DroppableTarget, VaadinMixin}
import scala.Some
import com.vaadin.event.dd.DropHandler
import com.vaadin.event.dd.acceptcriteria.AcceptAll
import com.quane.little.ide.view.html.dnd.CodeTransferable
import com.quane.little.data.model.CodeCategory
import com.quane.little.ide.presenter.command.{AddFunctionReferenceCommand, IDECommandExecutor}

object EventListenerLayout {
  val Style = "l-event-listener"
  val StyleHeader = Style + "-head"
  val StyleBody = Style + "-body"
  val StyleFooter = Style + "-foot"
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

  val header = new EventListenerHeader(this)

  setStyleName(EventListenerLayout.Style)
  add(header)

  override def setEvent(event: Event) = header.setEvent(event)

  override def createBlock() = new BlockPresenter(add(new BlockLayout))

}

/** The header of the HTML event listener layout.
  * Provides the controls enabling the user to configure the event listener.
  *
  * @param view the event listener layout
  * @author Sean Connolly
  */
class EventListenerHeader(view: EventListenerLayout)
  extends HorizontalLayout
  with VaadinMixin {

  setSpacing(true)
  setDefaultComponentAlignment(Alignment.MIDDLE_LEFT)
  setStyleNames(ExpressionLayout.Style, EventListenerLayout.StyleHeader)

  var event: Option[Event] = None
  val eventBox = new ComboBox() {
    setImmediate(true)
    addValueChangeListener(new EventChangeListener)
    Event.values foreach {
      event => addItem(event)
    }
  }
  add(new Label("When"))
  add(eventBox)
  add(new Label("then.."))
  add(CloseButton(view))

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
