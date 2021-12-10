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

package com.hmos.compat.utils;

import ohos.agp.components.AttrSet;
import ohos.agp.components.element.Element;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

/**
 * Utils for Attributes.
 */
public class AttrUtils {
    private static final HiLogLabel HILOG_LABEL = new HiLogLabel(0, 0, "attrutils");

    private AttrUtils() {
    }

    /**
     * Function to get int value from attribute.
     *
     * @param attrs AttrSet attrs
     * @param name String name
     * @param defaultValue int defaultValue
     * @return value
     */
    public static int getIntFromAttr(AttrSet attrs, String name, int defaultValue) {
        int value = defaultValue;
        try {
            if (attrs.getAttr(name).isPresent() && attrs.getAttr(name).get() != null) {
                value = attrs.getAttr(name).get().getIntegerValue();
            }
        } catch (Exception e) {
            HiLog.error(HILOG_LABEL, "exception in getIntFromAttr");
        }
        return value;
    }

    /**
     * Function to get float value from attribute.
     *
     * @param attrs Attribute set
     * @param name String name
     * @param defaultValue default value
     * @return value
     *
     */
    public static float getFloatFromAttr(AttrSet attrs, String name, float defaultValue) {
        float value = defaultValue;
        try {
            if (attrs.getAttr(name).isPresent() && attrs.getAttr(name).get() != null) {
                value = attrs.getAttr(name).get().getFloatValue();
            }
        } catch (Exception e) {
            HiLog.error(HILOG_LABEL, "exception in getFloatFromAttr");
        }
        return value;
    }

    /**
     * Function to get boolean value from attribute.
     *
     * @param attrs Attribute set
     * @param name String name
     * @param defaultValue default value
     * @return value
     *
     */
    public static boolean getBooleanFromAttr(AttrSet attrs, String name, boolean defaultValue) {
        boolean value = defaultValue;
        try {
            if (attrs.getAttr(name).isPresent() && attrs.getAttr(name).get() != null) {
                value = attrs.getAttr(name).get().getBoolValue();
            }
        } catch (Exception e) {
            HiLog.error(HILOG_LABEL, "exception in getBooleanFromAttr");
        }
        return value;
    }

    /**
     * Function to get Long value from attribute.
     *
     * @param attrs Attribute set
     * @param name String name
     * @param defaultValue default value
     * @return value
     *
     */
    public static long getLongFromAttr(AttrSet attrs, String name, long defaultValue) {
        long value = defaultValue;
        try {
            if (attrs.getAttr(name).isPresent() && attrs.getAttr(name).get() != null) {
                value = attrs.getAttr(name).get().getLongValue();
            }
        } catch (Exception e) {
            HiLog.error(HILOG_LABEL, "exception in getLongFromAttr");
        }
        return value;
    }

    /**
     * Function to get Color value from attribute.
     *
     * @param attrs Attribute set
     * @param name String name
     * @param defaultValue default value
     * @return value
     *
     */
    public static int getColorFromAttr(AttrSet attrs, String name, int defaultValue) {
        int value = defaultValue;
        try {
            if (attrs.getAttr(name).isPresent() && attrs.getAttr(name).get() != null) {
                value = attrs.getAttr(name).get().getColorValue().getValue();
            }
        } catch (Exception e) {
            HiLog.error(HILOG_LABEL, "exception in getColorFromAttr");
        }
        return value;
    }

    /**
     * Function to get Dimension value from attribute.
     *
     * @param attrs Attribute set
     * @param name String name
     * @param defaultValue default value
     * @return value
     *
     */
    public static int getDimensionFromAttr(AttrSet attrs, String name, int defaultValue) {
        int value = defaultValue;
        try {
            if (attrs.getAttr(name).isPresent() && attrs.getAttr(name).get() != null) {
                value = attrs.getAttr(name).get().getDimensionValue();
            }
        } catch (Exception e) {
            HiLog.error(HILOG_LABEL, "exception in getDimensionFromAttr: int defaultValue");
        }
        return value;
    }

    /**
     * Function to get Dimension value from attribute.
     *
     * @param attrs Attribute set
     * @param name String name
     * @param defaultValue default value
     * @return value
     */
    public static int getDimensionFromAttr(AttrSet attrs, String name, float defaultValue) {
        float value = defaultValue;
        try {
            if (attrs.getAttr(name).isPresent() && attrs.getAttr(name).get() != null) {
                value = attrs.getAttr(name).get().getDimensionValue();
            }
        } catch (Exception e) {
            HiLog.error(HILOG_LABEL, "exception in getDimensionFromAttr: float defaultValue");
        }
        return (int) value;
    }

    /**
     * Function to get string value from attribute.
     *
     * @param attrs Attribute set
     * @param name String name
     * @param defaultValue default value
     * @return value
     *
     */
    public static String getStringFromAttr(AttrSet attrs, String name, String defaultValue) {
        String value = defaultValue;
        try {
            if (attrs.getAttr(name).isPresent() && attrs.getAttr(name).get() != null) {
                value = attrs.getAttr(name).get().getStringValue();
            }
        } catch (Exception e) {
            HiLog.error(HILOG_LABEL, "exception in getStringFromAttr: String defaultValue");
        }
        return value;
    }

    /**
     * Function to get string value from attribute.
     *
     * @param attrs Attribute set
     * @param name String name
     * @return value
     *
     */
    public static String getStringFromAttr(AttrSet attrs, String name) {
        String value = "";
        try {
            if (attrs.getAttr(name).isPresent() && attrs.getAttr(name).get() != null) {
                value = attrs.getAttr(name).get().getStringValue();
            }
        } catch (Exception e) {
            HiLog.error(HILOG_LABEL, "exception in getStringFromAttr");
        }
        return value;
    }

    /**
     * Function to get character sequence value from attribute.
     *
     * @param attrs Attribute set
     * @param name String name
     * @return value
     */
    public static CharSequence getCharSequenceFromAttr(AttrSet attrs, String name) {
        CharSequence value = "";
        try {
            if (attrs.getAttr(name).isPresent() && attrs.getAttr(name).get() != null) {
                value = attrs.getAttr(name).get().getStringValue();
            }
        } catch (Exception e) {
            HiLog.error(HILOG_LABEL, "exception in getCharSequenceFromAttr");
        }
        return value;
    }

    /**
     * Function to get Element value from attribute.
     *
     * @param attrs Attribute set
     * @param name String name
     * @return value
     *
     */
    public static Element getElementFromAttr(AttrSet attrs, String name) {
        Element value = null;
        try {
            if (attrs.getAttr(name).isPresent() && attrs.getAttr(name).get() != null) {
                value = attrs.getAttr(name).get().getElement();
            }
        } catch (Exception e) {
            HiLog.error(HILOG_LABEL, "exception in getElementFromAttr");
        }
        return value;
    }
}