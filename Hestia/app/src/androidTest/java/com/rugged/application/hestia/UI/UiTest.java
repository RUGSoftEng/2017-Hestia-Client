package com.rugged.application.hestia.UI;

import hestia.UI.HestiaApplication;

public class UiTest {
    public final String PACKAGE_NAME = "com.rugged.application.hestia";

    public String getStr(int id) {
        return HestiaApplication.getContext().getString(id);
    }
}
