package com.quane.glass.ide

import scala.swing.Component

trait HighlightableComponent extends Component {

    /** Highlight the {@code WorkspacePanel} to indicate to the user an item
      * can be dropped on it.
      */
    def highlight {
        border = GlassIDE.HIGHLIGHTED_BORDER
    }

    /** Remove any highlighting from the {@code WorkspacePanel}, returning it
      * to it's normal state.
      */
    def unhighlight {
        border = GlassIDE.DEFAULT_BORDER
    }

}