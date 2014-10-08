package com.quane.little.ide.view.html

import com.quane.little.ide.view.{EvaluableCodeView, BlockViewPresenter, BlockView}
import com.quane.little.ide.view.html.BlockLayout._
import com.vaadin.ui._
import com.quane.little.ide.presenter.command._
import com.vaadin.event.dd.{DragAndDropEvent, DropHandler}
import com.vaadin.event.dd.acceptcriteria.AcceptAll
import com.quane.vaadin.scala.{VaadinMixin, DroppableTarget}
import com.quane.little.data.model.{CodeType, RecordId}
import com.quane.little.ide.view.html.dnd.CodeTransferable

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
  setDefaultComponentAlignment(Alignment.TOP_CENTER)

  override def addMathStep(index: Int) = addStep(new MathLayout(), index)

  override def addConditionalStep(index: Int) = addStep(new ConditionalLayout(), index)

  override def addGetStep(index: Int) = addStep(new GetterLayout(), index)

  override def addSetStep(index: Int) = addStep(new SetterLayout(), index)

  override def addPrintStep(index: Int) = addStep(new PrinterLayout(), index)

  override def addLogicStep(index: Int) = addStep(new LogicLayout, index)

  override def addFunctionStep(index: Int) = addStep(new FunctionReferenceLayout(), index)

  override def addCodeMenu(stepIndex: Int) = {
    val menuIndex = if (stepIndex < 0) 0 else getComponentIndex(stepIndex) + 1
    val space = new BlockStepSeparator(this)
    addComponent(space, menuIndex)
    space.menu
  }

  override def removeComponent(component: Component) {
    component match {
      case step: EvaluableCodeView[_] => removeStep(getStepIndex(component))
      case _ => throw new IllegalArgumentException("Attempt to remove " + component.getClass
        + " from " + classOf[BlockLayout] + ", can only remove " + classOf[EvaluableCodeView[_]])
    }
  }

  private def addStep[C <: Component](component: C, stepIndex: Int): C = {
    val componentIndex = getComponentIndex(stepIndex)
    addComponent(component, componentIndex)
    component
  }

  private def removeStep(stepIndex: Int): Unit = {
    // Remove component & separator
    val componentIndex = getComponentIndex(stepIndex)
    val step = getComponent(componentIndex)
    val separator = getComponent(componentIndex + 1)
    super.removeComponent(separator)
    super.removeComponent(step)
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
  def getStepIndex(c: Component): Int = getStepIndex(getComponentIndex(c))

  /** Return the index of the expression step in the block, given it's Component's index.
    *
    * @param componentIndex the index of the component
    * @return the index of the step in the block
    */
  def getStepIndex(componentIndex: Int): Int = {
    scala.math.round(componentIndex / 2)
  }

  /** Return the index of the Component, given the index of the step in the block.
    *
    * @param stepIndex the index of the step in the block
    * @return the index of the component
    */
  def getComponentIndex(stepIndex: Int): Int = {
    if (stepIndex < 0) {
      stepIndex
    } else {
      (stepIndex * 2) + 1
    }
  }

  /** Return the number of Components in the layout.
    *
    * @return the component count
    */
  def componentCount: Int = getComponentCount

}

private class BlockStepSeparator(block: BlockLayout)
  extends HorizontalLayout
  with VaadinMixin {

  setSizeFull()
  setStyleName(BlockLayout.StyleSeparator)

  val menu = new CodeMenuLayout[BlockViewPresenter](block, index)
  val dndHandler = new BlockDropHandler(this)
  val dndTargetLeft = new DroppableTarget(new HorizontalLayout())
  val dndTargetRight = new DroppableTarget(new HorizontalLayout())
  dndTargetLeft.setDropHandler(dndHandler)
  dndTargetRight.setDropHandler(dndHandler)
  add(dndTargetLeft)
  add(menu)
  add(dndTargetRight)
  setExpandRatio(dndTargetLeft, 1f)
  setExpandRatio(dndTargetRight, 1f)

  def addFunction(functionId: RecordId) =
    IDECommandExecutor.execute(
      new AddCodeCommand(block.presenter, functionId, index())
    )

  def index(): Int = block.getStepIndex(this)

}

/**
 * Handler for drag &amp; drop events onto a [[com.quane.little.ide.view.html.BlockStepSeparator]].
 *
 * @param block the block to interact with
 */
class BlockDropHandler(block: BlockStepSeparator) extends DropHandler {

  override def getAcceptCriterion = AcceptAll.get()

  override def drop(event: DragAndDropEvent) =
    event.getTransferable match {
      case transferable: CodeTransferable if transferable.category == CodeType.Function =>
        block.addFunction(transferable.codeId)
      case _ =>
        throw new IllegalAccessException("Drop not supported: " + event.getTransferable)
    }

}