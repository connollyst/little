package com.quane.little.ide.view.html

import com.quane.little.ide.presenter._
import com.quane.vaadin.scala.DroppableTarget
import com.vaadin.event.dd.{DragAndDropEvent, DropHandler}
import com.vaadin.event.dd.acceptcriteria.{AcceptCriterion, AcceptAll}
import com.quane.little.ide.view.{BlockViewPresenter, BlockView}
import com.quane.little.ide.view.html.BlockLayout._
import com.vaadin.ui.{Component, MenuBar, HorizontalLayout, VerticalLayout}
import com.vaadin.ui.MenuBar.Command

object BlockLayout {
  val DefaultIndex = -1
  val Style = "l-block"
  val StyleSeparator = Style + "-separator"
}

/** An HTML layout view representing an expression block.
  *
  * @author Sean Connolly
  */
class BlockLayout
  extends VerticalLayout
  with BlockView
  with RemovableComponent {

  setSpacing(false)
  setStyleName(Style)

  super.addComponent(new BlockStepSeparator(this))

  override def addConditional() = addConditional(DefaultIndex)

  override def addConditional(index: Int) =
    new ConditionalPresenter(add(new ConditionalLayout(), componentIndex(index)))

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
    new FunctionReferencePresenter(add(new FunctionReferenceLayout(), componentIndex(index)))

  override def addComponent(component: Component) = {
    super.addComponent(component)
    super.addComponent(new BlockStepSeparator(this))
    component
  }

  def add[C <: Component](component: C, index: Int): C = {
    if (index < 0) {
      addComponent(component)
    } else {
      super.addComponent(component, index)
      super.addComponent(new BlockStepSeparator(this), index + 1)
    }
    component
  }

  override def removeComponent(c: Component) {
    val index = getComponentIndex(c)
    val separator = getComponent(index + 1)
    super.removeComponent(separator)
    super.removeComponent(c)
  }

  /** Returns `true` if this layout contains the given Component.
    *
    * @param c the component to check
    * @return `true` if the component is a child
    */
  def contains(c: Component): Boolean = getComponentIndex(c) != -1

  /** Return the index of the expression step in the block, given it's Component.
    *
    * @param c the step component
    * @return the index of the step in the block
    */
  def stepIndex(c: Component): Int = stepIndex(getComponentIndex(c))

  /** Return the index of the expression step in the block, given it's Component's index.
    *
    * @param i the index of the component
    * @return the index of the step in the block
    */
  def stepIndex(i: Int): Int = scala.math.round(i / 2)

  /** Return the index of the Component, given the index of the step in the block.
    *
    * @param i the index of the step in the block
    * @return the index of the component
    */
  def componentIndex(i: Int): Int = {
    if (i < 0) {
      i
    } else {
      (i * 2) + 1
    }
  }

  /** Return the number of Components in the layout.
    *
    * @return the component count
    */
  def componentCount: Int = getComponentCount

  //  def component(index: Int): Component = {
  //    super.components[index]
  //  }

  def requestAddConditional(index: Int) = {
    // TODO: DEBUGGING: I think the way we are using generics doesn't extend to
    val pres: BlockViewPresenter = presenter
    pres.requestAddConditional(index)
    _viewPresenter.foreach {
      listener => listener.requestAddConditional(index)
    }
  }

  def requestAddGetStatement(index: Int) = {
    _viewPresenter.foreach {
      listener => listener.requestAddGetStatement(index)
    }
  }

  def requestAddSetStatement(index: Int) = {
    _viewPresenter.foreach {
      listener => listener.requestAddSetStatement(index)
    }
  }

  def requestAddPrintStatement(index: Int) = {
    _viewPresenter.foreach {
      listener => listener.requestAddPrintStatement(index)
    }
  }

  def requestAddFunctionReference(name: String, index: Int) = {
    _viewPresenter.foreach {
      listener => listener.requestAddFunctionReference(name, index)
    }
  }
}

private class BlockStepSeparator(block: BlockLayout)
  extends DroppableTarget(new HorizontalLayout) {

  component.setStyleName(BlockLayout.StyleSeparator)

  component.addComponent(new BlockMenuBar(block, this))

  setDropHandler(new DropHandler() {

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
      //      list.addComponent(droppedStep, separatorIndex + 1)
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

  })
}

private class BlockMenuBar(block: BlockLayout, separator: BlockStepSeparator)
  extends MenuBar {

  val item = addItem("+", null, null)
  item.addItem("get", null, new Command {
    def menuSelected(item: MenuBar#MenuItem) =
      block.requestAddGetStatement(index)
  })
  item.addItem("set", null, new Command {
    def menuSelected(item: MenuBar#MenuItem) =
      block.requestAddSetStatement(index)
  })
  item.addItem("print", null, new Command {
    def menuSelected(item: MenuBar#MenuItem) =
      block.requestAddPrintStatement(index)
  })
  item.addSeparator()
  item.addItem("if/else", null, new Command {
    def menuSelected(item: MenuBar#MenuItem) =
      block.requestAddConditional(index)
  })
  item.addSeparator()
  val functions = item.addItem("functions", null, null)
  functions.addItem("move", null, new Command {
    def menuSelected(item: MenuBar#MenuItem) =
      block.requestAddFunctionReference(item.getText, index)
  })
  functions.addItem("stop", null, new Command {
    def menuSelected(item: MenuBar#MenuItem) =
      block.requestAddFunctionReference(item.getText, index)
  })
  functions.addItem("turn", null, new Command {
    def menuSelected(item: MenuBar#MenuItem) =
      block.requestAddFunctionReference(item.getText, index)
  })

  private def index: Int = block.stepIndex(separator)

}