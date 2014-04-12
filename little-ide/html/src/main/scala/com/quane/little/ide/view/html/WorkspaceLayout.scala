package com.quane.little.ide.view.html

import com.quane.little.ide.presenter.FunctionDefinitionPresenter
import com.quane.little.ide.view.WorkspaceView
import com.quane.little.language.Expression
import com.quane.vaadin.scala.{VaadinMixin, DroppableTarget}
import com.vaadin.event.dd.acceptcriteria.{AcceptAll, AcceptCriterion}
import com.vaadin.event.dd.{DragAndDropEvent, DropHandler}
import com.vaadin.ui.HorizontalLayout

object WorkspaceLayout {
  val Style = "l-workspace"
}

class WorkspaceLayout
  extends DroppableTarget(new WorkspaceLayoutBody)
  with WorkspaceView {

  setSizeFull()
  setDropHandler(new DropHandler {

    override def drop(event: DragAndDropEvent): Unit = {
      // TODO add data flavor check
      // TODO avoid casting?
      val expression = event.getTransferable.getData("little-expression").asInstanceOf[Expression]
      println("Dropped " + expression)
    }

    override def getAcceptCriterion: AcceptCriterion = {
      // TODO figure out ContainsDataFlavor
      AcceptAll.get()
    }
  })

  override def createFunctionDefinition(): FunctionDefinitionPresenter[_] =
    new FunctionDefinitionPresenter(component.add(new FunctionDefinitionLayout()))

}

class WorkspaceLayoutBody
  extends HorizontalLayout
  with RemovableComponent
  with VaadinMixin {

  setSpacing(true)
  setStyleName(WorkspaceLayout.Style)

  //    addComponent(
  //      new VerticalLayout {
  //        // TODO this isn't a long term solution
  //        FunctionService.FunctionNames.foreach {
  //          function =>
  //            addComponent(new Button(function, new ClickListener {
  //              def buttonClick(event: ClickEvent) = {
  //                _viewPresenter.foreach {
  //                  listener: WorkspaceViewPresenter =>
  //                    listener.openFunctionDefinition(function)
  //                }
  //              }
  //            }))
  //        }
  //      }
  //    )
}
