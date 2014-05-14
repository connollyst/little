package com.quane.little.ide.view

import com.quane.little.data.model.ListenerRecord

trait EventListenerView extends View[EventListenerViewPresenter] {

  def setEvent(event: Event): Unit

  def createBlock(): BlockView

  def compile(): EventListener = presenter.compile()

  def save(): ListenerRecord = presenter.save()

}

trait EventListenerViewPresenter
  extends ViewPresenter {

  def onEventChange(event: Event): Unit

  def compile(): EventListener

  def save(): ListenerRecord

}
