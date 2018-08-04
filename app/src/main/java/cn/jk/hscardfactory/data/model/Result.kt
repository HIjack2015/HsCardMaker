package cn.jk.hscardfactory.data.model

class Result private constructor(var code: Int, var msg: String?, var data: Any?) {

    val isSuccess: Boolean
        get() = code == SUCCESS

    companion object {
        private val SUCCESS = 0
        private val ARGU_ERROR = 1
        val FAIL = 2

        fun getSuccessResult(message: String): Result {
            return Result(SUCCESS, message, null)
        }

        fun getSuccessResultWithData(data: Any): Result {
            return Result(SUCCESS, "success", data)
        }

        fun getFailResultWithMessage(message: String): Result {
            return Result(ARGU_ERROR, message, null)
        }

        val successResult: Result
            get() = Result(SUCCESS, "success", null)
    }
}
