package com.trust.demo.code.gsonparsing.entity;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;

public class CommonList<T> implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -369558847578246550L;

	/**
	 * �Ƿ�ɹ�
	 */
	private Boolean success;

	/**
	 * ����
	 */
	private List<T> data;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public static CommonList fromJson(String json, Class clazz) {
		Gson gson = new Gson();
		Type objectType = type(CommonList.class, clazz);
		return gson.fromJson(json, objectType);
	}

	public String toJson(Class<T> clazz) {
		Gson gson = new Gson();
		Type objectType = type(CommonList.class, clazz);
		return gson.toJson(this, objectType);
	}

	static ParameterizedType type(final Class raw, final Type... args) {
		return new ParameterizedType() {
			public Type getRawType() {
				return raw;
			}

			public Type[] getActualTypeArguments() {
				return args;
			}

			public Type getOwnerType() {
				return null;
			}
		};
	}

}
