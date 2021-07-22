let baseUrl = "http://192.168.5.100:8088/expense-business"; //地址1
let bpmnUrl = "http://192.168.5.100:8088/expense-bpm-server";
let rootUrl = "http://192.168.5.100:8088";
// let baseUrl2 = "192.168.1.6:8080"; //地址2

export const baseURL = baseUrl;

export const basicUrl = rootUrl;
// export const baseUrl = process.env.NODE_ENV === 'development';

export const timeout = 5000;

export default {
	baseURL,timeout,bpmnUrl,basicUrl
}
