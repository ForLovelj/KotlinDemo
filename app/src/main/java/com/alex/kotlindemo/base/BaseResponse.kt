package com.alex.kotlindemo.base

/**
 * Created by dth
 * Des:
 * Date: 2017/6/8.
 */

class BaseResponse<DataType> {

    /**
     * 通用返回值属性
     */
    var code: Int = 0
    /**
     * 通用返回信息。
     */
    var msg: String? = null
    /**
     * 具体的内容。
     */
    var data: DataType? = null

    override fun toString(): String {
        return "BaseResponse{" +
                "code=" + code +
                ", msg='" + msg + '\''.toString() +
                ", data=" + data +
                '}'.toString()
    }

    companion object {

        val RESULT_CODE_SUCCESS = 0
        val RESULT_CODE_TOKEN_EXPIRED = 401
    }
}
