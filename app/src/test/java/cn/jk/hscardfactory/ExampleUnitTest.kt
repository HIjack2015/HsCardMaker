package cn.jk.hscardfactory

import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val originPath = "m 72.083772,354.1818 c 0,0 138.390898,-67.03301 268.195498,-1.01015"
        val regex = Regex("-*\\d+\\.\\d+")
        val compatPath = regex.replace(originPath) { m ->
            (m.value.toFloat() * 2.2f).toInt().toString()
        }
        System.out.print(compatPath)
    }
}
