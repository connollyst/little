package com.quane.little.ide.view

import com.quane.little.ide.presenter.{PresenterAcceptsFunctionReference, FunctionReferencePresenter}
import com.quane.little.language.event.Event.Event

trait EventListenerView extends View[EventListenerViewPresenter] {

  def setEvent(event: Event): Unit

  def createFunctionReference(): FunctionReferencePresenter[_]

}

trait EventListenerViewPresenter
  extends ViewPresenter
  with PresenterAcceptsFunctionReference {

  def onEventChange(event: Event): Unit

}
