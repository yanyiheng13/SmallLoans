package com.virgo.financeloan.util;

import android.os.Environment;

import com.virgo.financeloan.AppApplication;

import java.io.File;

/**
 * 功能说明：文件的读写
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/13 16:35
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */

public class FileUtil {
    /**
     * 外部存储文件
     * @return
     */
    public static String getExternalTempFile() {
        File externalFile = Environment.getExternalStorageDirectory();
        if (externalFile == null) {
            return null;
        }
        String externalPath = externalFile.getAbsolutePath();

        File dest = new File(externalPath + "/SmallLoans/tmp/");
        if (!dest.exists()) {
            dest.mkdirs();
        }
        return dest.getAbsolutePath();
    }

    /**
     * 内部存储文件
     * @param prefix
     * @param postfix
     * @return
     */
    public static String getInternalTempFile(String prefix, String postfix) {
        String externalPath;
        File externalFile = AppApplication.mApplication.getFilesDir();
        if (externalFile == null) {
            externalPath = "data/data/".concat(AppApplication.mApplication.getPackageName()).concat("/files");
        } else {
            externalPath = externalFile.getAbsolutePath();
        }

        File dest = new File(externalPath + "/SmallLoans/tmp/");
        dest.mkdirs();
        if (!dest.exists()) return null;

        return dest.getAbsolutePath();
    }
}
