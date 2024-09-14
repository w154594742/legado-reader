package com.nancheung.plugins.jetbrains.legadoreader.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ex.CustomComponentAction;
import com.intellij.openapi.project.DumbAware;
import com.nancheung.plugins.jetbrains.legadoreader.dao.CurrentReadData;
import com.nancheung.plugins.jetbrains.legadoreader.toolwindow.IndexUI;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChapterInputAction extends com.intellij.openapi.actionSystem.AnAction implements CustomComponentAction, DumbAware {

    private final IndexUI indexUI = IndexUI.getInstance();

    @NotNull
    @Override
    public JComponent createCustomComponent(@NotNull com.intellij.openapi.actionSystem.Presentation presentation, @NotNull String place) {
        // 创建包含提示文案和输入框的 JPanel
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));  // 使用流式布局

        // 创建提示文案
        JLabel label = new JLabel("跳转到章节: ");

        // 创建输入框
        JTextField chapterInputField = new JTextField(6); // 设置输入框的列数（影响字符数）

        // 设置输入框的首选宽度
        chapterInputField.setPreferredSize(new Dimension(40, 24)); // 宽度和高度像素值

        // 添加回车键的事件监听
        chapterInputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String chapterText = chapterInputField.getText();
                if (chapterText != null && !chapterText.isEmpty()) {
                    try {
                        int chapterIndex = Integer.parseInt(chapterText);
                        goToChapter(chapterIndex);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "请输入有效的章节号！", "无效输入", JOptionPane.ERROR_MESSAGE);
                    }
                }
                // 清空输入框
                chapterInputField.setText("");
            }
        });

        // 将提示文案和输入框添加到面板
        panel.add(label);
        panel.add(chapterInputField);

        return panel;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // 不需要在这里实现任何操作
    }

    // 实现跳转到指定章节的逻辑
    private void goToChapter(int chapterIndex) {
        System.out.println("跳转到章节: " + chapterIndex);
        CurrentReadData.setBookIndex(--chapterIndex);
        indexUI.switchChapter(0);
    }
}