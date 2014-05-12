package com.quane.little.ide.view.html

import com.quane.little.ide.view.IDEView
import com.quane.little.ide.presenter.{GamespacePresenter, ToolboxPresenter, WorkspacePresenter}
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

  setExpandRatio(center, 1f)

  override def createToolbox() = {
    center.toolbox = new ToolboxLayout
    new ToolboxPresenter(center.toolbox)
  }

  override def createWorkspace() = {
    center.workspace = new WorkspaceTabSheet
    new WorkspacePresenter(center.workspace)
  }


  override def createGamespace() = {
    center.gamespace = new GamespaceLayout
    new GamespacePresenter(center.gamespace)
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
    var _workspace: Option[WorkspaceTabSheet] = None
    var _gamespace: Option[GamespaceLayout] = None

    val _rightPanel = new HorizontalSplitPanel
    _rightPanel.setSplitPosition(70, Sizeable.Unit.PERCENTAGE)
    setSecondComponent(_rightPanel)

    private[IDELayout] def toolbox_=(t: ToolboxLayout) = {
      if (_toolbox.isDefined) throw new IllegalAccessException("Toolbox already defined.")
      _toolbox = Some(t)
      setFirstComponent(t)
    }

    private[IDELayout] def toolbox: ToolboxLayout =
      _toolbox match {
        case Some(t) => t
        case None => throw new IllegalAccessException("No toolbox defined.")
      }

    private[IDELayout] def workspace_=(w: WorkspaceTabSheet) = {
      if (_workspace.isDefined) throw new IllegalAccessException("Workspace already defined.")
      _workspace = Some(w)
      _rightPanel.setFirstComponent(w)
    }

    private[IDELayout] def workspace: WorkspaceTabSheet =
      _workspace match {
        case Some(w) => w
        case None => throw new IllegalAccessException("No workspace defined.")
      }

    private[IDELayout] def gamespace_=(g: GamespaceLayout) = {
      if (_gamespace.isDefined) throw new IllegalAccessException("Gamespace already defined.")
      _gamespace = Some(g)
      _rightPanel.setSecondComponent(g)
    }

    private[IDELayout] def gamespace: GamespaceLayout =
      _gamespace match {
        case Some(g) => g
        case None => throw new IllegalAccessException("No gamespace defined.")
      }

  }

}