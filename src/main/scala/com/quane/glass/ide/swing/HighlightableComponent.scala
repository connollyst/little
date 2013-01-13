package com.quane.glass.ide.swing

import scala.swing.Component
import com.quane.glass.ide.GlassIDE
import javax.swing.BorderFactory
import java.awt.Color

trait HighlightableComponent extends Component {

    private val DEFAULT_BORDER = BorderFactory.createLineBorder(Color.lightGray)
    private val HIGHLIGHTED_BORDER = BorderFactory.createLineBorder(Color.yellow)

    border = DEFAULT_BORDER
    
    /** Highlight the {@code WorkspacePanel} to indicate to the user an item
      * can be dropped on it.
      */
    def highlight {
        border = HIGHLIGHTED_BORDER
    }

    /** Remove any highlighting from the {@code WorkspacePanel}, returning it
      * to it's normal state.
      */
    def unhighlight {
        border = DEFAULT_BORDER
    }

}