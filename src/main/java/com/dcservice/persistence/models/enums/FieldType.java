package com.dcservice.persistence.models.enums;

import com.dcservice.common.helpers.EnumHelper;
import com.dcservice.common.helpers.ResourcesHelper;

public enum FieldType {
	SINGLE_LINE_TEXT,
	TEXTAREA,
	RADIO_BUTTON,
	CHECKBOX,
	COMBOBOX,
	DATE,
	MISTIC_TYPE;
	
	@Override
    public String toString()
    {
        return ResourcesHelper.getEnum(EnumHelper.toStringFormatter(this));
    }
}
