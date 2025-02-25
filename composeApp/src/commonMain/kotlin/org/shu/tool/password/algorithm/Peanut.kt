package org.shu.keytool.base.algorithm

import java.net.URL

class PeanutEncryptionAlgorithm:IEncryptionAlgorithm{
    override fun encrypt(websiteUrl: String, privateKey: String): String {
        val password = StringBuilder()
        password.append(privateKey)

        val host = URL(websiteUrl).host
        if (host.isNotBlank()){
            val domain = host.split('.')
            var number = 0
            for (s in domain) {
                number = charToNumber(s[0])
                password.append(number)
            }
            password.append(StrBoox[(number + privateKey.length) % StrBoox.length])
            password.append(domain[1].first().uppercase())
            password.append(domain[1].last())
        }

        return password.toString()
    }

}

val Codebook = arrayOf(
    "abcABC",
    "defDEF",
    "ghiGHI",
    "jklJKL",
    "mnoMNO",
    "pqrsPQRS",
    "tuvTUV",
    "wxyzWXYZ"
)
val StrBoox = ")!@#$%^&*("
fun charToNumber(char: Char):Int {
   return when (char) {
       in '0'..'9' -> {
           1
       }
       in 'a'..'z', in 'A'..'Z' -> {
           Codebook.indexOfFirst { it.contains(char) } + 2
       }
       else -> {
           0
       }
   }
}