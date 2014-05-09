package com.quane.little.ide.presenter

import com.quane.little.ide.view.{ToolboxView, ToolboxViewPresenter}
import com.quane.little.data.model.CodeCategory
import com.quane.little.data.service.{ListenerService, FunctionService}
import com.quane.little.language.event.Event

/** Presenter for the toolbox view, from which the user grab code components.
  *
  * @author Sean Connolly
  */
class ToolboxPresenter[V <: ToolboxView](val view: V)
  extends ToolboxViewPresenter {

  view.registerViewPresenter(this)

  initFunctions()
  initEvents()
  initEventListeners()

  private def initFunctions() = {
    CodeCategory.values foreach {
      category =>
        view.createToolboxTab(category.toString)
    }
    FunctionService().findByUser("connollyst") foreach {
      function =>
        view.createToolboxItem(function.category.toString, function.definition.name, function.id)
    }
  }

  private def initEvents() = {
    view.createToolboxTab("Events")
    Event.values foreach {
      event =>
        view.createToolboxItem("Events", event.toString, null)
    }
  }

  private def initEventListeners() = {
    view.createToolboxTab("Event Handlers")
    ListenerService().init()
    ListenerService().findByUser("connollyst") foreach {
      listener =>
        view.createToolboxItem("Event Handlers", listener.listener.event.toString, listener.id)
    }
  }

}
