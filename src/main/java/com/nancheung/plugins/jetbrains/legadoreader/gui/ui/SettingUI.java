package com.nancheung.plugins.jetbrains.legadoreader.gui.ui;

import cn.hutool.core.util.StrUtil;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.ui.ColorPicker;
import com.intellij.ui.JBColor;
import com.nancheung.plugins.jetbrains.legadoreader.common.Constant;
import com.nancheung.plugins.jetbrains.legadoreader.dao.Data;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SettingUI {
    private JPanel rootPanel;
    
    private JLabel textBodyFontColorLabel;

    private JSpinner textBodyFontSizeSpinner;
    private JTextArea apiCustomParamTextArea;
    private JCheckBox enableErrorLogCheckBox;

    public SettingUI() {
        // 为null时创建所有依赖对象（使用jrebel插件调试时会为null导致空指针异常）
        this.createAllDependIfNull();


        // 正文大小输入范围
        textBodyFontSizeSpinner.setModel(new SpinnerNumberModel(0, 0, 100, 1));
        // 读取已有配置
        readSettings();
        // 更新内存数据
        updateMemoryData();
        
        // 正文字体颜色选择的点击事件
        textBodyFontColorLabel.addMouseListener(chooseColorMouseListener());
    }

    private void createAllDependIfNull() {
        if(rootPanel == null){
            rootPanel = new JPanel();
        }
        if(textBodyFontColorLabel == null){
            textBodyFontColorLabel = new JLabel();
        }
        if(textBodyFontSizeSpinner == null){
            textBodyFontSizeSpinner = new JSpinner();
        }
        if(apiCustomParamTextArea == null){
            apiCustomParamTextArea = new JTextArea();
        }
        if(enableErrorLogCheckBox == null){
            enableErrorLogCheckBox = new JCheckBox();
        }
    }

    @NotNull
    private MouseAdapter chooseColorMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Color newColor = ColorPicker.showDialog(rootPanel, textBodyFontColorLabel.getText() + " Color", textBodyFontColorLabel.getForeground(), true, null, true);
                if (newColor != null) {
                    textBodyFontColorLabel.setForeground(newColor);
                }
            }
        };
    }
    
    public JComponent getComponent() {
        return rootPanel;
    }
    
    public void readSettings() {
        // 读取本地配置
        String textBodyFontColor = PropertiesComponent.getInstance().getValue(Constant.PLUGIN_SETTING_ID + ".textBodyFontColor");
        int textBodyFontSize = PropertiesComponent.getInstance().getInt(Constant.PLUGIN_SETTING_ID + ".textBodyFontSize", 0);
        String customParam = PropertiesComponent.getInstance().getValue(Constant.PLUGIN_SETTING_ID + ".apiCustomParam");
        boolean enableErrorLog = PropertiesComponent.getInstance().getBoolean(Constant.PLUGIN_SETTING_ID + ".enableErrorLog", false);

        if (StrUtil.isNotBlank(textBodyFontColor)) {
            assert textBodyFontColor != null;
            int rgb = Integer.parseInt(textBodyFontColor);
            textBodyFontColorLabel.setForeground(new JBColor(new Color(rgb), new Color(rgb)));
        }

        if (textBodyFontSize > 0) {
            textBodyFontSizeSpinner.setValue(textBodyFontSize);
        }
    
        if (StrUtil.isNotBlank(customParam)) {
            apiCustomParamTextArea.setText(customParam);
        }

        enableErrorLogCheckBox.setSelected(enableErrorLog);
    }
    
    
    public void saveSettings() {
        // 更新内存数据
        updateMemoryData();
        
        // 持久化本地配置
        PropertiesComponent.getInstance().setValue(Constant.PLUGIN_SETTING_ID + ".textBodyFontColor", String.valueOf(textBodyFontColorLabel.getForeground().getRGB()));
        PropertiesComponent.getInstance().setValue(Constant.PLUGIN_SETTING_ID + ".textBodyFontSize", String.valueOf(textBodyFontSizeSpinner.getValue()));
        PropertiesComponent.getInstance().setValue(Constant.PLUGIN_SETTING_ID + ".apiCustomParam", String.valueOf(apiCustomParamTextArea.getText()));
        PropertiesComponent.getInstance().setValue(Constant.PLUGIN_SETTING_ID + ".enableErrorLog", enableErrorLogCheckBox.isSelected());
    }
    
    private void updateMemoryData() {
        Data.textBodyFontColor = textBodyFontColorLabel.getForeground();
        if ((int)textBodyFontSizeSpinner.getValue() == 0) {
            Data.textBodyFont=textBodyFontSizeSpinner.getFont();
        }else {
            Data.textBodyFont = new Font(textBodyFontSizeSpinner.getFont().getName(), Font.PLAIN, (int) textBodyFontSizeSpinner.getValue());
        }
    
        Data.setApiCustomParam(apiCustomParamTextArea.getText());
        Data.enableErrorLog = enableErrorLogCheckBox.isSelected();
    }
}
