package com.quane.little.ide.view.html

import com.quane.little.ide.presenter._
import com.quane.little.ide.view.BlockView
import com.quane.little.ide.view.html.BlockLayout._
import com.vaadin.ui._
import com.quane.little.ide.presenter.command._
import com.vaadin.event.dd.{DragAndDropEvent, DropHandler}
import com.vaadin.event.dd.acceptcriteria.AcceptAll
import com.quane.vaadin.scala.DroppableTarget
import com.quane.little.data.model.{CodeCategory, RecordId}
import com.quane.little.ide.view.html.dnd.CodeTransferable

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
    new GetterPresenter(add(new GetStatementLayout(), componentIndex(index)))

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

}

private class BlockStepSeparator(block: BlockLayout)
  extends HorizontalLayout {

  setSizeFull()
  setStyleName(BlockLayout.StyleSeparator)
  addComponent(new ExpressionMenu(block, index))
  val dndTarget = new DroppableTarget(new HorizontalLayout())
  dndTarget.setDropHandler(new BlockDropHandler(this))
  // TODO expand to fill separator height & width
  dndTarget.component.setHeight("20px")
  dndTarget.component.setWidth("200px")
  dndTarget.setSizeFull()
  addComponent(dndTarget)

  def addFunction(functionId: RecordId) =
    IDECommandExecutor.execute(
      new AddFunctionReferenceCommand(block.presenter, functionId, index)
    )

  def addExpression(primitiveId: RecordId) =
    IDECommandExecutor.execute(
      new AddExpressionCommand(block.presenter, primitiveId, index)
    )

  def addStatement(primitiveId: RecordId) =
    IDECommandExecutor.execute(
      new AddStatementCommand(block.presenter, primitiveId, index)
    )

  def index: Int = block.stepIndex(this)

}

/**
 * Handler for drag &amp; drop events onto a block.
 *
 * @param block the block to interact with
 */
class BlockDropHandler(block: BlockStepSeparator) extends DropHandler {

  override def getAcceptCriterion = AcceptAll.get()

  override def drop(event: DragAndDropEvent) =
    event.getTransferable match {
      case transferable: CodeTransferable if transferable.category == CodeCategory.Expression =>
        block.addExpression(transferable.codeId)
      case transferable: CodeTransferable if transferable.category == CodeCategory.Statement =>
        block.addStatement(transferable.codeId)
      case transferable: CodeTransferable if transferable.category == CodeCategory.Function =>
        block.addFunction(transferable.codeId)
      case _ =>
        throw new IllegalAccessException("Drop not supported: " + event.getTransferable)
    }

}