package com.quane.little.ide.presenter

import com.quane.little.ide.view.{CodeViewPresenter, EventListenerViewPresenter, EventListenerView}
import com.quane.little.data.model.{ListenerId, ListenerRecord, Id}
import com.quane.little.data.service.ListenerService
import com.escalatesoft.subcut.inject.{Injectable, BindingModule}
import com.quane.little.language.event.Event.Event
import com.quane.little.language.event.EventListener
import com.quane.little.language.Code

class EventListenerPresenter[V <: EventListenerView](view: V)(implicit val bindingModule: BindingModule)
  extends EventListenerViewPresenter
  with Injectable {

  view.registerViewPresenter(this)

  private val listenerService = inject[ListenerService]

  private var _id: Option[ListenerId] = None
  private val _username = "connollyst"
  private var _event: Option[Event] = None
  private val _block = new BlockPresenter(view.createBlock())

  private[presenter] def initialize(id: ListenerId, listener: EventListener): EventListenerPresenter[V] = {
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

  private[presenter] def +=(step: CodeViewPresenter): Unit = _block.add(step)

  private[presenter] def steps: List[_ <: CodeViewPresenter] = _block.steps

  private[presenter] def steps_=(steps: List[_ <: Code]): Unit = _block.steps = steps

  override def onEventChange(e: Event) = event = e

  def compile(): EventListener = {
    val listener = new EventListener(event)
    listener.steps = _block.compile().steps
    listener
  }

  override def save(): ListenerRecord = {
    val listener = compile()
    _id match {
      case Some(id) =>
        println("Saving changes to listener " + id + "..")
        listenerService.update(id, listener)
      case None =>
        println("Saving new listener..")
        val record = listenerService.insert(_username, listener)
        _id = Some(record.id)
        record
    }
  }

}
