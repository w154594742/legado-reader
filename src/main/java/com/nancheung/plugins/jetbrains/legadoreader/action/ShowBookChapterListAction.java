package com.nancheung.plugins.jetbrains.legadoreader.action;

import cn.hutool.core.collection.CollUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.nancheung.plugins.jetbrains.legadoreader.api.dto.BookChapterDTO;
import com.nancheung.plugins.jetbrains.legadoreader.dao.CurrentReadData;
import com.nancheung.plugins.jetbrains.legadoreader.toolwindow.IndexUI;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ShowBookChapterListAction extends AnAction {

    private final IndexUI indexUI = IndexUI.getInstance();

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) {
            return;
        }

        // 获取章节列表数据
        List<BookChapterDTO> chapters = CurrentReadData.getBookChapterList();
        if (CollUtil.isEmpty(chapters)) {
            return;
        }

        // 使用 JBPopupFactory 显示章节列表
        JBPopup jbPopup = JBPopupFactory.getInstance().createPopupChooserBuilder(chapters)
                .setTitle("章节目录")
                .setItemChosenCallback((selectedChapter) -> {
                    // 选择章节后跳转到选择的章节
                    CurrentReadData.setBookIndex(selectedChapter.getIndex());
                    indexUI.switchChapter(0);
                })
                .setRenderer(new ListCellRenderer<BookChapterDTO>() {
                    @Override
                    public Component getListCellRendererComponent(JList<? extends BookChapterDTO> list, BookChapterDTO value, int index, boolean isSelected, boolean cellHasFocus) {
                        JLabel label = new JLabel(value.getTitle());
                        label.setOpaque(true);
                        return label;
                    }
                })
                .createPopup();
        jbPopup.showInBestPositionFor(e.getDataContext());
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        e.getPresentation().setEnabledAndVisible(CurrentReadData.getBook() != null && CollUtil.isNotEmpty(CurrentReadData.getBookChapterList()));
    }


}
