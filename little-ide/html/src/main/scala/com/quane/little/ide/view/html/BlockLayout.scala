package com.quane.little.ide.view.html

import com.quane.little.ide.presenter._
import com.quane.vaadin.scala.DroppableTarget
import vaadin.scala.{VerticalLayout, Component}
import com.vaadin.event.dd.{DragAndDropEvent, DropHandler}
import com.vaadin.event.dd.acceptcriteria.{AcceptCriterion, AcceptAll}
import com.quane.little.ide.view.BlockView
import vaadin.scala.HorizontalLayout
import vaadin.scala.MenuBar

object BlockLayout {
  val Style = "l-step-list"
  val StyleSeparator = Style + "-separator"
}

class BlockLayout
  extends VerticalLayout with BlockView {

  spacing = false
  styleName = BlockLayout.Style

  override def addSetStatement(): SetStatementPresenter[SetStatementLayout] = {
    println("Adding a new SET expression view..")
    val view = new SetStatementLayout()
    add(view)
    new SetStatementPresenter(view)
  }

  override def addGetStatement(): GetStatementPresenter[GetStatementLayout] = {
    println("Adding a new GET expression view..")
    val view = new GetStatementLayout()
    add(view)
    new GetStatementPresenter(view)
  }

  override def addPrintStatement(): PrintStatementPresenter[PrintStatementLayout] = {
    println("Adding a new PRINT expression view..")
    val view = new PrintStatementLayout()
    add(view)
    new PrintStatementPresenter(view)
  }

  override def addConditionalStatement(): ConditionalPresenter[ConditionalComponent] = {
    println("Adding a new CONDITIONAL expression view..")
    val view = new ConditionalComponent()
    add(view)
    new ConditionalPresenter(view)
  }

  override def addFunctionReference(): FunctionReferencePresenter[FunctionReferenceComponent] = {
    println("Adding a new FUNCTION REFERENCE view..")
    val view = new FunctionReferenceComponent()
    add(view)
    new FunctionReferencePresenter(view)
  }

  override def add[C <: Component](component: C): C = {
    super.add(component)
    super.add(new ExpressionListSeparator(this))
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

  def requestAddSetStatement() = {
    viewListeners.foreach {
      listener => listener.requestAddSetStatement()
    }
  }

  def requestAddPrintStatement() = {
    viewListeners.foreach {
      listener => listener.requestAddPrintStatement()
    }
  }

}

class ExpressionListSeparator(block: BlockLayout)
  extends DroppableTarget(new HorizontalLayout) {

  component.styleName = BlockLayout.StyleSeparator

  val menu = new MenuBar
  val item = menu.addItem("+")
  item.addItem("Set", {
    item => block.requestAddSetStatement()
  })
  item.addItem("Print", {
    item => block.requestAddPrintStatement()
  })
  component.add(menu)

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