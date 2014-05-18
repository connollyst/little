package com.quane.little.ide.view.html

import com.quane.little.ide.view.{MathView, BlockView}
import com.quane.little.ide.view.html.BlockLayout._
import com.vaadin.ui._
import com.quane.little.ide.presenter.command._
import com.vaadin.event.dd.{DragAndDropEvent, DropHandler}
import com.vaadin.event.dd.acceptcriteria.AcceptAll
import com.quane.vaadin.scala.{VaadinMixin, DroppableTarget}
import com.quane.little.data.model.{CodeCategory, RecordId}
import com.quane.little.ide.view.html.dnd.CodeTransferable
import scala.collection.JavaConversions._
import com.vaadin.server.Sizeable

object BlockLayout {
  val DefaultIndex = -1
  val Style = "l-block"
  val StyleStep = Style + "-step"
  val StyleStepBorder = StyleStep + "-border"
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

  override def addMath(): MathView = addMathStep(DefaultIndex)

  override def addMathStep(index: Int) = add(new MathLayout(), componentIndex(index))

  override def addConditional() = addConditionalStep(DefaultIndex)

  override def addConditionalStep(index: Int) = add(new ConditionalLayout(), componentIndex(index))

  override def addGetStatement() = addGetStep(DefaultIndex)

  override def addGetStep(index: Int) = add(new GetterLayout(), componentIndex(index))

  override def addSetStatement() = addSetStep(DefaultIndex)

  override def addSetStep(index: Int) = add(new SetterLayout(), componentIndex(index))

  override def addPrintStatement() = addPrintStep(DefaultIndex)

  override def addPrintStep(index: Int) = add(new PrinterLayout(), componentIndex(index))

  override def addLogicalOperation() = addLogicStep(DefaultIndex)

  override def addLogicStep(index: Int) = add(new LogicLayout, componentIndex(index))

  override def addFunctionReference() = addFunctionStep(DefaultIndex)

  override def addFunctionStep(index: Int) = add(new FunctionReferenceLayout(), componentIndex(index))

  override def addComponent(component: Component) = {
    super.addComponent(component)
    super.addComponent(new BlockStepSeparator(this))
    component
  }

  def add[C <: Component](component: C, index: Int): C = {
    if (index < 0) {
      addComponent(component)
    } else {
      super.addComponent(new BlockStep(component), index)
      super.addComponent(new BlockStepSeparator(this), index + 1)
    }
    components foreach {
      case step: BlockStep => step.border.index = stepIndex(step) + 1
      case _ => // do nothing
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

}

private class BlockStep(step: Component)
  extends HorizontalLayout
  with VaadinMixin {

  setSizeFull()
  setStyleName(StyleStep)

  val border = new BlockStepBorder

  add(border)
  add(step)
  setExpandRatio(step, 1f)

}

private class BlockStepSeparator(block: BlockLayout)
  extends HorizontalLayout
  with VaadinMixin {

  setSizeFull()
  setStyleName(BlockLayout.StyleSeparator)
  setDefaultComponentAlignment(Alignment.MIDDLE_CENTER)

  val border = new BlockStepBorder
  val menu = new ExpressionMenu(block, index)
  val dndTarget = new DroppableTarget(new HorizontalLayout())
  dndTarget.setDropHandler(new BlockDropHandler(this))
  dndTarget.setSizeFull()

  add(border)
  add(menu)
  add(dndTarget)
  setExpandRatio(dndTarget, 1f)

  def addExpression(primitiveId: RecordId) =
    IDECommandExecutor.execute(
      new AddExpressionCommand(block.presenter, primitiveId, index)
    )

  def addStatement(primitiveId: RecordId) =
    IDECommandExecutor.execute(
      new AddStatementCommand(block.presenter, primitiveId, index)
    )

  def addFunction(functionId: RecordId) =
    IDECommandExecutor.execute(
      new AddFunctionReferenceCommand(block.presenter, functionId, index)
    )

  def index: Int = block.stepIndex(this)

}

private class BlockStepBorder extends VerticalLayout {

  private var _index: Int = 0
  private val indexLabel = new Label(if (_index > 0) {
    _index.toString
  } else {
    ""
  })

  def index: Int = _index

  def index_=(i: Int) = {
    _index = i
    indexLabel.setValue(_index.toString)
  }

  setStyleName(StyleStepBorder)
  setWidth(25, Sizeable.Unit.PIXELS)
  setHeight(100, Sizeable.Unit.PERCENTAGE)
  setDefaultComponentAlignment(Alignment.TOP_CENTER)

  addComponent(indexLabel)
  setExpandRatio(indexLabel, 1)

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