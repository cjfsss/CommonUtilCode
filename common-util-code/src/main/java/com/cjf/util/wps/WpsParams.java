package com.cjf.util.wps;

/**
 * <p>Title: WpsModel </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/7/7 16:52
 */
public interface WpsParams {
    String OPEN_MODE = "OpenMode";// 打开文件的模式。
    String SEND_SAVE_BROAD = "SendSaveBroad";// 文件保存时是否发送广播。
    String SEND_CLOSE_BROAD = "SendCloseBroad";// 文件关闭时是否发送广播
    String THIRD_PACKAGE = "ThirdPackage";// 第三方的包名，关闭的广播会包含该项。
    String CLEAR_BUFFER = "ClearBuffer";// 关闭文件时是否请空临时文件。
    String CLEAR_TRACE = "ClearTrace";// 关闭文件时是否删除使用记录。
    String CLEAR_FILE = "ClearFile";// 关闭文件时是否删除打开的文件。
    String VIEW_PROGRESS = "ViewProgress";// 文件上次查看的进度。
    String AUTO_JUMP = "AutoJump";// 是否自动跳转到上次查看的进度。
    String SAVE_PATH = "SavePath";// 文件保存路径。
    String VIEW_SCALE = "ViewScale";// 文件上次查看的视图的缩放。
    String VIEW_SCALE_X = "ViewScrollX";// 文件上次查看的视图的X坐标。
    String VIEW_SCALE_Y = "ViewScrollY";// 文件上次查看的视图的Y坐标。
    String USER_NAME = "UserName";// 批注的作者。
    String HOMEKEY_DOWN = "HomeKeyDown";// 监听home键并发广播
    String BACKKEY_DOWN = "BackKeyDown";// 监听back键并发广播
    String ENTER_REVISE_MODE = "EnterReviseMode";// 以修订模式打开文档
    String CACHE_FILE_INVISIBLE = "CacheFileInvisible";// Wps生成的缓存文件外部是否可见

    interface OpenMode {
        String NORMAL = "Normal";// 只读模式
        String READ_ONLY = "ReadOnly";// 正常模式
        String READ_MODE = "ReadMode";// 打开直接进入阅读器模式
        // 仅Word、TXT文档支持
        String SAVE_ONLY = "SaveOnly";// 保存模式(打开文件,另存,关闭)
        // 仅Word、TXT文档支持
    }

    interface ClassName {
        String NORMAL = "cn.wps.moffice.documentmanager.PreStartActivity2";// 普通版
        String ENGLISH = "cn.wps.moffice.documentmanager.PreStartActivity2";// 英文版
        String ENTERPRISE = "cn.wps.moffice.documentmanager.PreStartActivity2";// 企业版
    }

    interface PackageName {
        String NORMAL = "cn.wps.moffice_eng";// 普通版
        String ENGLISH = "cn.wps.moffice_eng";// 英文版
    }

    interface Reciver {
        String ACTION_BACK = "com.kingsoft.writer.back.key.down";// 返回键广播
        String ACTION_HOME = "com.kingsoft.writer.home.key.down";// Home键广播
        String ACTION_SAVE = "cn.wps.moffice.file.save";// 保存广播
        String ACTION_CLOSE = "cn.wps.moffice.file.close";// 关闭文件广播
    }
}
