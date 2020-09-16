/*
 * Tencent is pleased to support the open source community by making QMUI_Android available.
 *
 * Copyright (C) 2017-2018 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the MIT License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cjf.util.log;

import android.os.AsyncTask;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

/**
 * <p>Title: PrintLog </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/1/8 20:12
 */
@Keep
public class LogX {

    static final String TAG = "PrintLog";

    @Keep
    public interface LogDelegate {

        void d(@NonNull final Object... contents);

        void dTag(@NonNull final String tag, @NonNull final Object... contents);

        void i(@NonNull final Object... contents);

        void iTag(@NonNull final String tag, @NonNull final Object... contents);

        void w(@NonNull final Object... contents);

        void wTag(@NonNull final String tag, @NonNull final Object... contents);

        void e(@NonNull final Object... contents);

        void eTag(@NonNull final String tag, @NonNull final Object... contents);

        void file(@NonNull final Object content);

        void file(@NonNull final String tag, @NonNull final Object content);

        void json(@NonNull final Object content);

        void json(@NonNull final String tag, @NonNull final Object content);

        void xml(@NonNull final String content);

        void xml(@NonNull final String tag, @NonNull final String content);

        void printErrStackTrace(@NonNull String tag, @NonNull Throwable tr, @NonNull final Object... obj);
    }

    private static LogDelegate DELEGETE = null;

    public static void setDelegate(@NonNull LogDelegate delegete) {
        DELEGETE = delegete;
    }

    public static void d(@NonNull final Object... contents) {
        if (DELEGETE != null) {
            DELEGETE.d(contents);
        }
    }

    public static void dTag(@NonNull final String tag, @NonNull final Object... contents) {
        if (DELEGETE != null) {
            DELEGETE.dTag(tag, contents);
        }
    }

    public static void i(@NonNull final Object... contents) {
        if (DELEGETE != null) {
            DELEGETE.i(contents);
        }
    }

    public static void iTag(@NonNull final String tag, @NonNull final Object... contents) {
        if (DELEGETE != null) {
            DELEGETE.iTag(tag, contents);
        }
    }

    public static void w(@NonNull final Object... contents) {
        if (DELEGETE != null) {
            DELEGETE.w(contents);
        }
    }

    public static void wTag(@NonNull final String tag, @NonNull final Object... contents) {
        if (DELEGETE != null) {
            DELEGETE.wTag(tag, contents);
        }
    }

    public static void e(@NonNull final Object... contents) {
        if (DELEGETE != null) {
            DELEGETE.e(contents);
        }
    }

    public static void eTag(@NonNull final String tag, @NonNull final Object... contents) {
        if (DELEGETE != null) {
            DELEGETE.eTag(tag, contents);
        }
    }


    public static void file(@NonNull final Object content) {
        if (DELEGETE != null) {
            DELEGETE.file(content);
        }
    }

    public static void file(@NonNull final String tag, @NonNull final Object content) {
        if (DELEGETE != null) {
            DELEGETE.file(tag, content);
        }
    }

    public static void json(@NonNull final Object content) {
        if (DELEGETE != null) {
            DELEGETE.json(content);
        }
    }

    public static void json(@NonNull final String tag, @NonNull final Object content) {
        if (DELEGETE != null) {
            DELEGETE.json(tag, content);
        }
    }

    public static void xml(@NonNull final String content) {
        if (DELEGETE != null) {
            DELEGETE.xml(content);
        }
    }

    public static void xml(@NonNull final String tag, @NonNull final String content) {
        if (DELEGETE != null) {
            DELEGETE.xml(tag, content);
        }
    }

    public static void printErrStackTrace(@NonNull final String tag, @NonNull final Throwable throwable,
            final Object... obj) {
        if (DELEGETE != null) {
            DELEGETE.printErrStackTrace(tag, throwable, obj);
        }
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                LogXWrite.writeToLocal(throwable, tag, obj);
                return null;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


}
