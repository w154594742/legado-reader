package com.nancheung.plugins.jetbrains.legadoreader.common;

public interface Constant {
    /**
     * 插件toolWindow id
     */
    String PLUGIN_TOOL_WINDOW_ID = "Legado Reader";
    /**
     * 插件id前缀
     */
    String PLUGIN_ID_PREFIX = "com.nancheung.legado-reader";
    /**
     * 插件设置id
     */
    String PLUGIN_SETTING_ID = PLUGIN_ID_PREFIX + ".setting";
    
    /**
     * action id前缀
     */
    String PLUGIN_ACTION_ID_PREFIX = PLUGIN_ID_PREFIX + ".action";
    
    /**
     * 正文阅读tool bar
     */
    String PLUGIN_TOOL_BAR_ID = PLUGIN_ID_PREFIX + ".bar.textBodyToolbar";
    
    
    /**
     * 持久化数据
     */
    String PLUGIN__PERSISTENCE_DATA = PLUGIN_ID_PREFIX + ".persistence.data";
}
