package com.trust.demo.gsonparsing.entity;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.google.gson.Gson;

public class Common<T> implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -3440061414071692254L;

	/**
	 * ?????
	 */
	private String status;
	private String message;
	
	public String getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return "CommonJson [status=" + status + ", message=" + message
				+ ", data=" + data + "]";
	}

	public Common(String status, String message, T data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public Common() {
		super();
	}

	public Common(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * ????
	 */
	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	public static Common fromJson(String json, Class clazz) {
		Gson gson = new Gson();
		Type objectType = type(Common.class, clazz);
		return gson.fromJson(json, objectType);
	}

	public String toJson(Class<T> clazz) {
		Gson gson = new Gson();
		Type objectType = type(Common.class, clazz);
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