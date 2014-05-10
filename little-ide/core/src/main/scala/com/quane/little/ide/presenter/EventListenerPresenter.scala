package com.quane.little.ide.presenter

import com.quane.little.ide.view.{EventListenerViewPresenter, EventListenerView}
import com.quane.little.language.event.EventListener
import com.quane.little.data.model.RecordId
import com.quane.little.language.event.Event.Event
import com.quane.little.language.FunctionReference
import com.quane.little.data.service.FunctionService

class EventListenerPresenter[V <: EventListenerView](view: V)
  extends EventListenerViewPresenter {

  view.registerViewPresenter(this)

  private var _event: Option[Event] = None
  private val _function = view.createFunctionReference()

  private[presenter] def initialize(id: RecordId, listener: EventListener): EventListenerPresenter[V] = {
    event = listener.event
    function = listener.function
    this
  }

  private[presenter] def event: Event = _event.get

  private[presenter] def event_=(e: Event): Unit = {
    _event = Some(e)
    view.setEvent(e)
  }

  private[presenter] def function: FunctionReferencePresenter[_] = _function

  private[presenter] def function_=(fun: FunctionReference): Unit =
    _function.initialize(fun)

  override def onEventChange(e: Event) = event = e

  override def requestAddFunctionReference(id: RecordId, index: Int) = {
    println("Adding function reference to event listener")
    _function.initialize(FunctionService().findReference(id))
  }

  def compile: EventListener = new EventListener(event, function.compile)

}
