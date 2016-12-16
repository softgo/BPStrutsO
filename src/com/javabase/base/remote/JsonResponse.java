package com.javabase.base.remote;

import java.util.HashMap;

/**
 * json 返回.
 * 
 * @author bruce
 * 
 */
public class JsonResponse {

	private JsonResponseHeader head;

	private Object body;

	public JsonResponse() {
		this(JsonResponseHeader.STATUS_OK, null);
	}

	public JsonResponse(int status, Object body) {
		if (head == null) {
			head = new JsonResponseHeader(status);
		}
		else {
			head.setStatus(status);
		}
		if (body == null) {
			body = new HashMap();
		}
		this.body = body;
	}

	public JsonResponse(int status) {
		this(status, null);
	}

	public JsonResponse(Object body) {
		this(JsonResponseHeader.STATUS_OK, body);
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public JsonResponseHeader getHead() {
		return head;
	}

	public void setHead(JsonResponseHeader head) {
		this.head = head;
	}
}
