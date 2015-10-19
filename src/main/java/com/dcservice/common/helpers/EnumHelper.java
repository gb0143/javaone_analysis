package com.dcservice.common.helpers;

import com.dcservice.all.base.BaseBaseClass;

public class EnumHelper  extends BaseBaseClass  {
	public static <E extends Enum<E>> String toStringFormatter(E obj) {
		if (obj == null) {
			return null;
		}

		return obj.getClass().getSimpleName().substring(0, 1).toLowerCase()
				+ obj.getClass().getSimpleName().substring(1) + obj.name();
	}
}
