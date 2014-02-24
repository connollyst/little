package com.quane.little.ide.view.html

import com.quane.little.ide.presenter._
import com.quane.vaadin.scala.DroppableTarget
import vaadin.scala.{VerticalLayout, Component}
import com.vaadin.event.dd.{DragAndDropEvent, DropHandler}
import com.vaadin.event.dd.acceptcriteria.{AcceptCriterion, AcceptAll}
import com.quane.little.ide.view.BlockView
import vaadin.scala.HorizontalLayout
import vaadin.scala.MenuBar
import com.quane.little.ide.view.html.BlockLayout._

object BlockLayout {
  val DefaultIndex = -1
  val Style = "l-step-list"
  val StyleSeparator = Style + "-separator"
}

class BlockLayout
  extends VerticalLayout
  with BlockView
  with HtmlComponent {

  spacing = false
  styleName = Style

  super.add(new ExpressionListSeparator(this))

  override def addConditional() = addConditional(DefaultIndex)

  override def addConditional(index: Int) =
    new ConditionalPresenter(add(new ConditionalComponent(), componentIndex(index)))

  override def addGetStatement() = addGetStatement(DefaultIndex)

  override def addGetStatement(index: Int) =
    new GetStatementPresenter(add(new GetStatementLayout(), componentIndex(index)))

  override def addSetStatement() = addSetStatement(DefaultIndex)

  override def addSetStatement(index: Int) =
    new SetStatementPresenter(add(new SetStatementLayout(), componentIndex(index)))

  override def addPrintStatement() = addPrintStatement(DefaultIndex)

  override def addPrintStatement(index: Int) =
    new PrintStatementPresenter(add(new PrintStatementLayout(), componentIndex(index)))

  override def addFunctionReference() = addFunctionReference(DefaultIndex)

  override def addFunctionReference(index: Int) =
    new FunctionReferencePresenter(add(new FunctionReferenceComponent(), componentIndex(index)))

  override def add[C <: Component](component: C): C = {
    super.add(component)
    super.add(new ExpressionListSeparator(this))
    component
  }

  def add[C <: Component](component: C, index: Int): C = {
    if (index < 0) {
      add(component)
    } else {
      println("Adding component at index " + index)
      super.add(component, index = index)
      println("Adding component separator at index " + (index + 1))
      super.add(new ExpressionListSeparator(this), index = index + 1)
    }
    component
  }

  override def removeComponent(c: Component) {
    val index = componentIndex(c)
    //    val separator = component(index + 1);
    //    super.removeComponent(separator);
    super.removeComponent(c)
  }

  /** Returns `true` if this layout contains the given Component.
    *
    * @param c the component to check
    * @return `true` if the component is a child
    */
  def contains(c: Component): Boolean = componentIndex(c) != -1

  /** Return the index of the Component in the layout.
    *
    * @param c the component to check
    * @return the index of the component
    */
  def componentIndex(c: Component): Int = p.getComponentIndex(c.p)

  /** Return the index of the expression step in the block, given it's Component.
    *
    * @param c the step component
    * @return the index of the step in the block
    */
  def stepIndex(c: Component): Int = stepIndex(p.getComponentIndex(c.p))

  /** Return the index of the expression step in the block, given it's Component's index.
    *
    * @param i the index of the component
    * @return the index of the step in the block
    */
  def stepIndex(i: Int): Int = Math.round(i / 2)

  /** Return the index of the Component, given the index of the step in the block.
    *
    * @param i the index of the step in the block
    * @return the index of the component
    */
  def componentIndex(i: Int): Int = {
    if (i < 0) {
      i
    } else {
      val index = (i * 2) + 1
      println("Translating " + i + " => " + index)
      index
    }
  }

  /** Return the number of Components in the layout.
    *
    * @return the component count
    */
  def componentCount: Int = p.getComponentCount

  //  def component(index: Int): Component = {
  //    super.components[index]
  //  }

  def requestAddConditional(index: Int) = {
    println("Requesting add conditional @ " + index)
    viewListeners.foreach {
      listener => listener.requestAddConditional(index)
    }
  }

  def requestAddGetStatement(index: Int) = {
    println("Requesting add get @ " + index)
    viewListeners.foreach {
      listener => listener.requestAddGetStatement(index)
    }
  }

  def requestAddSetStatement(index: Int) = {
    println("Requesting add set @ " + index)
    viewListeners.foreach {
      listener => listener.requestAddSetStatement(index)
    }
  }

  def requestAddPrintStatement(index: Int) = {
    println("Requesting add print @ " + index)
    viewListeners.foreach {
      listener => listener.requestAddPrintStatement(index)
    }
  }

  def requestAddFunctionReference(name: String, index: Int) = {
    println("Requesting add " + name + " @ " + index)
    viewListeners.foreach {
      listener => listener.requestAddFunctionReference(name, index)
    }
  }
}

private class ExpressionListSeparator(block: BlockLayout)
  extends DroppableTarget(new HorizontalLayout) {

  component.styleName = BlockLayout.StyleSeparator

  component.add(new BlockMenuBar(block, this))

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

private class BlockMenuBar(block: BlockLayout, separator: ExpressionListSeparator)
  extends MenuBar {

  val item = addItem("+")
  item.addItem("get", {
    item => block.requestAddGetStatement(index)
  })
  item.addItem("set", {
    item => block.requestAddSetStatement(index)
  })
  item.addItem("print", {
    item => block.requestAddPrintStatement(index)
  })
  item.addSeparator()
  item.addItem("if/else", {
    item => block.requestAddConditional(index)
  })
  item.addSeparator()
  val functions = item.addItem("functions")
  functions.addItem("move", {
    item => block.requestAddFunctionReference(item.text, index)
  })
  functions.addItem("stop", {
    item => block.requestAddFunctionReference(item.text, index)
  })
  functions.addItem("turn", {
    item => block.requestAddFunctionReference(item.text, index)
  })

  private def index: Int = block.stepIndex(separator)

}