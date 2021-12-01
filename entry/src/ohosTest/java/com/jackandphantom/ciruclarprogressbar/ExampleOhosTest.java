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
package com.jackandphantom.ciruclarprogressbar;

import com.jackandphantom.circularprogressbar.CircleProgressbar;
import ohos.aafwk.ability.delegation.AbilityDelegatorRegistry;
import ohos.agp.components.Attr;
import ohos.agp.components.AttrSet;
import ohos.agp.utils.Color;
import ohos.app.Context;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class ExampleOhosTest {
    private AttrSet attrSet;
    private Context context;
    private CircleProgressbar circleProgressbar;

    @Before
    public void setUp() {
        context = AbilityDelegatorRegistry.getAbilityDelegator().getAppContext();
        attrSet = new AttrSet() {
            @Override
            public Optional<String> getStyle() {
                return Optional.empty();
            }

            @Override
            public int getLength() {
                return 0;
            }

            @Override
            public Optional<Attr> getAttr(int i) {
                return Optional.empty();
            }

            @Override
            public Optional<Attr> getAttr(String s) {
                return Optional.empty();
            }
        };
    }

    @Test
    public void testBundleName() {
        final String actualBundleName = AbilityDelegatorRegistry.getArguments().getTestBundleName();
        assertEquals("com.jackandphantom.ciruclarprogressbar", actualBundleName);
    }

    @Test
    public void testClockwise() {
        circleProgressbar = new CircleProgressbar(context,attrSet);
        circleProgressbar.setClockwise(true);
        assertEquals(true,circleProgressbar.isClockWise());
    }

    @Test
    public void testMaxProgress() {
        circleProgressbar = new CircleProgressbar(context,attrSet);
        circleProgressbar.setMaxProgress(2f);
        assertEquals(String.valueOf(2f),String.valueOf(circleProgressbar.getMaxProgress()));
    }

    @Test
    public void testBackgroundProgressWidth() {
        circleProgressbar = new CircleProgressbar(context,attrSet);
        circleProgressbar.setBackgroundProgressWidth(10);
        assertEquals(10,circleProgressbar.getBackgroundProgressWidth());
    }

    @Test
    public void testForegroundProgressWidth() {
        circleProgressbar = new CircleProgressbar(context,attrSet);
        circleProgressbar.setForegroundProgressWidth(20);
        assertEquals(20,circleProgressbar.getForegroundProgressWidth());
    }

    @Test
    public void testBackgroundProgressColor() {
        circleProgressbar = new CircleProgressbar(context,attrSet);
        circleProgressbar.setBackgroundProgressColor(Color.BLACK.getValue());
        assertEquals(Color.BLACK.getValue(),circleProgressbar.getBackgroundProgressColor());
    }

    @Test
    public void testForegroundProgressColor() {
        circleProgressbar = new CircleProgressbar(context,attrSet);
        circleProgressbar.setForegroundProgressColor(Color.BLUE.getValue());
        assertEquals(Color.BLUE.getValue(),circleProgressbar.getForegroundProgressColor());
    }

    @Test
    public void testProgress() {
        circleProgressbar = new CircleProgressbar(context,attrSet);
        circleProgressbar.setProgress(5f);
        assertEquals(String.valueOf(5f),String.valueOf(circleProgressbar.getProgress()));
    }

    @Test
    public void testEnabledTouch() {
        circleProgressbar = new CircleProgressbar(context,attrSet);
        circleProgressbar.enabledTouch(true);
        assertEquals(true,circleProgressbar.isTouchEnabled());
    }
}