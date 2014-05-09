package com.quane.little.ide.presenter

import com.quane.little.ide.view.{EventListenerViewPresenter, EventListenerView}
import com.quane.little.language.event.{Event, EventListener}
import com.quane.little.language.FunctionReference
import com.quane.little.data.model.RecordId

class EventListenerPresenter[V <: EventListenerView](view: V)
  extends EventListenerViewPresenter {

  private[presenter] def initialize(id: RecordId, listener: EventListener): EventListenerPresenter[V] = {
    // TODO initialize presenter & view
    this
  }

  def compile: EventListener =
  // TODO get Event type and function reference from presenter
    new EventListener(Event.OnSpawn, new FunctionReference("TODO"))

}
