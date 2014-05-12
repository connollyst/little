package com.quane.little.ide.view

import com.quane.little.ide.presenter.BlockPresenter
import com.quane.little.language.event.Event.Event
import com.quane.little.data.model.ListenerRecord
import com.quane.little.language.event.EventListener

trait EventListenerView extends View[EventListenerViewPresenter] {

  def setEvent(event: Event): Unit

  def createBlock(): BlockPresenter[_]

}

trait EventListenerViewPresenter
  extends ViewPresenter {

  def onEventChange(event: Event): Unit

  def compile: EventListener

  def save(): ListenerRecord

}
