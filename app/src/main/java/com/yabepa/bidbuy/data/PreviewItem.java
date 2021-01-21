package com.yabepa.bidbuy.data;

public class PreviewItem implements IPreviewItem{
    public String key;
    public String value;

    public PreviewItem(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }
}
