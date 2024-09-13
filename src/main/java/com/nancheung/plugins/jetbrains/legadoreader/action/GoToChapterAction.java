package com.nancheung.plugins.jetbrains.legadoreader.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import com.nancheung.plugins.jetbrains.legadoreader.dao.CurrentReadData;
import com.nancheung.plugins.jetbrains.legadoreader.toolwindow.IndexUI;

public class GoToChapterAction extends AnAction {

    private final IndexUI indexUI = IndexUI.getInstance();

    @Override
    public void actionPerformed(AnActionEvent e) {
        // 显示一个输入框让用户输入章节号
        String chapterNumber = Messages.showInputDialog(
                e.getProject(),
                "输入要跳转的章节号：",
                "跳转到章节",
                Messages.getQuestionIcon()
        );

        // 检查输入是否为有效数字
        if (chapterNumber != null && !chapterNumber.isEmpty()) {
            try {
                int chapterIndex = Integer.parseInt(chapterNumber);
                CurrentReadData.setBookIndex(--chapterIndex);
                indexUI.switchChapter(0);
                System.out.println("跳转到章节: " + chapterIndex);
            } catch (NumberFormatException ex) {
                Messages.showErrorDialog("请输入有效的数字！", "无效的输入");
            }
        }
    }
}
