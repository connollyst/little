package com.quane.little.ide.presenter

import com.quane.little.ide.view.{EvaluableCodeViewPresenter, EventListenerViewPresenter, EventListenerView}
import com.quane.little.language.event.EventListener
import com.quane.little.data.model.{ListenerRecord, RecordId}
import com.quane.little.language.event.Event.Event
import com.quane.little.language.EvaluableCode
import com.quane.little.data.service.ListenerService

class EventListenerPresenter[V <: EventListenerView](view: V)
  extends EventListenerViewPresenter {

  view.registerViewPresenter(this)

  private var _id: Option[RecordId] = None
  private val _username = "connollyst"
  private var _event: Option[Event] = None
  private val _block = view.createBlock()

  private[presenter] def initialize(id: RecordId, listener: EventListener): EventListenerPresenter[V] = {
    _id = Some(id)
    event = listener.event
    steps = listener.steps
    this
  }

  private[presenter] def event: Event = _event.get

  private[presenter] def event_=(e: Event): Unit = {
    _event = Some(e)
    view.setEvent(e)
  }

  private[presenter] def +=(step: EvaluableCodeViewPresenter): Unit = _block.add(step)

  private[presenter] def steps: List[_ <: EvaluableCodeViewPresenter] = _block.steps

  private[presenter] def steps_=(steps: List[_ <: EvaluableCode]): Unit = _block.steps = steps

  override def onEventChange(e: Event) = event = e

  def compile: EventListener = {
    val listener = new EventListener(event)
    listener.steps = _block.compile.steps
    listener
  }

  override def save: ListenerRecord = {
    val listener = compile
    _id match {
      case Some(id) =>
        println("Saving changes to listener " + id + "..")
        ListenerService().update(id, listener)
      case None =>
        println("Saving new listener..")
        val record = ListenerService().insert(_username, listener)
        _id = Some(record.id)
        record
    }
  }

}
