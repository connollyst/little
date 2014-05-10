package com.quane.little.ide.view.html

import com.vaadin.ui._
import com.quane.little.ide.view.EventListenerView
import com.quane.little.ide.presenter.FunctionReferencePresenter
import com.quane.little.language.event.Event
import com.quane.little.language.event.Event.Event
import com.vaadin.data.Property.{ValueChangeEvent, ValueChangeListener}
import com.quane.vaadin.scala.{DroppableTarget, VaadinMixin}
import scala.Some
import com.vaadin.event.dd.{DragAndDropEvent, DropHandler}
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
  val body = new EventListenerBody(this)
  val footer = new EventListenerFooter(this)

  setStyleName(EventListenerLayout.Style)
  add(header)
  add(body)
  add(footer)

  override def setEvent(event: Event) = header.setEvent(event)

  override def createFunctionReference() =
    new FunctionReferencePresenter(body.createFunctionReference())

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

class EventListenerBody(view: EventListenerLayout)
  extends CssLayout
  with VaadinMixin {

  setStyleName(EventListenerLayout.StyleBody)

  var function: Option[FunctionReferenceLayout] = None
  var dropTarget = add(new DroppableTarget(new CssLayout))
  dropTarget.setDropHandler(new EventListenerDropHandler(view))

  def createFunctionReference(): FunctionReferenceLayout = {
    function match {
      case Some(f) => f.removeFromParent()
      case None => // no problem
    }
    val view = new FunctionReferenceLayout
    function = Some(view)
    dropTarget.component.addComponent(view)
    view
  }

  private class EventListenerDropHandler(view: EventListenerLayout)
    extends DropHandler {
    override def getAcceptCriterion = AcceptAll.get()

    override def drop(event: DragAndDropEvent) =
      event.getTransferable match {
        case transferable: CodeTransferable if transferable.category == CodeCategory.Function =>
          IDECommandExecutor.execute(
            new AddFunctionReferenceCommand(view.presenter, transferable.codeId)
          )
      }
  }

}

class EventListenerFooter(view: EventListenerLayout)
  extends CssLayout
  with VaadinMixin {

  setStyleNames(ExpressionLayout.Style, EventListenerLayout.StyleFooter)

}