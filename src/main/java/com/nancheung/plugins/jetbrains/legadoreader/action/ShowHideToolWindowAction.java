package com.nancheung.plugins.jetbrains.legadoreader.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.nancheung.plugins.jetbrains.legadoreader.common.Constant;

import java.util.Objects;

public class ShowHideToolWindowAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(Objects.requireNonNull(e.getProject()));
        // Constant.PLUGIN_TOOL_WINDOW_ID的值为plugin.xml中toolWindow标签定义的id
        ToolWindow toolWindow = toolWindowManager.getToolWindow(Constant.PLUGIN_TOOL_WINDOW_ID);
        if (toolWindow != null) {
            if (toolWindow.isVisible()) {
                toolWindow.hide(null);
            } else {
                toolWindow.show(null);
            }
        }
    }
}
