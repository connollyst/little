package com.quane.little.ide.view.html

import com.quane.little.ide.presenter.{SetPresenter, GetPresenter, PrintPresenter}
import com.quane.vaadin.scala.DroppableTarget
import vaadin.scala.{CssLayout, VerticalLayout, Component}
import com.vaadin.event.dd.{DragAndDropEvent, DropHandler}
import com.vaadin.event.dd.acceptcriteria.{AcceptCriterion, AcceptAll}
import com.quane.little.ide.view.BlockView


object BlockLayout {
  val Style = "l-step-list"
  val StyleSeparator = Style + "-separator"
}

class BlockLayout
  extends VerticalLayout with BlockView {

  spacing = false
  styleName = BlockLayout.Style


  override def createSetExpression(): SetPresenter[SetStatementLayout] = {
    println("Adding a new SET expression view..")
    val view = new SetStatementLayout()
    add(view)
    new SetPresenter(view)
  }

  override def createGetExpression(): GetPresenter[GetStatementLayout] = {
    println("Adding a new GET expression view..")
    val view = new GetStatementLayout()
    add(view)
    new GetPresenter(view)
  }

  override def createPrintStatement(): PrintPresenter[PrintStatementLayout] = {
    println("Adding a new PRINT expression view..")
    val view = new PrintStatementLayout()
    add(view)
    new PrintPresenter(view)
  }

  override def add[C <: Component](component: C): C = {
    super.add(component)
    super.add(new ExpressionListSeparator())
    component
  }

  override def addComponent[C <: Component](component: C): C = {
    add(component)
  }

  override def removeComponent(c: Component) {
    val index = componentIndex(c)
    //    val separator = component(index + 1);
    //    super.removeComponent(separator);
    super.removeComponent(c)
  }

  def contains(c: Component): Boolean = {
    componentIndex(c) != -1
  }

  def componentIndex(c: Component): Int = {
    p.getComponentIndex(c.p)
  }

  //  def component(index: Int): Component = {
  //    super.components[index]
  //  }

}

class ExpressionListSeparator extends DroppableTarget(new CssLayout) {

  component.styleName = BlockLayout.StyleSeparator
  dropHandler = new DropHandler() {

    override def getAcceptCriterion: AcceptCriterion = {
      // TODO only accept appropriate Little components
      AcceptAll.get()
    }

    override def drop(event: DragAndDropEvent): Unit = {
      //      val droppedStep = getDroppedStep(event)
      //      val list = getStepList
      //      if (list.contains(droppedStep)) {
      //        list.removeComponent(droppedStep)
      //      }
      //      val separator = ExpressionListSeparator.this
      //      val separatorIndex = list.componentIndex(separator)
      //      list.add(droppedStep, separatorIndex + 1)
    }

    //    def getStepList: BlockLayout = {
    //      val t = ExpressionListSeparator.this
    //      val p = t.parent
    //      p.asInstanceOf[BlockLayout]
    //    }
    //
    //    def getDroppedStep(event: DragAndDropEvent): ExpressionView[ExpressionPresenter] = {
    //      val sourceComponent = event.getTransferable.getSourceComponent.asInstanceOf[Component]
    //      if (sourceComponent.isInstanceOf[ExpressionView[ExpressionPresenter]]) {
    //        // An existing step is being moved from elsewhere
    //        sourceComponent.asInstanceOf[ExpressionView[ExpressionPresenter]]
    //      } else if (sourceComponent.isInstanceOf[ToolboxItemComponent]) {
    //        // A new step is being dropped from the toolbox
    //        sourceComponent.asInstanceOf[ToolboxItemComponent].getStep
    //      } else {
    //        throw new ClassCastException("Drop not supported for "
    //          + sourceComponent.getClass.getSimpleName)
    //      }
    //    }

  }
}