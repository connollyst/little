package com.quane.little.ide.view.html

import com.quane.little.ide.view.{BlockViewPresenter, BlockView}
import com.quane.little.ide.view.html.BlockLayout._
import com.vaadin.ui._
import com.quane.little.ide.presenter.command._
import com.vaadin.event.dd.{DragAndDropEvent, DropHandler}
import com.vaadin.event.dd.acceptcriteria.AcceptAll
import com.quane.vaadin.scala.{VaadinMixin, DroppableTarget}
import com.quane.little.data.model.{CodeType, RecordId}
import com.quane.little.ide.view.html.dnd.CodeTransferable
import com.vaadin.server.Sizeable
import scala.collection.JavaConversions._

object BlockLayout {
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

  override def addMathStep(index: Int) = addStep(new MathLayout(), index)

  override def addConditionalStep(index: Int) = addStep(new ConditionalLayout(), index)

  override def addGetStep(index: Int) = addStep(new GetterLayout(), index)

  override def addSetStep(index: Int) = addStep(new SetterLayout(), index)

  override def addPrintStep(index: Int) = addStep(new PrinterLayout(), index)

  override def addLogicStep(index: Int) = addStep(new LogicLayout, index)

  override def addFunctionStep(index: Int) = addStep(new FunctionReferenceLayout(), index)

  // C <: EvaluableCodeView
  private def addStep[C <: Component](component: C, sIndex: Int): C = {
    val index = componentIndex(sIndex)
    addComponent(new BlockStep(component), index)
    // Update line counts & menus
    components foreach {
      case step: BlockStep => if (stepIndex(step) > sIndex) step.border.index = stepIndex(step) + 1
      case space: BlockStepSeparator => if (stepIndex(space) > sIndex) space.menu.index = stepIndex(space) + 1
      case _ => // do nothing
    }
    component
  }

  override def addCodeMenu(stepIndex: Int) = {
    val menuIndex = if (stepIndex < 0) 0 else componentIndex(stepIndex) + 1
    val nextStepIndex = if (stepIndex < 0) 0 else stepIndex + 1
    val menu = new CodeMenuLayout(this, nextStepIndex)
    val space = new BlockStepSeparator(this, menu)
    addComponent(space, menuIndex)
    menu
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
  def stepIndex(i: Int): Int = {
    scala.math.round(i / 2)
  }

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

private class BlockStep(val step: Component)
  extends HorizontalLayout
  with VaadinMixin {

  setSizeFull()
  setStyleName(StyleStep)

  val border = new BlockStepBorder

  add(border)
  add(step)
  setExpandRatio(step, 1f)

}

private class BlockStepSeparator(block: BlockLayout, val menu: CodeMenuLayout[BlockViewPresenter])
  extends HorizontalLayout
  with VaadinMixin {

  setSizeFull()
  setStyleName(BlockLayout.StyleSeparator)
  setDefaultComponentAlignment(Alignment.MIDDLE_CENTER)

  val border = new BlockStepBorder
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
      case transferable: CodeTransferable if transferable.category == CodeType.Expression =>
        block.addExpression(transferable.codeId)
      case transferable: CodeTransferable if transferable.category == CodeType.Statement =>
        block.addStatement(transferable.codeId)
      case transferable: CodeTransferable if transferable.category == CodeType.Function =>
        block.addFunction(transferable.codeId)
      case _ =>
        throw new IllegalAccessException("Drop not supported: " + event.getTransferable)
    }

}