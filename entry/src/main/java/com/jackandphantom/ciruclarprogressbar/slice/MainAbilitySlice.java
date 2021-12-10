/*
 * Copyright (C) 2020-21 Application Library Engineering Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jackandphantom.ciruclarprogressbar.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import com.jackandphantom.circularprogressbar.CircleProgressbar;
import com.jackandphantom.ciruclarprogressbar.ResourceTable;

/**
 * Sample app to test the CircleProgressBar library functionality.
 */
public class MainAbilitySlice extends AbilitySlice {
    private static final HiLogLabel HI_LOG_LABEL = new HiLogLabel(0, 0, "MainAbilitySlice");

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        CircleProgressbar cpb = (CircleProgressbar) findComponentById(ResourceTable.Id_circleprogressbar);
        Thread t = new Thread() {
            @Override
            public void run() {
                while (cpb.getProgress() < 100) {
                    try {
                        getContext().getUITaskDispatcher()
                                .asyncDispatch(new Runnable() {
                                    float oldProgress = 0;
                                    public void run() {
                                        float value = cpb.getProgress();
                                        value += 5;
                                        if (value != oldProgress) {
                                            oldProgress = value;
                                            cpb.setProgress(value);
                                        }
                                    }
                                });
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        HiLog.error(HI_LOG_LABEL, "InterruptedException in onStart method" + e);
                        Thread.currentThread().interrupt();
                    }
                }
            }
        };
        t.start();
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}