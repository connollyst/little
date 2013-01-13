package com.quane.glass.ide.language

import com.quane.glass.core.language.Expression

trait FunctionPanel
        extends GlassPanel {

    def stepPanels: List[GlassPanelController[Expression[Any]]]

}