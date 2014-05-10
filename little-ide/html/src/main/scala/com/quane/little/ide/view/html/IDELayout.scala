package com.quane.little.ide.view.html

import com.quane.little.ide.view.IDEView
import com.quane.little.ide.presenter.{ToolboxPresenter, WorkspacePresenter}
import com.vaadin.ui._
import com.vaadin.server.Sizeable
import scala.Some
import com.quane.vaadin.scala.{LabelIcon, VaadinMixin}
import com.porotype.iconfont.FontAwesome
import com.porotype.iconfont.FontAwesome.{IconVariant, Icon}
import com.vaadin.ui.themes.Reindeer

object IDELayout {
  val Title = "little"

  val Style = "l-ide"
  val StyleHeader = Style + "-head"
  val StyleCenter = Style + "-center"
  val StyleFooter = Style + "-foot"
}

/** The base view of the HTML IDE.
  *
  * @author Sean Connolly
  */
class IDELayout
  extends VerticalLayout
  with IDEView
  with RemovableComponent
  with VaadinMixin {

  FontAwesome.load()

  setSizeFull()
  setStyleName(IDELayout.Style)

  private val header = new IDELayoutHeader
  private val center = new IDELayoutCenter
  add(header)
  add(center)

  setExpandRatio(center, 1.0f)

  // createGameWindow()

  private def createGameWindow() = {
    val subWindow = new GameWindow()
    UI.getCurrent.addWindow(subWindow)
  }

  override def createToolbox() = {
    center.toolbox = new ToolboxLayout
    new ToolboxPresenter(center.toolbox)
  }

  override def createWorkspace() = {
    center.workspace = new WorkspaceLayout
    new WorkspacePresenter(center.workspace)
  }

  /** The IDE header, containing basic controls and links.
    */
  private final class IDELayoutHeader
    extends HorizontalLayout
    with VaadinMixin {

    setHeight(60, Sizeable.Unit.PIXELS)
    setWidth(100, Sizeable.Unit.PERCENTAGE)
    setStyleName(IDELayout.StyleHeader)
    setDefaultComponentAlignment(Alignment.MIDDLE_LEFT)

    val title = add(new Label(IDELayout.Title))
    val play = add(LabelIcon(Icon.play_circle, IconVariant.SIZE_2X))

    title.setStyleName(Reindeer.LABEL_H1)
    play.setStyleName("l-button")

    setExpandRatio(title, 1.0f)

  }

  /** The center part of the IDE, containing the toolbox and workspace alongside
    * each other.
    */
  private class IDELayoutCenter
    extends HorizontalSplitPanel {

    setPrimaryStyleName(IDELayout.StyleCenter)
    setSplitPosition(20, Sizeable.Unit.PERCENTAGE)

    var _toolbox: Option[ToolboxLayout] = None
    var _workspace: Option[WorkspaceLayout] = None

    private[IDELayout] def toolbox_=(t: ToolboxLayout) = {
      if (_toolbox.isDefined) throw new IllegalAccessException("Toolbox already defined.")
      _toolbox = Some(t)
      setFirstComponent(t)
    }

    private[IDELayout] def toolbox: ToolboxLayout = {
      _toolbox match {
        case Some(t) => t
        case None => throw new IllegalAccessException("No toolbox defined.")
      }
    }

    private[IDELayout] def workspace_=(w: WorkspaceLayout) = {
      if (_workspace.isDefined) throw new IllegalAccessException("Workspace already defined.")
      _workspace = Some(w)
      setSecondComponent(w)
    }

    private[IDELayout] def workspace: WorkspaceLayout = {
      _workspace match {
        case Some(t) => t
        case None => throw new IllegalAccessException("No workspace defined.")
      }
    }
  }

}