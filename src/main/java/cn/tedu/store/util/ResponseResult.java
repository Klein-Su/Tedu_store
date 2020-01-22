package cn.tedu.store.util;

import java.io.Serializable;

/**
 * 服務器端向客戶端響應結果的類型
 * @author Klein
 * @param <T> 服務器端向客戶端響應的數據的類型
 */
//ResponseResult<T,A,Hello>
//看類使用多少個T型(泛型佔位符)，就要類名稱後聲明多少個T型參數，但不得使用保留字
public class ResponseResult<T> implements Serializable {
	
	private static final long serialVersionUID = -1626793180717240861L;
	
	private Integer state;
	private String message;
	private T data;
	//private A data2;
	//private Hello data3;
	
	//GET/SET方法，Serializable
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	//toString方法，非必要
	@Override
	public String toString() {
		return "ResponseResult [state=" + state + ", message=" + message + ", data=" + data + "]";
	}
	
	//構造方法
	//無參數構造方法
	public ResponseResult() {
		super();
	}
	//構造方法(int state)：表示操作成功時
	public ResponseResult(Integer state) {
		super();
		setState(state);
	}
	//構造方法(int state, String message)：表示操作失敗時
	public ResponseResult(Integer state, String message) {
		this(state);
		setMessage(message);
	}
	//構造方法(int state, Exception e)：表示操作失敗時
	public ResponseResult(Integer state, Exception e) {
		this(state, e.getMessage());
	}
	//構造方法(int state, T data)：表示用戶請求數據成功時
	public ResponseResult(Integer state, T data) {
		this(state);
		setData(data);
	}
}




